package com.lsc.events.combat;

import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.cap.CapabilityPlayerStats;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.capabilities.implementation.Stats;
import com.lsc.init.ModDamageSources;
import com.lsc.items.base.ItemAdvancedMelee;
import com.lsc.loot.Attribute;
import com.lsc.loot.Rarity;
import com.lsc.player.DamageType;
import com.lsc.player.DamageUtils;
import com.lsc.player.WeaponUtils;
import com.lsc.util.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventLivingHurtAttack 
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingHurt(LivingHurtEvent event)
	{
		/*
		 * Player attacks a monster OR another player
		 */
		if (event.getSource().getTrueSource() instanceof EntityPlayer && !event.getSource().getTrueSource().getEntityWorld().isRemote)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			//EntityLivingBase enemy = event.getEntityLiving();
			ItemStack stack = player.inventory.getCurrentItem();
			PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			Stats stats = (Stats) player.getCapability(CapabilityPlayerStats.STATS, null);
			
			if (playerInfo != null && stats != null && stack != null)
			{
				if (stack.getItem() instanceof ItemSword)
				{
					playerMeleeAttack(event, stack, player, playerInfo, stats);
				}
				
				if (stack.getItem() instanceof ItemBow && event.getSource().getImmediateSource() instanceof EntityArrow)
				{
					EntityArrow projectile = (EntityArrow) event.getSource().getImmediateSource();
					playerRangedAttack(event, stack, player, projectile, playerInfo, stats);
				}
			}
		}
		
		// gets called if the Player is attacked by another Mob OR Player OR specified Damage Source
		if (event.getEntityLiving() instanceof EntityPlayer && (event.getSource().getTrueSource() instanceof EntityLivingBase || event.getSource() == ModDamageSources.FROST || event.getSource() == ModDamageSources.LIGHTNING || event.getSource() == ModDamageSources.POISON))
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (playerInfo != null)
			{
				for (ItemStack stack : player.inventory.armorInventory)
				{
					NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
					
					if (playerInfo.getPlayerLevel() >= nbt.getInteger("Level"))
					{
						if (event.getSource().getTrueSource() instanceof EntityLivingBase)
						{
							//EntityLivingBase enemy = (EntityLivingBase) event.getSource().getTrueSource();
							
							// do stuff?
						}
						else // apply resistances to custom Damage Sources.
						{	
							if (Attribute.FROST_RESIST.hasAttribute(nbt) && event.getSource() == ModDamageSources.FROST) event.setAmount((float) (event.getAmount() - (event.getAmount() * Attribute.FROST_RESIST.getAmount(nbt))));
							if (Attribute.LIGHTNING_RESIST.hasAttribute(nbt) && event.getSource() == ModDamageSources.LIGHTNING) event.setAmount((float) (event.getAmount() - (event.getAmount() * Attribute.LIGHTNING_RESIST.getAmount(nbt))));
							if (Attribute.POISON_RESIST.hasAttribute(nbt) && event.getSource() == ModDamageSources.POISON) event.setAmount((float) (event.getAmount() - (event.getAmount() * Attribute.POISON_RESIST.getAmount(nbt))));
						}
						
						if (Attribute.DURABLE.hasAttribute(nbt) && Math.random() < Attribute.DURABLE.getAmount(nbt)) stack.setItemDamage(stack.getItemDamage() + 1);
					}
					else
					{
						stack.damageItem((int) (stack.getMaxDamage() * 0.20), player);
						player.sendMessage(new TextComponentString(TextFormatting.RED + "WARNING: You are using a high-leveled item. It will be useless and will take significantly more damage if it is not removed."));
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingAttack(LivingAttackEvent event)
	{
		/*
		 * Player attacks a monster OR another player
		 */
		if (event.getSource().getTrueSource() instanceof EntityPlayer && !event.getSource().getTrueSource().getEntityWorld().isRemote)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			EntityLivingBase enemy = event.getEntityLiving();
			ItemStack stack = player.inventory.getCurrentItem();
			PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (playerInfo != null && stack != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemBow) && !(stack.getItem() instanceof ItemAdvancedMelee))
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (playerInfo.getPlayerLevel() >= nbt.getInteger("Level"))
				{
					WeaponUtils.useWeaponAttributes(event.getAmount(), player, enemy, stack, nbt);
				}
			}
		}
	}
	
	private static void playerMeleeAttack(LivingHurtEvent event, ItemStack stack, EntityPlayer player, PlayerInformation playerInfo, Stats stats)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
		{
			if (playerInfo.getPlayerLevel() < nbt.getInteger("Level")) 
			{
				event.setAmount(0);
				stack.damageItem((int) (stack.getMaxDamage() * 0.20), player);
				player.sendMessage(new TextComponentString(TextFormatting.RED + "WARNING: You are using a high-leveled item. It will be useless and will take significantly more damage if it is not removed."));
			}
			else
			{
				// set the true amount of damage.
				double trueDamage = Math.random() * (nbt.getInteger("MaxDamage") - nbt.getInteger("MinDamage")) + nbt.getInteger("MinDamage");
				trueDamage = DamageUtils.applyDamageModifiers(playerInfo, trueDamage, DamageType.PHYSICAL_MELEE);
				trueDamage = DamageUtils.applyCriticalModifier(stats, trueDamage, nbt);
				
				event.setAmount((float) trueDamage);
			}
		}
	}
	
	private static void playerRangedAttack(LivingHurtEvent event, ItemStack stack, EntityPlayer player, EntityArrow projectile, PlayerInformation playerInfo, Stats stats)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
		{
			if (playerInfo.getPlayerLevel() < nbt.getInteger("Level")) 
			{
				event.setAmount(0);
				stack.damageItem((int) (stack.getMaxDamage() * 0.20), player);
				player.sendMessage(new TextComponentString(TextFormatting.RED + "WARNING: You are using a high-leveled item. It will be useless and will take significantly more damage if it is not removed."));
			}
			else
			{
				// set the true amount of damage.
				double trueDamage = Math.random() * (nbt.getInteger("MaxDamage") - nbt.getInteger("MinDamage")) + nbt.getInteger("MinDamage");
				trueDamage = DamageUtils.applyDamageModifiers(playerInfo, trueDamage, DamageType.PHYSICAL_RANGED);
				trueDamage = DamageUtils.applyCriticalModifier(stats, trueDamage, nbt);
				
				event.setAmount((float) trueDamage);
			}
		}
	}
}

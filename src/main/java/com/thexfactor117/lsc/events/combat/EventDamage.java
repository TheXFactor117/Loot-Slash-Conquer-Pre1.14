package com.thexfactor117.lsc.events.combat;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.Attribute;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.player.WeaponUtils;
import com.thexfactor117.lsc.util.DamageUtil;
import com.thexfactor117.lsc.util.ItemUtil;
import com.thexfactor117.lsc.util.PlayerUtil;
import com.thexfactor117.lsc.util.misc.LSCDamageSource;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
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
public class EventDamage
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingHurt(LivingHurtEvent event)
	{
		if (!event.getEntityLiving().world.isRemote)
		{
			/* 
			 * Player attacks Player/Monster
			 * -----------------------------
			 * - damage randomized between min/max.
			 * - apply damage modifiers (stat modifiers, critical modifiers, ANYTHING PERTAINING TO THE WEAPON OR ATTACKING PLAYER).
			 * - use weapon attributes
			 * - set the amount and finish the event.
			 */
			if (event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource() instanceof LSCDamageSource) && !event.getSource().isFireDamage())
			{
				EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
				EntityLivingBase enemy = event.getEntityLiving();
				ItemStack weapon = player.getHeldItem(player.getActiveHand());
				LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
				
				if (cap != null && weapon != ItemStack.EMPTY)
				{
					// melee attack
					if (weapon.getItem() instanceof ItemSword)
					{
						playerMeleeAttack(event, player, enemy, weapon, cap);
					}
					// TODO: ranged attack
					else if (weapon.getItem() instanceof ItemBow && event.getSource().getImmediateSource() instanceof EntityArrow)
					{
						playerRangedAttack(event, player, enemy, weapon, cap);
					}
					
					// NOTE: magical attacks are handled inside its own class.
				}
			}
			
			/*
			 * Player is attacked by Player/Monster
			 * ------------------------------------
			 * - damage passed in has already been modified if coming from a player (if it comes from a monster, it is already ready too).
			 * - apply damage reductions (armor reductions, elemental reductions, ANYTHING PERTAINING TO ARMOR OR PLAYER STATS).
			 * - set the amount and finish the event.
			 */
			if (event.getEntityLiving() instanceof EntityPlayer && (event.getSource().getTrueSource() instanceof EntityLivingBase || (event.getSource() instanceof LSCDamageSource) || event.getSource().isFireDamage()))
			{
				EntityPlayer player = (EntityPlayer) event.getEntityLiving();
				//EntityLivingBase enemy = (EntityLivingBase) event.getSource().getTrueSource();
				LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
				
				if (cap != null)
				{
					// check if the damage is elemental damage
					if ((event.getSource() instanceof LSCDamageSource || event.getSource().isFireDamage()) && (event.getSource() instanceof LSCDamageSource && !((LSCDamageSource) event.getSource()).isChainedDamage()))
					{
						double damage = DamageUtil.applyElementalResistance(event.getAmount(), (LSCDamageSource) event.getSource(), player);
						event.setAmount((float) damage);					
					}
					// if it isn't elemental damage, let's apply armor reductions (so elemental damage bypasses armor).
					else
					{
						double damage = DamageUtil.applyArmorReductions(event.getAmount(), player, cap);
						event.setAmount((float) damage);
						
						// loop through all armor pieces to use the durability attribute
						for (ItemStack stack : player.getArmorInventoryList())
						{
							NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
							
							if (Attribute.DURABLE.hasAttribute(nbt) && Math.random() < Attribute.DURABLE.getAmount(nbt)) stack.setItemDamage(stack.getItemDamage() - 1);
						}
					}
				}
			}
			
			/*
			 * Monster is attacked by Player
			 * -----------------------------
			 * - damage passed in has been modified already by the Player.
			 * - check to make sure if the monster has armor, the reductions are happening properly.
			 * - apply elemental modifiers if the monster has any.
			 * 
			 * (because elemental modifiers for monsters haven't been added, this probably will do nothing for now)
			 */
			if (event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getEntityLiving() instanceof EntityPlayer)) 
			{
				
			}
		}
	}
	
	// working properly
	private static void playerMeleeAttack(LivingHurtEvent event, EntityPlayer player, EntityLivingBase enemy, ItemStack weapon, LSCPlayerCapability cap)
	{
		if (ItemUtil.getItemRarity(weapon) != Rarity.DEFAULT)
		{
			if (cap.getPlayerLevel() < ItemUtil.getItemLevel(weapon))
			{
				underLevelAttack(event, player, weapon);
			}
			else
			{
				// set the true amount of damage.
				double trueDamage = ItemUtil.getItemDamage(weapon);
				trueDamage = DamageUtil.applyDamageModifiers(cap, trueDamage, DamageUtil.DamageType.PHYSICAL_MELEE);
				trueDamage = DamageUtil.applyCriticalModifier(cap, trueDamage);

				//WeaponUtils.useWeaponAttributes((float) trueDamage, player, enemy, weapon, nbt);
				
				event.setAmount((float) trueDamage);
			}
		}
	}
	
	// working properly
	private static void playerRangedAttack(LivingHurtEvent event, EntityPlayer player, EntityLivingBase enemy, ItemStack weapon, LSCPlayerCapability cap) 
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(weapon);
		
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
		{
			if (cap.getPlayerLevel() < nbt.getInteger("Level"))
			{
				underLevelAttack(event, player, weapon);
			}
			else
			{
				// set the true amount of damage.
				double trueDamage = Math.random() * (nbt.getInteger("MaxDamage") - nbt.getInteger("MinDamage")) + nbt.getInteger("MinDamage");
				trueDamage = DamageUtil.applyDamageModifiers(cap, trueDamage, DamageUtil.DamageType.PHYSICAL_RANGED);
				trueDamage = DamageUtil.applyCriticalModifier(cap, trueDamage);
				
				event.setAmount((float) trueDamage);
				WeaponUtils.useWeaponAttributes(event.getAmount(), player, enemy, weapon, nbt);
			}
		}
	}
	
	/**
	 * When using a high level weapon, it will do zero damage and take a lot durability in the process.
	 * @param event
	 * @param player
	 * @param weapon
	 */
	private static void underLevelAttack(LivingHurtEvent event, EntityPlayer player, ItemStack weapon)
	{
		event.setAmount(0);
		
		if (Configs.damageHighLeveledEquipment)
		{
			weapon.damageItem((int) (weapon.getMaxDamage() * 0.20), player);
		}
		
		player.sendMessage(new TextComponentString(TextFormatting.RED + "WARNING: You are using a high-leveled item. It will be useless and will take significantly more damage if it is not removed."));
	}
}

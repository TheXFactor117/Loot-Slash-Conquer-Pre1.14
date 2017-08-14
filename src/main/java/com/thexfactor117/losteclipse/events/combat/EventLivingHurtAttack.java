package com.thexfactor117.losteclipse.events.combat;

import java.util.Iterator;
import java.util.List;

import com.thexfactor117.losteclipse.api.Rarity;
import com.thexfactor117.losteclipse.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerinfo.PlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerstats.CapabilityPlayerStats;
import com.thexfactor117.losteclipse.capabilities.playerstats.Stats;
import com.thexfactor117.losteclipse.init.ModDamageSources;
import com.thexfactor117.losteclipse.items.melee.ItemLEAdvancedMelee;
import com.thexfactor117.losteclipse.stats.PlayerStatHelper;
import com.thexfactor117.losteclipse.stats.attributes.ArmorAttribute;
import com.thexfactor117.losteclipse.stats.attributes.WeaponAttribute;
import com.thexfactor117.losteclipse.util.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventLivingHurtAttack 
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingHurt(LivingHurtEvent event)
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
			
			if (stack != null && stack.getItem() instanceof ItemSword && playerInfo != null)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
				{
					if (playerInfo.getPlayerLevel() < nbt.getInteger("Level")) event.setAmount(0);
					else
					{
						// set the true amount of damage.
						double trueDamage = Math.random() * (nbt.getInteger("MaxDamage") - nbt.getInteger("MinDamage")) + nbt.getInteger("MinDamage");
						event.setAmount((float) (trueDamage + (PlayerStatHelper.ATTACK_DAMAGE_MULTIPLIER * playerInfo.getTotalStrength())));
					}
				}
			}
		}
		
		// gets called if the Player is attacked by another Mob OR Player OR specified Damage Source
		if (event.getEntityLiving() instanceof EntityPlayer && (event.getSource().getTrueSource() instanceof EntityLivingBase || event.getSource() == ModDamageSources.FROST || event.getSource() == ModDamageSources.LIGHTNING || event.getSource() == ModDamageSources.POISON))
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			
			for (ItemStack stack : player.inventory.armorInventory)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (event.getSource().getTrueSource() instanceof EntityLivingBase)
				{
					//EntityLivingBase enemy = (EntityLivingBase) event.getSource().getTrueSource();
					
					// do stuff?
				}
				else // apply resistances to custom Damage Sources.
				{	
					if (ArmorAttribute.FROST_RESIST.hasAttribute(nbt) && event.getSource() == ModDamageSources.FROST) event.setAmount((float) (event.getAmount() - (event.getAmount() * ArmorAttribute.FROST_RESIST.getAmount(nbt))));
					if (ArmorAttribute.LIGHTNING_RESIST.hasAttribute(nbt) && event.getSource() == ModDamageSources.LIGHTNING) event.setAmount((float) (event.getAmount() - (event.getAmount() * ArmorAttribute.LIGHTNING_RESIST.getAmount(nbt))));
					if (ArmorAttribute.POISON_RESIST.hasAttribute(nbt) && event.getSource() == ModDamageSources.POISON) event.setAmount((float) (event.getAmount() - (event.getAmount() * ArmorAttribute.POISON_RESIST.getAmount(nbt))));
				}
				
				if (ArmorAttribute.DURABLE.hasAttribute(nbt) && Math.random() < ArmorAttribute.DURABLE.getAmount(nbt)) stack.setItemDamage(stack.getItemDamage() + 1);
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
			
			if (playerInfo != null && stack != null && stack.getItem() instanceof ItemSword && !(stack.getItem() instanceof ItemLEAdvancedMelee))
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (playerInfo.getPlayerLevel() >= nbt.getInteger("Level"))
				{
					useWeaponAttributes(event.getAmount(), player, enemy, stack, nbt);
				}
			}
		}
	}
	
	/** Called to use the current stack's attributes. Called from LivingAttackEvent and projectiles. */
	public static void useWeaponAttributes(float damage, EntityPlayer player, EntityLivingBase enemy, ItemStack stack, NBTTagCompound nbt)
	{
		if (WeaponAttribute.DURABLE.hasAttribute(nbt) && Math.random() < WeaponAttribute.DURABLE.getAmount(nbt)) stack.setItemDamage(stack.getItemDamage() + 1);
		if (WeaponAttribute.FIRE.hasAttribute(nbt)) 
		{
			enemy.attackEntityFrom(DamageSource.ON_FIRE, (float) WeaponAttribute.FIRE.getAmount(nbt));
			enemy.hurtResistantTime = 0;
		}
		if (WeaponAttribute.FROST.hasAttribute(nbt))
		{
			enemy.attackEntityFrom(ModDamageSources.FROST, (float) WeaponAttribute.FROST.getAmount(nbt));
			enemy.hurtResistantTime = 0;
			enemy.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 3, 5));
		}
		if (WeaponAttribute.LIGHTNING.hasAttribute(nbt))
		{
			enemy.attackEntityFrom(ModDamageSources.LIGHTNING, (float) WeaponAttribute.LIGHTNING.getAmount(nbt));
			enemy.hurtResistantTime = 0;
			
			// remove half the lightning damage dealt from mana.
			if (enemy instanceof EntityPlayer)
			{
				Stats statsCap = (Stats) enemy.getCapability(CapabilityPlayerStats.STATS, null);
				
				if (statsCap != null)
				{
					statsCap.decreaseMana((int) (WeaponAttribute.LIGHTNING.getAmount(nbt) / 2));
				}
			}
		}
		if (WeaponAttribute.POISON.hasAttribute(nbt)) 
		{
			enemy.attackEntityFrom(ModDamageSources.POISON, (float) WeaponAttribute.POISON.getAmount(nbt));
			enemy.hurtResistantTime = 0;
		}
		if (WeaponAttribute.LIFE_STEAL.hasAttribute(nbt)) player.setHealth((float) (player.getHealth() + (damage * WeaponAttribute.LIFE_STEAL.getAmount(nbt))));
		if (WeaponAttribute.MANA_STEAL.hasAttribute(nbt))
		{
			Stats statsCap = (Stats) enemy.getCapability(CapabilityPlayerStats.STATS, null);
			
			if (statsCap != null)
			{
				// adds mana to the player each attack.
				statsCap.increaseMana((int) (WeaponAttribute.MANA_STEAL.getAmount(nbt) * damage));
			}
		}
		if (WeaponAttribute.CHAINED.hasAttribute(nbt))
		{
			double radius = WeaponAttribute.CHAINED.getAmount(nbt);
			World world = enemy.getEntityWorld();
			List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius));
			Iterator<EntityLivingBase> iterator = entityList.iterator();
			
			while (iterator.hasNext())
			{
                Entity entity = (Entity) iterator.next();
				
				if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime))
				{
					entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) (damage * 0.25));
					entity.hurtResistantTime = 0;
				}
			}
		}
		if (WeaponAttribute.VOID.hasAttribute(nbt) && Math.random() < WeaponAttribute.VOID.getAmount(nbt)) enemy.setHealth(0.00001F);
	}
}

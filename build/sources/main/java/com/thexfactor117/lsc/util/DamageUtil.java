package com.thexfactor117.lsc.util;

import com.thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.thexfactor117.lsc.capabilities.implementation.EnemyInfo;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.AttributeWeapon;
import com.thexfactor117.lsc.util.misc.LSCDamageSource;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 *
 * @author TheXFactor117
 *
 */
public class DamageUtil
{
	/**
	 * Applies damage modifiers to the damage passed in. This adds additional damage based on
	 * stats like Strength, Dexterity, and Intelligence, where applicable.
	 * @param playerInfo
	 * @param damage
	 * @param type
	 * @return
	 */
	public static double applyDamageModifiers(LSCPlayerCapability cap, double damage, DamageType type, EntityLivingBase entity)
	{
		EnemyInfo enemy = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);

		switch (type)
		{
			case PHYSICAL_MELEE:
				return damage + cap.getPhysicalPower() / cap.getPlayerLevel() / enemy.getEnemyLevel();
			case PHYSICAL_RANGED:
				return damage + cap.getRangedPower() / cap.getPlayerLevel() / enemy.getEnemyLevel();
			case MAGICAL:
				return damage + cap.getMagicalPower() / cap.getPlayerLevel() / enemy.getEnemyLevel();
			default:
				return damage;
		}
	}
	
	/**
	 * Applies critical damage to the passed in damage value. This effectively increases the damage
	 * of the attack based on the player's critical stats.
	 * @param stats
	 * @param damage
	 * @param nbt
	 * @return
	 */
	public static double applyCriticalModifier(LSCPlayerCapability cap, ItemStack stack, double damage)
	{
		double damageBeforeCrit = damage;
		
		if (cap.getCriticalChance(stack) > 0)
		{
			if (Math.random() < cap.getCriticalChance(stack))
			{
				damage = (cap.getCriticalDamage(stack) * damageBeforeCrit) + damageBeforeCrit;
			}
		}
		
		return damage;
	}
	
	/**
	 * Iterates through all of the attributes that the stack has, calling its onHit method.
	 * @param cap
	 * @param stack
	 * @param attacker
	 * @param enemy
	 * @param damage
	 */
	public static void applyAttributes(LSCPlayerCapability cap, ItemStack stack, EntityLivingBase attacker, EntityLivingBase enemy, double damage)
	{
		for (Attribute attribute : ItemUtil.getSecondaryAttributes(stack))
		{
			if (attribute instanceof AttributeWeapon)
			{
				((AttributeWeapon) attribute).onHit(stack, (float) damage, attacker, enemy);
			}
		}
	}
	
	/**
	 * Applies custom armor reductions to the passed in damage value. This uses a custom algorithm separate from Vanilla's,
	 * allowing us to fully customize the damage algorithm and force all damage to use this instead of Vanilla's.
	 * @param damage
	 * @param player
	 * @param playerInfo
	 * @return
	 */
	public static double applyArmorReductions(double damage, EntityPlayer player, LSCPlayerCapability cap)
	{
		return damage * (damage / (damage + getTotalArmor(player, cap)));
	}
	
	/**
	 * Applies elemental resistances to the damage passed in. Note, this method only handles elemental resistances for the player.
	 * @param damage
	 * @param source
	 * @param player
	 * @return
	 */
	public static double applyElementalResistance(double damage, LSCDamageSource source, LSCPlayerCapability cap)
	{
		double reducedDamage = damage;
		
		if (source.isFireDamage()) reducedDamage = damage * (damage / (damage + cap.getFireResistance()));
		else if (source.isFrostDamage()) reducedDamage = damage * (damage / (damage + cap.getFrostResistance()));
		else if (source.isLightningDamage()) reducedDamage = damage * (damage / (damage + cap.getLightningResistance()));
		else if (source.isPoisonDamage()) reducedDamage = damage * (damage / (damage + cap.getPoisonResistance()));
		
		return reducedDamage >= 0 ? reducedDamage : 0;
	}
	
	/**
	 * Returns the total number of Armor Points on all the equipped pieces of armor the player currently has.
	 * @param player
	 * @param playerInfo
	 * @return
	 */
	public static double getEquippedArmor(EntityPlayer player, LSCPlayerCapability cap)
	{
		double totalArmorPoints = 0;
		
		// checks total armor points
		for (ItemStack stack : player.getArmorInventoryList())
		{
			if (stack.getItem() instanceof ItemArmor)
			{
				if (ItemUtil.getItemLevel(stack) <= cap.getPlayerLevel())
				{
					totalArmorPoints += ItemUtil.getItemArmor(stack);
				}
			}
		}
		
		return totalArmorPoints;
	}
	
	/**
	 * Returns the total amount of Armor Points for a player, includding equipped Armor and Physical Resistance.
	 * @param player
	 * @param playerInfo
	 * @return
	 */
	public static double getTotalArmor(EntityPlayer player, LSCPlayerCapability cap)
	{
		return getEquippedArmor(player, cap) + cap.getPhysicalResistance();
	}
	
	public static enum DamageType
	{
		PHYSICAL_MELEE(),
		PHYSICAL_RANGED(),
		MAGICAL();
	}
}

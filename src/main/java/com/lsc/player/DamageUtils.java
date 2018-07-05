package com.lsc.player;

import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.capabilities.implementation.Stats;
import com.lsc.config.Configs;

import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class DamageUtils
{
	/**
	 * Applies damage modifiers to the damage passed in. This adds additional damage based on
	 * stats like Strength, Dexterity, and Intelligence, where applicable.
	 * @param playerInfo
	 * @param damage
	 * @param type
	 * @return
	 */
	public static double applyDamageModifiers(PlayerInformation playerInfo, double damage, DamageType type)
	{
		switch (type)
		{
			case PHYSICAL_MELEE:
				return damage + getMeleePower(playerInfo);
			case PHYSICAL_RANGED:
				return damage + getRangedPower(playerInfo);
			case MAGICAL:
				return damage + getMagicalPower(playerInfo);
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
	public static double applyCriticalModifier(Stats stats, double damage, NBTTagCompound nbt)
	{
		double damageBeforeCrit = damage;
		
		if (stats.getCriticalChance() > 0)
		{
			if (Math.random() < stats.getCriticalChance())
			{
				damage = (stats.getCriticalDamage() * damageBeforeCrit) + damageBeforeCrit;
			}
		}
		
		return damage;
	}
	
	// TODO: if power is less than 1 (decimal), set to zero to prevent the decimal being added on as extra damage.
	// not too important but still a thing.
	
	public static double getMeleePower(PlayerInformation playerInfo)
	{
		double multiplier = (Math.random() * (Configs.weaponCategory.damageMaxRandFactor - Configs.weaponCategory.damageMinRandFactor) + Configs.weaponCategory.damageMinRandFactor);
		return (Math.pow(Configs.weaponCategory.damageBaseFactor, playerInfo.getPlayerLevel()) + playerInfo.getTotalStrength()) * (0.85 * multiplier);
	}
	
	public static double getRangedPower(PlayerInformation playerInfo)
	{
		double multiplier = (Math.random() * (Configs.weaponCategory.damageMaxRandFactor - Configs.weaponCategory.damageMinRandFactor) + Configs.weaponCategory.damageMinRandFactor);
		return (Math.pow(Configs.weaponCategory.damageBaseFactor, playerInfo.getPlayerLevel()) + playerInfo.getBonusDexterityStat()) * (0.85 * multiplier);
	}
	
	public static double getMagicalPower(PlayerInformation playerInfo)
	{
		double multiplier = (Math.random() * (Configs.weaponCategory.damageMaxRandFactor - Configs.weaponCategory.damageMinRandFactor) + Configs.weaponCategory.damageMinRandFactor);
		return (Math.pow(Configs.weaponCategory.damageBaseFactor, playerInfo.getPlayerLevel()) + playerInfo.getTotalIntelligence()) * (0.85 * multiplier);
	}
}

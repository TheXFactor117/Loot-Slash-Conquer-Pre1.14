package com.thexfactor117.lsc.player;

import com.thexfactor117.lsc.capabilities.implementation.PlayerInformation;
import com.thexfactor117.lsc.capabilities.implementation.PlayerStats;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.Attribute;
import com.thexfactor117.lsc.util.LSCDamageSource;
import com.thexfactor117.lsc.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
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
	public static double applyCriticalModifier(PlayerStats playerstats, double damage, NBTTagCompound nbt)
	{
		double damageBeforeCrit = damage;
		
		if (playerstats.getCriticalChance() > 0)
		{
			if (Math.random() < playerstats.getCriticalChance())
			{
				damage = (playerstats.getCriticalDamage() * damageBeforeCrit) + damageBeforeCrit;
			}
		}
		
		return damage;
	}
	
	/**
	 * Applies custom armor reductions to the passed in damage value. This uses a custom algorithm separate from Vanilla's,
	 * allowing us to fully customize the damage algorithm and force all damage to use this instead of Vanilla's.
	 * @param damage
	 * @param player
	 * @param playerInfo
	 * @return
	 */
	public static double applyArmorReductions(double damage, EntityPlayer player, PlayerInformation playerInfo)
	{	
		return damage * (damage / (damage + getTotalArmor(player, playerInfo)));
	}
	
	/**
	 * Applies elemental resistances to the damage passed in. Note, this method only handles elemental resistances for the Player's Armor.
	 * @param damage
	 * @param source
	 * @param player
	 * @return
	 */
	public static double applyElementalResistance(double damage, LSCDamageSource source, EntityPlayer player)
	{
		double reducedDamage = damage;
		
		for (ItemStack stack : player.getArmorInventoryList())
		{
			if (stack.getItem() instanceof ItemArmor)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);

				if (source.isFireDamage() && Attribute.FIRE_RESIST.hasAttribute(nbt)) reducedDamage *= Attribute.FIRE_RESIST.getAmount(nbt);
				else if (source.isFrostDamage() && Attribute.FROST_RESIST.hasAttribute(nbt)) reducedDamage *= Attribute.FROST_RESIST.getAmount(nbt);
				else if (source.isLightningDamage() && Attribute.LIGHTNING_RESIST.hasAttribute(nbt)) reducedDamage *= Attribute.LIGHTNING_RESIST.getAmount(nbt);
			}
		}
		
		return reducedDamage;
	}
	
	// TODO: if power is less than 1 (decimal), set to zero to prevent the decimal being added on as extra damage.
	// not too important but still a thing.
	
	/**
	 * Returns the player's raw melee power. This will take the player's Strength stat and run it through an algorithm
	 * to determine bonus damage. The result is already scaled by the player's Level.
	 * @param playerInfo
	 * @return
	 */
	public static double getMeleePower(PlayerInformation playerinfo)
	{
		double meleePower = (Math.pow(Configs.weaponCategory.damageBaseFactor, playerinfo.getPlayerLevel()) + playerinfo.getTotalStrength()) * (0.85 * 0.8);
		return playerinfo.getTotalStrength() != 0 ? meleePower : 0;
	}
	
	/**
	 * Returns the player's raw ranged power. This will take the player's Dexterity stat and run it through an algorithm
	 * to determine bonus damage. The result is already scaled by the player's Level.
	 * @param playerInfo
	 * @return
	 */
	public static double getRangedPower(PlayerInformation playerinfo)
	{
		double rangedPower = (Math.pow(Configs.weaponCategory.damageBaseFactor, playerinfo.getPlayerLevel()) + playerinfo.getTotalDexterity()) * (0.85 * 0.8);
		return playerinfo.getTotalDexterity() != 0 ? rangedPower : 0;
	}
	
	/**
	 * Returns the player's raw magical power. This will take the player's Intelligence stat and run it through an algorithm
	 * to determine bonus damage. The result is already scaled by the player's Level.
	 * @param playerInfo
	 * @return
	 */
	public static double getMagicalPower(PlayerInformation playerinfo)
	{
		double magicalPower = (Math.pow(Configs.weaponCategory.damageBaseFactor, playerinfo.getPlayerLevel()) + playerinfo.getTotalIntelligence()) * (0.85 * 0.8);
		return playerinfo.getTotalIntelligence() != 0 ? magicalPower : 0;
	}
	
	public static double getPhysicalResistance(PlayerInformation playerInfo)
	{
		double multiplier = (Math.random() * (Configs.weaponCategory.damageMaxRandFactor - Configs.weaponCategory.damageMinRandFactor) + Configs.weaponCategory.damageMinRandFactor);
		return (Math.pow(1.05, playerInfo.getPlayerLevel()) + playerInfo.getTotalStrength()) * (0.85 * multiplier);
	}
	
	/**
	 * Returns the total number of Armor Points on all the equipped pieces of armor the player currently has.
	 * @param player
	 * @param playerInfo
	 * @return
	 */
	public static double getEquippedArmor(EntityPlayer player, PlayerInformation playerInfo)
	{
		double totalArmorPoints = 0;
		
		// checks total armor points
		for (ItemStack stack : player.getArmorInventoryList())
		{
			if (stack.getItem() instanceof ItemArmor)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				totalArmorPoints += nbt.getDouble("ArmorPoints");
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
	public static double getTotalArmor(EntityPlayer player, PlayerInformation playerInfo)
	{
		return getEquippedArmor(player, playerInfo) + getPhysicalResistance(playerInfo);
	}
}

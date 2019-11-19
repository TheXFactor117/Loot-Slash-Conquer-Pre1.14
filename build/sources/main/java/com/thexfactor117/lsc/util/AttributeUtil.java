package com.thexfactor117.lsc.util;

import java.util.Random;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 * 
 * TODO: consolidate code
 *
 */
public class AttributeUtil
{	
	public static Attribute getRandomWeaponAttribute()
	{
		return Attribute.WEAPON_ATTRIBUTES.next(ItemGenerationUtil.rand);
	}
	
	public static Attribute getRandomArmorAttribute()
	{
		return Attribute.ARMOR_ATTRIBUTES.next(ItemGenerationUtil.rand);
	}
	
	/**
	 * Adds the supplied attribute as a damage attribute.
	 * base value is weighted by level/item rarity
	 * min/max values are set based off of weighted value (range weighted by level)
	 * true value is randomized based off of new min/max values
	 * @param attribute
	 * @param stack
	 * @param nbt
	 * @param rand
	 */
	public static void addDamageAttribute(Attribute attribute, ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		// weight the base value by level and rarity.
		double weightedBase = ItemGenerationUtil.getWeightedDamage(nbt.getInteger("Level"), ItemUtil.getItemRarity(stack), attribute.getBaseValue());
		// set min/max values
		setDamageMinMax(attribute, stack, nbt, weightedBase);
		// calculate the actual value by randomizing between min/max.
		int trueDamage = (int) (Math.random() * (attribute.getAttributeMaxValue(nbt) - attribute.getAttributeMinValue(nbt)) + attribute.getAttributeMinValue(nbt));
		
		nbt.setDouble(attribute.getName() + "_value", trueDamage);
	}
	
	/**
	 * Adds the supplied attribute as a percentage attribute.
	 * base value is weighted based on item rarity (level doesn't matter here)
	 * min/max values are based off of weighted value (range is randomized, not weighted)
	 * true value is randomized based off of new min/max values
	 * @param attribute
	 * @param stack
	 * @param nbt
	 * @param rand
	 * @param rangeMultiplier
	 */
	public static void addPercentageAttribute(Attribute attribute, ItemStack stack, NBTTagCompound nbt, Random rand, double rangeMultiplier)
	{
		// weight the base value by attribute rarity
		double weightedPercentage = getWeightedPercentage(ItemUtil.getItemRarity(stack), attribute.getBaseValue(), rangeMultiplier);
		// set min/max values
		setPercentageMinMax(attribute, stack, nbt, weightedPercentage);
		// calculate the actual value by randomizing between min/max.
		double truePercentage = Math.random() * (attribute.getAttributeMaxValue(nbt) - attribute.getAttributeMinValue(nbt)) + attribute.getAttributeMinValue(nbt);
		
		nbt.setDouble(attribute.getName() + "_value", truePercentage);
	}
	
	/**
	 * Adds the supplied attribute as a stat attribute.
	 * base value is weighted based on item rarity and level
	 * min/max values are based off of the weighted value (range weighted slightly by level)
	 * true value is randomized based off of new min/max values
	 * @param attribute
	 * @param stack
	 * @param nbt
	 * @param rand
	 */
	public static void addStatAttribute(Attribute attribute, ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		double weightedStat = getWeightedStat(stack, ItemUtil.getItemRarity(stack), attribute.getBaseValue());
		
		setStatMinMax(attribute, stack, nbt, weightedStat);
		
		int trueStat = (int) (Math.random() * (attribute.getAttributeMaxValue(nbt) - attribute.getAttributeMinValue(nbt)) + attribute.getAttributeMinValue(nbt));
		
		nbt.setDouble(attribute.getName() + "_value", trueStat);
	}
	
	public static void addResistanceAttribute(Attribute attribute, ItemStack stack, NBTTagCompound nbt)
	{
		double weightedResistance = ItemGenerationUtil.getWeightedArmor(ItemUtil.getItemRarity(stack), ItemUtil.getItemLevel(stack), attribute.getBaseValue());
		
		setResistanceMinMax(attribute, stack, nbt, weightedResistance);
		
		int trueResistance = (int) (Math.random() * (attribute.getAttributeMaxValue(nbt) - attribute.getAttributeMinValue(nbt)) + attribute.getAttributeMinValue(nbt));
		
		nbt.setDouble(attribute.getName() + "_value", trueResistance);
	}
	
	/**
	 * Sets the min/max damage value. Code is documented.
	 * @param attribute
	 * @param stack
	 * @param nbt
	 * @param startingValue
	 */
	private static void setDamageMinMax(Attribute attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		// adds randomization to the range - controls how big the range is.
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double randomMultiplier = Math.random() * (maxRandFactor - minRandFactor) + minRandFactor;
		
		// scales the range by the item's level.
		double levelMultiplier = ItemUtil.getItemLevel(stack) * 0.1;
		
		int range = (int) (randomMultiplier * levelMultiplier);

		int minValue = (int) (startingValue - range);
		int maxValue = (int) (startingValue + levelMultiplier);
		
		nbt.setDouble(attribute.getName() + "_minvalue", minValue);
		nbt.setDouble(attribute.getName() + "_maxvalue", maxValue);
	}
	
	/**
	 * Sets the min/max percentage value. Code is documented.
	 * @param attribute
	 * @param stack
	 * @param nbt
	 * @param startingValue
	 */
	private static void setPercentageMinMax(Attribute attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		// adds randomization to the range - controls how big the range is.
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double randomMultiplier = Math.random() * (maxRandFactor - minRandFactor) + minRandFactor;
		
		// scales range?
		
		double range = randomMultiplier * startingValue + 0.01;
		
		double minValue = startingValue - (range / 2);
		double maxValue = startingValue + (range / 2);
		
		nbt.setDouble(attribute.getName() + "_minvalue", minValue);
		nbt.setDouble(attribute.getName() + "_maxvalue", maxValue);
	}
	
	/**
	 * Sets the min/max stat value. Code is documented.
	 * @param attribute
	 * @param stack
	 * @param nbt
	 * @param startingValue
	 */
	private static void setStatMinMax(Attribute attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		// adds randomization to the range - controls how big it is.
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double randomMultiplier = Math.random() * (maxRandFactor - minRandFactor) + minRandFactor;
		
		// scale range based off of item level.
		double levelMultiplier = ItemUtil.getItemLevel(stack) * 0.05;
		
		int range = (int) (randomMultiplier * levelMultiplier);
		
		int minValue = (int) (startingValue + range);
		int maxValue = (int) (startingValue + levelMultiplier);
		
		nbt.setDouble(attribute.getName() + "_minvalue", minValue);
		nbt.setDouble(attribute.getName() + "_maxvalue", maxValue);
	}
	
	private static void setResistanceMinMax(Attribute attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double randomMultiplier = Math.random() * (maxRandFactor - minRandFactor) + minRandFactor;
		
		double levelMultiplier = ItemUtil.getItemLevel(stack) * randomMultiplier;
		
		int minValue = (int) (startingValue + levelMultiplier * randomMultiplier);
		int maxValue = (int) (startingValue + levelMultiplier);
		
		nbt.setDouble(attribute.getName() + "_minvalue", minValue);
		nbt.setDouble(attribute.getName() + "_maxvalue", maxValue);
	}
	
	/**
	 * Weights the given percentage according to rarity. An optional multiplier option
	 * is supplied for further tuning (default use 1). For higher weighted percentages,
	 * set the optional multiplier to a decimal higher than 1 (for example, 1.2). Default
	 * average weighted percentage with 0.1 supplied as a base = 0.038/0.047/0.075/0.116/0.169
	 * @param rarity
	 * @param baseValue
	 * @param optionalMultiplier
	 * @return
	 */
	private static double getWeightedPercentage(Rarity rarity, double baseValue, double optionalMultiplier)
	{
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double randomMultiplier = Math.random() * (minRandFactor - minRandFactor) + minRandFactor;
		
		double multiplier = randomMultiplier * optionalMultiplier;
		
		switch (rarity)
		{
			case COMMON:
				return baseValue * 1 * multiplier;
			case UNCOMMON:
				return baseValue * 1.25 * multiplier;
			case RARE:
				return baseValue * 2 * multiplier;
			case EPIC:
				return baseValue * 3.1 * multiplier;
			case LEGENDARY:
				return baseValue * 4.5 * multiplier;
			default:
				return 0;
		}
	}
	
	/**
	 * Weights the given stat according to level and rarity. Rarity factors are probably fine
	 * and shouldn't be touched. The baseFactor controls how much effectiveness the level has
	 * on the stat value (very sensitive - keep it close to 1.1). Multiplier adds some
	 * randomness and can be tweaked for more/less randomness.
	 * @param stack
	 * @param rarity
	 * @param baseValue
	 * @return
	 */
	private static double getWeightedStat(ItemStack stack, Rarity rarity, double baseValue)
	{
		double baseFactor = Configs.weaponCategory.damageBaseFactor;
		// min/max rand factor controls the range of the random decimal (this creates a sort of range for the damage to fall in,
		// based on the base damage.
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);
		
		// set the new damage equal to the base multiplied by the multiplier and the rarity factor.
		switch (rarity)
		{
			case COMMON:
				return baseValue * Math.pow(baseFactor, 1.0);
			case UNCOMMON:
				return baseValue * Math.pow(baseFactor, 2.0);
			case RARE:
				return baseValue * Math.pow(baseFactor, 3.0);
			case EPIC:
				return baseValue * Math.pow(baseFactor, 4.0);
			case LEGENDARY:
				return baseValue * Math.pow(baseFactor, 5.0);
			default:
				return baseValue;
		}
	}
}

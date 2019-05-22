package com.thexfactor117.lsc.loot.attributes;

import java.util.Random;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.generation.ItemGeneratorHelper;
import com.thexfactor117.lsc.util.ItemUtil;

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
	public static void addDamageAttribute(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		// weight the base value by level and rarity.
		double weightedBase = ItemGeneratorHelper.getWeightedDamage(nbt.getInteger("Level"), ItemUtil.getItemRarity(stack), attribute.getBaseValue());
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
	public static void addPercentageAttribute(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt, Random rand, double rangeMultiplier)
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
	public static void addStatAttribute(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		double weightedStat = getWeightedStat(stack, ItemUtil.getItemRarity(stack), attribute.getBaseValue());
		
		setStatMinMax(attribute, stack, nbt, weightedStat);
		
		int trueStat = (int) (Math.random() * (attribute.getAttributeMaxValue(nbt) - attribute.getAttributeMinValue(nbt)) + attribute.getAttributeMinValue(nbt));
		
		nbt.setDouble(attribute.getName() + "_value", trueStat);
	}
	
	public static void addResistanceAttribute(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt)
	{
		double weightedResistance = ItemGeneratorHelper.getWeightedArmor(ItemUtil.getItemRarity(stack), ItemUtil.getItemLevel(stack), attribute.getBaseValue());
		
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
	private static void setDamageMinMax(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		// adds randomization to the range - controls how big the range is.
		double minRandomFactor = 0.1;
		double maxRandomFactor = 0.4;
		double randomMultiplier = Math.random() * (maxRandomFactor - minRandomFactor) + minRandomFactor;
		
		// scales the range by the item's level.
		double levelMultiplier = ItemUtil.getItemLevel(stack) * 0.1;
		
		int range = (int) ((randomMultiplier * startingValue + 0.5) * levelMultiplier);

		int minValue = (int) (startingValue - (range / 2));
		int maxValue = (int) (startingValue + (range / 2));
		
		if (minValue == maxValue) minValue -= 1;
		if (minValue <= 0) minValue = 1;
		while (minValue >= maxValue) maxValue += 1;
		
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
	private static void setPercentageMinMax(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		// adds randomization to the range - controls how big the range is.
		double minRandomFactor = 0.1;
		double maxRandomFactor = 0.4;
		double randomMultiplier = Math.random() * (maxRandomFactor - minRandomFactor) + minRandomFactor;
		
		// scales range?
		
		double range = randomMultiplier * startingValue + 0.01;
		
		double minValue = startingValue - (range / 2);
		double maxValue = startingValue + (range / 2);
		
		if (minValue < 0.01) minValue = 0.01;
		while (minValue >= maxValue) maxValue += 0.01;
		
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
	private static void setStatMinMax(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		// adds randomization to the range - controls how big it is.
		double minRandomFactor = 0.1;
		double maxRandomFactor = 0.4;
		double randomMultiplier = Math.random() * (maxRandomFactor - minRandomFactor) + minRandomFactor;
		
		// scale range based off of item level.
		double levelMultiplier = ItemUtil.getItemLevel(stack) * 0.05;
		
		int range = (int) ((randomMultiplier * startingValue + 0.5) * levelMultiplier);
		
		int minValue = (int) (startingValue - (range / 2));
		int maxValue = (int) (startingValue + (range / 2));
		
		if (minValue == maxValue) minValue -= 1;
		if (minValue <= 0) minValue = 1;
		while (minValue >= maxValue) maxValue += 1;
		
		nbt.setDouble(attribute.getName() + "_minvalue", minValue);
		nbt.setDouble(attribute.getName() + "_maxvalue", maxValue);
	}
	
	private static void setResistanceMinMax(AttributeBase attribute, ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		double minRandomFactor = 0.1;
		double maxRandomFactor = 0.4;
		double randomMultiplier = Math.random() * (maxRandomFactor - minRandomFactor) + minRandomFactor;
		
		double levelMultiplier = ItemUtil.getItemLevel(stack) * 0.1;
		
		int range = (int) ((randomMultiplier * startingValue + 0.5) * levelMultiplier);
		
		int minValue = (int) (startingValue - (range / 2));
		int maxValue = (int) (startingValue + (range / 2));
		
		if (minValue == maxValue) minValue -= 1;
		if (minValue <= 0) minValue = 1;
		while (minValue >= maxValue) maxValue += 1;
		
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
		double minRandomFactor = 0.6;
		double maxRandomFactor = 0.9;
		double randomMultiplier = Math.random() * (maxRandomFactor - minRandomFactor) + minRandomFactor;
		
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
		double baseFactor = 1.1;
		// min/max rand factor controls the range of the random decimal (this creates a sort of range for the damage to fall in,
		// based on the base damage.
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);
		
		// set the new damage equal to the base multiplied by the multiplier and the rarity factor.
		switch (rarity)
		{
			case COMMON:
				return (Math.pow(baseFactor, ItemUtil.getItemLevel(stack)) * (baseValue * (Configs.weaponCategory.commonFactor * multiplier)));
			case UNCOMMON:
				return (Math.pow(baseFactor, ItemUtil.getItemLevel(stack)) * (baseValue * (Configs.weaponCategory.uncommonFactor * multiplier)));
			case RARE:
				return (Math.pow(baseFactor, ItemUtil.getItemLevel(stack)) * (baseValue * (Configs.weaponCategory.rareFactor * multiplier)));
			case EPIC:
				return (Math.pow(baseFactor, ItemUtil.getItemLevel(stack)) * (baseValue * (Configs.weaponCategory.epicFactor * multiplier)));
			case LEGENDARY:
				return (Math.pow(baseFactor, ItemUtil.getItemLevel(stack)) * (baseValue * (Configs.weaponCategory.legendaryFactor * multiplier)));
			default:
				return baseValue;
		}
	}
}

package com.thexfactor117.lsc.loot.attributes;

import java.util.Random;

import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.generation.ItemGeneratorHelper;
import com.thexfactor117.lsc.util.ItemUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeBaseWeapon extends AttributeBase
{
	private boolean isActive;

	public AttributeBaseWeapon(String name, String key, double baseValue, boolean upgradeable, boolean isBonus, boolean isActive)
	{
		super(name, key, baseValue, upgradeable, isBonus);
		this.isActive = isActive;
	}

	/**
	 * Called every time a weapon strikes and deals damage. Each attribute will implement its own effect.
	 * 
	 * @param damage
	 * @param attacker
	 * @param enemy
	 * @param stack
	 * @param nbt
	 */
	public void onHit(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy) { }
	
	public double getPassiveValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(this.getName() + "_value");
	}

	/**
	 * Adds the specific attribute with a randomized damage value weighted by item level, attribute rarity, and some randomization.
	 * @param stack
	 * @param nbt
	 * @param rand
	 */
	public void addDamageAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		// weight the base value by level and rarity.
		double weightedBase = ItemGeneratorHelper.getWeightedDamage(nbt.getInteger("Level"), this.getAttributeRarity(nbt), this.getBaseValue());
		// set min/max values
		this.setDamageMinMax(stack, nbt, weightedBase);
		// calculate the actual value by randomizing between min/max.
		int trueDamage = (int) (Math.random() * (this.getAttributeMaxValue(nbt) - this.getAttributeMinValue(nbt)) + this.getAttributeMinValue(nbt));
		
		nbt.setDouble(this.getName() + "_value", trueDamage);
	}
	
	public void addPercentageAttribute(ItemStack stack, NBTTagCompound nbt, Random rand, double rangeMultiplier)
	{
		// weight the base value by attribute rarity
		double weightedPercentage = this.getWeightedPercentage(this.getAttributeRarity(nbt), this.getBaseValue(), rangeMultiplier);
		// set min/max values
		this.setPercentageMinMax(stack, nbt, weightedPercentage);
		// calculate the actual value by randomizing between min/max.
		double truePercentage = Math.random() * (this.getAttributeMaxValue(nbt) - this.getAttributeMinValue(nbt)) + this.getAttributeMinValue(nbt);
		
		nbt.setDouble(this.getName() + "_value", truePercentage);
	}
	
	private void setDamageMinMax(ItemStack stack, NBTTagCompound nbt, double startingValue)
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
		
		nbt.setDouble(this.getName() + "_minvalue", minValue);
		nbt.setDouble(this.getName() + "_maxvalue", maxValue);
	}
	
	private void setPercentageMinMax(ItemStack stack, NBTTagCompound nbt, double startingValue)
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
		
		nbt.setDouble(this.getName() + "_minvalue", minValue);
		nbt.setDouble(this.getName() + "_maxvalue", maxValue);
	}
	
	private double getWeightedPercentage(Rarity rarity, double baseValue, double optionalMultiplier)
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
	 * Returns whether or not this is an active or passive attribute.
	 * 
	 * @return
	 */
	public boolean isActive()
	{
		return isActive;
	}
}

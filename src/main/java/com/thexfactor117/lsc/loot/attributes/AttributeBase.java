package com.thexfactor117.lsc.loot.attributes;

import java.util.Random;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeStrength;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFireDamage;
import com.thexfactor117.lsc.util.misc.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeBase
{
	private String name;
	private String key;
	private double minAmount;
	private double maxAmount;
	private boolean upgradeable;
	
	public static final AttributeBase FIRE_DAMAGE = new AttributeFireDamage();
	
	public static final AttributeBase STRENGTH = new AttributeStrength();
	
	public static final RandomCollection<AttributeBase> WEAPON_SECONDARY_ATTRIBUTES = new RandomCollection<AttributeBase>();
	public static final RandomCollection<AttributeBase> WEAPON_BONUS_ATTRIBUTES = new RandomCollection<AttributeBase>();
	public static final RandomCollection<AttributeBase> ARMOR_SECONDARY_ATTRIBUTES = new RandomCollection<AttributeBase>();
	public static final RandomCollection<AttributeBase> ARMOR_BONUS_ATTRIBUTES = new RandomCollection<AttributeBase>();
	
	public AttributeBase(String name, String key, double min, double max, boolean upgradeable)
	{
		this.name = name;
		this.key = key;
		this.minAmount = min;
		this.maxAmount = max;
		this.upgradeable = upgradeable;
	}

	/**
	 * Returns true if the NBTTagCompound has the specified Attribute.
	 * @param nbt
	 * @return
	 */
	public boolean hasAttribute(NBTTagCompound nbt)
	{
		return nbt.getBoolean(name);
	}
	
	public void addAttribute(NBTTagCompound nbt, Random rand)
	{
		nbt.setBoolean(name, true);
		nbt.setInteger(name + "_rarity", Rarity.getRandomRarity(nbt, rand).ordinal());
	}
	
	public void removeAttribute(NBTTagCompound nbt)
	{
		nbt.removeTag(name);
	}
	
	/**
	 * Returns the value of the specified Attribute.
	 * @param nbt
	 * @return
	 */
	public double getAttributeValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(name + "_value");
	}
	
	public double getAttributeMinValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(name + "_minvalue");
	}
	
	public double getAttributeMaxValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(name + "_maxvalue");
	}
	
	public Rarity getAttributeRarity(NBTTagCompound nbt)
	{
		switch (nbt.getInteger(name + "_rarity"))
		{
			case 1:
				return Rarity.COMMON;
			case 2:
				return Rarity.UNCOMMON;
			case 3:
				return Rarity.RARE;
			case 4:
				return Rarity.EPIC;
			case 5:
				return Rarity.LEGENDARY;
			default:
				return Rarity.DEFAULT;
		}
	}
	
	/**
	 * Creates a randomized min/max value using the given baseValue. The value given should already
	 * be scaled if it is damage related. Percentages do not need to be scaled.
	 * @param nbt
	 * @param baseValue
	 * @param rangeMultiplier
	 */
	public void setMinMaxIntegers(NBTTagCompound nbt, double baseValue, double rangeFactor)
	{
		double minRandFactor = Configs.weaponCategory.rangeMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.rangeMaxRandFactor;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);
		
		double rangeMultiplier = nbt.getInteger("Level") * rangeFactor;
		int range = (int) ((multiplier * 4 + 0.5) * rangeMultiplier);
		
		int minValue = (int) (baseValue - (range / 2));
		int maxValue = (int) (baseValue + (range / 2));
		
		if (minValue == maxValue) minValue -= 1;
		if (minValue <= 0) minValue = 1;
		while (minValue >= maxValue) maxValue += 1;
		
		nbt.setDouble(name + "_minvalue", minValue);
		nbt.setDouble(name + "_maxvalue", maxValue);
	}
	
	public void setMinMaxPercentages(NBTTagCompound nbt, double baseValue, double rangeMultiplier)
	{
		double minRandFactor = Configs.weaponCategory.rangeMinRandFactor / 10;
		double maxRandFactor = Configs.weaponCategory.rangeMaxRandFactor / 10;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);
		
		double range = (multiplier * 0.85 + 0.05) * rangeMultiplier;
		
		double minValue = baseValue - (range / 2);
		double maxValue = baseValue + (range / 2);
		
		if (minValue == maxValue) minValue -= 0.01;
		if (minValue == 0) minValue = 0.01;
		while (minValue >= maxValue) maxValue += 0.01;
		
		nbt.setDouble(name + "_minvalue", minValue);
		nbt.setDouble(name + "_maxvalue", maxValue);
	}
	
	static
	{
		WEAPON_SECONDARY_ATTRIBUTES.add(1, FIRE_DAMAGE);
		
		ARMOR_SECONDARY_ATTRIBUTES.add(1, STRENGTH);
	}
	
	public enum AttributeType
	{
		RANDOM_INTEGER(),
		DAMAGE_INTEGER(),
		ROUNDED_PERCENTAGE(),
		PERCENTAGE();
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public double getMinBaseValue()
	{
		return minAmount;
	}
	
	public double getMaxBaseValue()
	{
		return maxAmount;
	}
	
	public boolean isUpgradeable()
	{
		return upgradeable;
	}
}

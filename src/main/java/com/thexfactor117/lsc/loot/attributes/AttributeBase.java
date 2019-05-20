package com.thexfactor117.lsc.loot.attributes;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Lists;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeStrength;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeBonusExperience;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFireDamage;
import com.thexfactor117.lsc.util.ItemUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeBase
{
	private String name;
	private String key;
	private double baseValue;
	private boolean upgradeable;
	private boolean isBonus;
	
	public static final AttributeBase FIRE_DAMAGE = new AttributeFireDamage();
	
	public static final AttributeBase BONUS_EXPERIENCE = new AttributeBonusExperience();
	
	public static final AttributeBase STRENGTH = new AttributeStrength();
	
	
	public static final ArrayList<AttributeBase> ALL_ATTRIBUTES = Lists.newArrayList();
	public static ArrayList<AttributeBase> WEAPON_ATTRIBUTES = Lists.newArrayList();
	public static ArrayList<AttributeBase> ARMOR_ATTRIBUTES = Lists.newArrayList();
	
	public AttributeBase(String name, String key, double baseValue, boolean upgradeable, boolean isBonus)
	{
		this.name = name;
		this.key = key;
		this.baseValue = baseValue;
		this.upgradeable = upgradeable;
		this.isBonus = isBonus;
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
	
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		nbt.setBoolean(name, true);
		nbt.setInteger(name + "_rarity", Rarity.getWeightedRarity(rand, ItemUtil.getItemRarity(stack)).ordinal());
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
	
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		return "";
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
		double minRandFactor = Configs.weaponCategory.rangeMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.rangeMaxRandFactor;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);
		// avg. 0.56
		double range = (multiplier * 0.85 + 0.05) * rangeMultiplier;
		
		double minValue = baseValue - (range / 2);
		double maxValue = baseValue + (range / 2);
		
		if (minValue == maxValue) minValue -= 0.01;
		if (minValue == 0) minValue = 0.01;
		while (minValue >= maxValue) maxValue += 0.01;
		
		nbt.setDouble(name + "_minvalue", minValue);
		nbt.setDouble(name + "_maxvalue", maxValue);
	}
	
	public double getWeightedPercentage(Rarity rarity, double baseValue)
	{
		switch (rarity)
		{
			case COMMON:
				return baseValue * Configs.weaponCategory.commonFactor;
			case UNCOMMON:
				return baseValue * Configs.weaponCategory.uncommonFactor;
			case RARE:
				return baseValue * Configs.weaponCategory.rareFactor;
			case EPIC:
				return baseValue * Configs.weaponCategory.epicFactor;
			case LEGENDARY:
				return baseValue * Configs.weaponCategory.legendaryFactor;
			default:
				return 0;
		}
	}
	
	static
	{
		ALL_ATTRIBUTES.add(FIRE_DAMAGE);
		
		ALL_ATTRIBUTES.add(STRENGTH);
		
		for (String name : Configs.weaponCategory.weaponAttributes)
		{
			if (getAttributeFromString(name) != null)
			{
				WEAPON_ATTRIBUTES.add(getAttributeFromString(name));
			}
		}
		
		for (String name : Configs.weaponCategory.armorAttributes)
		{
			if (getAttributeFromString(name) != null)
			{
				ARMOR_ATTRIBUTES.add(getAttributeFromString(name));
			}
		}
	}
	
	public enum AttributeType
	{
		RANDOM_INTEGER(),
		DAMAGE_INTEGER(),
		ROUNDED_PERCENTAGE(),
		PERCENTAGE();
	}
	
	public static AttributeBase getAttributeFromString(String name)
	{
		for (AttributeBase attribute : ALL_ATTRIBUTES)
		{
			if (attribute.getName().equals(name))
			{
				return attribute;
			}
		}
		
		return null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public double getBaseValue()
	{
		return baseValue;
	}
	
	public boolean isUpgradeable()
	{
		return upgradeable;
	}
	
	public boolean isBonusAttribute()
	{
		return isBonus;
	}
}

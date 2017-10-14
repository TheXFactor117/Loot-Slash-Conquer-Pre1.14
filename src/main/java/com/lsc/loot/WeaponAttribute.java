package com.lsc.loot;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.lsc.util.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum WeaponAttribute
{
	FIRE("Fire Damage", "fire", Rarity.COMMON, 1, 6),
	FROST("Frost Damage", "frost", Rarity.COMMON, 1, 6),
	LIGHTNING("Lightning Damage", "lightning", Rarity.COMMON, 1, 6),
	POISON("Poison Damage", "poison", Rarity.COMMON, 1, 6),
	LIFE_STEAL("Life Steal", "steal", Rarity.UNCOMMON, 0.05, 0.25),
	MANA_STEAL("Mana Steal", "steal", Rarity.UNCOMMON, 0.05, 0.25),
	MIN_DAMAGE("Minimum Damage", "min_max", Rarity.UNCOMMON, 1, 4),
	MAX_DAMAGE("Maximum Damage", "min_max", Rarity.UNCOMMON, 1, 4),
	CHAINED("Chained Radius", "chained", Rarity.RARE, 5, 20),
	VOID("Void", "void", Rarity.RARE, 0.01, 0.1),
	
	STRENGTH("Strength", "strength", Rarity.COMMON, 1, 8),
	AGILITY("Agility", "agility", Rarity.COMMON, 1, 8),
	DEXTERITY("Dexterity", "dexterity", Rarity.COMMON, 1, 8),
	INTELLIGENCE("Intelligence", "intelligence", Rarity.COMMON, 1, 8),
	WISDOM("Wisdom", "wisdom", Rarity.COMMON, 1, 8),
	FORTITUDE("Fortitude", "fortitude", Rarity.COMMON, 1, 8),
	DURABLE("Durable", "durable", Rarity.COMMON, 0.05, 0.5),
	GOLD("Gold", "gold", Rarity.UNCOMMON, 1, 5),
	ALL_STATS("All Stats", "all_stats", Rarity.RARE, 4, 12);
	
	private String name;
	private String localizedString;
	private Rarity baseRarity;
	private double minAmount;
	private double maxAmount;
	
	public static final RandomCollection<WeaponAttribute> RANDOM_ATTRIBUTES = new RandomCollection<WeaponAttribute>();
		
	WeaponAttribute(String name, String localizedString, Rarity baseRarity, double min, double max)
	{
		this.name = name;
		this.localizedString = localizedString;
		this.baseRarity = baseRarity;
		this.minAmount = min;
		this.maxAmount = max;
	}
	
	public static WeaponAttribute getRandomAttribute(Random rand)
	{
		return RANDOM_ATTRIBUTES.next(rand);
	}
	
	public static WeaponAttribute getRandomActiveAttribute(NBTTagCompound nbt)
	{
		List<WeaponAttribute> list = Lists.newArrayList();
		
		for (WeaponAttribute attribute : WeaponAttribute.values())
		{
			if (attribute.hasAttribute(nbt))
			{
				list.add(attribute);
			}
		}
		
		return list.size() > 0 ? list.get((int) (Math.random() * list.size())) : null;
	}
	
	/**
	 * Returns true if the NBT tag compound has the specified Attribute.
	 * @param nbt
	 * @return
	 */
	public boolean hasAttribute(NBTTagCompound nbt)
	{
		return nbt.getBoolean(toString());
	}
	
	public double getAmount(NBTTagCompound nbt)
	{
		return nbt.getDouble(name + "_attribute_stat");
	}
	
	/**
	 * Adds the specified Attribute to the NBT tag compound.
	 * @param nbt
	 */
	public void addAttribute(NBTTagCompound nbt)
	{
		nbt.setBoolean(toString(), true);
		nbt.setInteger(name + "_Rarity", Rarity.getRandomRarity(nbt, new Random()).ordinal()); // sets the Attribute randomized rarity (how effective the attribute will be).
		double amount = (Math.random() * (maxAmount - minAmount)) + minAmount;
		
		if (amount < 1)
			nbt.setDouble(name + "_attribute_stat", amount);
		else
			nbt.setDouble(name + "_attribute_stat", (int) amount);
	}
	
	public void addAttribute(NBTTagCompound nbt, double amount)
	{
		nbt.setBoolean(toString(), true);
		
		if (amount < 1)
			nbt.setDouble(name + "_attribute_stat", amount);
		else
			nbt.setDouble(name + "_attribute_stat", (int) amount);
	}
	
	/**
	 * Removes the specified Attribute from the NBT tag compound.
	 * @param nbt
	 */
	public void removeAttribute(NBTTagCompound nbt)
	{
		nbt.removeTag(toString());
	}
	
	/**
	 * Returns the randomized Rarity the attribute was assigned when added.
	 * @param nbt
	 * @return
	 */
	public Rarity getRandomizedRarity(NBTTagCompound nbt)
	{
		if (nbt.getInteger(name + "_Rarity") == 1) return Rarity.COMMON;
		else if (nbt.getInteger(name + "_Rarity") == 2) return Rarity.UNCOMMON;
		else if (nbt.getInteger(name + "_Rarity") == 3) return Rarity.RARE;
		else if (nbt.getInteger(name + "_Rarity") == 4) return Rarity.LEGENDARY;
		else if (nbt.getInteger(name + "_Rarity") == 5) return Rarity.EXOTIC;
		else return Rarity.DEFAULT;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public String getName()
	{
		return name;
	}
	
	public String getLocalizedString()
	{
		return localizedString;
	}
	
	public Rarity getBaseRarity()
	{
		return baseRarity;
	}
	
	public double getMinAmount()
	{
		return minAmount;
	}
	
	public double getMaxAmount()
	{
		return maxAmount;
	}
	
	static
	{
		for (WeaponAttribute attribute : WeaponAttribute.values())
		{
			if (attribute.getBaseRarity().getChance() > 0.0D)
			{
				RANDOM_ATTRIBUTES.add(attribute.getBaseRarity().getChance(), attribute);
			}
		}
	}
}

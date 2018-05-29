package com.lsc.loot;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.lsc.util.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;

public enum JewelryAttribute
{
	STRENGTH("Strength", "strength", Rarity.COMMON, 1, 8),
	AGILITY("Agility", "agility", Rarity.COMMON, 1, 8),
	DEXTERITY("Dexterity", "dexterity", Rarity.COMMON, 1, 8),
	INTELLIGENCE("Intelligence", "intelligence", Rarity.COMMON, 1, 8),
	WISDOM("Wisdom", "wisdom", Rarity.COMMON, 1, 8),
	FORTITUDE("Fortitude", "fortitude", Rarity.COMMON, 1, 8),
	LIFE_STEAL("Life Steal", "steal", Rarity.UNCOMMON, 0.05, 0.25),
	MANA_STEAL("Mana Steal", "steal", Rarity.UNCOMMON, 0.05, 0.25),
	FIRE_RESIST("Fire Resistance", "fire", Rarity.UNCOMMON, 0.05, 1),
	FROST_RESIST("Frost Resistance", "frost", Rarity.UNCOMMON, 0.05, 1),
	LIGHTNING_RESIST("Lightning Resistance", "lightning", Rarity.UNCOMMON, 0.05, 1),
	POISON_RESIST("Poison Resistance", "poison", Rarity.UNCOMMON, 0.05, 1),
	VOID("Void", "void", Rarity.RARE, 0.01, 0.1),
	ALL_STATS("All Stats", "all_stats", Rarity.RARE, 4, 12);
	
	private String name;
	private String localizedName;
	private Rarity baseRarity;
	private double minAmount;
	private double maxAmount;
	
	public static final RandomCollection<JewelryAttribute> RANDOM_ATTRIBUTES = new RandomCollection<JewelryAttribute>();
		
	JewelryAttribute(String name, String localizedName, Rarity baseRarity, double min, double max)
	{
		this.name = name;
		this.localizedName = localizedName;
		this.baseRarity = baseRarity;
		this.minAmount = min;
		this.maxAmount = max;
	}
	
	public static JewelryAttribute getRandomAttribute(Random rand)
	{
		return RANDOM_ATTRIBUTES.next(rand);
	}
	
	public static JewelryAttribute getRandomActiveAttribute(NBTTagCompound nbt)
	{
		List<JewelryAttribute> list = Lists.newArrayList();
		
		for (JewelryAttribute attribute : JewelryAttribute.values())
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
		
		// ints
		if (this == STRENGTH || this == AGILITY || this == DEXTERITY || this == INTELLIGENCE || this == WISDOM || this == FORTITUDE || this == ALL_STATS)
		{
			int base = (int) ((Math.random() * (maxAmount - minAmount)) + minAmount);
			nbt.setDouble(name + "_attribute_stat", base);
		}
		// percentages
		else if (this == FIRE_RESIST || this == FROST_RESIST || this == LIGHTNING_RESIST || this == POISON_RESIST || this == LIFE_STEAL || this == MANA_STEAL)
		{
			double base = (Math.random() * (maxAmount - minAmount)) + minAmount;
			double amount = (Math.round(base * 20)) / 20.0;
			
			nbt.setDouble(name + "_attribute_stat", amount);
		}
		// void
		else if (this == VOID)
		{
			double base = (Math.random() * (maxAmount - minAmount)) + minAmount;
			nbt.setDouble(name + "_attribute_stat", base);
		}
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
		else if (nbt.getInteger(name + "_Rarity") == 4) return Rarity.EPIC;
		else if (nbt.getInteger(name + "_Rarity") == 5) return Rarity.LEGENDARY;
		else return Rarity.DEFAULT;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public String getName()
	{
		return name;
	}
	
	public String getLocalizedName()
	{
		return localizedName;
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
		for (JewelryAttribute attribute : JewelryAttribute.values())
		{
			if (attribute.getBaseRarity().getChance() > 0.0D)
			{
				RANDOM_ATTRIBUTES.add(attribute.getBaseRarity().getChance(), attribute);
			}
		}
	}
}

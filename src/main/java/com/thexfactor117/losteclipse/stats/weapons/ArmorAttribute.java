package com.thexfactor117.losteclipse.stats.weapons;

import java.util.Random;

import com.thexfactor117.losteclipse.util.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum ArmorAttribute 
{
	MOVE_SPEED("Movement Speed", Rarity.COMMON, 0.05, 0.5),
	STRENGTH("Strength", Rarity.COMMON, 1, 8),
	AGILITY("Agility", Rarity.COMMON, 1, 8),
	DEXTERITY("Dexterity", Rarity.COMMON, 1, 8),
	INTELLIGENCE("Intelligence", Rarity.COMMON, 1, 8),
	WISDOM("Wisdom", Rarity.COMMON, 1, 8),
	FORTITUDE("Fortitude", Rarity.COMMON, 1, 8),
	DURABLE("Durable", Rarity.COMMON, 0.05, 0.5),
	FIRE_RESIST("Fire Resistance", Rarity.UNCOMMON, 0.05, 1),
	FROST_RESIST("Frost Resistance", Rarity.UNCOMMON, 0.05, 1),
	LIGHTNING_RESIST("Lightning Resistance", Rarity.UNCOMMON, 0.05, 1),
	POISON_RESIST("Poison Resistance", Rarity.UNCOMMON, 0.05, 1),
	ANCIENT("Ancient", Rarity.RARE, 4, 12);
	
	private String name;
	private Rarity baseRarity;
	private double minAmount;
	private double maxAmount;
	
	private static final RandomCollection<ArmorAttribute> RANDOM_ATTRIBUTES = new RandomCollection<ArmorAttribute>();
	
	ArmorAttribute(String name, Rarity baseRarity, double min, double max)
	{
		this.name = name;
		this.baseRarity = baseRarity;
		this.minAmount = min;
		this.maxAmount = max;
	}
	
	public static ArmorAttribute getRandomAttribute(Random rand)
	{
		return RANDOM_ATTRIBUTES.next(rand);
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
		else if (nbt.getInteger(name + "_Rarity") == 5) return Rarity.MYTHIC;
		else return Rarity.DEFAULT;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public String getName()
	{
		return name;
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
		for (ArmorAttribute attribute : ArmorAttribute.values())
		{
			if (attribute.getBaseRarity().getChance() > 0.0D)
			{
				RANDOM_ATTRIBUTES.add(attribute.getBaseRarity().getChance(), attribute);
			}
		}
	}
}

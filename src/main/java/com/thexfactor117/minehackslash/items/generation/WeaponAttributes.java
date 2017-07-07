package com.thexfactor117.minehackslash.items.generation;

import java.util.Random;

import com.thexfactor117.minehackslash.util.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum WeaponAttributes
{
	FIRE("Fire", Rarity.COMMON),
	FROST("Frost", Rarity.COMMON),
	LIGHTNING("Lightning", Rarity.COMMON),
	POISON("Poison", Rarity.COMMON),
	ETHEREAL("Ethereal", Rarity.UNCOMMON),
	MAGICAL("Magical", Rarity.UNCOMMON),
	MIN_DAMAGE("Minimum Damage", Rarity.UNCOMMON),
	MAX_DAMAGE("Maximum Damage", Rarity.UNCOMMON),
	CHAINED("Chained", Rarity.RARE),
	VOID("Void", Rarity.RARE),
	
	STRENGTH("Strength", Rarity.COMMON),
	AGILITY("Agility", Rarity.COMMON),
	DEXTERITY("Dexterity", Rarity.COMMON),
	INTELLIGENCE("Intelligence", Rarity.COMMON),
	WISDOM("Wisdom", Rarity.COMMON),
	FORTITUDE("Fortitude", Rarity.COMMON),
	DURABLE("Durable", Rarity.COMMON),
	SHINY("Shiny", Rarity.UNCOMMON),
	ANCIENT("Ancient", Rarity.RARE);
	
	private String name;
	private Rarity baseRarity;
	
	private static final RandomCollection<WeaponAttributes> RANDOM_ATTRIBUTES = new RandomCollection<WeaponAttributes>();
	
	WeaponAttributes(String name, Rarity baseRarity)
	{
		this.name = name;
		this.baseRarity = baseRarity;
	}
	
	public static WeaponAttributes getRandomAttribute(Random rand)
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
	
	/**
	 * Adds the specified Attribute to the NBT tag compound.
	 * @param nbt
	 */
	public void addAttribute(NBTTagCompound nbt)
	{
		nbt.setBoolean(toString(), true);
		nbt.setInteger(name + "_Rarity", Rarity.getRandomRarity(nbt, new Random()).ordinal()); // sets the Attribute randomized rarity (how effective the attribute will be).
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
	
	public String getName()
	{
		return name;
	}
	
	public Rarity getBaseRarity()
	{
		return baseRarity;
	}
	
	static
	{
		for (WeaponAttributes attribute : WeaponAttributes.values())
		{
			if (attribute.getBaseRarity().getChance() > 0.0D)
			{
				RANDOM_ATTRIBUTES.add(attribute.getBaseRarity().getChance(), attribute);
			}
		}
	}
}

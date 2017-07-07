package com.thexfactor117.minehackslash.items.generation;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum ArmorAttributes 
{
	MOVE_SPEED("Movement Speed", Rarity.COMMON),
	STRENGTH("Strength", Rarity.COMMON),
	AGILITY("Agility", Rarity.COMMON),
	DEXTERITY("Dexterity", Rarity.COMMON),
	INTELLIGENCE("Intelligence", Rarity.COMMON),
	WISDOM("Wisdom", Rarity.COMMON),
	FORTITUDE("Fortitude", Rarity.COMMON),
	DURABLE("Durable", Rarity.COMMON),
	DEFENSE("Defense", Rarity.UNCOMMON),
	FIRE_RESIST("Fire Resistance", Rarity.UNCOMMON),
	FROST_RESIST("Frost Resistance", Rarity.UNCOMMON),
	LIGHTNING_RESIST("Lightning Resistance", Rarity.UNCOMMON),
	POISON_RESIST("Poison Resistance", Rarity.UNCOMMON),
	ANCIENT("Ancient", Rarity.RARE);
	
	private String name;
	private Rarity baseRarity;
	
	ArmorAttributes(String name, Rarity baseRarity)
	{
		this.name = name;
		this.baseRarity = baseRarity;
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
		nbt.setInteger(name + "_Rarity", Rarity.getRandomRarity(nbt, new Random()).ordinal());
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
}

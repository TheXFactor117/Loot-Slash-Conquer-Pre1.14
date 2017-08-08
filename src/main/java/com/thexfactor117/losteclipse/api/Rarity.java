package com.thexfactor117.losteclipse.api;

import java.util.Random;

import com.thexfactor117.losteclipse.util.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum Rarity 
{
	DEFAULT("default", TextFormatting.DARK_GRAY, 0),
	COMMON("Common", TextFormatting.WHITE, 0.6),
	UNCOMMON("Uncommon", TextFormatting.DARK_GREEN, 0.2),
	RARE("Rare", TextFormatting.AQUA, 0.12),
	LEGENDARY("Legendary", TextFormatting.DARK_PURPLE, 0.07),
	EXOTIC("Exotic", TextFormatting.GOLD, 0.01);
	
	private String name;
	private String color;
	private double chance;
	
	private static final RandomCollection<Rarity> RANDOM_RARITIES = new RandomCollection<Rarity>();
	
	Rarity(String name, Object color, double chance)
	{
		this.name = name;
		this.color = color.toString();
		this.chance = chance;
	}
	
	/**
	 * Returns a randomized rarity.
	 * @param nbt
	 * @param rand
	 * @return
	 */
	public static Rarity getRandomRarity(NBTTagCompound nbt, Random rand)
	{	
		return RANDOM_RARITIES.next(rand);
	}
	
	/**
	 * Return the current rarity in the given NBTTagCompound. Returns Default if it can't find it.
	 * @param nbt
	 * @return
	 */
	public static Rarity getRarity(NBTTagCompound nbt)
	{
		return nbt.hasKey("RARITY") ? Rarity.values()[nbt.getInteger("RARITY")] : DEFAULT;
	}
	
	/**
	 * Sets the rarity specified to the given NBTTagCompound.
	 * @param nbt
	 * @param rarity
	 */
	public static void setRarity(NBTTagCompound nbt, Rarity rarity)
	{
		nbt.setInteger("RARITY", rarity.ordinal());
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public String getName()
	{
		return name;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public double getChance()
	{
		return chance;
	}
	
	static
	{
		for (Rarity rarity : Rarity.values())
		{
			if (rarity.chance > 0.0D)
			{
				RANDOM_RARITIES.add(rarity.chance, rarity);
			}
		}
	}
}

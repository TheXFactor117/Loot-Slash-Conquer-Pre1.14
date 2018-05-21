package com.lsc.loot;

import java.util.Random;

import com.lsc.util.RandomCollection;

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
	COMMON("Common", TextFormatting.WHITE, 80),
	UNCOMMON("Uncommon", TextFormatting.DARK_GREEN, 10),
	RARE("Rare", TextFormatting.AQUA, 6),
	EPIC("Epic", TextFormatting.DARK_PURPLE, 3),
	LEGENDARY("Legendary", TextFormatting.GOLD, 1);
	
	private String name;
	private String color;
	private double chance;
	
	private static final int MULTIPLIER = 15;
	
	private static final RandomCollection<Rarity> RANDOM_RARITIES = new RandomCollection<Rarity>();
	
	private static final RandomCollection<Rarity> COMMON_RARITIES = new RandomCollection<Rarity>();
	private static final RandomCollection<Rarity> UNCOMMON_RARITIES = new RandomCollection<Rarity>();
	private static final RandomCollection<Rarity> RARE_RARITIES = new RandomCollection<Rarity>();
	private static final RandomCollection<Rarity> EPIC_RARITIES = new RandomCollection<Rarity>();
	private static final RandomCollection<Rarity> LEGENDARY_RARITIES = new RandomCollection<Rarity>();
	
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
	
	public static Rarity getWeightedRarity(NBTTagCompound nbt, Random rand, Rarity rarity)
	{
		if (rarity == Rarity.COMMON) return COMMON_RARITIES.next(rand);
		else if (rarity == Rarity.UNCOMMON) return UNCOMMON_RARITIES.next(rand);
		else if (rarity == Rarity.RARE) return RARE_RARITIES.next(rand);
		else if (rarity == Rarity.EPIC) return EPIC_RARITIES.next(rand);
		else if (rarity == Rarity.LEGENDARY) return LEGENDARY_RARITIES.next(rand);
		
		return Rarity.DEFAULT;
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
				
				if (rarity == Rarity.COMMON)
				{
					COMMON_RARITIES.add(rarity.chance, rarity);
					UNCOMMON_RARITIES.add(rarity.chance - (MULTIPLIER * 2), rarity);
					RARE_RARITIES.add(rarity.chance - (MULTIPLIER * 3), rarity);
					EPIC_RARITIES.add(rarity.chance - (MULTIPLIER * 4), rarity);
					LEGENDARY_RARITIES.add(rarity.chance - (MULTIPLIER * 5), rarity);
				}
				else
				{
					COMMON_RARITIES.add(rarity.chance, rarity);
					UNCOMMON_RARITIES.add(rarity.chance, rarity);
					RARE_RARITIES.add(rarity.chance, rarity);
					EPIC_RARITIES.add(rarity.chance, rarity);
					LEGENDARY_RARITIES.add(rarity.chance, rarity);
				}
			}
		}
	}
}

package com.thexfactor117.lsc.loot;

import java.util.Random;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.util.misc.RandomCollection;

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
	COMMON("Common", TextFormatting.WHITE, Configs.weaponCategory.commonChance),
	UNCOMMON("Uncommon", TextFormatting.DARK_GREEN, Configs.weaponCategory.uncommonChance),
	RARE("Rare", TextFormatting.AQUA, Configs.weaponCategory.rareChance),
	EPIC("Epic", TextFormatting.DARK_PURPLE, Configs.weaponCategory.epicChance),
	LEGENDARY("Legendary", TextFormatting.GOLD, Configs.weaponCategory.legendaryChance);
	
	private String name;
	private String color;
	private double chance;

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
	
	public static Rarity getWeightedRarity(Random rand, Rarity rarity)
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
					COMMON_RARITIES.add(62, rarity);
					UNCOMMON_RARITIES.add(20, rarity);
					RARE_RARITIES.add(10, rarity);
					EPIC_RARITIES.add(5, rarity);
					LEGENDARY_RARITIES.add(2, rarity);
				}
				else if (rarity == Rarity.UNCOMMON)
				{
					COMMON_RARITIES.add(20, rarity);
					UNCOMMON_RARITIES.add(40, rarity);
					RARE_RARITIES.add(20, rarity);
					EPIC_RARITIES.add(15, rarity);
					LEGENDARY_RARITIES.add(10, rarity);
				}
				else if (rarity == Rarity.RARE)
				{
					COMMON_RARITIES.add(10, rarity);
					UNCOMMON_RARITIES.add(20, rarity);
					RARE_RARITIES.add(40, rarity);
					EPIC_RARITIES.add(20, rarity);
					LEGENDARY_RARITIES.add(20, rarity);
				}
				else if (rarity == Rarity.EPIC)
				{
					COMMON_RARITIES.add(6, rarity);
					UNCOMMON_RARITIES.add(15, rarity);
					RARE_RARITIES.add(20, rarity);
					EPIC_RARITIES.add(40, rarity);
					LEGENDARY_RARITIES.add(30, rarity);
				}
				else if (rarity == Rarity.LEGENDARY)
				{
					COMMON_RARITIES.add(2, rarity);
					UNCOMMON_RARITIES.add(5, rarity);
					RARE_RARITIES.add(18, rarity);
					EPIC_RARITIES.add(20, rarity);
					LEGENDARY_RARITIES.add(40, rarity);
				}
			}
		}
	}
}

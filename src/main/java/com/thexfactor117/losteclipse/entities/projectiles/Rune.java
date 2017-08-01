package com.thexfactor117.losteclipse.entities.projectiles;

import java.util.Random;

import com.thexfactor117.losteclipse.util.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum Rune 
{
	DEFAULT("default", TextFormatting.GRAY, 0),
	FIREBALL("Fireball", TextFormatting.RED, 2),
	ICEBOLT("Icebolt", TextFormatting.AQUA, 2),
	LIGHTNING("Lightning", TextFormatting.DARK_PURPLE, 2),
	FIRESTORM("Firestorm", TextFormatting.RED, 1),
	BLIZZARD("Blizzard", TextFormatting.AQUA, 1),
	DISCHARGE("Discharge", TextFormatting.DARK_PURPLE, 1);
	
	private String name;
	private String color;
	private double chance;
	
	private static final RandomCollection<Rune> RANDOM_RUNES = new RandomCollection<Rune>();
	
	Rune(String name, Object color, double chance)
	{
		this.name = name;
		this.color = color.toString();
		this.chance = chance;
	}
	
	public static Rune getRandomRune(NBTTagCompound nbt, Random rand)
	{
		return RANDOM_RUNES.next(rand);
	}
	
	public static Rune getRune(NBTTagCompound nbt)
	{
		return nbt.hasKey("Rune") ? Rune.values()[nbt.getInteger("Rune")] : DEFAULT;
	}
	
	public static void setRune(NBTTagCompound nbt, Rune rune)
	{
		nbt.setInteger("Rune", rune.ordinal());
	}
	
	public static void fireRune()
	{
		
	}
	
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
		for (Rune rune : Rune.values())
		{
			if (rune.chance > 0.0D)
			{
				RANDOM_RUNES.add(rune.chance, rune);
			}
		}
	}
}

package com.lsc.entities;

import java.util.Random;

import com.lsc.capabilities.enemylevel.EnemyInfo;
import com.lsc.util.RandomCollection;

import net.minecraft.util.text.TextFormatting;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum EnemyTier 
{
	DEFAULT("default", TextFormatting.DARK_GRAY, 0),
	NORMAL("Normal", TextFormatting.WHITE, 50),
	HARDENED("Hardened", TextFormatting.DARK_GREEN, 20),
	SUPERIOR("Superior", TextFormatting.AQUA, 15),
	ELITE("Elite", TextFormatting.DARK_PURPLE, 10),
	LEGENDARY("Legendary", TextFormatting.GOLD, 5);
	
	public String name;
	public Object color;
	public double chance;
	
	private static final RandomCollection<EnemyTier> RANDOM_TIERS = new RandomCollection<EnemyTier>();
	
	EnemyTier(String name, Object color, double chance)
	{
		this.name = name;
		this.color = color.toString();
		this.chance = chance;
	}
	
	public static EnemyTier getRandomEnemyTier(Random rand)
	{
		return RANDOM_TIERS.next(rand);
	}
	
	public static EnemyTier getEnemyTier(EnemyInfo info)
	{
		if (info.getEnemyTier() == 1) return NORMAL;
		else if (info.getEnemyTier() == 2) return HARDENED;
		else if (info.getEnemyTier() == 3) return SUPERIOR;
		else if (info.getEnemyTier() == 4) return ELITE;
		else if (info.getEnemyTier() == 5) return LEGENDARY;
		else return DEFAULT;
	}
	
	public static void setEnemyTier(EnemyInfo info, EnemyTier tier)
	{
		if (tier == NORMAL) info.setEnemyTier(1);
		else if (tier == HARDENED) info.setEnemyTier(2);
		else if (tier == SUPERIOR) info.setEnemyTier(3);
		else if (tier == ELITE) info.setEnemyTier(4);
		else if (tier == LEGENDARY) info.setEnemyTier(5);
	}
	
	public String getName()
	{
		return name;
	}
	
	public Object getColor()
	{
		return color;
	}
	
	public double getChance()
	{
		return chance;
	}
	
	static
	{
		for (EnemyTier tier : EnemyTier.values())
		{
			if (tier.chance > 0.0D)
			{
				RANDOM_TIERS.add(tier.chance, tier);
			}
		}
	}
}

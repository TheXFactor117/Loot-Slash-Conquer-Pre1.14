package thexfactor117.lsc.entities;

import java.util.Random;

import net.minecraft.util.text.TextFormatting;
import thexfactor117.lsc.capabilities.implementation.EnemyInfo;
import thexfactor117.lsc.config.Configs;
import thexfactor117.lsc.util.RandomCollection;

/**
 * 
 * @author TheXFactor117
 *
 */
public enum EnemyTier 
{
	DEFAULT("default", TextFormatting.DARK_GRAY, 0),
	NORMAL("Normal", TextFormatting.WHITE, Configs.monsterLevelTierCategory.normalChance),
	HARDENED("Hardened", TextFormatting.DARK_GREEN, Configs.monsterLevelTierCategory.hardenedChance),
	SUPERIOR("Superior", TextFormatting.AQUA, Configs.monsterLevelTierCategory.superiorChance),
	ELITE("Elite", TextFormatting.DARK_PURPLE, Configs.monsterLevelTierCategory.eliteChance),
	LEGENDARY("Legendary", TextFormatting.GOLD, Configs.monsterLevelTierCategory.legendaryChance);
	
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
		switch (info.getEnemyTier())
		{
			case 1:
				return NORMAL;
			case 2:
				return HARDENED;
			case 3:
				return SUPERIOR;
			case 4:
				return ELITE;
			case 5:
				return LEGENDARY;
			default:
				return DEFAULT;
		}
	}
	
	public static void setEnemyTier(EnemyInfo info, EnemyTier tier)
	{
		switch (tier)
		{
			case NORMAL:
				info.setEnemyTier(1);
				break;
			case HARDENED:
				info.setEnemyTier(2);
				break;
			case SUPERIOR:
				info.setEnemyTier(3);
				break;
			case ELITE:
				info.setEnemyTier(4);
				break;
			case LEGENDARY:
				info.setEnemyTier(5);
				break;
			default:
				info.setEnemyTier(0);
				break;
		}
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

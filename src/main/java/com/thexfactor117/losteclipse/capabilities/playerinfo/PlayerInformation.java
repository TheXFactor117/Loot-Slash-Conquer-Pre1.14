package com.thexfactor117.losteclipse.capabilities.playerinfo;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PlayerInformation implements IPlayerInformation
{
	private int playerClass;
	private int level;
	private int experience;
	private int skillPoints;
	
	// stats
	private int strengthStat;
	private int agilityStat;
	private int dexterityStat;
	private int intelligenceStat;
	private int wisdomStat;
	private int fortitudeStat;
	
	private int strengthBonusStat;
	private int agilityBonusStat;
	private int dexterityBonusStat;
	private int intelligenceBonusStat;
	private int wisdomBonusStat;
	private int fortitudeBonusStat;
	
	@SuppressWarnings("unused")
	private final EntityLivingBase entity;
	
	public PlayerInformation(@Nullable EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
	/** Returns the amount of experience needed to level up given the current level. */
	public int getLevelUpExperience(int currentLevel) 
	{
		return (int) Math.pow(currentLevel, 2) * 30;
	}
	
	/** Sets all bonus stats to zero. */
	public void removeBonusStats()
	{
		this.strengthBonusStat = 0;
		this.agilityBonusStat = 0;
		this.dexterityBonusStat = 0;
		this.intelligenceBonusStat = 0;
		this.wisdomBonusStat = 0;
		this.fortitudeBonusStat = 0;
	}
	
	public int getTotalStrength()
	{
		return this.strengthStat + this.strengthBonusStat;
	}
	
	public int getTotalAgility()
	{
		return this.agilityStat + this.agilityBonusStat;
	}
	
	public int getTotalDexterity()
	{
		return this.dexterityStat + this.dexterityBonusStat;
	}
	
	public int getTotalIntelligence()
	{
		return this.intelligenceStat + this.intelligenceBonusStat;
	}
	
	public int getTotalWisdom()
	{
		return this.wisdomStat + this.wisdomBonusStat;
	}
	
	public int getTotalFortitude()
	{
		return this.fortitudeStat + this.fortitudeBonusStat;
	}
	
	
	
	/*
	 * 
	 * GETTERS AND SETTERS
	 *
	 */
	
	@Override
	public int getPlayerClass() 
	{
		return playerClass;
	}

	@Override
	public void setPlayerClass(int playerClass) 
	{
		this.playerClass = playerClass;
	}

	@Override
	public int getPlayerLevel() 
	{
		return level;
	}

	@Override
	public void setPlayerLevel(int level) 
	{
		this.level = level;
	}

	@Override
	public int getPlayerExperience() 
	{
		return experience;
	}

	@Override
	public void setPlayerExperience(int experience) 
	{
		this.experience = experience;
	}

	@Override
	public int getSkillPoints() 
	{
		return skillPoints;
	}

	@Override
	public void setSkillPoints(int skillPoints) 
	{
		this.skillPoints = skillPoints;
	}

	/*
	 * STATS
	 */
	
	@Override
	public int getStrengthStat() 
	{
		return strengthStat;
	}

	@Override
	public void setStrengthStat(int stat) 
	{
		this.strengthStat = stat;
	}

	@Override
	public int getAgilityStat() 
	{
		return agilityStat;
	}

	@Override
	public void setAgilityStat(int stat) 
	{
		this.agilityStat = stat;
	}

	@Override
	public int getDexterityStat() 
	{
		return dexterityStat;
	}

	@Override
	public void setDexterityStat(int stat) 
	{
		this.dexterityStat = stat;
	}

	@Override
	public int getIntelligenceStat() 
	{
		return intelligenceStat;
	}

	@Override
	public void setIntelligenceStat(int stat) 
	{
		this.intelligenceStat = stat;
	}

	@Override
	public int getWisdomStat() 
	{
		return wisdomStat;
	}

	@Override
	public void setWisdomStat(int stat) 
	{
		this.wisdomStat = stat;
	}

	@Override
	public int getFortitudeStat() 
	{
		return fortitudeStat;
	}

	@Override
	public void setFortitudeStat(int stat) 
	{
		this.fortitudeStat = stat;
	}
	
	// bonuses
	
	@Override
	public int getBonusStrengthStat() 
	{
		return strengthBonusStat;
	}

	@Override
	public void setBonusStrengthStat(int stat) 
	{
		this.strengthBonusStat = stat;
	}

	@Override
	public int getBonusAgilityStat() 
	{
		return agilityBonusStat;
	}

	@Override
	public void setBonusAgilityStat(int stat) 
	{
		this.agilityBonusStat = stat;
	}

	@Override
	public int getBonusDexterityStat() 
	{
		return dexterityBonusStat;
	}

	@Override
	public void setBonusDexterityStat(int stat) 
	{
		this.dexterityBonusStat = stat;
	}

	@Override
	public int getBonusIntelligenceStat() 
	{
		return intelligenceBonusStat;
	}

	@Override
	public void setBonusIntelligenceStat(int stat) 
	{
		this.intelligenceBonusStat = stat;
	}

	@Override
	public int getBonusWisdomStat() 
	{
		return wisdomBonusStat;
	}

	@Override
	public void setBonusWisdomStat(int stat) 
	{
		this.wisdomBonusStat = stat;
	}

	@Override
	public int getBonusFortitudeStat() 
	{
		return fortitudeBonusStat;
	}

	@Override
	public void setBonusFortitudeStat(int stat) 
	{
		this.fortitudeBonusStat = stat;
	}
}

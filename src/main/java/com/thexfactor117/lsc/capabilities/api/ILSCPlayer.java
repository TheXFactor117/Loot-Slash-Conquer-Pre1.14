package com.thexfactor117.lsc.capabilities.api;

/**
 *
 * @author TheXFactor117
 *
 */
public interface ILSCPlayer
{
	/*
	 * Basic Player Info
	 */
	int getPlayerClass();
	void setPlayerClass(int playerClass);
	
	int getPlayerLevel();
	void setPlayerLevel(int level);
	
	int getPlayerExperience();
	void setPlayerExperience(int experience);
	
	int getSkillPoints();
	void setSkillPoints(int skillPoints);
	
	/*
	 * Player Modifiers
	 */
	
	// magic
	public void setMana(int mana);
	public int getMana();
	
	public void setMaxMana(int maxMana);
	public int getMaxMana();
	
	public void setManaPerSecond(int manaPerSecond);
	public int getManaPerSecond();
	
	public void setMagicalPower(double power);
	public double getMagicalPower();
	
	// health
	public void setHealthPerSecond(int healthPerSecond);
	public int getHealthPerSecond();
	
	// criticals
	public void setCriticalChance(double criticalChance);
	public double getCriticalChance();
	
	public void setCriticalDamage(double criticalDamage);
	public double getCriticalDamage();

	// update ticks
	public void setUpdateTicks(int ticks);
	public int getUpdateTicks();
	
	public void setRegenTicks(int ticks);
	public int getRegenTicks();
	
	/*
	 * Player Stats
	 */
	int getStrengthStat();
	void setStrengthStat(int stat);
	int getAgilityStat();
	void setAgilityStat(int stat);
	int getDexterityStat();
	void setDexterityStat(int stat);
	int getIntelligenceStat();
	void setIntelligenceStat(int stat);
	int getWisdomStat();
	void setWisdomStat(int stat);
	int getFortitudeStat();
	void setFortitudeStat(int stat);
	
	int getBonusStrengthStat();
	void setBonusStrengthStat(int stat);
	int getBonusAgilityStat();
	void setBonusAgilityStat(int stat);
	int getBonusDexterityStat();
	void setBonusDexterityStat(int stat);
	int getBonusIntelligenceStat();
	void setBonusIntelligenceStat(int stat);
	int getBonusWisdomStat();
	void setBonusWisdomStat(int stat);
	int getBonusFortitudeStat();
	void setBonusFortitudeStat(int stat);
}

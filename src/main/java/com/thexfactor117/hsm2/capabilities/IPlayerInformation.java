package com.thexfactor117.hsm2.capabilities;

/**
 * 
 * @author TheXFactor117
 *
 * Capability interface for storing player information (class, level, experience, stats, etc...)
 *
 */
public interface IPlayerInformation 
{
	String getPlayerClass();
	void setPlayerClass(String playerClass);
	
	int getPlayerLevel();
	void setPlayerLevel(int level);
	
	int getPlayerExperience();
	void setPlayerExperience(int experience);
	int getLevelUpExperience(int currentLevel); // returns the amount of experience needed to level given the current level.
	
	int getSkillPoints();
	void setSkillPoints(int skillPoints);
	
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
}

package com.lsc.capabilities.api;

/**
 * 
 * @author TheXFactor117
 *
 */
public interface IStats 
{
	/*
	 * MANA
	 */
	public void setMana(int mana);
	public int getMana();
	
	public void setMaxMana(int maxMana);
	public int getMaxMana();
	
	public void setManaPerSecond(int manaPerSecond);
	public int getManaPerSecond();
	
	/*
	 * MAGICAL POWER
	 */
	public void setMagicalPower(double power);
	public double getMagicalPower();
	
	/*
	 * HEALTH
	 */
	public void setHealthPerSecond(int healthPerSecond);
	public int getHealthPerSecond();
	
	/*
	 * CRITICAL
	 */
	public void setCriticalChance(double criticalChance);
	public double getCriticalChance();
	
	public void setCriticalDamage(double criticalDamage);
	public double getCriticalDamage();
}

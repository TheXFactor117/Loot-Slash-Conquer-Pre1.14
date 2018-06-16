package com.lsc.capabilities.implementation;

import javax.annotation.Nullable;

import com.lsc.capabilities.api.IStats;

import net.minecraft.entity.EntityLivingBase;

/**
 * 
 * @author TheXFactor117
 *
 */
public class Stats implements IStats
{
	private int mana;
	private int maxMana;
	private int manaPerSecond;
	
	private double magicalPower;
	
	private int healthPerSecond;
	
	private double criticalChance;
	private double criticalDamage;
	
	@SuppressWarnings("unused")
	private final EntityLivingBase entity;
	
	public Stats(@Nullable EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
	/** Increases the current mana count by the given amount. */
	public void increaseMana(int mana)
	{
		this.mana += mana;
		
		if (this.mana > this.maxMana) this.mana = this.maxMana;
	}
	
	/** Decreases the current mana count by the given amount. */
	public void decreaseMana(int mana)
	{
		this.mana -= mana;
		
		if (this.mana < 0) this.mana = 0;
	}
	
	
	
	/*
	 * 
	 * GETTERS AND SETTERS
	 * 
	 */
	
	/*
	 * MANA
	 */
	
	@Override
	public void setMana(int mana) 
	{
		this.mana = mana;
		
		if (mana > getMaxMana()) this.mana = getMaxMana();
		else if (mana < 0) this.mana = 0;
	}

	@Override
	public int getMana() 
	{
		return mana;
	}

	@Override
	public void setMaxMana(int maxMana) 
	{
		this.maxMana = maxMana;
	}

	@Override
	public int getMaxMana() 
	{
		return maxMana;
	}

	@Override
	public void setManaPerSecond(int manaPerSecond) 
	{
		this.manaPerSecond = manaPerSecond;
	}

	@Override
	public int getManaPerSecond() 
	{
		return manaPerSecond;
	}
	
	/*
	 * MAGICAL POWER
	 */
	@Override
	public void setMagicalPower(double power)
	{
		this.magicalPower = power;
	}
	
	@Override
	public double getMagicalPower()
	{
		return magicalPower;
	}
	
	/*
	 * HEALTH
	 */

	@Override
	public void setHealthPerSecond(int healthPerSecond) 
	{
		this.healthPerSecond = healthPerSecond;
	}

	@Override
	public int getHealthPerSecond() 
	{
		return healthPerSecond;
	}

	/*
	 * CRITICAL
	 */
	
	@Override
	public void setCriticalChance(double criticalChance) 
	{
		this.criticalChance = criticalChance;
	}

	@Override
	public double getCriticalChance() 
	{
		return criticalChance;
	}

	@Override
	public void setCriticalDamage(double criticalDamage) 
	{
		this.criticalDamage = criticalDamage;
	}

	@Override
	public double getCriticalDamage() 
	{
		return criticalDamage;
	}
}

package com.thexfactor117.losteclipse.capabilities;

import javax.annotation.Nullable;

import com.thexfactor117.losteclipse.capabilities.api.IStats;

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
	
	private int healthPerSecond;
	
	private double criticalChance;
	private double criticalDamage;
	
	@SuppressWarnings("unused")
	private final EntityLivingBase entity;
	
	public Stats(@Nullable EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
	/*
	 * MANA
	 */
	
	@Override
	public void setMana(int mana) 
	{
		this.mana = mana;
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

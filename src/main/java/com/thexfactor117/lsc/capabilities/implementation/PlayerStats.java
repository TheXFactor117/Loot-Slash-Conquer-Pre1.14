package com.thexfactor117.lsc.capabilities.implementation;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.capabilities.api.IPlayerStats;

import net.minecraft.entity.EntityLivingBase;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PlayerStats implements IPlayerStats
{
	private int mana;
	private int maxMana;
	private int manaPerSecond;
	
	private double magicalPower;
	
	private int healthPerSecond;
	
	private double criticalChance;
	private double criticalDamage;
	
	private int updateTicks;
	private int regenTicks;
	
	@SuppressWarnings("unused")
	private final EntityLivingBase entity;
	
	public PlayerStats(@Nullable EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
	/** Increases the current mana count by the given amount. */
	public void increaseMana(int mana)
	{
		this.setMana(this.getMana() + mana);
		
		if (this.mana > this.maxMana) this.mana = this.maxMana;
	}
	
	/** Decreases the current mana count by the given amount. */
	public void decreaseMana(int mana)
	{
		this.setMana(this.getMana() - mana);
		
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
	
	/*
	 * TICKS
	 */
	
	public void incrementUpdateTicks()
	{
		this.updateTicks += 1;
	}
	
	public void incrementRegenTicks()
	{
		this.regenTicks += 1;
	}
	
	public void resetUpdateTicks()
	{
		this.updateTicks = 0;
	}
	
	public void resetRegenTicks()
	{
		this.regenTicks = 0;
	}
	
	@Override
	public void setUpdateTicks(int ticks)
	{
		this.updateTicks = ticks;
	}
	
	@Override
	public int getUpdateTicks()
	{
		return updateTicks;
	}
	
	@Override
	public void setRegenTicks(int ticks)
	{
		this.regenTicks = ticks;
	}
	
	@Override
	public int getRegenTicks()
	{
		return regenTicks;
	}
}

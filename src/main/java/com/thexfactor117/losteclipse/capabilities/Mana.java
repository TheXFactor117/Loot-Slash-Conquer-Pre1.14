package com.thexfactor117.losteclipse.capabilities;

import javax.annotation.Nullable;

import com.thexfactor117.losteclipse.capabilities.api.IMana;

import net.minecraft.entity.EntityLivingBase;

/**
 * 
 * @author TheXFactor117
 *
 */
public class Mana implements IMana
{
	private int mana;
	private int maxMana;
	private int manaPerSecond;
	
	@SuppressWarnings("unused")
	private final EntityLivingBase entity;
	
	public Mana(@Nullable EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
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
}

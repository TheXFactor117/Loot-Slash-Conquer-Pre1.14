package com.lsc.capabilities.enemylevel;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;

public class EnemyInfo implements IEnemyInfo
{
	private int level;
	private int tier;
	
	@SuppressWarnings("unused")
	private final EntityLivingBase entity;
	
	public EnemyInfo(@Nullable EntityLivingBase entity)
	{
		this.entity = entity;
	}

	@Override
	public int getEnemyLevel() 
	{
		return level;
	}

	@Override
	public void setEnemyLevel(int enemyLevel) 
	{
		level = enemyLevel;
	}

	@Override
	public int getEnemyTier() 
	{
		return tier;
	}

	@Override
	public void setEnemyTier(int enemyTier) 
	{
		tier = enemyTier;
	}
}

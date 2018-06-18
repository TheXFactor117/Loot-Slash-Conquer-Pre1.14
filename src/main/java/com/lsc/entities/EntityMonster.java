package com.lsc.entities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityMonster extends EntityMob
{
	public EntityMonster(World world)
	{
		super(world);
	}
	
	@Override
	protected void initEntityAI()
	{
		this.tasks.taskEntries.clear();
		this.targetTasks.taskEntries.clear();
	}
	
	@Override
	public boolean getCanSpawnHere()
	{
		int chance = this.world.isDaytime() ? 4 : 1;
		
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && (int) (Math.random() * chance) == 0;
	}
	
	@Override
	public boolean canDespawn()
	{
		return true;
	}
	
	@Override
	public boolean isValidLightLevel()
	{
		return true;
	}
}

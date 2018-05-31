package com.lsc.entities;

import net.minecraft.entity.monster.EntityMob;
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

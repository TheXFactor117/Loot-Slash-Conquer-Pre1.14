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
	public static int rarity;
	
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
		// TODO: make mobs spawn less often in lower level areas.
		/*IChunkLevelHolder chunkLevelHolder = this.world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		int chance = 0;
		
		if (chunkLevelHolder != null)
		{
			IChunkLevel chunkLevelCap = chunkLevelHolder.getChunkLevel(new ChunkPos(this.getPosition()));
			int chunkLevel = chunkLevelCap.getChunkLevel();
			
			chance = this.world.isDaytime() ? (int) (4 / (chunkLevel * 0.3) * 2) : 4;
		}*/

		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel();
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

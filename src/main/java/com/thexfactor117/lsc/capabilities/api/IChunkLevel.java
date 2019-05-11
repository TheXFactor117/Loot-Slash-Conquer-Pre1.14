package com.thexfactor117.lsc.capabilities.api;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public interface IChunkLevel 
{
	public World getWorld();
	
	public ChunkPos getChunkPos();
	
	public int getChunkLevel();
}

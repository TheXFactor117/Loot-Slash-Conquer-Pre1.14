package com.thexfactor117.losteclipse.capabilities.chunk;

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

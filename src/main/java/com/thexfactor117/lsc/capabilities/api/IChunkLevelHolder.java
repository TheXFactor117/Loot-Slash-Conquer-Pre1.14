package com.thexfactor117.lsc.capabilities.api;

import net.minecraft.util.math.ChunkPos;

/**
 * 
 * @author TheXFactor117
 *
 */
public interface IChunkLevelHolder 
{
	public IChunkLevel getChunkLevel(ChunkPos pos);
	
	public void setChunkLevel(ChunkPos pos, IChunkLevel level);
	
	public void removeChunkLevel(ChunkPos pos);
}

package com.lsc.capabilities.implementation;

import java.util.HashMap;
import java.util.Map;

import com.lsc.capabilities.api.IChunkLevel;
import com.lsc.capabilities.api.IChunkLevelHolder;

import net.minecraft.util.math.ChunkPos;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ChunkLevelHolder implements IChunkLevelHolder
{
	private final Map<ChunkPos, IChunkLevel> chunkLevels = new HashMap<>();

	@Override
	public IChunkLevel getChunkLevel(ChunkPos pos) 
	{
		return chunkLevels.get(pos);
	}

	@Override
	public void setChunkLevel(ChunkPos pos, IChunkLevel level) 
	{
		chunkLevels.put(pos, level);
	}

	@Override
	public void removeChunkLevel(ChunkPos pos) 
	{
		chunkLevels.remove(pos);
	}
}

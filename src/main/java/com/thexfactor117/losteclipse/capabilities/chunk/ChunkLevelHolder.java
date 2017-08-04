package com.thexfactor117.losteclipse.capabilities.chunk;

import java.util.HashMap;
import java.util.Map;

import com.thexfactor117.losteclipse.capabilities.api.IChunkLevel;
import com.thexfactor117.losteclipse.capabilities.api.IChunkLevelHolder;

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

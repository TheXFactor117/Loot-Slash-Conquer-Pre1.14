package thexfactor117.lsc.capabilities.implementation;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.math.ChunkPos;
import thexfactor117.lsc.capabilities.api.IChunkLevel;
import thexfactor117.lsc.capabilities.api.IChunkLevelHolder;

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

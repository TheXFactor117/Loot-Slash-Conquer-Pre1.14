package com.thexfactor117.lsc.capabilities.implementation;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;

import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ChunkLevel implements IChunkLevel, INBTSerializable<NBTTagInt>
{
	private World world;
	private ChunkPos pos;
	
	private int level;
	
	public ChunkLevel(World world, ChunkPos pos, int level)
	{
		this.world = world;
		this.pos = pos;
		this.level = level;
	}
	
	@Override
	public NBTTagInt serializeNBT() 
	{
		return new NBTTagInt(level);
	}

	@Override
	public void deserializeNBT(NBTTagInt nbt) 
	{
		level = nbt.getInt();
	}
	
	@Override
	public World getWorld() 
	{
		return world;
	}

	@Override
	public ChunkPos getChunkPos() 
	{
		return pos;
	}
	
	@Override
	public int getChunkLevel()
	{
		return level;
	}
}

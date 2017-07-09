package com.thexfactor117.minehackslash.worldgen.procedural;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PotentialPosition 
{
	private BlockPos pos;
	private Rotation rotation;
	
	public PotentialPosition(BlockPos pos, Rotation rotation)
	{
		this.pos = pos;
		this.rotation = rotation;
	}
	
	public void setPosition(BlockPos pos, Rotation rotation)
	{
		this.pos = pos;
		this.rotation = rotation;
	}
	
	public BlockPos getPos()
	{
		return pos;
	}
	
	public Rotation getRotation()
	{
		return rotation;
	}
}

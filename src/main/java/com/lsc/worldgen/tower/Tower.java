package com.lsc.worldgen.tower;

import java.util.Random;

import com.lsc.worldgen.StructureHelper;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * 
 * @author TheXFactor117
 *
 */
public class Tower implements IWorldGenerator
{
	protected int towerHeight; // min height of 3, max height of 6
	protected int towerDepth; // min depth of 1, max depth 3
	
	public Tower()
	{
		this.towerHeight = (int) (Math.random() * 4) + 3;
		this.towerDepth = (int) (Math.random() * 3) + 1;
	}
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{		
		int blockX = chunkX * 16 + (rand.nextInt(16) + 8);
		int blockZ = chunkZ * 16 + (rand.nextInt(16) + 8);
		int blockY = StructureHelper.getGroundFromAbove(world, blockX, blockZ);
		BlockPos pos = new BlockPos(blockX, blockY, blockZ);
		
		if ((int) (Math.random() * 300) == 0 && generateEntrance(world, pos))
		{
			handleGeneration(world);
		}
	}
	
	private void handleGeneration(World world)
	{
		
	}
	
	/**
	 * Generates the entrance of the Tower Dungeon at the rotated BlockPos.
	 * @param world
	 * @param pos
	 * @return
	 */
	private boolean generateEntrance(World world, BlockPos pos)
	{
		Template entrance = TowerHelper.getRandomEntrance(world);
		
		if (StructureHelper.canSpawnHere(entrance, world, pos))
		{
			PlacementSettings settings = new PlacementSettings().setRotation(Rotation.values()[(int) (Math.random() * 4)]);
			entrance.addBlocksToWorld(world, pos, settings);
			StructureHelper.handleDataBlocks(entrance, world, pos, settings);
			
			return true;
		}
		
		return false;
	}
	
	private void generateFloor()
	{
		
	}
}

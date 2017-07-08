package com.thexfactor117.minehackslash.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * 
 * @author TheXFactor117
 *
 */
public class MHSWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		
		switch (world.provider.getDimension())
		{
			case -1: 
				generateNether(world, rand, blockX, blockZ);
				break;
			case 0: 
				generateOverworld(world, rand, blockX, blockZ);
				break;
			case 1: 
				generateEnd(world, rand, blockX, blockZ);
				break;
		}
	}
	
	private void generateOverworld(World world, Random rand, int blockX, int blockZ)
	{	
		
	}
	
	private void generateNether(World world, Random rand, int chunkX, int chunkZ) {}
	private void generateEnd(World world, Random rand, int chunkX, int chunkZ) {}
	
	private void addOreSpawn(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY)
	{
		for (int i = 0; i < chanceToSpawn; i++)
		{
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(maxZ);
			new WorldGenMinable(block, maxVeinSize).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
}

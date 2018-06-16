package com.lsc.worldgen;

import java.util.Random;

import com.lsc.LootSlashConquer;
import com.lsc.worldgen.boss.StructureCorruptedTower;
import com.lsc.worldgen.tower.Tower;

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
public class LSCWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch (world.provider.getDimension())
		{
			case -1: 
				generateNether(world, rand, chunkX, chunkZ);
				break;
			case 0: 
				generateOverworld(world, rand, chunkX, chunkZ);
				break;
			case 1: 
				generateEnd(world, rand, chunkX, chunkZ);
				break;
		}
	}
	
	private void generateOverworld(World world, Random rand, int chunkX, int chunkZ)
	{	
		//int blockX = chunkX * 16 + (rand.nextInt(16) + 8);
		//int blockZ = chunkZ * 16 + (rand.nextInt(16) + 8);
		
		//WorldServer server = (WorldServer) world;
		//TemplateManager manager = server.getStructureTemplateManager();
		
		for (int i = 0; i < Tower.towerParts.size(); i++)
		{
			StructureOutline outline = Tower.towerParts.get(i);
			
			if (StructureHelper.canSpawnInChunk(outline, world))
			{
				LootSlashConquer.LOGGER.info("Successfully spawned part of a tower from the list!");
				outline.generate(world);
				outline.setHasGenerated(true);
				Tower.towerParts.remove(i);
			}
		}
		
		for (int i = 0; i < StructureCorruptedTower.parts.size(); i++)
		{
			StructureOutline outline = StructureCorruptedTower.parts.get(i);
			
			if (outline.canSpawnInChunk(world))
			{
				LootSlashConquer.LOGGER.info("Successfully spawned part of a boss structure from the list!");
				outline.generate(world);
				StructureCorruptedTower.parts.remove(i);
			}
		}
		
		if ((int) (Math.random() * 120) == 0)
		{
			//LootSlashConquer.LOGGER.info("Attempting Tower generation...");
			Tower tower = new Tower();
			//tower.generate(rand, chunkX, chunkZ, world);
		}
		
		if ((int) (Math.random() * 200) == 0 && LSCWorldSavedData.get(world).getCorruptedTowers() < 3)
		{
			StructureCorruptedTower tower = new StructureCorruptedTower();
			tower.generate(rand, chunkX, chunkZ, world);
		}
	}
	
	private void generateNether(World world, Random rand, int chunkX, int chunkZ) {}
	private void generateEnd(World world, Random rand, int chunkX, int chunkZ) {}
	
	@SuppressWarnings("unused")
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

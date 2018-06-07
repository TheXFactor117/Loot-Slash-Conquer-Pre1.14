package com.lsc.worldgen.tower;

import java.util.Random;

import com.lsc.LootSlashConquer;
import com.lsc.worldgen.StructureHelper;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

/**
 * 
 * @author TheXFactor117
 *
 */
public class Tower
{
	protected int towerHeight; // min height of 3, max height of 6
	protected int towerDepth; // min depth of 1, max depth 3
	protected static final int floorHeight = 7; // y-size of the floor structure
	
	public Tower()
	{
		this.towerHeight = (int) (Math.random() * 4) + 3;
		this.towerDepth = (int) (Math.random() * 3) + 1;
	}
	
	/**
	 * Starts the generation process.
	 * @param rand
	 * @param chunkX
	 * @param chunkZ
	 * @param world
	 */
	public void generate(Random rand, int chunkX, int chunkZ, World world)
	{		
		int blockX = chunkX * 16 + (rand.nextInt(16) + 8);
		int blockZ = chunkZ * 16 + (rand.nextInt(16) + 8);
		int blockY = StructureHelper.getGroundFromAbove(world, blockX, blockZ);
		BlockPos pos = new BlockPos(blockX, blockY, blockZ);
		
		if ((int) (Math.random() * 300) == 0 && generateEntrance(world, pos))
		{
			handleGeneration(world, pos);
		}
	}
	
	/**
	 * Handles the positioning of different floor pieces, as well as the structure building up and down.
	 * @param world
	 * @param center
	 */
	private void handleGeneration(World world, BlockPos center)
	{
		LootSlashConquer.LOGGER.info("Tower Spawning: Generating top half...");
		
		// top half
		for (int i = 1; i <= towerHeight; i++)
		{
			BlockPos floorCenter = center.up(floorHeight * i);
			generateFloor(world, floorCenter);
		}
		
		LootSlashConquer.LOGGER.info("Tower Spawning: Generating bottom half...");
		
		// bottom half
		for (int j = 1; j <= towerHeight; j++)
		{
			BlockPos floorCenter = center.down(floorHeight * j);
			generateFloor(world, floorCenter);
		}
	}
	
	/**
	 * Generates the entrance of the Tower Dungeon at the rotated BlockPos.
	 * @param world
	 * @param pos
	 * @return
	 */
	private boolean generateEntrance(World world, BlockPos center)
	{
		Template entrance = TowerHelper.getRandomEntrance(world);
		PlacementSettings settings = new PlacementSettings().setRotation(Rotation.values()[(int) (Math.random() * 4)]);
		// translate the center position to the corner for proper spawning. Use spawnPos when dealing with the placement of the structure.
		BlockPos spawnPos = StructureHelper.translateToCorner(entrance, center, settings.getRotation());
		
		if (StructureHelper.canSpawnHere(entrance, world, spawnPos))
		{
			entrance.addBlocksToWorld(world, spawnPos, settings);
			StructureHelper.handleDataBlocks(entrance, world, spawnPos, settings);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Generates a floor of the Tower Dungeon based on the passed in center position.
	 * @param world
	 * @param center
	 */
	private void generateFloor(World world, BlockPos center)
	{
		Template floor = TowerHelper.getRandomFloor(world);
		PlacementSettings settings = new PlacementSettings().setRotation(Rotation.values()[(int) (Math.random() * 4)]);
		BlockPos spawnPos = StructureHelper.translateToCorner(floor, center, settings.getRotation());
		floor.addBlocksToWorld(world, spawnPos, settings);
		StructureHelper.handleDataBlocks(floor, world, spawnPos, settings);
	}
}

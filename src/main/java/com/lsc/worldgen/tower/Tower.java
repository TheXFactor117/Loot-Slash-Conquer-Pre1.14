package com.lsc.worldgen.tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lsc.LootSlashConquer;
import com.lsc.worldgen.StructureHelper;
import com.lsc.worldgen.StructureOutline;

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
	public static List<StructureOutline> towerParts = new ArrayList<StructureOutline>();
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
	 * 
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
		Rotation mainRotation = Rotation.values()[(int) (Math.random() * 4)]; // random rotation for the odd floor

		if (generateEntrance(world, pos, mainRotation))
		{
			LootSlashConquer.LOGGER.info("Generating Tower at: " + pos);
			handleGeneration(world, pos, mainRotation);
		}
		else
		{
			LootSlashConquer.LOGGER.info("Tower generation failed...");
		}
	}

	/**
	 * Handles the positioning of different floor pieces, as well as the
	 * structure building up and down.
	 * 
	 * @param world
	 * @param center
	 */
	private void handleGeneration(World world, BlockPos center, Rotation mainRotation)
	{
		// top half
		for (int i = 1; i <= towerHeight; i++)
		{
			BlockPos floorCenter = center.up(floorHeight * i);

			if (i == towerHeight)
			{
				if (i % 2 == 0) // even
				{
					generateTopFloor(world, floorCenter, mainRotation.add(Rotation.CLOCKWISE_180));
				}
				else // odd
				{
					generateTopFloor(world, floorCenter, mainRotation);
				}
			}
			else
			{
				if (i % 2 == 0) // even
				{
					generateFloor(world, floorCenter, mainRotation);
				}
				else // odd
				{
					generateFloor(world, floorCenter, mainRotation.add(Rotation.CLOCKWISE_180));
				}
			}
		}

		// bottom half
		for (int j = 1; j <= towerDepth; j++)
		{
			BlockPos floorCenter = center.down(floorHeight * j);

			if (j % 2 == 0) // even
			{
				generateFloor(world, floorCenter, mainRotation);
			}
			else
			{
				generateFloor(world, floorCenter, mainRotation.add(Rotation.CLOCKWISE_180));
			}
		}
	}

	/**
	 * Generates the entrance of the Tower Dungeon at the rotated BlockPos.
	 * 
	 * @param world
	 * @param pos
	 * @return
	 */
	private boolean generateEntrance(World world, BlockPos center, Rotation rotation)
	{
		Template entrance = TowerHelper.getRandomEntrance(world);
		TowerFloorOutline outline = new TowerFloorOutline(entrance, rotation, center);

		boolean canSpawn = StructureHelper.canSpawnInChunk(outline, world);

		if (canSpawn)
		{
			LootSlashConquer.LOGGER.info("All chunks generated, generating tower piece.");
			outline.generate(world);
		}
		else
		{
			LootSlashConquer.LOGGER.info("Some chunks haven't been generated yet, storing tower piece.");
			towerParts.add(outline);
		}

		return true;
	}

	/**
	 * Generates a floor of the Tower Dungeon based on the passed in center
	 * position.
	 * 
	 * @param world
	 * @param center
	 */
	private void generateFloor(World world, BlockPos center, Rotation rotation)
	{
		Template floor = TowerHelper.getRandomFloor(world);
		PlacementSettings settings = new PlacementSettings().setRotation(rotation);
		TowerFloorOutline outline = new TowerFloorOutline(floor, settings.getRotation(), center);

		if (StructureHelper.canSpawnInChunk(outline, world))
		{
			LootSlashConquer.LOGGER.info("All chunks generated, generating tower piece.");
			outline.generate(world);
		}
		else
		{
			LootSlashConquer.LOGGER.info("Some chunks haven't been generated yet, storing tower piece.");
			towerParts.add(outline);
		}
	}

	/**
	 * Generates the top floor of the Tower Dungeon.
	 * 
	 * @param world
	 * @param center
	 * @param rotation
	 */
	private void generateTopFloor(World world, BlockPos center, Rotation rotation)
	{
		Template floor = TowerHelper.getRandomTopFloor(world);
		PlacementSettings settings = new PlacementSettings().setRotation(rotation);
		TowerFloorOutline outline = new TowerFloorOutline(floor, settings.getRotation(), center);

		if (StructureHelper.canSpawnInChunk(outline, world))
		{
			LootSlashConquer.LOGGER.info("All chunks generated, generating tower piece.");
			outline.generate(world);
		}
		else
		{
			LootSlashConquer.LOGGER.info("Some chunks haven't been generated yet, storing tower piece.");
			towerParts.add(outline);
		}
	}
}

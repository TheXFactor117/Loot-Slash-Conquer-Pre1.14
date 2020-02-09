package com.thexfactor117.lsc.world.generation.tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.world.generation.util.StructureBlockProcessor;
import com.thexfactor117.lsc.world.generation.util.StructureOutline;
import com.thexfactor117.lsc.world.generation.util.StructureUtils;

import java.util.Random;

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
public class StructureTower implements IWorldGenerator
{
	public static List<StructureOutline> parts = new ArrayList<StructureOutline>();
	protected int towerHeight; // min height of 3, max height of 6
	protected int towerDepth; // min depth of 1, max depth 3
	protected static final int floorHeight = 7; // y-size of the floor structure

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.getWorldInfo().isMapFeaturesEnabled() && Configs.worldgenCategory.enableLSCWorldGen && Configs.worldgenCategory.enableTowerGeneration)
		{
			// try and spawn additional parts if possible
			for (int i = 0; i < parts.size(); i++)
			{
				StructureOutline outline = parts.get(i);

				if (outline.canSpawnInChunk(world))
				{
					//LootSlashConquer.LOGGER.info("Successfully spawned part of a tower from the list!");
					outline.generate(world, new StructureBlockProcessor(outline.getCenter(), outline.getSettings(), StructureBlockProcessor.TOWER_FLOOR));
					// TODO: if a treasure room gets spawned from here, it'll have lower chest spawn rates.
					// this will need to be fixed eventually. Maybe add an additional parameter to
					// StructureOutline.
					this.handleDataBlocks(outline.getTemplate(), world, outline.getCorner(), outline.getSettings(), 0);
					parts.remove(i);
				}
			}

			// check to make sure spawning works as it should for Alpha release
			// TODO: restrict to certain biomes?
			if ((int) (Math.random() * Configs.worldgenCategory.towerSpawnRate) == 0 && world.provider.getDimension() == 0)
			{
				int blockX = chunkX * 16 + (rand.nextInt(16) + 8);
				int blockZ = chunkZ * 16 + (rand.nextInt(16) + 8);
				int blockY = StructureUtils.getGroundFromAbove(world, blockX, blockZ);
				BlockPos pos = new BlockPos(blockX, blockY, blockZ);
				Rotation mainRotation = Rotation.values()[(int) (Math.random() * 4)]; // random rotation for the odd floor
				this.towerHeight = (int) (Math.random() * Configs.worldgenCategory.towerAdditionalHeight) + Configs.worldgenCategory.towerMinHeight;
				this.towerDepth = (int) (Math.random() * Configs.worldgenCategory.towerAdditionalDepth) + Configs.worldgenCategory.towerMinDepth;

				if (generateEntrance(world, pos, mainRotation))
				{
					//LootSlashConquer.LOGGER.info("Generating Tower at: " + pos);
					handleGeneration(world, pos, mainRotation);
				}
				else
				{
					//LootSlashConquer.LOGGER.info("Tower generation failed...");
				}
			}
		}
	}

	/**
	 * Handles the positioning of different floor pieces, as well as the structure building up and down.
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
		Template entrance = TowerUtils.getRandomEntrance(world);
		StructureOutline outline = new StructureOutline(entrance, rotation, center);

		boolean canSpawn = outline.canSpawnInChunk(world);

		if (canSpawn)
		{
			//LootSlashConquer.LOGGER.info("All chunks generated, generating tower piece.");
			outline.generate(world, new StructureBlockProcessor(outline.getCenter(), outline.getSettings(), StructureBlockProcessor.TOWER_FLOOR));
			this.handleDataBlocks(outline.getTemplate(), world, outline.getCorner(), outline.getSettings(), 0);
		}
		else
		{
			//LootSlashConquer.LOGGER.info("Some chunks haven't been generated yet, storing tower piece.");
			parts.add(outline);
		}

		return true;
	}

	/**
	 * Generates a floor of the Tower Dungeon based on the passed in center position.
	 * 
	 * @param world
	 * @param center
	 */
	private void generateFloor(World world, BlockPos center, Rotation rotation)
	{
		Template floor = TowerUtils.getRandomFloor(world);
		StructureOutline outline = new StructureOutline(floor, rotation, center);

		if (outline.canSpawnInChunk(world))
		{
			//LootSlashConquer.LOGGER.info("All chunks generated, generating tower piece.");
			outline.generate(world, new StructureBlockProcessor(outline.getCenter(), outline.getSettings(), StructureBlockProcessor.TOWER_FLOOR));
			this.handleDataBlocks(outline.getTemplate(), world, outline.getCorner(), outline.getSettings(), 0);
		}
		else
		{
			//LootSlashConquer.LOGGER.info("Some chunks haven't been generated yet, storing tower piece.");
			parts.add(outline);
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
		Template floor = TowerUtils.getRandomTopFloor(world);
		StructureOutline outline = new StructureOutline(floor, rotation, center);

		if (outline.canSpawnInChunk(world))
		{
			//LootSlashConquer.LOGGER.info("All chunks generated, generating tower piece.");
			outline.generate(world, new StructureBlockProcessor(outline.getCenter(), outline.getSettings(), StructureBlockProcessor.TOWER_FLOOR));
			this.handleDataBlocks(outline.getTemplate(), world, outline.getCorner(), outline.getSettings(), 1);
		}
		else
		{
			//LootSlashConquer.LOGGER.info("Some chunks haven't been generated yet, storing tower piece.");
			parts.add(outline);
		}
	}

	/**
	 * Handles the manipulation of the data blocks.
	 * @param template
	 * @param world
	 * @param pos
	 * @param settings
	 */
	private void handleDataBlocks(Template template, World world, BlockPos pos, PlacementSettings settings, int flag)
	{
		// loop through all data blocks within the structure
		for (Entry<BlockPos, String> e : template.getDataBlocks(pos, settings).entrySet())
		{
			BlockPos dataBlockPos = e.getKey();
			
			switch (flag)
			{
				// NORMAL FLOOR/ENTRANCE
				case 0:
					StructureUtils.handleChests(world, dataBlockPos, e, Configs.worldgenCategory.towerNormalChestChance);
					StructureUtils.handleSpawners(world, dataBlockPos, e, Configs.worldgenCategory.towerNormalSpawnerChance);
					break;
				// TREASURE ROOM
				case 1:
					StructureUtils.handleChests(world, dataBlockPos, e, Configs.worldgenCategory.towerTreasureChestChance);
					break;
			}
		}		
	}
}

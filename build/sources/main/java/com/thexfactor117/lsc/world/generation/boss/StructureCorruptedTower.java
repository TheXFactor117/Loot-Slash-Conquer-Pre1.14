package com.thexfactor117.lsc.world.generation.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.init.ModBlocks;
import com.thexfactor117.lsc.init.ModItems;
import com.thexfactor117.lsc.util.misc.Reference;
import com.thexfactor117.lsc.world.LSCWorldSavedData;
import com.thexfactor117.lsc.world.generation.util.StructureOutline;
import com.thexfactor117.lsc.world.generation.util.StructureUtils;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 *
 * @author TheXFactor117
 *
 */
public class StructureCorruptedTower implements IWorldGenerator
{
	/** Keeps a running list of parts which need to still be generated. */
	public static List<StructureOutline> parts = new ArrayList<StructureOutline>();
	private static final int OFFSET = -16;
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.getWorldInfo().isMapFeaturesEnabled() && Configs.worldgenCategory.enableLSCWorldGen && Configs.worldgenCategory.enableBossStructures)
		{
			// try and spawn additional parts if possible
			for (int i = 0; i < parts.size(); i++)
			{
				StructureOutline outline = parts.get(i);
				
				if (outline.canSpawnInChunk(world))
				{
					//LootSlashConquer.LOGGER.info("Successfully spawned part of a boss structure from the list!");
					BlockPos corner = outline.generate(world);
					this.handleDataBlocks(outline.getTemplate(), world, corner, new PlacementSettings().setRotation(outline.getRotation()));
					parts.remove(i);
				}
			}
			
			// preliminary spawn check, including a weight
			// TODO: make it spawn somewhere in a specific area range (e.g. Areas 10-15).
			if ((int) (Math.random() * Configs.worldgenCategory.corruptedTowerSpawnRate) == 0 && LSCWorldSavedData.get(world).getCorruptedTowers() < Configs.worldgenCategory.maxCorruptedTowers && world.provider.getDimension() == 0)
			{
				// get the specific block position of the structure
				int blockX = chunkX * 16 + (rand.nextInt(16) + 8);
				int blockZ = chunkZ * 16 + (rand.nextInt(16) + 8);
				int blockY = StructureUtils.getGroundFromAbove(world, blockX, blockZ);
				BlockPos pos = new BlockPos(blockX, blockY, blockZ);
				
				// check to make sure the center is in a good biome, and check to make sure the corners are in the same biome too.
				if ((world.getBiome(pos) == Biomes.PLAINS || world.getBiome(pos) == Biomes.DESERT) && this.canSpawnInBiome(world, pos))
				{
					// debug
					//LootSlashConquer.LOGGER.info("Spawning boss structure at: " + pos);
					
					WorldServer server = (WorldServer) world;
					TemplateManager manager = server.getStructureTemplateManager();
					
					// we're good to place blocks or generate the outline
					generateNESide(world, manager, pos);
					generateNWSide(world, manager, pos);
					generateSESide(world, manager, pos);
					generateSWSide(world, manager, pos);
					
					// increase this counter to limit how many structures spawn per world.
					LSCWorldSavedData.get(world).increaseCorruptedTowers();
				}
			}
		}
	}
	
	/**
	 * Generates the Northeast side of the tower.
	 * @param world
	 * @param manager
	 * @param center
	 */
	private void generateNESide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos neCenter = center.add(16, OFFSET, -16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_ne_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, neCenter.up(32 * i));
			
			// if chunks are loaded, generate, else add the piece to the running list
			if (outline.canSpawnInChunk(world))
			{
				BlockPos corner = outline.generate(world);
				this.handleDataBlocks(outline.getTemplate(), world, corner, new PlacementSettings().setRotation(outline.getRotation()));
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	/**
	 * Generates the Northwest side of the tower.
	 * @param world
	 * @param manager
	 * @param center
	 */
	private void generateNWSide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos nwCenter = center.add(-16, OFFSET, -16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_nw_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, nwCenter.up(32 * i));
			
			// if chunks are loaded, generate, else add the piece to the running list
			if (outline.canSpawnInChunk(world))
			{
				BlockPos corner = outline.generate(world);
				this.handleDataBlocks(outline.getTemplate(), world, corner, new PlacementSettings().setRotation(outline.getRotation()));
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	/**
	 * Generates the Southeast side of the tower.
	 * @param world
	 * @param manager
	 * @param center
	 */
	private void generateSESide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos seCenter = center.add(16, OFFSET, 16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_se_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, seCenter.up(32 * i));
			
			// if chunks are loaded, generate, else add the piece to the running list
			if (outline.canSpawnInChunk(world))
			{
				BlockPos corner = outline.generate(world);
				this.handleDataBlocks(outline.getTemplate(), world, corner, new PlacementSettings().setRotation(outline.getRotation()));
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	/**
	 * Generates the Southwest side of the tower.
	 * @param world
	 * @param manager
	 * @param center
	 */
	private void generateSWSide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos swCenter = center.add(-16, OFFSET, 16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_sw_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, swCenter.up(32 * i));
			
			// if chunks are loaded, generate, else add the piece to the running list
			if (outline.canSpawnInChunk(world))
			{
				BlockPos corner = outline.generate(world);
				this.handleDataBlocks(outline.getTemplate(), world, corner, new PlacementSettings().setRotation(outline.getRotation()));
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	/**
	 * Checks to make sure the four corners of the structure are all apart of the same biome.
	 * This prevents the structure from generating into mountains, oceans, or forests.
	 * @param world
	 * @param center
	 * @return
	 */
	private boolean canSpawnInBiome(World world, BlockPos center)
	{
		boolean corner1 = world.getBiome(center.add(-32, 0, -32)) == Biomes.PLAINS || world.getBiome(center.add(-32, 0, -32)) == Biomes.DESERT ? true : false;
		boolean corner2 = world.getBiome(center.add(32, 0, -32)) == Biomes.PLAINS || world.getBiome(center.add(32, 0, -32)) == Biomes.DESERT ? true : false;
		boolean corner3 = world.getBiome(center.add(32, 0, 32)) == Biomes.PLAINS || world.getBiome(center.add(32, 0, 32)) == Biomes.DESERT ? true : false;
		boolean corner4 = world.getBiome(center.add(-32, 0, 32)) == Biomes.PLAINS || world.getBiome(center.add(-32, 0, 32)) == Biomes.DESERT ? true : false;
		
		return corner1 && corner2 && corner3 && corner4;
	}
	
	/**
	 * Handles the manipulation of the data blocks inside the tower. Specifically for the Corrupted Tower.
	 * @param template
	 * @param world
	 * @param pos
	 * @param settings
	 */
	private void handleDataBlocks(Template template, World world, BlockPos pos, PlacementSettings settings)
	{
		// loop through all data blocks within the structure
		for (Entry<BlockPos, String> e : template.getDataBlocks(pos, settings).entrySet())
		{
			BlockPos dataBlockPos = e.getKey();
			
			switch (e.getValue())
			{
				case "boss_door":
					world.setBlockState(dataBlockPos, ModBlocks.BOSS_DOOR.getDefaultState(), 3);
					break;
				case "key_chest":
					world.setBlockState(dataBlockPos, Blocks.AIR.getDefaultState(), 3);
					TileEntity tile = world.getTileEntity(dataBlockPos.down(1));
					
					if (tile instanceof TileEntityChest)
					{
						TileEntityChest chest = (TileEntityChest) tile;
						chest.setInventorySlotContents(0, new ItemStack(ModItems.CORRUPTED_TOWER_KEY));
					}
					break;
			}

			StructureUtils.handleChests(world, dataBlockPos, e, Configs.worldgenCategory.corruptedTowerChestChance);
			StructureUtils.handleSpawners(world, dataBlockPos, e, Configs.worldgenCategory.corruptedTowerSpawnerChance);
		}
	}
}

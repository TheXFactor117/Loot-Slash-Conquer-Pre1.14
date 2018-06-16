package com.lsc.worldgen.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.lsc.LootSlashConquer;
import com.lsc.init.ModBlocks;
import com.lsc.init.ModItems;
import com.lsc.util.Reference;
import com.lsc.worldgen.LSCWorldSavedData;
import com.lsc.worldgen.StructureHelper;
import com.lsc.worldgen.StructureOutline;

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
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 *
 * @author TheXFactor117
 *
 */
public class StructureCorruptedTower
{
	public static List<StructureOutline> parts = new ArrayList<StructureOutline>();
	private static final int OFFSET = -16;
	
	/**
	 * Handles the generation and placing of the tower.
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
		//Rotation mainRotation = Rotation.values()[(int) (Math.random() * 4)];
		
		//if (canTowerSpawn(world, pos, Rotation.NONE))
		//{
			//LootSlashConquer.LOGGER.info("Checks passed - generating structure at: " + pos);
			
		if ((world.getBiome(pos) == Biomes.PLAINS || world.getBiome(pos) == Biomes.DESERT) && this.canSpawnInBiome(world, pos))
		{
			LootSlashConquer.LOGGER.info("Spawning boss structure at: " + pos);
			WorldServer server = (WorldServer) world;
			TemplateManager manager = server.getStructureTemplateManager();
			
			generateNESide(world, manager, pos);
			generateNWSide(world, manager, pos);
			generateSESide(world, manager, pos);
			generateSWSide(world, manager, pos);
			
			LSCWorldSavedData.get(world).increaseCorruptedTowers();
		}
		
		
		//}
		//else
		//{
		//	LootSlashConquer.LOGGER.info("Boss structure generation failed during checks.");
		//}
	}
	
	private void generateNESide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos neCenter = center.add(16, OFFSET, -16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_ne_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, neCenter.up(32 * i));
			
			if (outline.canSpawnInChunk(world))
			{
				outline.generate(world);
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	private void generateNWSide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos nwCenter = center.add(-16, OFFSET, -16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_nw_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, nwCenter.up(32 * i));
			
			if (outline.canSpawnInChunk(world))
			{
				outline.generate(world);
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	private void generateSESide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos seCenter = center.add(16, OFFSET, 16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_se_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, seCenter.up(32 * i));
			
			if (outline.canSpawnInChunk(world))
			{
				outline.generate(world);
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	private void generateSWSide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos swCenter = center.add(-16, OFFSET, 16);
		
		for (int i = 0; i < 4; i++)
		{
			Template template = manager.get(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_sw_" + i));
			StructureOutline outline = new StructureOutline(template, Rotation.NONE, swCenter.up(32 * i));
			
			if (outline.canSpawnInChunk(world))
			{
				outline.generate(world);
			}
			else
			{
				parts.add(outline);
			}
		}
	}
	
	private boolean canSpawnInBiome(World world, BlockPos center)
	{
		boolean corner1 = world.getBiome(center.add(-32, 0, -32)) == Biomes.PLAINS || world.getBiome(center.add(-32, 0, -32)) == Biomes.DESERT ? true : false;
		boolean corner2 = world.getBiome(center.add(32, 0, -32)) == Biomes.PLAINS || world.getBiome(center.add(32, 0, -32)) == Biomes.DESERT ? true : false;
		boolean corner3 = world.getBiome(center.add(32, 0, 32)) == Biomes.PLAINS || world.getBiome(center.add(32, 0, 32)) == Biomes.DESERT ? true : false;
		boolean corner4 = world.getBiome(center.add(-32, 0, 32)) == Biomes.PLAINS || world.getBiome(center.add(-32, 0, 32)) == Biomes.DESERT ? true : false;
		
		return corner1 && corner2 && corner3 && corner4;
	}
	
	public static void handleDataBlocks(Template template, World world, BlockPos pos, PlacementSettings settings)
	{
		// loop through all data blocks within the structure
		for (Entry<BlockPos, String> e : template.getDataBlocks(pos, settings).entrySet())
		{
			BlockPos dataPos = e.getKey();
			
			if ("key_chest".equals(e.getValue()))
			{
				world.setBlockState(dataPos, Blocks.AIR.getDefaultState(), 3);
				TileEntity tile = world.getTileEntity(dataPos.down(1));
				
				if (tile instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest) tile;
					chest.setInventorySlotContents(0, new ItemStack(ModItems.CORRUPTED_TOWER_KEY));
				}
			}
			else if ("boss_door".equals(e.getValue()))
			{
				world.setBlockState(dataPos, ModBlocks.BOSS_DOOR.getDefaultState(), 3);
			}
		}
	}
}


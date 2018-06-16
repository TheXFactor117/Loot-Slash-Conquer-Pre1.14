package com.lsc.worldgen.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lsc.util.Reference;
import com.lsc.worldgen.StructureHelper;
import com.lsc.worldgen.StructureOutline;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
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
			WorldServer server = (WorldServer) world;
			TemplateManager manager = server.getStructureTemplateManager();
			
			generateNESide(world, manager, pos);
			generateNWSide(world, manager, pos);
			generateSESide(world, manager, pos);
			generateSWSide(world, manager, pos);
		//}
		//else
		//{
		//	LootSlashConquer.LOGGER.info("Boss structure generation failed during checks.");
		//}
	}
	
	private void generateNESide(World world, TemplateManager manager, BlockPos center)
	{
		BlockPos neCenter = center.add(16, -11, -16);
		
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
		BlockPos nwCenter = center.add(-16, -11, -16);
		
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
		BlockPos seCenter = center.add(16, -11, 16);
		
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
		BlockPos swCenter = center.add(-16, -11, 16);
		
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
	
	/**
	 * Returns whether or not the Tower can spawn without causing cascading worldgen.
	 * @param world
	 * @param center
	 * @return
	 */
	private boolean canTowerSpawn(World world, BlockPos center, Rotation rotation)
	{
		WorldServer server = (WorldServer) world;
		TemplateManager manager = server.getStructureTemplateManager();
		
		Template ne = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_ne_1"));
		Template nw = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_nw_1"));
		Template se = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_se_1"));
		Template sw = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "boss/corruptedtower/darktower_sw_1"));
		StructureOutline neOutline = new StructureOutline(ne, rotation, center);
		StructureOutline nwOutline = new StructureOutline(nw, rotation, center);
		StructureOutline seOutline = new StructureOutline(se, rotation, center);
		StructureOutline swOutline = new StructureOutline(sw, rotation, center);
		
		return neOutline.canSpawnInChunk(world) && nwOutline.canSpawnInChunk(world) && seOutline.canSpawnInChunk(world) && swOutline.canSpawnInChunk(world);
	}
}


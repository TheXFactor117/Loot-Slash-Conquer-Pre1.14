package com.lsc.worldgen.boss;

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
		Rotation mainRotation = Rotation.values()[(int) (Math.random() * 4)];
		
		if (canTowerSpawn(world, pos, mainRotation));
		{
			generateNESide(world, pos);
			generateNWSide(world, pos);
			generateSESide(world, pos);
			generateSWSide(world, pos);
		}
	}
	
	private void generateNESide(World world, BlockPos center)
	{
		
	}
	
	private void generateNWSide(World world, BlockPos center)
	{
		
	}
	
	private void generateSESide(World world, BlockPos center)
	{
		
	}
	
	private void generateSWSide(World world, BlockPos center)
	{
		
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


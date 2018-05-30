package com.lsc.worldgen;

import com.lsc.LootSlashConquer;
import com.lsc.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * @author TheXFactor117
 *
 */
public class Structure 
{
	private String name;
	private int x;
	private int z;
	private TemplateManager manager;
	
	public Structure(String name, int x, int z, TemplateManager manager)
	{
		this.name = name;
		this.x = x;
		this.z = z;
		this.manager = manager;
	}
	
	public void generate(World world)
	{
		Template template = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, name)); 
		int y = StructureHelper.getGroundFromAbove(world, x, z);
		BlockPos pos = new BlockPos(x, y, z);
		PlacementSettings settings = new PlacementSettings();
		
		if (StructureHelper.canSpawnHere(template, world, pos))
		{
			template.addBlocksToWorld(world, pos, settings);
			StructureHelper.handleDataBlocks(template, world, pos, settings);
			LootSlashConquer.LOGGER.info("Spawning structure: " + pos);
		}
	}
	
	public void generateRandomHouse(World world, int identifier)
	{
		Template template = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, name + identifier)); 
		int y = StructureHelper.getGroundFromAbove(world, x, z);
		BlockPos pos = new BlockPos(x, y, z);
		PlacementSettings settings = new PlacementSettings().setRotation(Rotation.values()[(int) (Math.random() * 4)]);
		int offset = 0;

		if (StructureHelper.canSpawnHere(template, world, pos))
		{
			if (identifier == 2 || identifier == 3) offset = 4; // offset building down if it has a basement
			template.addBlocksToWorld(world, pos.down(offset), settings);
			StructureHelper.handleDataBlocks(template, world, pos.down(offset), settings);
			LootSlashConquer.LOGGER.info("Spawning structure: " + pos);
		}
	}
}

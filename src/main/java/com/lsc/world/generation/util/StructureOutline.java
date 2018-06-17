package com.lsc.world.generation.util;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

/**
 *
 * @author TheXFactor117
 *
 * A Class used to help ease the generation of structures.
 * 
 */
public class StructureOutline
{
	private Template template;
	private Rotation rotation;
	private BlockPos center;
	private BlockPos corner;
	private PlacementSettings settings;
	
	public StructureOutline(Template template, Rotation rotation, BlockPos center)
	{
		this.template = template;
		this.rotation = rotation;
		this.center = center;
		this.corner = StructureUtils.translateToCorner(template, center, rotation);
		this.settings = new PlacementSettings().setRotation(rotation);
	}
	
	/**
	 * Generates the outline into the world, returning the corner pos in which the outline was
	 * generated at.
	 * @param world
	 * @return
	 */
	public BlockPos generate(World world)
	{
		template.addBlocksToWorld(world, corner, settings);
		return corner;
	}
	
	/**
	 * Generates the outline into the world, returning the corner pos in which the outline was
	 * generated at. Allows to specify the block processor as well.
	 * @param world
	 * @param processor
	 * @return
	 */
	public BlockPos generate(World world, ITemplateProcessor processor)
	{
		template.addBlocksToWorld(world, corner, processor, settings, 2);
		return corner;
	}

	/**
	 * Checks to make sure the four corners of the template are loaded in their respective chunk.
	 * Returns true if all corners are in loaded chunks.
	 * @param world
	 * @return
	 */
	public boolean canSpawnInChunk(World world)
	{
		BlockPos corner1 = this.getCenter();
		BlockPos corner2 = this.getCenter();
		BlockPos corner3 = this.getCenter();
		BlockPos corner4 = this.getCenter();
		
		int width = this.getTemplate().getSize().getX() / 2;
		int length = this.getTemplate().getSize().getZ() / 2;
		
		switch (this.getRotation())
		{
			case NONE:
				corner1 = this.getCenter().add(-width, 0, length);
				corner2 = this.getCenter().add(width, 0, length);
				corner3 = this.getCenter().add(width, 0, -length);
				corner4 = this.getCenter().add(-width, 0, -length);
				break;
			case CLOCKWISE_90:
				corner1 = this.getCenter().add(-length, 0, width);
				corner2 = this.getCenter().add(length, 0, width);
				corner3 = this.getCenter().add(length, 0, -width);
				corner4 = this.getCenter().add(-length, 0, -width);
				break;
			case CLOCKWISE_180:
				corner1 = this.getCenter().add(-width, 0, length);
				corner2 = this.getCenter().add(width, 0, length);
				corner3 = this.getCenter().add(width, 0, -length);
				corner4 = this.getCenter().add(-width, 0, -length);
				break;
			case COUNTERCLOCKWISE_90:
				corner1 = this.getCenter().add(-length, 0, width);
				corner2 = this.getCenter().add(length, 0, width);
				corner3 = this.getCenter().add(length, 0, -width);
				corner4 = this.getCenter().add(-length, 0, -width);
				break;
		}
		
		ChunkProviderServer chunkProvider = (ChunkProviderServer) world.getChunkProvider();
		
		boolean flag1 = chunkProvider.chunkExists(corner1.getX() >> 4, corner1.getZ() >> 4);
		boolean flag2 = chunkProvider.chunkExists(corner2.getX() >> 4, corner2.getZ() >> 4);
		boolean flag3 = chunkProvider.chunkExists(corner3.getX() >> 4, corner3.getZ() >> 4);
		boolean flag4 = chunkProvider.chunkExists(corner4.getX() >> 4, corner4.getZ() >> 4);
		
		/*LootSlashConquer.LOGGER.info("Generation flags:");
		LootSlashConquer.LOGGER.info("\t" + flag1 + " " + (corner1.getX() >> 4) + " " + (corner1.getZ() >> 4));
		LootSlashConquer.LOGGER.info("\t" + flag2 + " " + (corner2.getX() >> 4) + " " + (corner2.getZ() >> 4));
		LootSlashConquer.LOGGER.info("\t" + flag3 + " " + (corner3.getX() >> 4) + " " + (corner3.getZ() >> 4));
		LootSlashConquer.LOGGER.info("\t" + flag4 + " " + (corner4.getX() >> 4) + " " + (corner4.getZ() >> 4));*/
		
		if (flag1 && flag2 && flag3 && flag4)
		{
			return true;
		}
		
		return false;
	}
	
	public Template getTemplate()
	{
		return template;
	}
	
	public Rotation getRotation()
	{
		return rotation;
	}
	
	public BlockPos getCenter()
	{
		return center;
	}
	
	public BlockPos getCorner()
	{
		return corner;
	}
	
	public PlacementSettings getSettings()
	{
		return settings;
	}
}

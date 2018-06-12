package com.lsc.worldgen;

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
public class StructureOutline
{
	private Template template;
	private Rotation rotation;
	private BlockPos center;
	private boolean hasGenerated;
	
	public StructureOutline(Template template, Rotation rotation, BlockPos center)
	{
		this.template = template;
		this.rotation = rotation;
		this.center = center;
		this.hasGenerated = false;
	}
	
	public void generate(World world)
	{
		PlacementSettings settings = new PlacementSettings().setRotation(rotation);
		BlockPos pos = StructureHelper.translateToCorner(template, center, settings.getRotation());
		template.addBlocksToWorld(world, pos, settings);
		//StructureHelper.handleDataBlocks(template, world, pos, settings);
		hasGenerated = true;
	}
	
	public void setHasGenerated(boolean hasGenerated)
	{
		this.hasGenerated = hasGenerated;
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
	
	public boolean hasGenerated()
	{
		return hasGenerated;
	}
}

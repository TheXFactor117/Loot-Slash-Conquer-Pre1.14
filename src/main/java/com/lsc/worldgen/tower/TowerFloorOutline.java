package com.lsc.worldgen.tower;

import com.lsc.worldgen.StructureBlockProcessor;
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
public class TowerFloorOutline extends StructureOutline
{
	public TowerFloorOutline(Template template, Rotation rotation, BlockPos center)
	{
		super(template, rotation, center);
	}
	
	@Override
	public void generate(World world)
	{
		PlacementSettings settings = new PlacementSettings().setRotation(this.getRotation());
		BlockPos pos = StructureHelper.translateToCorner(this.getTemplate(), this.getCenter(), settings.getRotation());
		this.getTemplate().addBlocksToWorld(world, pos, new StructureBlockProcessor(pos, settings, StructureBlockProcessor.TOWER_FLOOR), settings, 2);
		StructureHelper.handleTowerDataBlocks(this.getTemplate(), world, pos, settings);
		this.setHasGenerated(true);
	}
	
	public void generateTreasureRoom(World world)
	{
		PlacementSettings settings = new PlacementSettings().setRotation(this.getRotation());
		BlockPos pos = StructureHelper.translateToCorner(this.getTemplate(), this.getCenter(), settings.getRotation());
		this.getTemplate().addBlocksToWorld(world, pos, new StructureBlockProcessor(pos, settings, StructureBlockProcessor.TOWER_FLOOR), settings, 2);
		StructureHelper.handleTreasureRoomDataBlocks(this.getTemplate(), world, pos, settings);
		this.setHasGenerated(true);
	}
}

package thexfactor117.lsc.world.generation.dungeon;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.Template;

/**
 * 
 * @author TheXFactor117
 *
 */
public class DungeonRoomPosition 
{
	/** Position of potential room. */
	private BlockPos pos;
	/** Template of potential room. */
	private Template template;
	/** Rotation of template. */
	private Rotation templateRotation;
	/** SIDE OF WALL WHICH IS THIS ROOM WILL SPAWN ON. */
	private Rotation side;
	private StructureBoundingBox boundingBox;
	
	public DungeonRoomPosition(BlockPos pos, Template template, Rotation templateRotation, Rotation side, StructureBoundingBox boundingBox)
	{
		this.pos = pos;
		this.template = template;
		this.templateRotation = templateRotation;
		this.side = side;
		this.boundingBox = boundingBox;
	}
	
	public BlockPos getPos()
	{
		return pos;
	}
	
	public Template getTemplate()
	{
		return template;
	}
	
	public Rotation getTemplateRotation()
	{
		return templateRotation;
	}
	
	public Rotation getSide()
	{
		return side;
	}
	
	public StructureBoundingBox getBoundingBox()
	{
		return boundingBox;
	}
}

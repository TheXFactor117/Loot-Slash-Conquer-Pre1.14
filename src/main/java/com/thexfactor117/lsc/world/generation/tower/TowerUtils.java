package com.thexfactor117.lsc.world.generation.tower;

import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 *
 * @author TheXFactor117
 *
 */
public class TowerUtils
{	
	private static final int ENTRANCES = 1; // amount of entrance templates
	private static final int FLOORS = 4; // amount of floor templates
	private static final int TOP_FLOORS = 1; // amount of top floor templates
	
	/**
	 * Returns a randomized entrance for the Tower Dungeon from all available entrance templates. 
	 * Be sure to set the correct amount of entrances above.
	 * @param world
	 * @param manager
	 * @return
	 */
	public static Template getRandomEntrance(World world)
	{
		WorldServer server = (WorldServer) world;
		TemplateManager manager = server.getStructureTemplateManager();
		int entranceID = (int) (Math.random() * ENTRANCES) + 1; // sets the random entrance room.
		Template template = manager.getTemplate(server.getMinecraftServer(), new ResourceLocation(Reference.MODID, "towers/entrance_" + entranceID));
		return template;
	}
	
	/**
	 * Returns a randomized floor for the Tower Dungeon from all available floor templates.
	 * Be sure to set the correct amount of floors above.
	 * @param world
	 * @param manager
	 * @return
	 */
	public static Template getRandomFloor(World world)
	{
		WorldServer server = (WorldServer) world;
		TemplateManager manager = server.getStructureTemplateManager();
		int floorID = (int) (Math.random() * FLOORS) + 1; // sets the random entrance room.
		Template template = manager.getTemplate(server.getMinecraftServer(), new ResourceLocation(Reference.MODID, "towers/floor_" + floorID));
		return template;
	}
	
	/**
	 * Returns a randomized floor for the top of Tower Dungeons from all available top floor templates.
	 * Be sure to set the correct amount of top floors above.
	 * @param world
	 * @return
	 */
	public static Template getRandomTopFloor(World world)
	{
		WorldServer server = (WorldServer) world;
		TemplateManager manager = server.getStructureTemplateManager();
		int floorID = (int) (Math.random() * TOP_FLOORS) + 1; // sets the random entrance room.
		Template template = manager.getTemplate(server.getMinecraftServer(), new ResourceLocation(Reference.MODID, "towers/top_floor_" + floorID));
		return template;
	}
}

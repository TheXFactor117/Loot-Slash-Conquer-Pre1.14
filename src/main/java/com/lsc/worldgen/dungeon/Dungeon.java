package com.lsc.worldgen.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.lsc.LootSlashConquer;
import com.lsc.util.Reference;
import com.lsc.worldgen.StructureHelper;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * @author TheXFactor117
 *
 * World Generator for the procedurally generated dungeons.
 * 
 * TODO: Fix cascading worldgen.
 *
 */
public class Dungeon extends WorldGenerator
{
	private int roomTries; // how many attempts will the generate try and make.
	private int roomCount;
	
	private ArrayList<StructureBoundingBox> roomList;
	
	public Dungeon(int roomTries)
	{
		this.roomTries = roomTries;
		this.roomCount = 0;
		this.roomList = Lists.newArrayList();
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) 
	{
		WorldServer server = (WorldServer) world;
		TemplateManager manager = server.getStructureTemplateManager();
		
		Template dungeonEntrance = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "dungeons/test_entrance"));
		PlacementSettings settings = new PlacementSettings();
		
		if (StructureHelper.canSpawnHere(dungeonEntrance, world, position))
		{
			LootSlashConquer.LOGGER.info("Generating Dungeon at " + position);
			
			// spawn the entrance on the surface
			BlockPos entrancePos = DungeonHelper.translateToCorner(dungeonEntrance, position, Rotation.NONE);
			dungeonEntrance.addBlocksToWorld(world, entrancePos, new DungeonBlockProcessor(entrancePos, settings, Blocks.NETHER_BRICK, Blocks.NETHERRACK), settings, 2);
			
			// start the procedural generation
			procedurallyGenerate(manager, world, position, this.generateStaircase(manager, world, position));
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Procedurally generate a dungeon through recursion using a simple 'Procedurally Build' algorithm.
	 * @param manager
	 * @param world
	 * @param startingPos - this position will ALWAYS be the position of the entrance.
	 */
	public void procedurallyGenerate(TemplateManager manager, World world, BlockPos startingPos, List<DungeonRoomPosition> nextPos)
	{
		List<DungeonRoomPosition> nextPos2 = Lists.newArrayList(); // list which will hold the positions to be tried in the NEXT recursion of the method.
		
		if (roomCount > roomTries) return;
		else
		{
			ArrayList<StructureBoundingBox> roomListCopy = Lists.newArrayList(roomList);
			
			for (DungeonRoomPosition room : nextPos)
			{	
				// check to make sure rooms don't overlap
				for (StructureBoundingBox existingBB : roomListCopy)
				{
					// if structures don't overlap
					if (!DungeonHelper.checkOverlap(existingBB, room.getBoundingBox()))
					{
						LootSlashConquer.LOGGER.info("Room generated successfully.");
						List<DungeonRoomPosition> nextRooms = this.generateRoom(manager, world, room);
						
						// copy new generated rooms into bigger list
						for (DungeonRoomPosition nextRoom : nextRooms)
						{
							nextPos2.add(nextRoom);
						}
					}
					else
					{
						LootSlashConquer.LOGGER.info("Room failed to generate.");
					}
				}
			}
		}
		
		roomCount++;
		procedurallyGenerate(manager, world, startingPos, nextPos2);
	}
	
	/** Generates the staircase underneath the entrance. */
	// WORKING
	private List<DungeonRoomPosition> generateStaircase(TemplateManager manager, World world, BlockPos entranceCenter)
	{
		Template encasedStaircase = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "dungeons/encased_staircase"));
		Template bottomStaircase = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "dungeons/bottom_staircase"));
		PlacementSettings settings = new PlacementSettings();
		int depth = 4; // how many staircases are generated?
		
		List<DungeonRoomPosition> list = null;
		
		for (int i = 0; i < depth; i++)
		{
			if (i < depth - 1) // make sure we aren't at the last staircase
			{
				BlockPos encasedStaircasePos = DungeonHelper.translateToCorner(encasedStaircase, entranceCenter.add(0, -4 * (i + 1), 0), Rotation.NONE); // get the staircase position; offset by height and multiply by depth.
				encasedStaircase.addBlocksToWorld(world, encasedStaircasePos, new DungeonBlockProcessor(encasedStaircasePos, settings, Blocks.NETHER_BRICK, Blocks.NETHERRACK), settings, 2);
			}
			else // we know this is the bottom staircase, so spawn bottom staircase and store potential rooms.
			{
				BlockPos bottomStaircaseCenteredPos = entranceCenter.add(0, -4 * (depth - 1) + -5, 0);
				BlockPos bottomStaircasePos = DungeonHelper.translateToCorner(bottomStaircase, bottomStaircaseCenteredPos, Rotation.NONE);
				bottomStaircase.addBlocksToWorld(world, bottomStaircasePos, new DungeonBlockProcessor(bottomStaircasePos, settings, Blocks.NETHER_BRICK, Blocks.NETHERRACK), settings, 2);
				roomList.add(DungeonHelper.getStructureBoundingBox(bottomStaircase, Rotation.NONE, bottomStaircaseCenteredPos)); // add StructureBoundingBox to room list. Used to make sure we don't generate rooms inside of other bounding boxes.
				
				list = this.generatePotentialRooms(manager, world, bottomStaircase, Rotation.NONE, bottomStaircaseCenteredPos, null);
			}
		}
		
		return list;
	}
	
	/** Generates a room based on the DungeonRoomPosition passed in. */
	@Nullable
	private List<DungeonRoomPosition> generateRoom(TemplateManager manager, World world, DungeonRoomPosition drp)
	{
		Template template = drp.getTemplate();
		PlacementSettings settings = new PlacementSettings().setRotation(drp.getTemplateRotation());
		BlockPos centeredPosition = drp.getPos();
		BlockPos cornerPosition = DungeonHelper.translateToCorner(template, centeredPosition, settings.getRotation());
		
		template.addBlocksToWorld(world, cornerPosition, new DungeonBlockProcessor(cornerPosition, settings, Blocks.NETHER_BRICK, Blocks.NETHERRACK), settings, 2);
		DungeonHelper.handleDataBlocks(template, world, cornerPosition, settings, 1);
		roomList.add(drp.getBoundingBox());
		
		return this.generatePotentialRooms(manager, world, template, settings.getRotation(), centeredPosition, drp.getSide());
	}
	
	/**
	 * Generates a stores potential room positions off of the current template. Note: this does not take into account existing rooms. Check for existing rooms when spawning each specific room position.
	 * @param currentTemplate
	 */
	private List<DungeonRoomPosition> generatePotentialRooms(TemplateManager manager, World world, Template currentTemplate, Rotation currentTemplateRotation, BlockPos currentCenter, Rotation currentSide)
	{
		List<DungeonRoomPosition> list = Lists.newArrayList();
		
		//Rotation side = Rotation.values()[(int) (Math.random() * 4)];
		for (Rotation side : Rotation.values())
		{
			Template nextTemplate = DungeonHelper.getRandomizedDungeonTemplate(manager, world);
			Rotation nextTemplateRotation = Rotation.values()[(int) (Math.random() * 4)];
			BlockPos centeredPosition = DungeonHelper.translateToNextRoom(currentTemplate, nextTemplate, currentCenter, side, currentTemplateRotation, nextTemplateRotation);
			StructureBoundingBox boundingBox = DungeonHelper.getStructureBoundingBox(nextTemplate, nextTemplateRotation, centeredPosition);
			
			if (currentSide == null || (currentSide != null && currentSide.add(Rotation.CLOCKWISE_180) != side)) // check to make sure we aren't spawning a room on the side we just spawned from.
			{
				list.add(new DungeonRoomPosition(centeredPosition, nextTemplate, nextTemplateRotation, side, boundingBox));
			}
		}
		
		return list;
	}
}

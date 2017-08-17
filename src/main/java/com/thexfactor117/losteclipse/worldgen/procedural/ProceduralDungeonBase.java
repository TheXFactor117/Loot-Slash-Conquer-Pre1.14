package com.thexfactor117.losteclipse.worldgen.procedural;

import java.util.ArrayList;

import com.thexfactor117.losteclipse.LostEclipse;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * @author TheXFactor117
 *
 */
public abstract class ProceduralDungeonBase extends WorldGenerator
{
	protected int roomCount; // amount of rooms generated on the current DEPTH.
	protected int depth; // current depth we are generating at.
	protected int maxRoomsPerDepth; // max rooms per depth level.
	protected int maxDepth; // amount of levels the dungeon will have.
	protected ArrayList<BlockPos> roomPositions; // stores ALL room positions that have been placed.
	
	public ProceduralDungeonBase(int maxDepth, int maxRoomsPerDepth)
	{
		this.roomCount = 0;
		this.depth = 1; // start at one because the first depth will never have rooms. 
		this.maxRoomsPerDepth = maxRoomsPerDepth;
		this.maxDepth = maxDepth;
		this.roomPositions = new ArrayList<BlockPos>();
	}
	
	public ArrayList<PotentialPosition> generateStaircase(TemplateManager manager, World world, BlockPos center)
	{
		// settings and such
		PlacementSettings settings = new PlacementSettings();
		Template template = DungeonHelper.getStaircaseTemplate(manager, world);

		// add blocks (and handle any data blocks)
		BlockPos corner = DungeonHelper.translateToCorner(template, center, Rotation.NONE); // translate from center to corner
		template.addBlocksToWorld(world, corner, settings); // spawn in template at corner pos
		DungeonHelper.handleDataBlocks(template, world, corner, settings, depth); // update chest contents
		roomPositions.add(center); // add template position to array
		
		ArrayList<PotentialPosition> potentialPositions = new ArrayList<PotentialPosition>(); // stores potential positions to be generated off this room.
		
		int rooms = (int) (Math.random() * 4); // the amount of rooms that could be generated
		
		for (int i = 0; i < rooms; i++)
		{
			Rotation nextRotation = Rotation.values()[(int) (Math.random() * 4)]; // generate a random rotation for next room
			potentialPositions.add(DungeonHelper.getPotentialRoomPosition(template, center, nextRotation));
		}
		
		return potentialPositions;
	}
	
	public ArrayList<PotentialPosition> generateRooms(TemplateManager manager, World world, ArrayList<PotentialPosition> potentialPositions, int depth)
	{
		ArrayList<PotentialPosition> nextPotentialPositions = new ArrayList<PotentialPosition>(); // master list of all next potential positions
		
		// loop through potential positions and generate a room at each one. store new potential positions in array to be passed back in.
		for (PotentialPosition position : potentialPositions)
		{
			ArrayList<PotentialPosition> tempNextPositions = generateRandomRoom(manager, world, position.getPos(), position.getRotation(), depth);
			
			if (tempNextPositions != null)
			{
				for (PotentialPosition tempPosition : tempNextPositions) // unpack temporary position list and add it to master list.
				{
					if (tempPosition != null) // remove null positions
						nextPotentialPositions.add(tempPosition); 
				}
			}
		}
		
		return nextPotentialPositions;
	}
	
	/**
	 * Generate a room at the given position. Returns an array of potential rooms to be tested next iteration.
	 */
	public ArrayList<PotentialPosition> generateRandomRoom(TemplateManager manager, World world, BlockPos center, Rotation rotation, int depth)
	{
		// if there is a room at a potential position, return.
		for (int i = 0; i < roomPositions.size(); i++)
		{
			if (center.equals(roomPositions.get(i)))
			{
				LostEclipse.LOGGER.debug("Room and potential position matched.");
				return null;
			}
		}
		
		// settings and such
		PlacementSettings settings = new PlacementSettings().setRotation(rotation);
		Template template = DungeonHelper.getRandomizedDungeonTemplate(manager, world, depth);
		
		// add blocks (and handle any data blocks)
		BlockPos corner = DungeonHelper.translateToCorner(template, center, rotation); // translate from center to corner
		template.addBlocksToWorld(world, corner, settings); // spawn in template at corner pos
		DungeonHelper.handleDataBlocks(template, world, corner, settings, depth); // update chest contents
		roomPositions.add(center); // add template position to array

		ArrayList<PotentialPosition> potentialPositions = new ArrayList<PotentialPosition>(); // stores potential positions to be generated off this room.
		
		int rooms = (int) (Math.random() * 4); // the amount of rooms that could be generated
		
		for (int i = 0; i < rooms; i++)
		{
			Rotation nextRotation = Rotation.values()[(int) (Math.random() * 4)]; // generate a random rotation for next room
			potentialPositions.add(DungeonHelper.getPotentialRoomPosition(template, center, nextRotation));
		}
		
		return potentialPositions;
	}
}

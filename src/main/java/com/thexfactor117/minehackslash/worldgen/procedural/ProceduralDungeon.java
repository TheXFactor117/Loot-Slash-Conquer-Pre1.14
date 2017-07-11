package com.thexfactor117.minehackslash.worldgen.procedural;

import java.util.ArrayList;
import java.util.Random;

import com.thexfactor117.minehackslash.MineHackSlash;
import com.thexfactor117.minehackslash.util.Reference;
import com.thexfactor117.minehackslash.worldgen.MHSWorldGenerator;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ProceduralDungeon extends ProceduralDungeonBase
{
	public ProceduralDungeon(int maxDepth, int maxRoomsPerDepth) 
	{
		super(maxDepth, maxRoomsPerDepth);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) 
	{
		WorldServer server = (WorldServer) world;
		TemplateManager manager = server.getStructureTemplateManager();
		
		// generate entrance
		Template dungeonEntrance = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "loot_room_1"));
		PlacementSettings settings = new PlacementSettings();
		
		// if can generate
		// generate starting room
		// generate FIRST staircase
		
		if (MHSWorldGenerator.canSpawnHere(dungeonEntrance, world, position))
		{	
			dungeonEntrance.addBlocksToWorld(world, DungeonHelper.translateToCorner(dungeonEntrance, position, Rotation.NONE), settings);
			DungeonHelper.handleDataBlocks(dungeonEntrance, world, position, settings);
			MineHackSlash.LOGGER.info("Generating Dungeon at " + position);
			// start procedural generate
			procedurallyGenerate(manager, world, position, null);
			
			return true;
		}
		
		return false;
	}
	
	public void procedurallyGenerate(TemplateManager manager, World world, BlockPos startingPos, ArrayList<PotentialPosition> potentialPositions)
	{
		MineHackSlash.LOGGER.info("Procedurally generating another round...");
		MineHackSlash.LOGGER.info("Current Depth: " + depth + "\tRoom Count: " + roomCount);
		ArrayList<PotentialPosition> nextPositions = potentialPositions; // create a new PotentialPosition array for the next iteration. 
		
		if (depth > maxDepth)
			return; // stop generating if we are over maxDepth.
		else
		{
			if (roomCount == 0)
			{
				int y = -8 * depth;
				
				if (depth == 1)
					y -= 8;
				
				// generate staircase underneath current staircase
				nextPositions = generateStaircase(manager, world, startingPos.add(0, y, 0)); 
			}
			else
			{
				// generate rooms. return potential positions
				nextPositions = generateRooms(manager, world, potentialPositions);
			}
		}
		
		roomCount++;
		
		if (roomCount == maxRoomsPerDepth)
		{
			depth++;
			roomCount = 0;
			nextPositions = null;
		}
		
		procedurallyGenerate(manager, world, startingPos, nextPositions);
	}
}

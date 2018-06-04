package com.lsc.worldgen;

import java.util.Map.Entry;

import com.lsc.init.ModLootTables;
import com.lsc.loot.Rarity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

/**
 * 
 * @author TheXFactor117
 *
 */
public class StructureHelper 
{
	public static int getGroundFromAbove(World world, int x, int z)
	{
		int y = 255;
		boolean foundGround = false;
		while(!foundGround && y-- >= 63)
		{
			Block blockAt = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = blockAt == Blocks.DIRT || blockAt == Blocks.GRASS || blockAt == Blocks.SAND || blockAt == Blocks.SNOW || blockAt == Blocks.SNOW_LAYER || blockAt == Blocks.GLASS;
		}

		return y;
	}
	
	public static boolean canSpawnHere(Template template, World world, BlockPos posAboveGround)
	{
		int zwidth = template.getSize().getZ();
		int xwidth = template.getSize().getX();
		
		// check all the corners to see which ones are replaceable
		boolean corner1 = isCornerValid(world, posAboveGround);
		boolean corner2 = isCornerValid(world, posAboveGround.add(xwidth, 0, zwidth));
		boolean corner3 = isCornerValid(world, posAboveGround.add(xwidth, 0, 0));
		boolean corner4 = isCornerValid(world, posAboveGround.add(0, 0, zwidth));
		
		// if Y > 60 and all corners pass the test, it's okay to spawn the structure
		return posAboveGround.getY() > 63 && corner1 && corner2 && corner3 && corner4;
	}
	
	public static boolean isCornerValid(World world, BlockPos pos)
	{
		int variation = 3;
		int highestBlock = getGroundFromAbove(world, pos.getX(), pos.getZ());
		
		if (highestBlock > pos.getY() - variation && highestBlock < pos.getY() + variation) return true;
				
		return false;
	}
	
	/**
	 * Iterates through every Data Structure Block in the given template. Used to add loot to chests.
	 * @param template
	 * @param world
	 * @param pos
	 * @param settings
	 */
	public static void handleDataBlocks(Template template, World world, BlockPos pos, PlacementSettings settings)
	{
		// loop through all data blocks within the structure
		for (Entry<BlockPos, String> e : template.getDataBlocks(pos, settings).entrySet())
		{
			handleChests(e, world);
			handleSpawners(e, world);
		}
	}
	
	private static void handleChests(Entry<BlockPos, String> e, World world)
	{
		BlockPos dataPos = e.getKey();
		int chance = (int) (Math.random() * 2);
		
		if ("common_chest".equals(e.getValue()))
		{
			world.setBlockState(dataPos, Blocks.CHEST.getDefaultState(), 3);
			TileEntity chest = world.getTileEntity(dataPos);
			
			if (chance == 0) setLootTable((TileEntityChest) chest, world, Rarity.COMMON);
			else world.setBlockToAir(dataPos);
		}
		else if ("uncommon_chest".equals(e.getValue()))
		{
			world.setBlockState(dataPos, Blocks.CHEST.getDefaultState(), 3);
			TileEntity chest = world.getTileEntity(dataPos);
			
			if (chance == 0) setLootTable((TileEntityChest) chest, world, Rarity.UNCOMMON);
			else world.setBlockToAir(dataPos);
		}
		else if ("rare_chest".equals(e.getValue()))
		{
			world.setBlockState(dataPos, Blocks.CHEST.getDefaultState(), 3);
			TileEntity chest = world.getTileEntity(dataPos);
			
			if (chance == 0) setLootTable((TileEntityChest) chest, world, Rarity.RARE);
			else world.setBlockToAir(dataPos);
		}
		else if ("epic_chest".equals(e.getValue()))
		{
			world.setBlockState(dataPos, Blocks.CHEST.getDefaultState(), 3);
			TileEntity chest = world.getTileEntity(dataPos);
			
			if (chance == 0) setLootTable((TileEntityChest) chest, world, Rarity.EPIC);
			else world.setBlockToAir(dataPos);
		}
		else if ("legendary_chest".equals(e.getValue()))
		{
			world.setBlockState(dataPos, Blocks.CHEST.getDefaultState(), 3);
			TileEntity chest = world.getTileEntity(dataPos);
			
			if (chance == 0) setLootTable((TileEntityChest) chest, world, Rarity.LEGENDARY);
			else world.setBlockToAir(dataPos);
		}
	}
	
	private static void handleSpawners(Entry<BlockPos, String> e, World world)
	{
		
	}
	
	private static void setLootTable(TileEntityChest chest, World world, Rarity chestRarity)
	{
		if (chestRarity == Rarity.COMMON)
		{
			Rarity lootRarity = Rarity.getWeightedRarity(world.rand, chestRarity);
			ResourceLocation table = getLootTable(lootRarity);
			chest.setLootTable(table, world.rand.nextLong());
			chest.setCustomName("Common Chest");
		}
		else if (chestRarity == Rarity.UNCOMMON)
		{
			Rarity lootRarity = Rarity.getWeightedRarity(world.rand, chestRarity);
			ResourceLocation table = getLootTable(lootRarity);
			chest.setLootTable(table, world.rand.nextLong());
			chest.setCustomName("Uncommon Chest");
		}
		else if (chestRarity == Rarity.RARE)
		{
			Rarity lootRarity = Rarity.getWeightedRarity(world.rand, chestRarity);
			ResourceLocation table = getLootTable(lootRarity);
			chest.setLootTable(table, world.rand.nextLong());
			chest.setCustomName("Rare Chest");
		}
		else if (chestRarity == Rarity.EPIC)
		{
			Rarity lootRarity = Rarity.getWeightedRarity(world.rand, chestRarity);
			ResourceLocation table = getLootTable(lootRarity);
			chest.setLootTable(table, world.rand.nextLong());
			chest.setCustomName("Epic Chest");
		}
		else if (chestRarity == Rarity.LEGENDARY)
		{
			Rarity lootRarity = Rarity.getWeightedRarity(world.rand, chestRarity);
			ResourceLocation table = getLootTable(lootRarity);
			chest.setLootTable(table, world.rand.nextLong());
			chest.setCustomName("Legendary Chest");
		}
	}
	
	private static ResourceLocation getLootTable(Rarity lootRarity)
	{
		if (lootRarity == Rarity.COMMON) return ModLootTables.common_chest;
		else if (lootRarity == Rarity.UNCOMMON) return ModLootTables.uncommon_chest;
		else if (lootRarity == Rarity.RARE) return ModLootTables.rare_chest;
		else if (lootRarity == Rarity.EPIC) return ModLootTables.epic_chest;
		else if (lootRarity == Rarity.LEGENDARY) return ModLootTables.legendary_chest;
		else return ModLootTables.common_chest;
	}
}

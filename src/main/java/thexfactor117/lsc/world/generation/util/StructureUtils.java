package thexfactor117.lsc.world.generation.util;

import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.template.Template;
import thexfactor117.lsc.entities.monsters.EntityBanshee;
import thexfactor117.lsc.entities.monsters.EntityBarbarian;
import thexfactor117.lsc.entities.monsters.EntityGhost;
import thexfactor117.lsc.entities.monsters.EntityGolem;
import thexfactor117.lsc.init.ModLootTables;
import thexfactor117.lsc.loot.Rarity;
import thexfactor117.lsc.util.RandomCollection;

/**
 *
 * @author TheXFactor117
 *
 * Utility class to help generate structures.
 *
 */
public class StructureUtils
{
	/*
	 * 
	 * BlockPos positioning helper methods. 
	 *
	 */
	
	/**
	 * Returns the topmost block at the given x/z coords.
	 * @param world
	 * @param x
	 * @param z
	 * @return
	 */
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
	
	/**
	 * Translates the given BlockPos to the corner of the template to correctly spawn structures.
	 * This is rotation friendly.
	 * @param template
	 * @param originalPos
	 * @param rotation
	 * @return
	 */
	public static BlockPos translateToCorner(Template template, BlockPos originalPos, Rotation rotation)
	{
		int x = originalPos.getX();
		int z = originalPos.getZ();
		
		switch (rotation)
		{
			case NONE:
				x -= template.getSize().getX() / 2;
				z -= template.getSize().getZ() / 2;
				break;
			case CLOCKWISE_90:
				x += template.getSize().getZ() / 2;
				z -= template.getSize().getX() / 2;
				break;
			case CLOCKWISE_180:
				x += template.getSize().getX() / 2;
				z += template.getSize().getZ() / 2;
				break;
			case COUNTERCLOCKWISE_90:
				x -= template.getSize().getZ() / 2;
				z += template.getSize().getX() / 2;
				break;
		}
		
		return new BlockPos(x, originalPos.getY(), z);
	}
	
	/*
	 * 
	 * Data block handling methods
	 * 
	 */
	
	/**
	 * Handles the data block manipulation for chests.
	 * This is a generic method, if we want more customization, implement
	 * it instead of calling this.
	 * @param world
	 * @param dataBlockPos
	 * @param e
	 * @param weight
	 */
	public static void handleChests(World world, BlockPos dataBlockPos, Entry<BlockPos, String> e, int weight)
	{
		int chance = (int) (Math.random() * weight);
		TileEntity tileentity;
		
		switch (e.getValue())
		{
			case "common_chest":
				world.setBlockState(dataBlockPos, Blocks.AIR.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos.down(1));
				
				if (tileentity instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest) tileentity;
					if (chance == 0) setLootTable(chest, world, Rarity.COMMON);
					else world.setBlockToAir(dataBlockPos.down(1));
				}
				break;
			case "uncommon_chest":
				world.setBlockState(dataBlockPos, Blocks.AIR.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos.down(1));
				
				if (tileentity instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest) tileentity;
					if (chance == 0) setLootTable(chest, world, Rarity.UNCOMMON);
					else world.setBlockToAir(dataBlockPos.down(1));
				}
				break;
			case "rare_chest":
				world.setBlockState(dataBlockPos, Blocks.AIR.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos.down(1));
				
				if (tileentity instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest) tileentity;
					if (chance == 0) setLootTable(chest, world, Rarity.RARE);
					else world.setBlockToAir(dataBlockPos.down(1));
				}
				break;
			case "epic_chest":
				world.setBlockState(dataBlockPos, Blocks.AIR.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos.down(1));
				
				if (tileentity instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest) tileentity;
					if (chance == 0) setLootTable(chest, world, Rarity.EPIC);
					else world.setBlockToAir(dataBlockPos.down(1));
				}
				break;
			case "legendary_chest":
				world.setBlockState(dataBlockPos, Blocks.AIR.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos.down(1));
				
				if (tileentity instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest) tileentity;
					if (chance == 0) setLootTable(chest, world, Rarity.LEGENDARY);
					else world.setBlockToAir(dataBlockPos.down(1));
				}
				break;
		}
	}
	
	/**
	 * Handles the data block manipulation for spawners.
	 * This is a generic method, if we want more customization, implement
	 * it instead of calling this.
	 * @param world
	 * @param dataBlockPos
	 * @param e
	 * @param weight
	 */
	public static void handleSpawners(World world, BlockPos dataBlockPos, Entry<BlockPos, String> e, int weight)
	{
		int chance = (int) (Math.random() * weight);
		if (chance != 0) return; // return if the chance doesn't equal zero.
		
		TileEntity tileentity;
		
		switch (e.getValue())
		{
			// TODO: implement differences between spawners
			
			
			case "common_mob_spawner":
				world.setBlockState(dataBlockPos, Blocks.MOB_SPAWNER.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos);
				
				if (tileentity instanceof TileEntityMobSpawner)
				{
					TileEntityMobSpawner spawner = (TileEntityMobSpawner) tileentity;
					MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();
					setSpawnerLogic(logic, world.rand);
				}
				break;
			case "uncommon_mob_spawner":
				world.setBlockState(dataBlockPos, Blocks.MOB_SPAWNER.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos);
				
				if (tileentity instanceof TileEntityMobSpawner)
				{
					TileEntityMobSpawner spawner = (TileEntityMobSpawner) tileentity;
					MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();
					setSpawnerLogic(logic, world.rand);
				}
				break;
			case "rare_mob_spawner":
				world.setBlockState(dataBlockPos, Blocks.MOB_SPAWNER.getDefaultState(), 3);
				tileentity = world.getTileEntity(dataBlockPos);
				
				if (tileentity instanceof TileEntityMobSpawner)
				{
					TileEntityMobSpawner spawner = (TileEntityMobSpawner) tileentity;
					MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();
					setSpawnerLogic(logic, world.rand);
				}
				break;
		}
	}
	
	/**
	 * Sets the loot table of the passed in chest entity.
	 * @param chest
	 * @param world
	 * @param chestRarity
	 */
	private static void setLootTable(TileEntityChest chest, World world, Rarity chestRarity)
	{
		switch (chestRarity)
		{
			case COMMON:
				chest.setLootTable(ModLootTables.COMMON_CHEST, world.rand.nextLong());
				chest.setCustomName("Common Chest");
				break;
			case UNCOMMON:
				chest.setLootTable(ModLootTables.UNCOMMON_CHEST, world.rand.nextLong());
				chest.setCustomName("Uncommon Chest");
				break;
			case RARE:
				chest.setLootTable(ModLootTables.RARE_CHEST, world.rand.nextLong());
				chest.setCustomName("Rare Chest");
				break;
			case EPIC:
				chest.setLootTable(ModLootTables.EPIC_CHEST, world.rand.nextLong());
				chest.setCustomName("Epic Chest");
				break;
			case LEGENDARY:
				chest.setLootTable(ModLootTables.LEGENDARY_CHEST, world.rand.nextLong());
				chest.setCustomName("Legendary Chest");
				break;
			default:
				break;
		}
	}
	
	/**
	 * Writes custom data to the logic of the spawner.
	 * @param logic
	 * @param rand
	 */
	private static void setSpawnerLogic(MobSpawnerBaseLogic logic, Random rand)
	{
		logic.setEntityId(getRandomMonster(rand));
		
		NBTTagCompound nbt = new NBTTagCompound();
		logic.writeToNBT(nbt);
		
		nbt.setShort("SpawnCount", (short) 4);
		nbt.setShort("MinSpawnDelay", (short) (20 * 10));
		nbt.setShort("MaxSpawnDelay", (short) (20 * 30));
		nbt.setShort("MaxNearbyEntities", (short) 6);
		nbt.setShort("SpawnRange", (short) 10);

		logic.readFromNBT(nbt);
	}
	
	/**
	 * Returns a randomized entity from a custom list with static weights.
	 * @param rand
	 * @return
	 */
	private static ResourceLocation getRandomMonster(Random rand)
	{
		RandomCollection<ResourceLocation> monsters = new RandomCollection<ResourceLocation>();
		
		monsters.add(10, EntityList.getKey(EntityZombie.class));
		monsters.add(10, EntityList.getKey(EntitySkeleton.class));
		monsters.add(10, EntityList.getKey(EntitySpider.class));
		monsters.add(7, EntityList.getKey(EntityGhost.class));
		monsters.add(7, EntityList.getKey(EntityBarbarian.class));
		//monsters.add(4, EntityList.getKey(EntityBandit.class));
		monsters.add(3, EntityList.getKey(EntityBanshee.class));
		monsters.add(1, EntityList.getKey(EntityGolem.class));
		
		return monsters.next(rand);
	}
	
	/*
	 * 
	 * Miscellaneous
	 * 
	 */
	
	public static void addOreSpawn(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY)
	{
		for (int i = 0; i < chanceToSpawn; i++)
		{
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(maxZ);
			new WorldGenMinable(block, maxVeinSize).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
}

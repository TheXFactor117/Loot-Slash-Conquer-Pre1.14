package com.thexfactor117.losteclipse.worldgen.procedural;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.init.ModLootTables;
import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * @author TheXFactor117
 *
 */
public class DungeonHelper 
{
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
			if ("random_chest".equals(e.getValue())) // check data block tag
			{
				BlockPos dataPos = e.getKey();
				world.setBlockState(dataPos, Blocks.AIR.getDefaultState(), 3); // remove data block
				TileEntity chestEntity = world.getTileEntity(dataPos.down()); // chest is located under data block
				int chance = (int) (Math.random() * 2);
				
				if (chestEntity instanceof TileEntityChest && chance == 0)
				{
					int rand = (int) (Math.random() * 10);
					
					if (rand > 3) ((TileEntityChest) chestEntity).setLootTable(ModLootTables.common_loot_room, world.rand.nextLong());
					else if (rand > 0) ((TileEntityChest) chestEntity).setLootTable(ModLootTables.rare_loot_room, world.rand.nextLong());
					else if (rand == 0) ((TileEntityChest) chestEntity).setLootTable(ModLootTables.legendary_loot_room, world.rand.nextLong());
				}
				else
				{
					world.setBlockState(chestEntity.getPos(), Blocks.AIR.getDefaultState(), 3);
				}
			}
			else if ("random_spawner".equals(e.getValue()))
			{
				BlockPos dataPos = e.getKey();
				world.setBlockState(dataPos, Blocks.AIR.getDefaultState(), 3);
				TileEntity spawnerEntity = world.getTileEntity(dataPos.down());
				
				if (spawnerEntity instanceof TileEntityMobSpawner)
				{
					MobSpawnerBaseLogic logic = ((TileEntityMobSpawner) spawnerEntity).getSpawnerBaseLogic();
					
					LostEclipse.LOGGER.info("Entity ID: " + EntityList.getKey(EntityEnderman.class));
					
					logic.setEntityId(getRandomMonster());
				}
			}
		}
	}
	
	private static ResourceLocation getRandomMonster()
	{
		ArrayList<ResourceLocation> entities = new ArrayList<ResourceLocation>();
		
		entities.add(EntityList.getKey(EntityZombie.class));
		entities.add(EntityList.getKey(EntitySpider.class));
		entities.add(EntityList.getKey(EntitySkeleton.class));
		entities.add(EntityList.getKey(EntityEnderman.class));
		entities.add(EntityList.getKey(EntityCreeper.class));
		entities.add(EntityList.getKey(EntityCaveSpider.class));
		
		return entities.get((int) (Math.random() * entities.size()));	
	}
	
	public static PotentialPosition getPotentialRoomPosition(Template template, BlockPos center, Rotation rotation)
	{
		int x = center.getX();
		int z = center.getZ();
		
		if (rotation == Rotation.NONE) x += template.getSize().getX() - 1;
		else if (rotation == Rotation.CLOCKWISE_90) z += template.getSize().getZ() - 1;
		else if (rotation == Rotation.CLOCKWISE_180) x -= template.getSize().getX() - 1;
		else if (rotation == Rotation.COUNTERCLOCKWISE_90) z -= template.getSize().getZ() - 1;
		
		return new PotentialPosition(new BlockPos(x, center.getY(), z), rotation);
	}
	
	public static Template getRandomizedDungeonTemplate(TemplateManager manager, World world)
	{
		ArrayList<Template> templates = new ArrayList<Template>();
		
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "loot_room_random1")));
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "loot_room_random2")));
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "spawner1")));
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "maze1")));
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "fluff1")));
		
		return templates.get((int) (Math.random() * (templates.size())));
	}
	
	public static Template getStaircaseTemplate(TemplateManager manager, World world)
	{
		ArrayList<Template> templates = new ArrayList<Template>();
		
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "staircase_1")));
		
		return templates.get((int) (Math.random() * (templates.size())));
	}
	
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
				x += template.getSize().getX() / 2;
				z -= template.getSize().getZ() / 2;
				break;
			case CLOCKWISE_180:
				x += template.getSize().getX() / 2;
				z += template.getSize().getZ() / 2;
				break;
			case COUNTERCLOCKWISE_90:
				x -= template.getSize().getX() / 2;
				z += template.getSize().getZ() / 2;
				break;
		}
		
		return new BlockPos(x, originalPos.getY(), z);
	}
}

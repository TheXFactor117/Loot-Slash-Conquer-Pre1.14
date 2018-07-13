package thexfactor117.lsc.world.generation.dungeon;

import java.util.ArrayList;
import java.util.Map.Entry;

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
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import thexfactor117.lsc.LootSlashConquer;
import thexfactor117.lsc.init.ModLootTables;
import thexfactor117.lsc.util.RandomCollection;
import thexfactor117.lsc.util.Reference;

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
	public static void handleDataBlocks(Template template, World world, BlockPos pos, PlacementSettings settings, int multiplier)
	{
		// loop through all data blocks within the structure
		for (Entry<BlockPos, String> e : template.getDataBlocks(pos, settings).entrySet())
		{
			if ("chest".equals(e.getValue())) // check data block tag
			{
				BlockPos dataPos = e.getKey();
				world.setBlockState(dataPos, Blocks.AIR.getDefaultState(), 3); // remove data block
				TileEntity chestEntity = world.getTileEntity(dataPos.down()); // chest is located under data block
				int chance = (int) (Math.random() * 4);
				
				if (chestEntity instanceof TileEntityChest && chance == 0)
				{
					setLootTable((TileEntityChest) chestEntity, world, multiplier);
				}
				else
				{
					world.setBlockState(chestEntity.getPos(), Blocks.AIR.getDefaultState(), 3);
				}
			}
			else if ("mob_spawner".equals(e.getValue()))
			{
				BlockPos dataPos = e.getKey();
				world.setBlockState(dataPos, Blocks.AIR.getDefaultState(), 3);
				world.setBlockState(dataPos.down(), Blocks.MOB_SPAWNER.getDefaultState(), 2);
				TileEntity spawnerEntity = world.getTileEntity(dataPos.down());
				
				if (spawnerEntity instanceof TileEntityMobSpawner)
				{
					MobSpawnerBaseLogic logic = ((TileEntityMobSpawner) spawnerEntity).getSpawnerBaseLogic();
										
					logic.setEntityId(getRandomMonster());
				}
			}
		}
	}
	
	private static void setLootTable(TileEntityChest chestEntity, World world, int multiplier)
	{
		double common = 75 / multiplier;
		double uncommon = 15;
		double rare = 6;
		double epic = 3;
		double legendary = 1;
		
		RandomCollection<ResourceLocation> loottables = new RandomCollection<ResourceLocation>();
		
		loottables.add(common, ModLootTables.COMMON_CHEST);
		
		ResourceLocation table = loottables.next(world.rand);
		chestEntity.setLootTable(table, world.rand.nextLong());
		
		if (table == ModLootTables.COMMON_CHEST) chestEntity.setCustomName("Common Chest");
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
	
	public static BlockPos translateWallPos(Template template, BlockPos wallPos, Rotation wallRotation)
	{
		int x = wallPos.getX();
		int z = wallPos.getZ();
		
		if (wallRotation == Rotation.NONE) x += template.getSize().getX() / 2;
		else if (wallRotation == Rotation.CLOCKWISE_90) z += template.getSize().getZ() / 2;
		else if (wallRotation == Rotation.CLOCKWISE_180) x -= template.getSize().getX() / 2;
		else if (wallRotation == Rotation.COUNTERCLOCKWISE_90) z -= template.getSize().getZ() / 2;
		
		return new BlockPos(x, wallPos.getY(), z);
	}
	
	/** Returns the bounding box of the specific template. Used to make sure it doesn't intersect with other rooms. */
	public static StructureBoundingBox getStructureBoundingBox(Template template, Rotation rotation, BlockPos center)
	{
		int minX = 0;
		int maxX = 0; 
		int minY = 0;
		int maxY = 0;
		int minZ = 0;
		int maxZ = 0;
		
		// we offset everything by one to get the inner room with walls, ceilings, or floors. Rooms can connect through walls so we want those checks to pass.
		if (rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_180)
		{
			minX = center.getX() - (template.getSize().getX() / 2) - 1;
			maxX = center.getX() + (template.getSize().getX() / 2) - 1;
			minY = center.getY() + 1;
			maxY = center.getY() + template.getSize().getY() - 1;
			minZ = center.getZ() - (template.getSize().getZ() / 2) - 1;
			maxZ = center.getZ() + (template.getSize().getZ() / 2) - 1;
		}
		else
		{
			minX = center.getX() - (template.getSize().getZ() / 2) - 1;
			maxX = center.getX() + (template.getSize().getZ() / 2) - 1;
			minY = center.getY() + 1;
			maxY = center.getY() + template.getSize().getY() - 1;
			minZ = center.getZ() - (template.getSize().getX() / 2) - 1;
			maxZ = center.getZ() + (template.getSize().getX() / 2) - 1;
		}
		
		return new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	/** Returns true if the two structures overlap. */
	public static boolean checkOverlap(StructureBoundingBox existingStructure, StructureBoundingBox nextStructure)
	{
		/*if (existingStructure.minX > nextStructure.maxX) return false;
		if (existingStructure.maxX < nextStructure.minX) return false;
		if (existingStructure.minY > nextStructure.maxY) return false;
		if (existingStructure.maxY < nextStructure.minY) return false;
		if (existingStructure.minZ > nextStructure.maxZ) return false;
		if (existingStructure.maxZ < nextStructure.minZ) return false;*/
		
		return false;
	}
	
	public static Template getRandomizedDungeonTemplate(TemplateManager manager, World world)
	{
		ArrayList<Template> templates = new ArrayList<Template>();

		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "dungeons/test_room1")));
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "dungeons/test_room2")));
		
		return templates.get((int) (Math.random() * (templates.size())));
	}
	
	public static Template getStaircaseTemplate(TemplateManager manager, World world)
	{
		ArrayList<Template> templates = new ArrayList<Template>();
		
		templates.add(manager.getTemplate(world.getMinecraftServer(), new ResourceLocation(Reference.MODID, "staircase1")));
		
		return templates.get((int) (Math.random() * (templates.size())));
	}
	
	/** Translates the given BlockPos to the corner of the structure for spawning. */
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
	
	/** Translates the given BlockPos to the next possible room position based on rotation and the size of the current and next template. */
	public static BlockPos translateToNextRoom(Template currentTemplate, Template nextTemplate, BlockPos currentCenter, Rotation side, Rotation currentTemplateRotation, Rotation nextTemplateRotation)
	{
		int x = currentCenter.getX();
		int z = currentCenter.getZ();
		int currentX = currentTemplate.getSize().getX() / 2;
		int currentZ = currentTemplate.getSize().getZ() / 2;
		int nextX = nextTemplate.getSize().getX() / 2;
		int nextZ = nextTemplate.getSize().getZ() / 2;
		
		if (side == Rotation.NONE)
		{
			if (currentTemplateRotation == Rotation.NONE || currentTemplateRotation == Rotation.CLOCKWISE_180)
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) x += currentX + nextX;
				else x += currentX + nextZ;
			}
			else
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) x += currentZ + nextX;
				else x += currentZ + nextZ;
			}
		}
		else if (side == Rotation.CLOCKWISE_90)
		{
			if (currentTemplateRotation == Rotation.NONE || currentTemplateRotation == Rotation.CLOCKWISE_180)
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) z += currentZ + nextZ;
				else z += currentZ + nextX;
			}
			else
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) z += currentX + nextZ;
				else z += currentX + nextX;
			}
		}
		else if (side == Rotation.CLOCKWISE_180)
		{
			if (currentTemplateRotation == Rotation.NONE || currentTemplateRotation == Rotation.CLOCKWISE_180)
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) x -= currentX + nextX;
				else x -= currentX + nextZ;
			}
			else
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) x -= currentZ + nextX;
				else x -= currentZ + nextZ;
			}
		}
		else if (side == Rotation.COUNTERCLOCKWISE_90)
		{
			if (currentTemplateRotation == Rotation.NONE || currentTemplateRotation == Rotation.CLOCKWISE_180)
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) z -= currentZ + nextZ;
				else z -= currentZ + nextX;
			}
			else
			{
				if (nextTemplateRotation == Rotation.NONE || nextTemplateRotation == Rotation.CLOCKWISE_180) z -= currentX + nextZ;
				else z -= currentX + nextX;
			}
		}
		
		return new BlockPos(x, currentCenter.getY(), z); 
	}
}

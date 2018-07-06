package com.lsc.init;

import com.lsc.blocks.BlockBossDoor;
import com.lsc.blocks.BlockDungeonBrick;
import com.lsc.blocks.BlockJar;
import com.lsc.blocks.BlockLootChest;
import com.lsc.loot.Rarity;
import com.lsc.tileentity.TileEntityLootChest;
import com.lsc.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class ModBlocks 
{
	public static final Block DUNGEON_BRICK = new BlockDungeonBrick(Material.ROCK, "dungeon_brick");
	public static final Block BOSS_DOOR = new BlockBossDoor("boss_door");
	public static final Block COMMON_LOOT_CHEST = new BlockLootChest("common_loot_chest", Rarity.COMMON);
	public static final Block UNCOMMON_LOOT_CHEST = new BlockLootChest("uncommon_loot_chest", Rarity.UNCOMMON);
	public static final Block RARE_LOOT_CHEST = new BlockLootChest("rare_loot_chest", Rarity.RARE);
	public static final Block EPIC_LOOT_CHEST = new BlockLootChest("epic_loot_chest", Rarity.EPIC);
	public static final Block LEGENDARY_LOOT_CHEST = new BlockLootChest("legendary_loot_chest", Rarity.LEGENDARY);
	public static final Block COMMON_JAR = new BlockJar("common_jar", Rarity.COMMON);
	public static final Block UNCOMMON_JAR = new BlockJar("uncommon_jar", Rarity.UNCOMMON);
	public static final Block RARE_JAR = new BlockJar("rare_jar", Rarity.RARE);
	public static final Block EPIC_JAR = new BlockJar("epic_jar", Rarity.EPIC);
	public static final Block LEGENDARY_JAR = new BlockJar("legendary_jar", Rarity.LEGENDARY);
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(DUNGEON_BRICK);
		event.getRegistry().register(BOSS_DOOR);
		event.getRegistry().register(COMMON_LOOT_CHEST);
		event.getRegistry().register(UNCOMMON_LOOT_CHEST);
		event.getRegistry().register(RARE_LOOT_CHEST);
		event.getRegistry().register(EPIC_LOOT_CHEST);
		event.getRegistry().register(LEGENDARY_LOOT_CHEST);
		event.getRegistry().register(COMMON_JAR);
		event.getRegistry().register(UNCOMMON_JAR);
		event.getRegistry().register(RARE_JAR);
		event.getRegistry().register(EPIC_JAR);
		event.getRegistry().register(LEGENDARY_JAR);
		
		// register tile entities
		GameRegistry.registerTileEntity(TileEntityLootChest.class, new ResourceLocation(Reference.MODID, "loot_chest"));
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(DUNGEON_BRICK).setRegistryName(DUNGEON_BRICK.getRegistryName()));
		event.getRegistry().register(new ItemBlock(BOSS_DOOR).setRegistryName(BOSS_DOOR.getRegistryName()));
		event.getRegistry().register(new ItemBlock(COMMON_LOOT_CHEST).setRegistryName(COMMON_LOOT_CHEST.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UNCOMMON_LOOT_CHEST).setRegistryName(UNCOMMON_LOOT_CHEST.getRegistryName()));
		event.getRegistry().register(new ItemBlock(RARE_LOOT_CHEST).setRegistryName(RARE_LOOT_CHEST.getRegistryName()));
		event.getRegistry().register(new ItemBlock(EPIC_LOOT_CHEST).setRegistryName(EPIC_LOOT_CHEST.getRegistryName()));
		event.getRegistry().register(new ItemBlock(LEGENDARY_LOOT_CHEST).setRegistryName(LEGENDARY_LOOT_CHEST.getRegistryName()));
		event.getRegistry().register(new ItemBlock(COMMON_JAR).setRegistryName(COMMON_JAR.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UNCOMMON_JAR).setRegistryName(UNCOMMON_JAR.getRegistryName()));
		event.getRegistry().register(new ItemBlock(RARE_JAR).setRegistryName(RARE_JAR.getRegistryName()));
		event.getRegistry().register(new ItemBlock(EPIC_JAR).setRegistryName(EPIC_JAR.getRegistryName()));
		event.getRegistry().register(new ItemBlock(LEGENDARY_JAR).setRegistryName(LEGENDARY_JAR.getRegistryName()));
	}
}

package com.thexfactor117.lsc.init;

import com.thexfactor117.lsc.blocks.BlockBossDoor;
import com.thexfactor117.lsc.blocks.BlockDungeonBrick;
import com.thexfactor117.lsc.blocks.BlockLootBarrel;
import com.thexfactor117.lsc.blocks.BlockLootChest;
import com.thexfactor117.lsc.blocks.BlockLootCrate;
import com.thexfactor117.lsc.blocks.BlockLootJar;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.tileentity.TileEntityLootChest;
import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.launchwrapper.Launch;
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
	public static final Block COMMON_JAR = new BlockLootJar("common_jar", Rarity.COMMON);
	public static final Block UNCOMMON_JAR = new BlockLootJar("uncommon_jar", Rarity.UNCOMMON);
	public static final Block RARE_JAR = new BlockLootJar("rare_jar", Rarity.RARE);
	public static final Block EPIC_JAR = new BlockLootJar("epic_jar", Rarity.EPIC);
	public static final Block LEGENDARY_JAR = new BlockLootJar("legendary_jar", Rarity.LEGENDARY);
	public static final Block COMMON_BARREL = new BlockLootBarrel("common_barrel", Rarity.COMMON);
	public static final Block UNCOMMON_BARREL = new BlockLootBarrel("uncommon_barrel", Rarity.UNCOMMON);
	public static final Block RARE_BARREL = new BlockLootBarrel("rare_barrel", Rarity.RARE);
	public static final Block EPIC_BARREL = new BlockLootBarrel("epic_barrel", Rarity.EPIC);
	public static final Block LEGENDARY_BARREL = new BlockLootBarrel("legendary_barrel", Rarity.LEGENDARY);
	public static final Block COMMON_CRATE = new BlockLootCrate("common_crate", Rarity.COMMON);
	public static final Block UNCOMMON_CRATE = new BlockLootCrate("uncommon_crate", Rarity.UNCOMMON);
	public static final Block RARE_CRATE = new BlockLootCrate("rare_crate", Rarity.RARE);
	public static final Block EPIC_CRATE = new BlockLootCrate("epic_crate", Rarity.EPIC);
	public static final Block LEGENDARY_CRATE = new BlockLootCrate("legendary_crate", Rarity.LEGENDARY);
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(DUNGEON_BRICK);
		event.getRegistry().register(BOSS_DOOR);
		
		if ((boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
		{
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
			event.getRegistry().register(COMMON_BARREL);
			event.getRegistry().register(UNCOMMON_BARREL);
			event.getRegistry().register(RARE_BARREL);
			event.getRegistry().register(EPIC_BARREL);
			event.getRegistry().register(LEGENDARY_BARREL);
			event.getRegistry().register(COMMON_CRATE);
			event.getRegistry().register(UNCOMMON_CRATE);
			event.getRegistry().register(RARE_CRATE);
			event.getRegistry().register(EPIC_CRATE);
			event.getRegistry().register(LEGENDARY_CRATE);
			
			// register tile entities
			GameRegistry.registerTileEntity(TileEntityLootChest.class, new ResourceLocation(Reference.MODID, "loot_chest"));
		}
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(DUNGEON_BRICK).setRegistryName(DUNGEON_BRICK.getRegistryName()));
		event.getRegistry().register(new ItemBlock(BOSS_DOOR).setRegistryName(BOSS_DOOR.getRegistryName()));
		
		if ((boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
		{
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
			event.getRegistry().register(new ItemBlock(COMMON_BARREL).setRegistryName(COMMON_BARREL.getRegistryName()));
			event.getRegistry().register(new ItemBlock(UNCOMMON_BARREL).setRegistryName(UNCOMMON_BARREL.getRegistryName()));
			event.getRegistry().register(new ItemBlock(RARE_BARREL).setRegistryName(RARE_BARREL.getRegistryName()));
			event.getRegistry().register(new ItemBlock(EPIC_BARREL).setRegistryName(EPIC_BARREL.getRegistryName()));
			event.getRegistry().register(new ItemBlock(LEGENDARY_BARREL).setRegistryName(LEGENDARY_BARREL.getRegistryName()));
			event.getRegistry().register(new ItemBlock(COMMON_CRATE).setRegistryName(COMMON_CRATE.getRegistryName()));
			event.getRegistry().register(new ItemBlock(UNCOMMON_CRATE).setRegistryName(UNCOMMON_CRATE.getRegistryName()));
			event.getRegistry().register(new ItemBlock(RARE_CRATE).setRegistryName(RARE_CRATE.getRegistryName()));
			event.getRegistry().register(new ItemBlock(EPIC_CRATE).setRegistryName(EPIC_CRATE.getRegistryName()));
			event.getRegistry().register(new ItemBlock(LEGENDARY_CRATE).setRegistryName(LEGENDARY_CRATE.getRegistryName()));
		}
	}
}

package com.lsc.events;

import com.lsc.config.Configs;
import com.lsc.init.ModLootTables;
import com.lsc.loot.table.CustomLootTable;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventLoadLootTable 
{
	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent event)
	{	
		if (event.getName() == ModLootTables.COMMON_CHEST) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.UNCOMMON_CHEST) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.RARE_CHEST) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.EPIC_CHEST) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.LEGENDARY_CHEST) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.COMMON_JAR) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.UNCOMMON_JAR) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.RARE_JAR) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.EPIC_JAR) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.LEGENDARY_JAR) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.COMMON_BARREL) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.UNCOMMON_BARREL) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.RARE_BARREL) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.EPIC_BARREL) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.LEGENDARY_BARREL) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.COMMON_CRATE) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.UNCOMMON_CRATE) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.RARE_CRATE) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.EPIC_CRATE) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		else if (event.getName() == ModLootTables.LEGENDARY_CRATE) event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		
		if (Configs.enableVanillaLoot)
		{
			changeVanillaTables(event);
		}
	}
	
	private static void changeVanillaTables(LootTableLoadEvent event)
	{
		// monsters
		if (event.getName() == LootTableList.ENTITIES_BLAZE) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_CREEPER) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ELDER_GUARDIAN) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDER_DRAGON) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDERMAN) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDERMITE) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_EVOCATION_ILLAGER) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_GHAST) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_GUARDIAN) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_HUSK) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_MAGMA_CUBE) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SHULKER) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SKELETON) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SLIME) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SPIDER) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_VEX) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_VINDICATION_ILLAGER) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_WITCH) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_WITHER_SKELETON) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE_PIGMAN) addEntityPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE_VILLAGER) addEntityPool(event.getTable());
		// chests
		else if (event.getName() == LootTableList.CHESTS_ABANDONED_MINESHAFT) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_DESERT_PYRAMID) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_END_CITY_TREASURE) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_IGLOO_CHEST) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_JUNGLE_TEMPLE) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_NETHER_BRIDGE) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_SIMPLE_DUNGEON) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_SPAWN_BONUS_CHEST) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_STRONGHOLD_CORRIDOR) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_STRONGHOLD_CROSSING) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_STRONGHOLD_LIBRARY) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_VILLAGE_BLACKSMITH) addChestPool(event.getTable());
		else if (event.getName() == LootTableList.CHESTS_WOODLAND_MANSION) addChestPool(event.getTable());
	}
	
	private static void addEntityPool(LootTable table)
	{
		LootEntry common = new LootEntryTable(new ResourceLocation("lootslashconquer:entities/common_entity"), 1, 1, new LootCondition[0], "common");
		LootPool pool = new LootPool(new LootEntry[] { common }, new LootCondition[0], new RandomValueRange(0, 1), new RandomValueRange(0), "loot");
		table.addPool(pool);
	}
	
	private static void addChestPool(LootTable table)
	{
		LootEntry entry = new LootEntryTable(new ResourceLocation("lootslashconquer:chests/common_chest"), 1, 1, new LootCondition[0], "common");
		LootPool pool = new LootPool(new LootEntry[] { entry }, new LootCondition[0], new RandomValueRange(0, 1), new RandomValueRange(0), "loot");
		table.addPool(pool);
	}
}

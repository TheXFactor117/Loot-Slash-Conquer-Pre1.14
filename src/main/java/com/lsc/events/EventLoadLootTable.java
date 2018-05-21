package com.lsc.events;

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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventLoadLootTable 
{
	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event)
	{	
		if (event.getName() == ModLootTables.common_chest)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
		
		changeVanillaTables(event);
	}
	
	private static void changeVanillaTables(LootTableLoadEvent event)
	{
		// monsters
		if (event.getName() == LootTableList.ENTITIES_BLAZE) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_CREEPER) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ELDER_GUARDIAN) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDER_DRAGON) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDERMAN) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDERMITE) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_EVOCATION_ILLAGER) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_GHAST) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_GUARDIAN) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_HUSK) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_MAGMA_CUBE) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SHULKER) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SKELETON) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SLIME) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SPIDER) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_VEX) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_VINDICATION_ILLAGER) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_WITCH) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_WITHER_SKELETON) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE_PIGMAN) addRandomizedPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE_VILLAGER) addRandomizedPool(event.getTable());
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
	
	private static void addRandomizedPool(LootTable table)
	{
		LootEntry common = new LootEntryTable(new ResourceLocation("lootslashconquer:chests/common_chest"), 1, 1, new LootCondition[0], "common");
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

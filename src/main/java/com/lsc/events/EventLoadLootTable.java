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
		
		if (event.getName() == ModLootTables.uncommon_chest)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
		
		if (event.getName() == ModLootTables.rare_chest)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
		
		if (event.getName() == ModLootTables.legendary_chest)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
		
		if (event.getName() == ModLootTables.exotic_chest)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
		
		changeVanillaTables(event);
	}
	
	private static void changeVanillaTables(LootTableLoadEvent event)
	{
		if (event.getName() == LootTableList.ENTITIES_BLAZE) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_CREEPER) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ELDER_GUARDIAN) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDER_DRAGON) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDERMAN) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ENDERMITE) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_EVOCATION_ILLAGER) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_GHAST) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_GUARDIAN) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_HUSK) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_MAGMA_CUBE) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SHULKER) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SKELETON) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SLIME) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_SPIDER) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_VEX) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_VINDICATION_ILLAGER) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_WITCH) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_WITHER_SKELETON) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE_PIGMAN) addPool(event.getTable());
		else if (event.getName() == LootTableList.ENTITIES_ZOMBIE_VILLAGER) addPool(event.getTable());
	}
	
	private static void addPool(LootTable table)
	{
		LootEntry common = new LootEntryTable(new ResourceLocation("lootslashconquer:chests/common_chest"), 60, 1, new LootCondition[0], "common");
		LootEntry uncommon = new LootEntryTable(new ResourceLocation("lootslashconquer:chests/uncommon_chest"), 25, 1, new LootCondition[0], "uncommon");
		LootEntry rare = new LootEntryTable(new ResourceLocation("lootslashconquer:chests/rare_chest"), 10, 1, new LootCondition[0], "rare");
		LootEntry legendary = new LootEntryTable(new ResourceLocation("lootslashconquer:chests/legendary_chest"), 5, 1, new LootCondition[0], "legendary");
		LootEntry exotic = new LootEntryTable(new ResourceLocation("lootslashconquer:chests/exotic_chest"), 2, 1, new LootCondition[0], "exotic");

		LootPool pool = new LootPool(new LootEntry[] { common, uncommon, rare, legendary, exotic }, new LootCondition[0], new RandomValueRange(0, 1), new RandomValueRange(0), "loot");

		table.addPool(pool);
	}
}

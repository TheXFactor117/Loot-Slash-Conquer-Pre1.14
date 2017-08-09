package com.thexfactor117.losteclipse.events;

import com.thexfactor117.losteclipse.init.ModLootTables;
import com.thexfactor117.losteclipse.loot.table.CustomLootTable;

import net.minecraft.world.storage.loot.LootPool;
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
	}
}

package com.thexfactor117.losteclipse.events.misc;

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
		if (event.getName() == ModLootTables.loot)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
		else if (event.getName() == ModLootTables.random_loot)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
	}
}

package com.thexfactor117.minehackslash.events.misc;

import com.thexfactor117.minehackslash.init.ModLootTables;
import com.thexfactor117.minehackslash.loot.CustomLootTable;

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
	public void test(LootTableLoadEvent event)
	{
		if (event.getName() == ModLootTables.LOOT)
		{
			event.setTable(new CustomLootTable(new LootPool[] { event.getTable().getPool("main") }));
		}
	}
}

package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.items.jewelry.ItemLEBauble;

import baubles.api.BaubleType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModItems
{
	// jewelry
	public static final Item GOLDEN_RING = new ItemLEBauble("golden_ring", BaubleType.RING);
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		// jewelry
		event.getRegistry().register(GOLDEN_RING);
	}
}

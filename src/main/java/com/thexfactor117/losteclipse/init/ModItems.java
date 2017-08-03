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
	public static final Item DIAMOND_RING = new ItemLEBauble("diamond_ring", BaubleType.RING);
	public static final Item GOLDEN_AMULET = new ItemLEBauble("golden_amulet", BaubleType.AMULET);
	public static final Item DIAMOND_AMULET = new ItemLEBauble("diamond_amulet", BaubleType.AMULET);
	public static final Item LEATHER_SASH = new ItemLEBauble("leather_sash", BaubleType.BELT);
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		// jewelry
		event.getRegistry().register(GOLDEN_RING);
		event.getRegistry().register(DIAMOND_RING);
		event.getRegistry().register(GOLDEN_AMULET);
		event.getRegistry().register(DIAMOND_AMULET);
		event.getRegistry().register(LEATHER_SASH);
	}
}

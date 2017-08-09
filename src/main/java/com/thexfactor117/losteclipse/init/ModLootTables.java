package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTables 
{
	public static ResourceLocation common_chest;
	public static ResourceLocation uncommon_chest;
	public static ResourceLocation rare_chest;
	public static ResourceLocation legendary_chest;
	public static ResourceLocation exotic_chest;
	
	public static void register()
	{
		common_chest = register("chests/common_chest");
		uncommon_chest = register("chests/uncommon_chest");
		rare_chest = register("chests/rare_chest");
		legendary_chest = register("chests/legendary_chest");
		exotic_chest = register("chests/exotic_chest");
	}
	
	private static ResourceLocation register(String name)
	{
		return LootTableList.register(new ResourceLocation(Reference.MODID, name));
	}
}

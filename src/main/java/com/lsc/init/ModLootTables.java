package com.lsc.init;

import com.lsc.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTables 
{
	public static ResourceLocation common_chest;
	public static ResourceLocation uncommon_chest;
	public static ResourceLocation rare_chest;
	public static ResourceLocation epic_chest;
	public static ResourceLocation legendary_chest;
	
	public static void register()
	{
		common_chest = register("chests/common_chest");
		uncommon_chest = register("chests/uncommon_chest");
		rare_chest = register("chests/rare_chest");
		epic_chest = register("chests/epic_chest");
		legendary_chest = register("chests/legendary_chest");
	}
	
	private static ResourceLocation register(String name)
	{
		return LootTableList.register(new ResourceLocation(Reference.MODID, name));
	}
}

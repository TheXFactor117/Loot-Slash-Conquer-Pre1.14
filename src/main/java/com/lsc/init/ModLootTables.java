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
	public static ResourceLocation common_jar;
	public static ResourceLocation uncommon_jar;
	public static ResourceLocation rare_jar;
	public static ResourceLocation epic_jar;
	public static ResourceLocation legendary_jar;
	
	public static void register()
	{
		common_chest = register("chests/common_chest");
		uncommon_chest = register("chests/uncommon_chest");
		rare_chest = register("chests/rare_chest");
		epic_chest = register("chests/epic_chest");
		legendary_chest = register("chests/legendary_chest");
		common_jar = register("jars/common_jar");
		uncommon_jar = register("jars/uncommon_jar");
		rare_jar = register("jars/rare_jar");
		epic_jar = register("jars/epic_jar");
		legendary_jar = register("jars/legendary_jar");
	}
	
	private static ResourceLocation register(String name)
	{
		return LootTableList.register(new ResourceLocation(Reference.MODID, name));
	}
}

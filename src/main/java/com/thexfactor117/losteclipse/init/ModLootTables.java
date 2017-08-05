package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTables 
{
	public static ResourceLocation loot;
	public static ResourceLocation random_loot;
	
	//public static ResourceLocation common_loot_room;
	public static ResourceLocation common_loot_room;
	
	public static void register()
	{
		loot = register("loot");
		random_loot = register("loot/random_loot");
		common_loot_room = register("structures/common_loot_room");
	}
	
	private static ResourceLocation register(String name)
	{
		return LootTableList.register(new ResourceLocation(Reference.MODID, name));
	}
}

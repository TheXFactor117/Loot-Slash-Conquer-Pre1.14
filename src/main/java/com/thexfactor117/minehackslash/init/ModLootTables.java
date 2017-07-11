package com.thexfactor117.minehackslash.init;

import com.thexfactor117.minehackslash.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTables 
{
	public static ResourceLocation LOOT;
	
	public static void register()
	{
		LOOT = register("loot");
	}
	
	private static ResourceLocation register(String name)
	{
		return LootTableList.register(new ResourceLocation(Reference.MODID, name));
	}
}

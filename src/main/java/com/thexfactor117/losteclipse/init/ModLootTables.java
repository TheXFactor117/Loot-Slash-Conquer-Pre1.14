package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTables 
{
	public static final ResourceLocation LOOT = register("loot");
	public static final ResourceLocation RANDOM_LOOT = register("loot/random_loot");
	
	private static ResourceLocation register(String name)
	{
		return LootTableList.register(new ResourceLocation(Reference.MODID, name));
	}
}

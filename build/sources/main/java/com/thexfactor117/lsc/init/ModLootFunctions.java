package com.thexfactor117.lsc.init;

import com.thexfactor117.lsc.loot.functions.CreateSpecial;
import com.thexfactor117.lsc.loot.functions.SetRarity;
import com.thexfactor117.lsc.loot.functions.TagLoot;

import net.minecraft.world.storage.loot.functions.LootFunctionManager;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModLootFunctions 
{
	public static void registerFunctions()
	{
		LootFunctionManager.registerFunction(new TagLoot.Serializer());
		LootFunctionManager.registerFunction(new CreateSpecial.Serializer());
		LootFunctionManager.registerFunction(new SetRarity.Serializer());
	}
}

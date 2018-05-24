package com.lsc.init;

import com.lsc.loot.functions.CreateSpecial;
import com.lsc.loot.functions.SetRarity;
import com.lsc.loot.functions.TagLoot;

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

package com.lsc.init;

import com.lsc.loot.functions.CreateCommon;
import com.lsc.loot.functions.CreateExotic;
import com.lsc.loot.functions.CreateLegendary;
import com.lsc.loot.functions.CreateRare;
import com.lsc.loot.functions.CreateSpecial;
import com.lsc.loot.functions.CreateStats;
import com.lsc.loot.functions.CreateUncommon;

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
		LootFunctionManager.registerFunction(new CreateStats.Serializer());
		LootFunctionManager.registerFunction(new CreateSpecial.Serializer());
		
		LootFunctionManager.registerFunction(new CreateCommon.Serializer());
		LootFunctionManager.registerFunction(new CreateUncommon.Serializer());
		LootFunctionManager.registerFunction(new CreateRare.Serializer());
		LootFunctionManager.registerFunction(new CreateLegendary.Serializer());
		LootFunctionManager.registerFunction(new CreateExotic.Serializer());
	}
}

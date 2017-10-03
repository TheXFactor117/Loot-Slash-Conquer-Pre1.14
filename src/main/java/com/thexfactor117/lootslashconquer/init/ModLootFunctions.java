package com.thexfactor117.lootslashconquer.init;

import com.thexfactor117.lootslashconquer.loot.functions.CreateCommon;
import com.thexfactor117.lootslashconquer.loot.functions.CreateExotic;
import com.thexfactor117.lootslashconquer.loot.functions.CreateLegendary;
import com.thexfactor117.lootslashconquer.loot.functions.CreateRare;
import com.thexfactor117.lootslashconquer.loot.functions.CreateSpecial;
import com.thexfactor117.lootslashconquer.loot.functions.CreateStats;
import com.thexfactor117.lootslashconquer.loot.functions.CreateUncommon;

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

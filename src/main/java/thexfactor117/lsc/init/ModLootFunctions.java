package thexfactor117.lsc.init;

import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import thexfactor117.lsc.loot.functions.CreateSpecial;
import thexfactor117.lsc.loot.functions.SetRarity;
import thexfactor117.lsc.loot.functions.TagLoot;

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

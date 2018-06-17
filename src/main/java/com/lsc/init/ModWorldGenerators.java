package com.lsc.init;

import com.lsc.world.generation.StructureCorruptedTower;
import com.lsc.world.generation.tower.StructureTower;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 *
 * @author TheXFactor117
 *
 */
public class ModWorldGenerators
{
	public static void registerWorldGenerators()
	{
		GameRegistry.registerWorldGenerator(new StructureTower(), 100);
		GameRegistry.registerWorldGenerator(new StructureCorruptedTower(), 100);
	}
}

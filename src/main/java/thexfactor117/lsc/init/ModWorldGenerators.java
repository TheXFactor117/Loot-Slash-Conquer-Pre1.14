package thexfactor117.lsc.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import thexfactor117.lsc.world.generation.boss.StructureCorruptedTower;
import thexfactor117.lsc.world.generation.tower.StructureTower;

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

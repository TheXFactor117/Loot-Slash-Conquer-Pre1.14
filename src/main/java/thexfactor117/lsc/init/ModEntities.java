package thexfactor117.lsc.init;

import java.lang.reflect.Field;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import thexfactor117.lsc.LootSlashConquer;
import thexfactor117.lsc.config.Configs;
import thexfactor117.lsc.entities.bosses.EntityCorruptedKnight;
import thexfactor117.lsc.entities.monsters.EntityBandit;
import thexfactor117.lsc.entities.monsters.EntityBanshee;
import thexfactor117.lsc.entities.monsters.EntityBarbarian;
import thexfactor117.lsc.entities.monsters.EntityGhost;
import thexfactor117.lsc.entities.monsters.EntityGolem;
import thexfactor117.lsc.entities.monsters.EntityMummy;
import thexfactor117.lsc.entities.monsters.EntitySpectralKnight;
import thexfactor117.lsc.entities.projectiles.EntityFireball;
import thexfactor117.lsc.entities.projectiles.EntityIcebolt;
import thexfactor117.lsc.entities.projectiles.EntityLightning;
import thexfactor117.lsc.util.Reference;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModEntities 
{
	private static int id = 0;
	public static Field mobCountDiv;
	
	public static void registerEntities()
	{
		// projectiles
		registerModProjectile(EntityFireball.class, "fireball");
		registerModProjectile(EntityIcebolt.class, "icebolt");
		registerModProjectile(EntityLightning.class, "lightning");
		
		// monsters
		registerModEntity(EntityBarbarian.class, "barbarian");
		registerModEntity(EntityGhost.class, "ghost");
		registerModEntity(EntityMummy.class, "mummy");
		registerModEntity(EntityBandit.class, "bandit");
		registerModEntity(EntityBanshee.class, "banshee");
		registerModEntity(EntityGolem.class, "golem");
		registerModEntity(EntitySpectralKnight.class, "spectralknight");
		
		// bosses
		registerModEntity(EntityCorruptedKnight.class, "corruptedknight");
		
		registerNaturalSpawns();
	}
	
	private static void registerModProjectile(Class<? extends Entity> entityClass, String name)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entityClass, name, ++id, LootSlashConquer.instance, 64, 10, true);
	}
	
	private static void registerModEntity(Class<? extends Entity> entityClass, String name)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entityClass, name, ++id, LootSlashConquer.instance, 80, 3, false);
	}
	
	private static void registerNaturalSpawns()
	{
		// basically all of the main biomes. some have been unincluded.
		Biome[] main = new Biome[] { Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, 
				Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.ICE_MOUNTAINS, Biomes.ICE_PLAINS,
				Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MESA, Biomes.MESA_CLEAR_ROCK, Biomes.MESA_ROCK, Biomes.PLAINS, Biomes.REDWOOD_TAIGA,
				Biomes.REDWOOD_TAIGA_HILLS, Biomes.ROOFED_FOREST, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SWAMPLAND, Biomes.TAIGA, Biomes.TAIGA_HILLS };
		Biome[] neutral = new Biome[] { Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.PLAINS, Biomes.TAIGA, Biomes.TAIGA_HILLS,
				Biomes.REDWOOD_TAIGA, Biomes.REDWOOD_TAIGA_HILLS, Biomes.ROOFED_FOREST };
		@SuppressWarnings("unused")
		Biome[] cold = new Biome[] { Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.ICE_MOUNTAINS, Biomes.ICE_PLAINS };
		Biome[] warm = new Biome[] { Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.MESA, Biomes.MESA_CLEAR_ROCK, Biomes.MESA_ROCK };
		
		EntityRegistry.addSpawn(EntityBarbarian.class, Configs.mainMonsterCategory.barbarianSpawnWeight, 2, 3, EnumCreatureType.MONSTER, main);
		EntityRegistry.addSpawn(EntityGhost.class, Configs.mainMonsterCategory.ghostSpawnWeight, 1, 2, EnumCreatureType.MONSTER, main);
		EntityRegistry.addSpawn(EntityMummy.class, Configs.mainMonsterCategory.mummySpawnWeight, 1, 2, EnumCreatureType.MONSTER, warm);
		EntityRegistry.addSpawn(EntityBanshee.class, Configs.mainMonsterCategory.bansheeSpawnWeight, 1, 1, EnumCreatureType.MONSTER, neutral);
		EntityRegistry.addSpawn(EntityGolem.class, Configs.mainMonsterCategory.golemSpawnWeight, 1, 1, EnumCreatureType.MONSTER, main);
		
		/*LootSlashConquer.LOGGER.info("Attempting reflection...");
		
		try
		{
			mobCountDiv = ReflectionHelper.findField(WorldEntitySpawner.class, "MOB_COUNT_DIV", "field_180268_a");
			mobCountDiv.setAccessible(true);
			
			Field modifiersField;
			try
			{
				modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(mobCountDiv, mobCountDiv.getModifiers() & ~Modifier.FINAL);
			}
			catch (NoSuchFieldException | SecurityException e)
			{
				e.printStackTrace();
			}
			
			mobCountDiv.setInt(mobCountDiv, 430);
			LootSlashConquer.LOGGER.info("Trying to catch reflection successful.");
			LootSlashConquer.LOGGER.info("MOB_COUNT_DIV is now : " + mobCountDiv.getInt(mobCountDiv));
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}*/
	}
}

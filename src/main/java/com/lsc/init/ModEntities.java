package com.lsc.init;

import com.lsc.LootSlashConquer;
import com.lsc.entities.bosses.EntityCorruptedKnight;
import com.lsc.entities.monsters.EntityBandit;
import com.lsc.entities.monsters.EntityBanshee;
import com.lsc.entities.monsters.EntityBarbarian;
import com.lsc.entities.monsters.EntityGhost;
import com.lsc.entities.monsters.EntityGolem;
import com.lsc.entities.monsters.EntityMummy;
import com.lsc.entities.monsters.EntitySpectralKnight;
import com.lsc.entities.projectiles.EntityFireball;
import com.lsc.entities.projectiles.EntityIcebolt;
import com.lsc.entities.projectiles.EntityLightning;
import com.lsc.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModEntities 
{
	private static int id = 0;
	
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
		
		EntityRegistry.addSpawn(EntityBarbarian.class, 2, 1, 3, EnumCreatureType.CREATURE, main);
		EntityRegistry.addSpawn(EntityGhost.class, 8, 1, 2, EnumCreatureType.CREATURE, main);
		EntityRegistry.addSpawn(EntityMummy.class, 10, 1, 2, EnumCreatureType.CREATURE, warm);
		EntityRegistry.addSpawn(EntityBanshee.class, 5, 1, 1, EnumCreatureType.CREATURE, neutral);
		EntityRegistry.addSpawn(EntityGolem.class, 3, 2, 2, EnumCreatureType.CREATURE, main);
	}
}

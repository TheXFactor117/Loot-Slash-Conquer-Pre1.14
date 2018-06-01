package com.lsc.init;

import com.lsc.LootSlashConquer;
import com.lsc.entities.monsters.EntityBandit;
import com.lsc.entities.monsters.EntityBanshee;
import com.lsc.entities.monsters.EntityBarbarian;
import com.lsc.entities.monsters.EntityGhost;
import com.lsc.entities.monsters.EntityMummy;
import com.lsc.entities.projectiles.EntityFireball;
import com.lsc.entities.projectiles.EntityIcebolt;
import com.lsc.entities.projectiles.EntityLightning;
import com.lsc.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
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
		registerModProjectile(EntityFireball.class, "entityFireball");
		registerModProjectile(EntityIcebolt.class, "entityIcebolt");
		registerModProjectile(EntityLightning.class, "entityLightning");
		
		// monsters
		registerModEntity(EntityBarbarian.class, "entityBarbarian");
		registerModEntity(EntityGhost.class, "entityGhost");
		registerModEntity(EntityMummy.class, "entityMummy");
		registerModEntity(EntityBandit.class, "entityBandit");
		registerModEntity(EntityBanshee.class, "entityBanshee");
	}
	
	private static void registerModProjectile(Class<? extends Entity> entityClass, String name)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entityClass, name, ++id, LootSlashConquer.instance, 64, 10, true);
	}
	
	private static void registerModEntity(Class<? extends Entity> entityClass, String name)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entityClass, name, ++id, LootSlashConquer.instance, 80, 3, false);
	}
}

package com.thexfactor117.lootslashconquer.init;

import com.thexfactor117.lootslashconquer.LootSlashConquer;
import com.thexfactor117.lootslashconquer.entities.projectiles.EntityFireball;
import com.thexfactor117.lootslashconquer.entities.projectiles.EntityIcebolt;
import com.thexfactor117.lootslashconquer.entities.projectiles.EntityLightning;
import com.thexfactor117.lootslashconquer.util.Reference;

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
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void registerModProjectile(Class entityClass, String name)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entityClass, name, id, LootSlashConquer.instance, 64, 10, true);
	}
}

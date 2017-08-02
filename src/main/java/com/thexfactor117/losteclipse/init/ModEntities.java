package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.entities.projectiles.EntityFireball;
import com.thexfactor117.losteclipse.entities.projectiles.EntityIcebolt;
import com.thexfactor117.losteclipse.entities.projectiles.EntityLightning;
import com.thexfactor117.losteclipse.util.Reference;

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
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, name), entityClass, name, id, LostEclipse.instance, 64, 10, true);
	}
}

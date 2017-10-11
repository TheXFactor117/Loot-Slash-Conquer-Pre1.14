package com.lsc.worldgen.structure;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * @author TheXFactor117
 *
 * Every structure has a tier which determines how common it spawns
 * along with the type of loot it will drop. Higher tiered structures
 * are usually harder to find, but will also drop much better loot.
 * However, all tiers of structures can spawn any type of loot usually.
 *
 */
public class Structure 
{
	private ResourceLocation location;
	private int tier;
	
	public Structure(ResourceLocation location, int tier)
	{
		this.location = location;
		this.tier = tier;
	}
	
	/** Returns the template of the structure. */
	public Template getTemplate(World world)
	{
		WorldServer server = (WorldServer) world;
		TemplateManager manager = server.getStructureTemplateManager();
		
		return manager.getTemplate(world.getMinecraftServer(), location);
	}
	
	public ResourceLocation getResourceLocation()
	{
		return location;
	}
	
	public int getTier()
	{
		return tier;
	}
}

package com.lsc.worldgen;

import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * @author TheXFactor117
 *
 */
public class Structure 
{
	private String name;
	private int x;
	private int z;
	private TemplateManager manager;
	
	public Structure(String name, int x, int z, TemplateManager manager)
	{
		this.name = name;
		this.x = x;
		this.z = z;
		this.manager = manager;
	}
}

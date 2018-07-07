package com.lsc.client.render.entities;

import com.lsc.client.models.entities.ModelMummy;
import com.lsc.entities.monsters.EntityMummy;
import com.lsc.util.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author TheXFactor117
 *
 */
public class RenderMummy extends RenderLiving<EntityMummy>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/mummy.png"); 
	
	public RenderMummy(RenderManager manager) 
	{
		super(manager, new ModelMummy(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMummy entity) 
	{
		return TEXTURE;
	}
}
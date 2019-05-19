package com.thexfactor117.lsc.client.render.entities;

import com.thexfactor117.lsc.client.models.entities.ModelSpectralKnight;
import com.thexfactor117.lsc.entities.monsters.EntitySpectralKnight;
import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author TheXFactor117
 *
 */
public class RenderSpectralKnight extends RenderLiving<EntitySpectralKnight>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/spectral_knight.png"); 
	
	public RenderSpectralKnight(RenderManager manager) 
	{
		super(manager, new ModelSpectralKnight(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySpectralKnight entity) 
	{
		return TEXTURE;
	}
}

package com.lsc.client.render.entities;

import com.lsc.client.models.entities.ModelCorruptedKnight;
import com.lsc.entities.bosses.EntityCorruptedKnight;
import com.lsc.util.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author TheXFactor117
 *
 */
public class RenderCorruptedKnight extends RenderLiving<EntityCorruptedKnight>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/corrupted_knight.png");
	
	public RenderCorruptedKnight(RenderManager manager) 
	{
		super(manager, new ModelCorruptedKnight(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCorruptedKnight entity) 
	{
		return TEXTURE;
	}
}

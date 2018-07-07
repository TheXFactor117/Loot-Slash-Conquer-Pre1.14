package com.lsc.client.models.entities;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.Entity;

/**
 *
 * @author TheXFactor117
 *
 */
public class ModelSpectralKnight extends ModelCorruptedKnight
{
	public ModelSpectralKnight()
	{
		super();
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		float scaleFactor = 0.6f;
	    GL11.glPushMatrix();
	    GL11.glTranslatef(0.0F, 1.5F - 1.5F * scaleFactor, 0.0F);
	    GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    //GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
	    GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		this.TorsoUpper.render(f5);
		this.LegR.render(f5);
		this.Head.render(f5);
		this.LegL.render(f5);
		this.ArmL.render(f5);
		this.TorsoLower.render(f5);
		this.ArmR.render(f5);
		
		GL11.glPopMatrix();
	}
}

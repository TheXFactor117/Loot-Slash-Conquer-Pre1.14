package com.lsc.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModelMiniGolem extends ModelBase
{
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer body3;
	private ModelRenderer body2;
	private ModelRenderer body1;
	private ModelRenderer head;
	private ModelRenderer armTop1;
	private ModelRenderer armTop2;
	private ModelRenderer armMiddle1;
	private ModelRenderer armMiddle2;
	private ModelRenderer armBottom1;
	private ModelRenderer armBottom2;

	public ModelMiniGolem()
	{
		textureWidth = 128;
		textureHeight = 64;

		leg1 = new ModelRenderer(this, 0, 0);
		leg1.addBox(-2.5F, 0F, -2.5F, 5, 15, 5);
		leg1.setRotationPoint(-7F, 9F, 0F);
		leg1.setTextureSize(128, 64);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 0);
		leg2.addBox(-2.5F, 0F, -2.5F, 5, 15, 5);
		leg2.setRotationPoint(7F, 9F, 0F);
		leg2.setTextureSize(128, 64);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		body3 = new ModelRenderer(this, 36, 47);
		body3.addBox(-9F, -3F, -4F, 18, 7, 10);
		body3.setRotationPoint(0F, 5F, -1F);
		body3.setTextureSize(128, 64);
		body3.mirror = true;
		setRotation(body3, 0F, 0F, 0F);
		body2 = new ModelRenderer(this, 0, 48);
		body2.addBox(-4F, -2F, -5F, 10, 8, 8);
		body2.setRotationPoint(-1F, -4F, 1F);
		body2.setTextureSize(128, 64);
		body2.mirror = true;
		setRotation(body2, 0F, 0F, 0F);
		body1 = new ModelRenderer(this, 0, 22);
		body1.addBox(-9F, -3F, -4F, 22, 13, 12);
		body1.setRotationPoint(-2F, -16F, -2F);
		body1.setTextureSize(128, 64);
		body1.mirror = true;
		setRotation(body1, 0F, 0F, 0F);
		head = new ModelRenderer(this, 20, 7);
		head.addBox(-3.5F, -7F, -3.5F, 7, 8, 7);
		head.setRotationPoint(0F, -20F, 0F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, 0F, 45F, 0F);
		armTop1 = new ModelRenderer(this, 48, 9);
		armTop1.addBox(0F, 0F, -2F, 7, 7, 6);
		armTop1.setRotationPoint(10F, -18F, -1F);
		armTop1.setTextureSize(128, 64);
		armTop1.mirror = true;
		setRotation(armTop1, 0F, 0F, 45F);
		armTop2 = new ModelRenderer(this, 48, 9);
		armTop2.addBox(-7F, 0F, -3F, 7, 7, 6);
		armTop2.setRotationPoint(-10F, -18.4F, 0F);
		armTop2.setTextureSize(128, 64);
		armTop2.mirror = true;
		setRotation(armTop2, 0F, 0F, 45F);
		armMiddle1 = new ModelRenderer(this, 74, 0);
		armMiddle1.addBox(-2F, 0F, -1F, 4, 15, 4); // old model
		// armMiddle1.addBox(-14F, 22F, -1F, 4, 15, 4); <- new model
		armMiddle1.setRotationPoint(15F, -15F, -1F);
		armMiddle1.setTextureSize(128, 64);
		armMiddle1.mirror = true;
		setRotation(armMiddle1, 0F, 0F, 0F);
		armMiddle2 = new ModelRenderer(this, 74, 0);
		armMiddle2.addBox(-2F, 0F, -2F, 4, 15, 4); // old model
		// armMiddle2.addBox(10F, 22F, -2F, 4, 15, 4); <- new model
		armMiddle2.setRotationPoint(-15F, -15F, 0F);
		armMiddle2.setTextureSize(128, 64);
		armMiddle2.mirror = true;
		setRotation(armMiddle2, 0F, 0F, 0F);
		armBottom1 = new ModelRenderer(this, 68, 23);
		// armBottom1.addBox(-32F, 35F, -4F, 8, 16, 8);
		armBottom1.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8);
		armBottom1.setRotationPoint(15F, 0F, 0F);
		armBottom1.setTextureSize(128, 64);
		armBottom1.mirror = true;
		setRotation(armBottom1, 0F, 0F, 0F);
		armBottom2 = new ModelRenderer(this, 68, 23);
		// armBottom2.addBox(23F, 35F, -4F, 8, 16, 8);
		armBottom2.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8);
		armBottom2.setRotationPoint(-15F, 0F, 0F);
		armBottom2.setTextureSize(128, 64);
		armBottom2.mirror = true;
		setRotation(armBottom2, 0F, 0F, 0F);
		// armTop1.addChild(armMiddle1);
		// armMiddle1.addChild(armBottom1);
		// armTop2.addChild(armMiddle2);
		// armMiddle2.addChild(armBottom2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		float scaleFactor = 0.4F;
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.5F - 1.5F * scaleFactor, 0.0F);
		GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);

		leg1.render(f5);
		leg2.render(f5);
		body3.render(f5);
		body2.render(f5);
		body1.render(f5);
		head.render(f5);
		armTop1.render(f5);
		armTop2.render(f5);
		armMiddle1.render(f5);
		armMiddle2.render(f5);
		armBottom1.render(f5);
		armBottom2.render(f5);

		GL11.glPopMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.head.rotateAngleY = 0.8F;
		this.head.rotateAngleX = 0F;
		this.armTop1.rotateAngleZ = -0.35F;
		this.armTop2.rotateAngleZ = 0.35F;
		this.armTop1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1 * 0.5F;
		this.armTop2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		// this.armMiddle1.rotateAngleZ = -0.35F;
		// this.armMiddle2.rotateAngleZ = 0.35F;
		// this.armBottom1.rotateAngleZ = -0.35F;
		// this.armBottom2.rotateAngleZ = 0.35F;
		this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		this.leg1.rotateAngleY = 0.0F;
		this.leg2.rotateAngleY = 0.0F;
	}
}

package com.lsc.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModelMummy extends ModelBase
{
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightarm;
	private ModelRenderer leftarm;
	private ModelRenderer rightleg;
	private ModelRenderer leftleg;
	private ModelRenderer Body_ext;

	public ModelMummy()
	{
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(64, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		body = new ModelRenderer(this, 16, 16);
		body.addBox(-4F, 0F, -2F, 8, 12, 4);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(64, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 40, 16);
		rightarm.addBox(-3F, -2F, -2F, 4, 12, 4);
		rightarm.setRotationPoint(-5F, 2F, 0F);
		rightarm.setTextureSize(64, 64);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 40, 16);
		leftarm.addBox(-1F, -2F, -2F, 4, 12, 4);
		leftarm.setRotationPoint(5F, 2F, 0F);
		leftarm.setTextureSize(64, 64);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		rightleg = new ModelRenderer(this, 0, 16);
		rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		rightleg.setRotationPoint(-2F, 12F, 0F);
		rightleg.setTextureSize(64, 64);
		rightleg.mirror = true;
		setRotation(rightleg, 0F, 0F, 0F);
		leftleg = new ModelRenderer(this, 0, 16);
		leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		leftleg.setRotationPoint(2F, 12F, 0F);
		leftleg.setTextureSize(64, 64);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);
		Body_ext = new ModelRenderer(this, 0, 32);
		Body_ext.addBox(-4F, 0F, 0F, 8, 12, 4);
		Body_ext.setRotationPoint(0F, 0F, -1F);
		Body_ext.setTextureSize(64, 64);
		Body_ext.mirror = true;
		setRotation(Body_ext, 0.095F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);
		Body_ext.render(f5);
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

		this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		float f6 = MathHelper.sin(this.swingProgress * (float) Math.PI);
		float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
		this.rightarm.rotateAngleZ = 0.0F;
		this.leftarm.rotateAngleZ = 0.0F;
		this.rightarm.rotateAngleY = -(0.1F - f6 * 0.6F);
		this.leftarm.rotateAngleY = 0.1F - f6 * 0.6F;
		this.rightarm.rotateAngleX = -((float) Math.PI / 2F);
		this.leftarm.rotateAngleX = -((float) Math.PI / 2F);
		this.rightarm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.leftarm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
		this.rightarm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		this.leftarm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
		this.rightarm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
		this.leftarm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
		this.rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		this.leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		this.rightleg.rotateAngleY = 0.0F;
		this.leftleg.rotateAngleY = 0.0F;
	}
}

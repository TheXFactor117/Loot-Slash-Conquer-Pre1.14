package com.lsc.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModelBarbarian extends ModelBiped
{
	/*private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightarm;
	private ModelRenderer leftarm;
	private ModelRenderer rightleg;
	private ModelRenderer leftleg;*/
	private ModelRenderer body2;
	private ModelRenderer helm;
	private ModelRenderer beard;
	private ModelRenderer Helm_Horns;

	public ModelBarbarian()
	{
		textureWidth = 128;
		textureHeight = 32;

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		this.bipedHead.setTextureSize(128, 32);
		setRotation(this.bipedHead, 0F, 0F, 0F);
		this.bipedBody = new ModelRenderer(this, 33, 12);
		this.bipedBody.addBox(-5F, 0F, -2F, 10, 6, 4);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
		this.bipedBody.setTextureSize(128, 32);
		this.bipedBody.mirror = true;
		setRotation(this.bipedBody, 0F, 0F, 0F);
		this.bipedRightArm = new ModelRenderer(this, 17, 16);
		this.bipedRightArm.addBox(-3F, -2F, -2F, 4, 12, 4);
		this.bipedRightArm.setRotationPoint(-6F, 2F, 0F);
		this.bipedRightArm.setTextureSize(128, 32);
		this.bipedRightArm.mirror = true;
		setRotation(this.bipedRightArm, 0F, 0F, 0F);
		this.bipedLeftArm = new ModelRenderer(this, 17, 16);
		this.bipedLeftArm.addBox(-1F, -2F, -2F, 4, 12, 4);
		this.bipedLeftArm.setRotationPoint(6F, 2F, 0F);
		this.bipedLeftArm.setTextureSize(128, 32);
		this.bipedLeftArm.mirror = true;
		setRotation(this.bipedLeftArm, 0F, 0F, 0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-2F, 0F, -2F, 4, 12, 4);
		this.bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
		this.bipedRightLeg.setTextureSize(128, 32);
		this.bipedRightLeg.mirror = true;
		setRotation(this.bipedRightLeg, 0F, 0F, 0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.addBox(-2F, 0F, -2F, 4, 12, 4);
		this.bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
		this.bipedLeftLeg.setTextureSize(128, 32);
		this.bipedLeftLeg.mirror = true;
		setRotation(this.bipedLeftLeg, 0F, 0F, 0F);
		
		/*head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(128, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		body = new ModelRenderer(this, 33, 12);
		body.addBox(-5F, 0F, -2F, 10, 6, 4);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(128, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 17, 16);
		rightarm.addBox(-3F, -2F, -2F, 4, 12, 4);
		rightarm.setRotationPoint(-6F, 2F, 0F);
		rightarm.setTextureSize(128, 32);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 17, 16);
		leftarm.addBox(-1F, -2F, -2F, 4, 12, 4);
		leftarm.setRotationPoint(6F, 2F, 0F);
		leftarm.setTextureSize(128, 32);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		rightleg = new ModelRenderer(this, 0, 16);
		rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		rightleg.setRotationPoint(-2F, 12F, 0F);
		rightleg.setTextureSize(128, 32);
		rightleg.mirror = true;
		setRotation(rightleg, 0F, 0F, 0F);
		leftleg = new ModelRenderer(this, 0, 16);
		leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		leftleg.setRotationPoint(2F, 12F, 0F);
		leftleg.setTextureSize(128, 32);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);*/
		body2 = new ModelRenderer(this, 33, 22);
		body2.addBox(-4F, 0F, -2F, 8, 6, 4);
		body2.setRotationPoint(0F, 6F, 0F);
		body2.setTextureSize(128, 32);
		body2.mirror = true;
		setRotation(body2, 0F, 0F, 0F);
		helm = new ModelRenderer(this, 92, 19);
		helm.addBox(-4.5F, -0.5F, -4.5F, 9, 4, 9);
		helm.setRotationPoint(0F, -8F, 0F);
		helm.setTextureSize(128, 32);
		helm.mirror = true;
		setRotation(helm, 0F, 0F, 0F);
		beard = new ModelRenderer(this, 61, 0);
		beard.addBox(-4F, -1F, -4F, 8, 8, 8);
		beard.setRotationPoint(0F, 0F, 0F);
		beard.setTextureSize(128, 32);
		beard.mirror = true;
		setRotation(beard, 0F, 0F, 0F);
		Helm_Horns = new ModelRenderer(this, 93, 0);
		Helm_Horns.addBox(-5F, 0F, -2F, 10, 6, 6);
		Helm_Horns.setRotationPoint(0F, -12F, -1F);
		Helm_Horns.setTextureSize(128, 32);
		Helm_Horns.mirror = true;
		setRotation(Helm_Horns, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		/*head.render(f5);
		body.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		rightleg.render(f5);
		leftleg.render(f5);*/
		body2.render(f5);
		helm.render(f5);
		beard.render(f5);
		Helm_Horns.render(f5);
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

		/*this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.helm.rotateAngleY = this.head.rotateAngleY;
		this.helm.rotateAngleX = this.head.rotateAngleX;
		this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1 * 0.5F;
		this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		this.rightarm.rotateAngleZ = 0.0F;
		this.leftarm.rotateAngleZ = 0.0F;
		this.rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		this.leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		this.rightleg.rotateAngleY = 0.0F;
		this.leftleg.rotateAngleY = 0.0F;*/
	}
}

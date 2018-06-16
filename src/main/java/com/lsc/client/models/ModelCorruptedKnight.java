package com.lsc.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * 
 * @author TheXFactor117
 * @artist Ithronyar
 *
 */
public class ModelCorruptedKnight extends ModelBase
{
	public ModelRenderer LegR;
	public ModelRenderer LegL;
	public ModelRenderer ArmL;
	public ModelRenderer ArmR;
	public ModelRenderer Head;
	public ModelRenderer LegClothR;
	public ModelRenderer LegClothL;
	public ModelRenderer ShoulderL;
	public ModelRenderer ShoulderL_1;
	public ModelRenderer HelmBase;
	public ModelRenderer HelmHornL1;
	public ModelRenderer HelmHornL2;
	public ModelRenderer HelmHornL3;
	public ModelRenderer HelmHornR1;
	public ModelRenderer HelmHornR2;
	public ModelRenderer HelmHornR3;
	public ModelRenderer TorsoLower;
	public ModelRenderer TorsoUpper;

	public ModelCorruptedKnight()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.HelmHornL1 = new ModelRenderer(this, 76, 0);
		this.HelmHornL1.setRotationPoint(5.0F, -7.0F, 0.0F);
		this.HelmHornL1.addBox(-1.5F, -7.0F, -1.5F, 3, 7, 3, 0.0F);
		this.setRotateAngle(HelmHornL1, 0.0F, 0.0F, 1.0471975511965976F);
		this.HelmHornL2 = new ModelRenderer(this, 88, 0);
		this.HelmHornL2.setRotationPoint(10.0F, -10.0F, 0.0F);
		this.HelmHornL2.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(HelmHornL2, 0.0F, 0.0F, 0.2617993877991494F);
		this.TorsoUpper = new ModelRenderer(this, 0, 20);
		this.TorsoUpper.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.TorsoUpper.addBox(-7.0F, -12.0F, -5.0F, 14, 12, 10, 0.0F);
		this.LegR = new ModelRenderer(this, 0, 42);
		this.LegR.mirror = true;
		this.LegR.setRotationPoint(-3.0F, 6.0F, 0.0F);
		this.LegR.addBox(-2.5F, 0.0F, -3.0F, 5, 18, 6, 0.0F);
		this.ShoulderL_1 = new ModelRenderer(this, 88, 7);
		this.ShoulderL_1.mirror = true;
		this.ShoulderL_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.ShoulderL_1.addBox(-7.0F, -3.0F, -4.0F, 7, 6, 8, 0.0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, -17.0F, 0.0F);
		this.Head.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, 0.0F);
		this.HelmHornR1 = new ModelRenderer(this, 76, 0);
		this.HelmHornR1.mirror = true;
		this.HelmHornR1.setRotationPoint(-5.0F, -7.0F, 0.0F);
		this.HelmHornR1.addBox(-1.5F, -7.0F, -1.5F, 3, 7, 3, 0.0F);
		this.setRotateAngle(HelmHornR1, 0.0F, 0.0F, -1.0471975511965976F);
		this.LegClothR = new ModelRenderer(this, 22, 42);
		this.LegClothR.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegClothR.addBox(-3.0F, 0.0F, -4.0F, 6, 8, 8, 0.0F);
		this.ShoulderL = new ModelRenderer(this, 88, 7);
		this.ShoulderL.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.ShoulderL.addBox(0.0F, -3.0F, -4.0F, 7, 6, 8, 0.0F);
		this.HelmHornL3 = new ModelRenderer(this, 96, 0);
		this.HelmHornL3.setRotationPoint(11.0F, -14.0F, 0.0F);
		this.HelmHornL3.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
		this.LegL = new ModelRenderer(this, 0, 42);
		this.LegL.setRotationPoint(3.0F, 6.0F, 0.0F);
		this.LegL.addBox(-2.5F, 0.0F, -3.0F, 5, 18, 6, 0.0F);
		this.HelmBase = new ModelRenderer(this, 40, 0);
		this.HelmBase.setRotationPoint(0.0F, 1.0F, 0.0F);
		this.HelmBase.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, 0.0F);
		this.HelmHornR3 = new ModelRenderer(this, 96, 0);
		this.HelmHornR3.mirror = true;
		this.HelmHornR3.setRotationPoint(-11.0F, -14.0F, 0.0F);
		this.HelmHornR3.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
		this.ArmL = new ModelRenderer(this, 88, 21);
		this.ArmL.setRotationPoint(7.0F, -14.0F, 0.0F);
		this.ArmL.addBox(0.0F, -2.0F, -3.0F, 6, 20, 6, 0.0F);
		this.HelmHornR2 = new ModelRenderer(this, 88, 0);
		this.HelmHornR2.mirror = true;
		this.HelmHornR2.setRotationPoint(-10.0F, -10.0F, 0.0F);
		this.HelmHornR2.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(HelmHornR2, 0.0F, 0.0F, -0.2617993877991494F);
		this.TorsoLower = new ModelRenderer(this, 48, 24);
		this.TorsoLower.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.TorsoLower.addBox(-6.0F, 0.0F, -4.0F, 12, 10, 8, 0.0F);
		this.ArmR = new ModelRenderer(this, 88, 21);
		this.ArmR.mirror = true;
		this.ArmR.setRotationPoint(-7.0F, -14.0F, 0.0F);
		this.ArmR.addBox(-6.0F, -2.0F, -3.0F, 6, 20, 6, 0.0F);
		this.LegClothL = new ModelRenderer(this, 50, 42);
		this.LegClothL.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LegClothL.addBox(-3.0F, 0.0F, -4.0F, 6, 8, 8, 0.0F);
		this.HelmBase.addChild(this.HelmHornL1);
		this.HelmBase.addChild(this.HelmHornL2);
		this.ArmR.addChild(this.ShoulderL_1);
		this.HelmBase.addChild(this.HelmHornR1);
		this.LegR.addChild(this.LegClothR);
		this.ArmL.addChild(this.ShoulderL);
		this.HelmBase.addChild(this.HelmHornL3);
		this.Head.addChild(this.HelmBase);
		this.HelmBase.addChild(this.HelmHornR3);
		this.HelmBase.addChild(this.HelmHornR2);
		this.LegL.addChild(this.LegClothL);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.TorsoUpper.render(f5);
		this.LegR.render(f5);
		this.Head.render(f5);
		this.LegL.render(f5);
		this.ArmL.render(f5);
		this.TorsoLower.render(f5);
		this.ArmR.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
		this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
		this.HelmBase.rotateAngleY = this.Head.rotateAngleY;
		this.HelmBase.rotateAngleX = this.Head.rotateAngleX;
		this.ArmR.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1 * 0.5F;
		this.ArmL.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
		this.ArmR.rotateAngleZ = 0.0F;
		this.ArmL.rotateAngleZ = 0.0F;
		this.LegR.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
		this.LegL.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
		this.LegR.rotateAngleY = 0.0F;
		this.LegL.rotateAngleY = 0.0F;
	}

	/**
	 * Helper method from Tabula.
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}

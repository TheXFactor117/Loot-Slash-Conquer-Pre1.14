package com.lsc.client.models.bosses;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModelCorruptedKnight extends ModelBase
{
	private ModelRenderer torsoUpper;
	private ModelRenderer torsoLower;
	private ModelRenderer shoulderLeft;
	private ModelRenderer shoulderRight;
	private ModelRenderer armLeft;
	private ModelRenderer armRight;
	private ModelRenderer legLeft;
	private ModelRenderer legRight;
	private ModelRenderer legClothLeft;
	private ModelRenderer legClothRight;
	private ModelRenderer head;
	private ModelRenderer helmMain;
	private ModelRenderer helmHornLeft1;
	private ModelRenderer helmHornLeft2;
	private ModelRenderer helmHornLeft3;
	private ModelRenderer helmHornRight1;
	private ModelRenderer helmHornRight2;
	private ModelRenderer helmHornRight3;

	public ModelCorruptedKnight()
	{
		textureWidth = 128;
		textureHeight = 128;

		torsoUpper = new ModelRenderer(this, 0, 20);
		torsoUpper.addBox(-7F, 0F, -5F, 14, 12, 10);
		torsoUpper.setRotationPoint(0F, -16F, 0F);
		torsoUpper.setTextureSize(128, 128);
		torsoUpper.mirror = true;
		setRotation(torsoUpper, 0F, 0F, 0F);
		torsoLower = new ModelRenderer(this, 48, 24);
		torsoLower.addBox(-6F, 0F, -4F, 12, 10, 8);
		torsoLower.setRotationPoint(0F, -4F, 0F);
		torsoLower.setTextureSize(128, 128);
		torsoLower.mirror = true;
		setRotation(torsoLower, 0F, 0F, 0F);
		shoulderLeft = new ModelRenderer(this, 88, 7);
		shoulderLeft.addBox(0F, -3F, -4F, 7, 6, 8);
		shoulderLeft.setRotationPoint(7F, -14F, 0F);
		shoulderLeft.setTextureSize(128, 128);
		shoulderLeft.mirror = true;
		setRotation(shoulderLeft, 0F, 0F, 0F);
		shoulderRight = new ModelRenderer(this, 88, 7);
		shoulderRight.addBox(-7F, 0F, -4F, 7, 6, 8);
		shoulderRight.setRotationPoint(-7F, -17F, 0F);
		shoulderRight.setTextureSize(128, 128);
		shoulderRight.mirror = true;
		setRotation(shoulderRight, 0F, 0F, 0F);
		shoulderRight.mirror = false;
		armLeft = new ModelRenderer(this, 88, 21);
		armLeft.addBox(0F, -2F, -3F, 6, 20, 6);
		armLeft.setRotationPoint(7F, -14F, 0F);
		armLeft.setTextureSize(128, 128);
		armLeft.mirror = true;
		setRotation(armLeft, 0F, 0F, 0F);
		armRight = new ModelRenderer(this, 88, 21);
		armRight.addBox(-6F, 0F, -3F, 6, 20, 6);
		armRight.setRotationPoint(-7F, -16F, 0F);
		armRight.setTextureSize(128, 128);
		armRight.mirror = true;
		setRotation(armRight, 0F, 0F, 0F);
		armRight.mirror = false;
		legLeft = new ModelRenderer(this, 0, 42);
		legLeft.addBox(-2.5F, 0F, -3F, 5, 18, 6);
		legLeft.setRotationPoint(3F, 6F, 0F);
		legLeft.setTextureSize(128, 128);
		legLeft.mirror = true;
		setRotation(legLeft, 0F, 0F, 0F);
		legRight = new ModelRenderer(this, 0, 42);
		legRight.addBox(-2.5F, 0F, -3F, 5, 18, 6);
		legRight.setRotationPoint(-3F, 6F, 0F);
		legRight.setTextureSize(128, 128);
		legRight.mirror = true;
		setRotation(legRight, 0F, 0F, 0F);
		legRight.mirror = false;
		legClothLeft = new ModelRenderer(this, 50, 42);
		legClothLeft.addBox(-3F, 0F, -4F, 6, 8, 8);
		legClothLeft.setRotationPoint(3F, 6F, 0F);
		legClothLeft.setTextureSize(128, 128);
		legClothLeft.mirror = true;
		setRotation(legClothLeft, 0F, 0F, 0F);
		legClothRight = new ModelRenderer(this, 22, 42);
		legClothRight.addBox(-3F, 0F, -4F, 6, 8, 8);
		legClothRight.setRotationPoint(-3F, 6F, 0F);
		legClothRight.setTextureSize(128, 128);
		legClothRight.mirror = true;
		setRotation(legClothRight, 0F, 0F, 0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-5F, -10F, -5F, 10, 10, 10);
		head.setRotationPoint(0F, -17F, 0F);
		head.setTextureSize(128, 128);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		helmMain = new ModelRenderer(this, 40, 0);
		helmMain.addBox(-6F, -12F, -6F, 12, 12, 12);
		helmMain.setRotationPoint(0F, -16F, 0F);
		helmMain.setTextureSize(128, 128);
		helmMain.mirror = true;
		setRotation(helmMain, 0F, 0F, 0F);
		helmHornLeft1 = new ModelRenderer(this, 76, 0);
		helmHornLeft1.addBox(-1.5F, -7F, -1.5F, 3, 7, 3);
		helmHornLeft1.setRotationPoint(5F, -23F, 0F);
		helmHornLeft1.setTextureSize(128, 128);
		helmHornLeft1.mirror = true;
		setRotation(helmHornLeft1, 0F, 0F, 1.047198F);
		helmHornLeft2 = new ModelRenderer(this, 88, 0);
		helmHornLeft2.addBox(-1F, -5F, -1F, 2, 5, 2);
		helmHornLeft2.setRotationPoint(10F, -26F, 0F);
		helmHornLeft2.setTextureSize(128, 128);
		helmHornLeft2.mirror = true;
		setRotation(helmHornLeft2, 0F, 0F, 0.2617994F);
		helmHornLeft3 = new ModelRenderer(this, 96, 0);
		helmHornLeft3.addBox(-0.5F, -5F, -0.5F, 1, 5, 1);
		helmHornLeft3.setRotationPoint(11F, -30F, 0F);
		helmHornLeft3.setTextureSize(128, 128);
		helmHornLeft3.mirror = true;
		setRotation(helmHornLeft3, 0F, 0F, 0F);
		helmHornRight1 = new ModelRenderer(this, 76, 0);
		helmHornRight1.addBox(-1.5F, -7F, -1.5F, 3, 7, 3);
		helmHornRight1.setRotationPoint(-5F, -23F, 0F);
		helmHornRight1.setTextureSize(128, 128);
		helmHornRight1.mirror = true;
		setRotation(helmHornRight1, 0F, 0F, -1.047198F);
		helmHornRight1.mirror = false;
		helmHornRight2 = new ModelRenderer(this, 88, 0);
		helmHornRight2.addBox(-1F, -5F, -1F, 2, 5, 2);
		helmHornRight2.setRotationPoint(-10F, -26F, 0F);
		helmHornRight2.setTextureSize(128, 128);
		helmHornRight2.mirror = true;
		setRotation(helmHornRight2, 0F, 0F, -0.2617994F);
		helmHornRight2.mirror = false;
		helmHornRight3 = new ModelRenderer(this, 96, 0);
		helmHornRight3.addBox(-0.5F, -5F, -0.5F, 1, 5, 1);
		helmHornRight3.setRotationPoint(-11F, -30F, 0F);
		helmHornRight3.setTextureSize(128, 128);
		helmHornRight3.mirror = true;
		setRotation(helmHornRight3, 0F, 0F, 0F);
		helmHornRight3.mirror = false;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		torsoUpper.render(f5);
		torsoLower.render(f5);
		shoulderLeft.render(f5);
		shoulderRight.render(f5);
		armLeft.render(f5);
		armRight.render(f5);
		legLeft.render(f5);
		legRight.render(f5);
		legClothLeft.render(f5);
		legClothRight.render(f5);
		head.render(f5);
		helmMain.render(f5);
		helmHornLeft1.render(f5);
		helmHornLeft2.render(f5);
		helmHornLeft3.render(f5);
		helmHornRight1.render(f5);
		helmHornRight2.render(f5);
		helmHornRight3.render(f5);
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
	}
}

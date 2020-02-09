package com.thexfactor117.lsc.client.models.entities;

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
	//public ModelRenderer Head;
	public ModelRenderer hat;
	//public ModelRenderer Body1;
	//public ModelRenderer RightArm;
	//public ModelRenderer LeftArm;
	//public ModelRenderer RightLeg;
	//public ModelRenderer LeftLeg;
	public ModelRenderer rightArmArmor;
	public ModelRenderer leftArmArmor;
	public ModelRenderer hair;
	public ModelRenderer body2;
	public ModelRenderer loincloth;
	public ModelRenderer rightHand;
	public ModelRenderer leftHand;

	public ModelBarbarian()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.bipedRightLeg = new ModelRenderer(this, 64, 18);
		this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.5F);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.hair = new ModelRenderer(this, 0, 18);
		this.hair.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hair.addBox(-4.0F, 0.0F, -4.0F, 8, 6, 8, 0.0F);
		this.bipedBody = new ModelRenderer(this, 0, 32);
		this.bipedBody.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.bipedBody.addBox(-5.0F, 0.0F, -3.25F, 10, 7, 7, 0.0F);
		this.rightArmArmor = new ModelRenderer(this, 48, 18);
		this.rightArmArmor.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.rightArmArmor.addBox(-3.0F, -2.5F, -2.0F, 4, 12, 4, 0.5F);
		this.bipedLeftLeg = new ModelRenderer(this, 64, 18);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.5F);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.loincloth = new ModelRenderer(this, 28, 50);
		this.loincloth.setRotationPoint(0.0F, -0.1F, -0.1F);
		this.loincloth.addBox(-4.5F, 14.0F, -2.5F, 9, 8, 6, 0.0F);
		this.leftArmArmor = new ModelRenderer(this, 48, 18);
		this.leftArmArmor.mirror = true;
		this.leftArmArmor.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.leftArmArmor.addBox(-1.0F, -2.5F, -2.0F, 4, 12, 4, 0.5F);
		this.body2 = new ModelRenderer(this, 0, 46);
		this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body2.addBox(-4.0F, 7.0F, -2.5F, 8, 7, 6, 0.0F);
		this.leftHand = new ModelRenderer(this, 36, 34);
		this.leftHand.mirror = true;
		this.leftHand.setRotationPoint(1.0F, 9.0F, 0.0F);
		this.leftHand.addBox(-2.5F, 0.0F, -2.5F, 5, 4, 5, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 32, 18);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.setRotationPoint(6.0F, 0.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
		this.rightHand = new ModelRenderer(this, 36, 34);
		this.rightHand.setRotationPoint(-1.0F, 9.0F, 0.0F);
		this.rightHand.addBox(-2.5F, 0.0F, -2.5F, 5, 4, 5, 0.0F);
		this.hat = new ModelRenderer(this, 32, 0);
		this.hat.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.hat.addBox(-4.0F, -8.0F, -4.0F, 8, 10, 8, 0.5F);
		this.bipedRightArm = new ModelRenderer(this, 32, 18);
		this.bipedRightArm.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.bipedHead.addChild(this.hair);
		this.bipedHead.addChild(this.hat);
		this.body2.addChild(this.loincloth);
		this.bipedBody.addChild(this.body2);
		this.bipedLeftArm.addChild(this.leftHand);
		this.bipedRightArm.addChild(this.rightHand);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.rightArmArmor.render(f5);
		this.leftArmArmor.render(f5);
		//this.hat.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}

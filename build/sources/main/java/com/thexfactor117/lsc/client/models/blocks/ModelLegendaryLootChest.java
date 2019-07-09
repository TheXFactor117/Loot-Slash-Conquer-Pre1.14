package com.thexfactor117.lsc.client.models.blocks;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
public class ModelLegendaryLootChest extends ModelLootChest
{
	//public ModelRenderer base;
	//public ModelRenderer lidBase;
	public ModelRenderer legs;
	public ModelRenderer leftBaseSide;
	public ModelRenderer rightBaseSide;
	public ModelRenderer leftLidLip;
	public ModelRenderer rightLidLip;

	public ModelLegendaryLootChest()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.rightBaseSide = new ModelRenderer(this, 0, 21);
		this.rightBaseSide.mirror = true;
		this.rightBaseSide.setRotationPoint(7.0F, 0.0F, -7.0F);
		this.rightBaseSide.addBox(7.0F, 1.0F, 7.0F, 1, 9, 14, 0.0F);
		this.base = new ModelRenderer(this, 0, 0);
		//this.base.setRotationPoint(0.0F, 21.0F, 0.0F);
		//this.base.addBox(-7.0F, -8.0F, -6.0F, 14, 9, 12, 0.0F);
		this.base.setRotationPoint(1F, 6F, 1F);
		this.base.addBox(0F, 1F, 1F, 14, 9, 12, 0.0F);
		this.leftBaseSide = new ModelRenderer(this, 0, 21);
		this.leftBaseSide.setRotationPoint(-8.0F, 0.0F, 0.0F);
		this.leftBaseSide.addBox(7.0F, 1.0F, 0.0F, 1, 9, 14, 0.0F);
		this.legs = new ModelRenderer(this, 0, 50);
		this.legs.setRotationPoint(0.0F, 22.0F, 0.0F);
		this.legs.addBox(-8.0F, 0.0F, -6.0F, 16, 2, 12, 0.0F);
		this.rightLidLip = new ModelRenderer(this, 52, 18);
		this.rightLidLip.mirror = true;
		this.rightLidLip.setRotationPoint(7.0F, -6.0F, -1.0F);
		this.rightLidLip.addBox(7.0F, -1.0F, -13.0F, 1, 7, 14, 0.0F);
		this.lid = new ModelRenderer(this, 52, 0);
		this.lid.setRotationPoint(1F, 7F, 15F);
		this.lid.addBox(0F, -6F, -13F, 14, 6, 12, 0.0F);
		this.leftLidLip = new ModelRenderer(this, 52, 18);
		this.leftLidLip.setRotationPoint(-8.0F, -6.0F, 0.0F);
		this.leftLidLip.addBox(7.0F, -1.0F, -14.0F, 1, 7, 14, 0.0F);
		this.base.addChild(this.rightBaseSide);
		this.base.addChild(this.leftBaseSide);
		this.lid.addChild(this.rightLidLip);
		this.lid.addChild(this.leftLidLip);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.base.render(f5);
		this.legs.render(f5);
		this.lid.render(f5);
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

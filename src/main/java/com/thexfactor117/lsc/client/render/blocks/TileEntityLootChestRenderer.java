package com.thexfactor117.lsc.client.render.blocks;

import com.thexfactor117.lsc.client.models.blocks.ModelLegendaryLootChest;
import com.thexfactor117.lsc.client.models.blocks.ModelLootChest;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.tileentity.TileEntityLootChest;
import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
public class TileEntityLootChestRenderer extends TileEntitySpecialRenderer<TileEntityLootChest>
{
	private static final ResourceLocation COMMON_TEXTURE = new ResourceLocation(Reference.MODID, "textures/models/tileentities/common_chest.png");
	private static final ResourceLocation UNCOMMON_TEXTURE = new ResourceLocation(Reference.MODID, "textures/models/tileentities/uncommon_chest.png");
	private static final ResourceLocation RARE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/models/tileentities/rare_chest.png");
	private static final ResourceLocation EPIC_TEXTURE = new ResourceLocation(Reference.MODID, "textures/models/tileentities/epic_chest.png");
	private static final ResourceLocation LEGENDARY_TEXTURE = new ResourceLocation(Reference.MODID, "textures/models/tileentities/legendary_chest.png");
	private final ModelLootChest lootChest = new ModelLootChest();
	private final ModelLegendaryLootChest legendaryLootChest = new ModelLegendaryLootChest();

	public TileEntityLootChestRenderer()
	{

	}

	// modified version of TileEntityChestRenderer
	public void render(TileEntityLootChest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i = 0;

		ModelLootChest modelchest;
		EnumFacing facing = (EnumFacing) te.getWorld().getBlockState(te.getPos()).getProperties().get(BlockHorizontal.FACING);

		if (te.rarity == Rarity.LEGENDARY)
		{
			modelchest = this.legendaryLootChest;
		}
		else
		{
			modelchest = this.lootChest;
		}

		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		else
		{
			switch (te.rarity)
			{
				case COMMON:
					this.bindTexture(COMMON_TEXTURE);
					break;
				case UNCOMMON:
					this.bindTexture(UNCOMMON_TEXTURE);
					break;
				case RARE:
					this.bindTexture(RARE_TEXTURE);
					break;
				case EPIC:
					this.bindTexture(EPIC_TEXTURE);
					break;
				case LEGENDARY:
					this.bindTexture(LEGENDARY_TEXTURE);
					break;
				default:
					break;
			}
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		if (destroyStage < 0)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		}

		GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);

		switch (facing)
		{
			case NORTH:
			{
				GlStateManager.rotate(180F, 0F, 1F, 0F);
				break;
			}
			case SOUTH:
			{
				GlStateManager.rotate(0F, 0F, 1F, 0F);
				break;
			}
			case WEST:
			{
				GlStateManager.rotate(90F, 0F, 1F, 0F);
				break;
			}
			case EAST:
			{
				GlStateManager.rotate(270F, 0F, 1F, 0F);
				break;
			}
			default:
			{
				GlStateManager.rotate(0F, 0F, 1F, 0F);
				break;
			}
		}

		int j = 0;

		if (i == 2)
		{
			j = 180;
		}

		if (i == 3)
		{
			j = 0;
		}

		if (i == 4)
		{
			j = 90;
		}

		if (i == 5)
		{
			j = -90;
		}

		GlStateManager.rotate((float) j, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;

		f = 1.0F - f;
		f = 1.0F - f * f * f;
		modelchest.lid.rotateAngleX = -(f * ((float) Math.PI / 2F));
		modelchest.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (destroyStage >= 0)
		{
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}

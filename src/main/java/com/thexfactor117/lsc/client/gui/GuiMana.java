package com.thexfactor117.lsc.client.gui;

import org.lwjgl.opengl.GL11;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.util.PlayerUtil;
import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMana extends Gui
{
	private Minecraft mc = Minecraft.getMinecraft();
	private static ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/gui/mana.png");
	
	public GuiMana()
	{
		super();
	}
	
	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Post event)
	{
		if (Configs.renderingCategory.renderCustomManabar)
		{
			if (event.getType() != ElementType.EXPERIENCE) return;
			else
			{
				ScaledResolution sr = event.getResolution();
				EntityPlayer player = mc.player;
				
				if (!player.capabilities.isCreativeMode)
				{
					LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
					
					if (cap != null)
					{
						if (cap.getMaxMana() != 0)
						{
							double manaBarWidth = (double) cap.getMana() / cap.getMaxMana() * 81.0;
							int xPos = sr.getScaledWidth() / 2 + 10;
							int yPos = sr.getScaledHeight() - 39;
							
							mc.renderEngine.bindTexture(location);

							//if (capMana.getMana() != capMana.getMaxMana())
							//{
								this.drawTexturedModalRect(xPos, yPos, 0, 0, 81, 9);
								this.drawTexturedModalRect(xPos, yPos, 0, 27, (int) manaBarWidth, 8);
							//}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderOverlayText(RenderGameOverlayEvent.Text event)
	{
		if (Configs.renderingCategory.renderCustomManabar)
		{
			ScaledResolution sr = event.getResolution();
			EntityPlayer player = Minecraft.getMinecraft().player;
			LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
			
			if (!player.capabilities.isCreativeMode && cap != null)
			{
				String mana = cap.getMana() + " / " + cap.getMaxMana();
				
				GL11.glPushMatrix();
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(mana, (sr.getScaledWidth() / 2 + 36) * 2, (sr.getScaledHeight() - 36) * 2, 0xFFFFFF);
				GL11.glPopMatrix();
			}
		}
	}
}

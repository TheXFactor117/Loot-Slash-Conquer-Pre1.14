package com.thexfactor117.lsc.client.gui;

import org.lwjgl.opengl.GL11;

import com.thexfactor117.lsc.capabilities.cap.CapabilityPlayerStats;
import com.thexfactor117.lsc.capabilities.implementation.PlayerStats;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.util.Reference;

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
					PlayerStats statsCap = (PlayerStats) player.getCapability(CapabilityPlayerStats.PLAYER_STATS, null);
					
					if (statsCap != null)
					{
						if (statsCap.getMaxMana() != 0)
						{
							double manaBarWidth = (double) statsCap.getMana() / statsCap.getMaxMana() * 81.0;
							int xPos = sr.getScaledWidth() / 2 + 10;
							int yPos = sr.getScaledHeight() - 38;
							
							mc.renderEngine.bindTexture(location);

							//if (capMana.getMana() != capMana.getMaxMana())
							//{
								this.drawTexturedModalRect(xPos, yPos, 0, 18, 81, 6);
								this.drawTexturedModalRect(xPos, yPos, 0, 24, (int) manaBarWidth, 5);
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
			PlayerStats statsCap = (PlayerStats) player.getCapability(CapabilityPlayerStats.PLAYER_STATS, null);
			
			if (!player.capabilities.isCreativeMode && statsCap != null)
			{
				String mana = statsCap.getMana() + " / " + statsCap.getMaxMana();
				
				GL11.glPushMatrix();
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(mana, (sr.getScaledWidth() / 2 + 37) * 2, (sr.getScaledHeight() - 37) * 2, 0xFFFFFF);
				GL11.glPopMatrix();
			}
		}
	}
}

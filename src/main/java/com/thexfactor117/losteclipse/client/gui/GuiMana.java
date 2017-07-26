package com.thexfactor117.losteclipse.client.gui;

import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerStats;
import com.thexfactor117.losteclipse.capabilities.api.IStats;
import com.thexfactor117.losteclipse.util.Reference;

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
		if (event.getType() != ElementType.EXPERIENCE) return;
		else
		{
			ScaledResolution sr = event.getResolution();
			EntityPlayer player = mc.player;
			
			if (!player.capabilities.isCreativeMode)
			{
				IStats statsCap = (IStats) player.getCapability(CapabilityPlayerStats.STATS, null);
				
				if (statsCap != null)
				{
					if (statsCap.getMaxMana() != 0)
					{
						double manaBarWidth = (double) statsCap.getMana() / statsCap.getMaxMana() * 96.0;
						int xPos = sr.getScaledWidth() / 2 + 110;
						int yPos = sr.getScaledHeight() - 20;
						
						mc.renderEngine.bindTexture(location);

						//if (capMana.getMana() != capMana.getMaxMana())
						//{
							this.drawTexturedModalRect(xPos, yPos, 0, 18, 96, 6);
							this.drawTexturedModalRect(xPos, yPos, 0, 24, (int) manaBarWidth, 5);
						//}
					}
				}
			}
		}
	}
}

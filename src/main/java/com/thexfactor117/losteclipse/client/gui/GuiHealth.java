package com.thexfactor117.losteclipse.client.gui;

import org.lwjgl.opengl.GL11;

import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
public class GuiHealth extends Gui
{
	private static ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/gui/mana.png");
	private Minecraft mc = Minecraft.getMinecraft();
	
	public GuiHealth()
	{
		super();
	}
	
	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Pre event)
	{	
		if (event.getType() == ElementType.HEALTH)
		{
			event.setCanceled(true);
		}
		
		/*
		 * Repositions other Gui elements. 
		 */
		
		if (event.getType() == ElementType.ARMOR)
		{
			GuiIngameForge.left_height = 49;
		}
		
		if (event.getType() == ElementType.FOOD)
		{
			GuiIngameForge.right_height = 49;
		}
		
		if (event.getType() == ElementType.AIR)
		{
			GuiIngameForge.right_height = 59;
		}
	}
	
	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Post event)
	{	
		if (event.getType() != ElementType.EXPERIENCE) return;
		else
		{	
			ScaledResolution sr = event.getResolution();
			EntityPlayer player = Minecraft.getMinecraft().player;
			
			if (!player.capabilities.isCreativeMode)
			{
				double healthBarWidth = (double) player.getHealth() / player.getMaxHealth() * 81.0;
				int xPos = sr.getScaledWidth() / 2 - 91;
				int yPos = sr.getScaledHeight() - 39;
				
				mc.renderEngine.bindTexture(location);
				
				this.drawTexturedModalRect(xPos, yPos, 0, 0, 81, 9);
				this.drawTexturedModalRect(xPos, yPos, 0, 9, (int) healthBarWidth, 8);	
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderOverlayText(RenderGameOverlayEvent.Text event)
	{
		ScaledResolution sr = event.getResolution();
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		if (!player.capabilities.isCreativeMode)
		{
			String health = (int) player.getHealth() + " / " + (int) player.getMaxHealth();
			
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(health, (sr.getScaledWidth() / 2 - 60) * 2, (sr.getScaledHeight() - 36) * 2, 0xFFFFFF);
			GL11.glPopMatrix();
		}
	}
}

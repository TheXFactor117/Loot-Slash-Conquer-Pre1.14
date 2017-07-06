package com.thexfactor117.minehackslash.client.gui;

import java.io.IOException;

import com.thexfactor117.minehackslash.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.minehackslash.capabilities.IPlayerInformation;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

/**
 * 
 * @author TheXFactor117
 *
 */
public class GuiPlayerInformation extends GuiScreen
{
	@Override
	public void initGui()
	{

	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground(); // draws background tint
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		EntityPlayer player = mc.player;
		IPlayerInformation playerInfo = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		
		if (player != null && playerInfo != null)
		{
			this.drawCenteredString(this.fontRenderer, I18n.format("minehackslash.gui.playerinfo.title"), this.width / 2, 20, 0xFFFFFF); // title
			this.drawString(this.fontRenderer, I18n.format("minehackslash.gui.playerinfo.class") + ": " + I18n.format("minehackslash.gui.playerinfo.class." + playerInfo.getPlayerClass()), this.width / 2 - 50, 40, 0xFFFFFF); // level
			this.drawString(this.fontRenderer, I18n.format("minehackslash.gui.playerinfo.level") + ": " + playerInfo.getPlayerLevel(), this.width / 2 - 50, 50, 0xFFFFFF); // level
			this.drawString(this.fontRenderer, I18n.format("minehackslash.gui.playerinfo.experience") + ": " + playerInfo.getPlayerExperience(), this.width / 2 - 50, 60, 0xFFFFFF); // experience
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{

	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}

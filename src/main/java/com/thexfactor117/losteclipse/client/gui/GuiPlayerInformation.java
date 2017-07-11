package com.thexfactor117.losteclipse.client.gui;

import java.io.IOException;

import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;

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
			this.drawCenteredString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.title"), this.width / 2, 20, 0xFFFFFF); // title
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.class") + ": " + I18n.format("losteclipse.gui.playerinfo.class." + playerInfo.getPlayerClass()), this.width / 2 - 50, 40, 0xFFFFFF); // level
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.level") + ": " + playerInfo.getPlayerLevel(), this.width / 2 - 50, 50, 0xFFFFFF); // level
			
			String experience = I18n.format("losteclipse.gui.playerinfo.experience") + ": " + playerInfo.getPlayerExperience() + " / " + playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel());
			double percent = (((double) playerInfo.getPlayerExperience() * 100) / (double) (playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())));
			String percentString = "  (" + String.format("%.0f%%", percent) + ")";
			this.drawString(this.fontRenderer, experience + percentString, this.width / 2 - 50, 60, 0xFFFFFF); // experience
			
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.skillpoints") + ": " + playerInfo.getSkillPoints(), this.width / 2 - 50, 70, 0xFFFFFF); // skill points
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

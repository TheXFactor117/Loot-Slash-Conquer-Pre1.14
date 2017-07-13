package com.thexfactor117.losteclipse.client.gui;

import java.io.IOException;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;
import com.thexfactor117.losteclipse.network.PacketUpdateIncreaseStat;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

/**
 * 
 * @author TheXFactor117
 *
 */
public class GuiPlayerInformation extends GuiScreen
{
	private GuiButton plusStrength;
	private GuiButton plusAgility;
	private GuiButton plusDexterity;
	private GuiButton plusIntelligence;
	private GuiButton plusWisdom;
	private GuiButton plusFortitude;
	
	@Override
	public void initGui()
	{
		plusStrength = new GuiButton(0, this.width / 2 + 95, 139, 10, 10, "+");
		plusAgility = new GuiButton(1, this.width / 2 + 95, 149, 10, 10, "+");
		plusDexterity = new GuiButton(2, this.width / 2 + 95, 159, 10, 10, "+");
		plusIntelligence = new GuiButton(3, this.width / 2 + 95, 169, 10, 10, "+");
		plusWisdom = new GuiButton(4, this.width / 2 + 95, 179, 10, 10, "+");
		plusFortitude = new GuiButton(5, this.width / 2 + 95, 189, 10, 10, "+");
		
		this.buttonList.add(plusStrength);
		this.buttonList.add(plusAgility);
		this.buttonList.add(plusDexterity);
		this.buttonList.add(plusIntelligence);
		this.buttonList.add(plusWisdom);
		this.buttonList.add(plusFortitude);
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
			// main info
			this.drawCenteredString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.title"), this.width / 2, 20, 0xFFFFFF); // title
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.class") + ": " + I18n.format("losteclipse.gui.playerinfo.class." + playerInfo.getPlayerClass()), this.width / 2 - 50, 40, 0xFFFFFF); // level
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.level") + ": " + playerInfo.getPlayerLevel(), this.width / 2 - 50, 50, 0xFFFFFF); // level
			
			String experience = I18n.format("losteclipse.gui.playerinfo.experience") + ": " + playerInfo.getPlayerExperience() + " / " + playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel());
			double percent = (((double) playerInfo.getPlayerExperience() * 100) / (double) (playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())));
			String percentString = "  (" + String.format("%.0f%%", percent) + ")";
			this.drawString(this.fontRenderer, experience + percentString, this.width / 2 - 50, 60, 0xFFFFFF); // experience
			
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.skillpoints") + ": " + playerInfo.getSkillPoints(), this.width / 2 - 50, 70, 0xFFFFFF); // skill points

			// stats
			this.drawCenteredString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.stats"), this.width / 2 + 150, 120, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.stats.strength") + ":", this.width / 2 + 110, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.stats.agility") + ":", this.width / 2 + 110, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.stats.dexterity") + ":", this.width / 2 + 110, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.stats.intelligence") + ":", this.width / 2 + 110, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.stats.wisdom") + ":", this.width / 2 + 110, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("losteclipse.gui.playerinfo.stats.fortitude") + ":", this.width / 2 + 110, 190, 0xFFFFFF);
			
			this.drawString(this.fontRenderer, "" + playerInfo.getStrengthStat() + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusStrengthStat() + TextFormatting.WHITE + ")", this.width / 2 + 180, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + playerInfo.getAgilityStat() + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusAgilityStat() + TextFormatting.WHITE + ")", this.width / 2 + 180, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + playerInfo.getDexterityStat() + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusDexterityStat() + TextFormatting.WHITE + ")", this.width / 2 + 180, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + playerInfo.getIntelligenceStat() + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusIntelligenceStat() + TextFormatting.WHITE + ")", this.width / 2 + 180, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + playerInfo.getWisdomStat() + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusWisdomStat() + TextFormatting.WHITE + ")", this.width / 2 + 180, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + playerInfo.getFortitudeStat() + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusFortitudeStat() + TextFormatting.WHITE + ")", this.width / 2 + 180, 190, 0xFFFFFF);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		EntityPlayer player = mc.player;
		IPlayerInformation playerInfo = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		
		if (player != null && playerInfo != null && playerInfo.getSkillPoints() > 0)
		{
			// increase stat by one.
			if (button == plusStrength) LostEclipse.network.sendToServer(new PacketUpdateIncreaseStat(1));
			else if (button == plusAgility) LostEclipse.network.sendToServer(new PacketUpdateIncreaseStat(2));
			else if (button == plusDexterity) LostEclipse.network.sendToServer(new PacketUpdateIncreaseStat(3));
			else if (button == plusIntelligence) LostEclipse.network.sendToServer(new PacketUpdateIncreaseStat(4));
			else if (button == plusWisdom) LostEclipse.network.sendToServer(new PacketUpdateIncreaseStat(5));
			else if (button == plusFortitude) LostEclipse.network.sendToServer(new PacketUpdateIncreaseStat(6));
			
			playerInfo.setSkillPoints(playerInfo.getSkillPoints() - 1);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}

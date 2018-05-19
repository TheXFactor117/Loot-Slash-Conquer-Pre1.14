package com.lsc.client.gui;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.playerinfo.CapabilityPlayerInformation;
import com.lsc.capabilities.playerinfo.PlayerInformation;
import com.lsc.capabilities.playerstats.CapabilityPlayerStats;
import com.lsc.capabilities.playerstats.Stats;
import com.lsc.network.PacketUpdateIncreaseStat;
import com.lsc.util.PlayerStatHelper;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.HoverChecker;

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
		plusStrength = new GuiButton(0, this.width / 2 + 65, 139, 10, 10, "+");
		plusAgility = new GuiButton(1, this.width / 2 + 65, 149, 10, 10, "+");
		plusDexterity = new GuiButton(2, this.width / 2 + 65, 159, 10, 10, "+");
		plusIntelligence = new GuiButton(3, this.width / 2 + 65, 169, 10, 10, "+");
		plusWisdom = new GuiButton(4, this.width / 2 + 65, 179, 10, 10, "+");
		plusFortitude = new GuiButton(5, this.width / 2 + 65, 189, 10, 10, "+");
		
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
		
		DecimalFormat format = new DecimalFormat("#.##");
		EntityPlayer player = mc.player;
		PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		Stats statsCap = (Stats) player.getCapability(CapabilityPlayerStats.STATS, null);
		
		if (player != null && playerInfo != null && statsCap != null)
		{
			// main info
			this.drawCenteredString(this.fontRenderer, I18n.format("gui.playerinfo.title"), this.width / 2, 20, 0xFFFFFF); // title
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.class") + ": " + I18n.format("gui.playerinfo.class." + playerInfo.getPlayerClass()), this.width / 2 - 50, 40, 0xFFFFFF); // level
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.level") + ": " + playerInfo.getPlayerLevel(), this.width / 2 - 50, 50, 0xFFFFFF); // level
			
			String experience = I18n.format("gui.playerinfo.experience") + ": " + playerInfo.getPlayerExperience() + " / " + playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel());
			double percent = (((double) playerInfo.getPlayerExperience() * 100) / (double) (playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())));
			String percentString = "  (" + String.format("%.0f%%", percent) + ")";
			this.drawString(this.fontRenderer, experience + percentString, this.width / 2 - 50, 60, 0xFFFFFF); // experience
			
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.skillpoints") + ": " + playerInfo.getSkillPoints(), this.width / 2 - 50, 70, 0xFFFFFF); // skill points

			// stats
			this.drawCenteredString(this.fontRenderer, I18n.format("gui.playerinfo.stats"), this.width / 2 + 120, 120, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.strength") + ":", this.width / 2 + 80, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.agility") + ":", this.width / 2 + 80, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.dexterity") + ":", this.width / 2 + 80, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.intelligence") + ":", this.width / 2 + 80, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.wisdom") + ":", this.width / 2 + 80, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.fortitude") + ":", this.width / 2 + 80, 190, 0xFFFFFF);
			
			this.drawString(this.fontRenderer, "" + (playerInfo.getStrengthStat() + playerInfo.getBonusStrengthStat()) + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusStrengthStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (playerInfo.getAgilityStat() + playerInfo.getBonusAgilityStat()) + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusAgilityStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (playerInfo.getDexterityStat() + playerInfo.getBonusDexterityStat()) + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusDexterityStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (playerInfo.getIntelligenceStat() + playerInfo.getBonusIntelligenceStat()) + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusIntelligenceStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (playerInfo.getWisdomStat() + playerInfo.getBonusWisdomStat()) + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusWisdomStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (playerInfo.getFortitudeStat() + playerInfo.getBonusFortitudeStat()) + " (" + TextFormatting.GREEN + "+" + playerInfo.getBonusFortitudeStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 190, 0xFFFFFF);
			
			// bonuses
			this.drawCenteredString(this.fontRenderer, I18n.format("gui.playerinfo.bonuses"), this.width / 2 - 130, 120, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.playerdamage") + ": " + TextFormatting.WHITE + "+" + (int) (PlayerStatHelper.ATTACK_DAMAGE_MULTIPLIER * (playerInfo.getTotalStrength())), this.width / 2 - 160, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.magicalpower") + ": " + TextFormatting.WHITE + "+" + (int) (PlayerStatHelper.MAGICAL_POWER_MULTIPLIER * (playerInfo.getTotalIntelligence())), this.width / 2 - 160, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.attackspeed") + ": " + TextFormatting.WHITE + "+" + format.format((PlayerStatHelper.ATTACK_SPEED_MULTIPLIER * (playerInfo.getTotalAgility()))), this.width / 2 - 160, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.armor") + ": " + TextFormatting.WHITE + "+" + format.format(player.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue()), this.width / 2 - 160, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.toughness") + ": " + TextFormatting.WHITE + "+" + format.format(player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()), this.width / 2 - 160, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.health") + ": " + TextFormatting.WHITE + "+" + (int) (PlayerStatHelper.MAX_HEALTH_MULTIPLIER * (playerInfo.getTotalFortitude())), this.width / 2 - 160, 190, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.hp5") + ": " + TextFormatting.WHITE + statsCap.getHealthPerSecond(), this.width / 2 - 160, 200, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.mana") + ": " + TextFormatting.WHITE + "+" + (int) (PlayerStatHelper.MAX_MANA_MULTIPLIER * (playerInfo.getTotalWisdom())), this.width / 2 - 160, 210, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.mp5") + ": " + TextFormatting.WHITE + statsCap.getManaPerSecond(), this.width / 2 - 160, 220, 0xFFFFFF);
		
			drawTooltips(mouseX, mouseY);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		EntityPlayer player = mc.player;
		PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		
		// REMEMBER TO UPDATE CLIENT SIDE TOO
		
		if (player != null && playerInfo != null && playerInfo.getSkillPoints() > 0)
		{
			// increase stat by one.
			if (button == plusStrength) 
			{
				playerInfo.setStrengthStat(playerInfo.getStrengthStat() + 1);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(1));
			}
			else if (button == plusAgility) 
			{
				playerInfo.setAgilityStat(playerInfo.getAgilityStat() + 1);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(2));
			}
			else if (button == plusDexterity) 
			{
				playerInfo.setDexterityStat(playerInfo.getDexterityStat() + 1);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(3));
			}
			else if (button == plusIntelligence) 
			{
				playerInfo.setIntelligenceStat(playerInfo.getIntelligenceStat() + 1);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(4));
			}
			else if (button == plusWisdom) 
			{
				playerInfo.setWisdomStat(playerInfo.getWisdomStat() + 1);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(5));
			}
			else if (button == plusFortitude) 
			{
				playerInfo.setFortitudeStat(playerInfo.getFortitudeStat() + 1);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(6));
			}
			
			playerInfo.setSkillPoints(playerInfo.getSkillPoints() - 1);
			
			PlayerStatHelper.updateAttributes(player);
		}
	}
	
	/**
	 * Check to see if mouse is hovering over a BUTTON. If so, display tooltip of button.
	 * @param mouseX
	 * @param mouseY
	 */
	private void drawTooltips(int mouseX, int mouseY)
	{
		HoverChecker strength = new HoverChecker(plusStrength, 0);
		HoverChecker agility = new HoverChecker(plusAgility, 0);
		HoverChecker dexterity = new HoverChecker(plusDexterity, 0);
		HoverChecker intelligence = new HoverChecker(plusIntelligence, 0);
		HoverChecker wisdom = new HoverChecker(plusWisdom, 0);
		HoverChecker fortitude = new HoverChecker(plusFortitude, 0);
		
		List<String> list = new ArrayList<String>();
		
		if (strength.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.RED + I18n.format("gui.playerinfo.stats.strength"));
			list.add(TextFormatting.GRAY + I18n.format("gui.playerinfo.stats.strength.info"));
			
			drawHoveringText(list, mouseX + 3, mouseY + 3);
		}
		else if (agility.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.DARK_GREEN + I18n.format("gui.playerinfo.stats.agility"));
			list.add(TextFormatting.GRAY + I18n.format("gui.playerinfo.stats.agility.info"));
			
			drawHoveringText(list, mouseX + 3, mouseY + 3);
		}
		else if (dexterity.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.GOLD + I18n.format("gui.playerinfo.stats.dexterity"));
			list.add(TextFormatting.GRAY + I18n.format("gui.playerinfo.stats.dexterity.info"));
			
			drawHoveringText(list, mouseX + 3, mouseY + 3);
		}
		else if (intelligence.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.BLUE + I18n.format("gui.playerinfo.stats.intelligence"));
			list.add(TextFormatting.GRAY + I18n.format("gui.playerinfo.stats.intelligence.info"));
			
			drawHoveringText(list, mouseX + 3, mouseY + 3);
		}
		else if (wisdom.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.DARK_PURPLE + I18n.format("gui.playerinfo.stats.wisdom"));
			list.add(TextFormatting.GRAY + I18n.format("gui.playerinfo.stats.wisdom.info"));
			
			drawHoveringText(list, mouseX + 3, mouseY + 3);
		}
		else if (fortitude.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.WHITE + I18n.format("gui.playerinfo.stats.fortitude"));
			list.add(TextFormatting.GRAY + I18n.format("gui.playerinfo.stats.fortitude.info"));
			
			drawHoveringText(list, mouseX + 3, mouseY + 3);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}

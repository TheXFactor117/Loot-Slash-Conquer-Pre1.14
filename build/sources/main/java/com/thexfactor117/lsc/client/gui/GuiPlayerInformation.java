package com.thexfactor117.lsc.client.gui;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.network.server.PacketUpdateIncreaseStat;
import com.thexfactor117.lsc.util.DamageUtil;
import com.thexfactor117.lsc.util.ExperienceUtil;
import com.thexfactor117.lsc.util.PlayerUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
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
		LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
		
		if (player != null && cap != null)
		{
			// main info
			this.drawCenteredString(this.fontRenderer, I18n.format("gui.playerinfo.title"), this.width / 2, 20, 0xFFFFFF); // title
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.class") + ": " + I18n.format("gui.playerinfo.class." + cap.getPlayerClass()), this.width / 2 - 50, 40, 0xFFFFFF); // level
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.level") + ": " + cap.getPlayerLevel(), this.width / 2 - 50, 50, 0xFFFFFF); // level
			
			String experience = I18n.format("gui.playerinfo.experience") + ": " + cap.getPlayerExperience() + " / " + ExperienceUtil.getLevelUpExperience(cap.getPlayerLevel());
			double percent = (((double) cap.getPlayerExperience() * 100) / (double) (ExperienceUtil.getLevelUpExperience(cap.getPlayerLevel())));
			String percentString = "  (" + String.format("%.0f%%", percent) + ")";
			this.drawString(this.fontRenderer, experience + percentString, this.width / 2 - 50, 60, 0xFFFFFF); // experience
			
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.skillpoints") + ": " + cap.getSkillPoints(), this.width / 2 - 50, 70, 0xFFFFFF); // skill points

			// stats
			this.drawCenteredString(this.fontRenderer, I18n.format("gui.playerinfo.stats"), this.width / 2 + 120, 120, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.strength") + ":", this.width / 2 + 80, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.agility") + ":", this.width / 2 + 80, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.dexterity") + ":", this.width / 2 + 80, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.intelligence") + ":", this.width / 2 + 80, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.wisdom") + ":", this.width / 2 + 80, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, I18n.format("gui.playerinfo.stats.fortitude") + ":", this.width / 2 + 80, 190, 0xFFFFFF);
			
			this.drawString(this.fontRenderer, "" + (cap.getStrengthStat() + cap.getBonusStrengthStat()) + " (" + TextFormatting.GREEN + "+" + cap.getBonusStrengthStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (cap.getAgilityStat() + cap.getBonusAgilityStat()) + " (" + TextFormatting.GREEN + "+" + cap.getBonusAgilityStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (cap.getDexterityStat() + cap.getBonusDexterityStat()) + " (" + TextFormatting.GREEN + "+" + cap.getBonusDexterityStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (cap.getIntelligenceStat() + cap.getBonusIntelligenceStat()) + " (" + TextFormatting.GREEN + "+" + cap.getBonusIntelligenceStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (cap.getWisdomStat() + cap.getBonusWisdomStat()) + " (" + TextFormatting.GREEN + "+" + cap.getBonusWisdomStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, "" + (cap.getFortitudeStat() + cap.getBonusFortitudeStat()) + " (" + TextFormatting.GREEN + "+" + cap.getBonusFortitudeStat() + TextFormatting.WHITE + ")", this.width / 2 + 150, 190, 0xFFFFFF);
			
			// bonuses
			double playerDamage = Configs.weaponCategory.damageBaseFactor * cap.getTotalStrength();
			double rangedPower = Configs.weaponCategory.damageBaseFactor * cap.getTotalDexterity();
			double magicalPower = Configs.weaponCategory.damageBaseFactor * cap.getTotalIntelligence();

			playerDamage = playerDamage < 1 ? 0 : playerDamage;
			rangedPower = rangedPower < 1 ? 0 : rangedPower;
			magicalPower = magicalPower < 1 ? 0 : magicalPower;

			this.drawCenteredString(this.fontRenderer, I18n.format("gui.playerinfo.bonuses"), this.width / 2 - 130, 120, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.playerdamage") + ": " + TextFormatting.WHITE + "+" + format.format(playerDamage), this.width / 2 - 160, 140, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.rangedpower") + ": " + TextFormatting.WHITE + "+" + format.format(rangedPower), this.width / 2 - 160, 150, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.magicalpower") + ": " + TextFormatting.WHITE + "+" + format.format(magicalPower), this.width / 2 - 160, 160, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.attackspeed") + ": " + TextFormatting.WHITE + "+" + format.format((Configs.playerCategory.attackSpeedMultiplier* (cap.getTotalAgility()))), this.width / 2 - 160, 170, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.armor") + ": " + TextFormatting.WHITE + "+" + format.format(DamageUtil.getEquippedArmor(player, cap)), this.width / 2 - 160, 180, 0xFFFFFF);
			//this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.toughness") + ": " + TextFormatting.WHITE + "+" + format.format(player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()), this.width / 2 - 160, 180, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.critchance") + ": " + TextFormatting.WHITE + ((int) (cap.getCriticalChance() * 100)) + "%", this.width / 2 - 160, 190, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.critdamage") + ": " + TextFormatting.WHITE + ((int) (cap.getCriticalDamage() * 100)) + "%", this.width / 2 - 160, 200, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.health") + ": " + TextFormatting.WHITE + "+" + (int) (Configs.playerCategory.maxHealthMultiplier * (cap.getTotalFortitude())), this.width / 2 - 160, 210, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.hp5") + ": " + TextFormatting.WHITE + cap.getHealthPerSecond(), this.width / 2 - 160, 220, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.mana") + ": " + TextFormatting.WHITE + "+" + (int) (Configs.playerCategory.maxManaMultiplier * (cap.getTotalWisdom())), this.width / 2 - 160, 230, 0xFFFFFF);
			this.drawString(this.fontRenderer, TextFormatting.GRAY + I18n.format("gui.playerinfo.bonuses.mp5") + ": " + TextFormatting.WHITE + cap.getManaPerSecond(), this.width / 2 - 160, 240, 0xFFFFFF);
		
			drawTooltips(mouseX, mouseY);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		EntityPlayer player = mc.player;
		LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
		
		// REMEMBER TO UPDATE CLIENT SIDE TOO
		
		if (player != null && cap != null && cap.getSkillPoints() > 0)
		{
			// increase stat by one.
			if (button == plusStrength) 
			{
				cap.setStrengthStat(cap.getStrengthStat() + 1);
				PlayerUtil.updateStrengthStat(player);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(1));
			}
			else if (button == plusAgility) 
			{
				cap.setAgilityStat(cap.getAgilityStat() + 1);
				PlayerUtil.updateAgilityStat(player);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(2));
			}
			else if (button == plusDexterity) 
			{
				cap.setDexterityStat(cap.getDexterityStat() + 1);
				PlayerUtil.updateDexterityStat(player);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(3));
			}
			else if (button == plusIntelligence) 
			{
				cap.setIntelligenceStat(cap.getIntelligenceStat() + 1);
				PlayerUtil.updateIntelligenceStat(player);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(4));
			}
			else if (button == plusWisdom) 
			{
				cap.setWisdomStat(cap.getWisdomStat() + 1);
				PlayerUtil.updateWisdomStat(player);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(5));
			}
			else if (button == plusFortitude) 
			{
				cap.setFortitudeStat(cap.getFortitudeStat() + 1);
				PlayerUtil.updateFortitudeStat(player);
				LootSlashConquer.network.sendToServer(new PacketUpdateIncreaseStat(6));
			}
			
			cap.updatePlayerPower();
			cap.setSkillPoints(cap.getSkillPoints() - 1);
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

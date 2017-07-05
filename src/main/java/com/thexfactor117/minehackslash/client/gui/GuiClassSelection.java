package com.thexfactor117.minehackslash.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thexfactor117.minehackslash.MineHackSlash;
import com.thexfactor117.minehackslash.network.PacketClassSelection;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.HoverChecker;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
public class GuiClassSelection extends GuiScreen
{
	private GuiButton warrior;
	private GuiButton mage;
	private GuiButton hunter;
	
	@Override
	public void initGui()
	{
		// GuiButton(index, x, y, width, height, name)
		warrior = new GuiButton(0, this.width / 2 - 50, 80, 100, 20, "Warrior");
		mage = new GuiButton(1, this.width / 2 - 50, 110, 100, 20, "Mage");
		hunter = new GuiButton(2, this.width / 2 - 50, 140, 100, 20, "Hunter");
		
		this.buttonList.add(warrior);
		this.buttonList.add(mage);
		this.buttonList.add(hunter);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground(); // draws background tint
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.drawCenteredString(this.fontRenderer, I18n.format("minehackslash.gui.class.title"), this.width / 2, 20, 0xFFFFFF); // title
		this.drawCenteredString(this.fontRenderer, I18n.format("minehackslash.gui.class.info"), this.width / 2, 40, 0xFFFFFF); // basic class information.
		
		drawTooltips(mouseX, mouseY);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		EntityPlayer player = mc.player;
		
		if (player != null)
		{
			if (button == warrior) MineHackSlash.network.sendToServer(new PacketClassSelection(1));
			else if (button == mage) MineHackSlash.network.sendToServer(new PacketClassSelection(2));
			else if (button == hunter) MineHackSlash.network.sendToServer(new PacketClassSelection(3));
			
			this.mc.player.closeScreen();
		}
	}
	
	/**
	 * Check to see if mouse is hovering over a BUTTON. If so, display tooltip of button.
	 * @param mouseX
	 * @param mouseY
	 */
	private void drawTooltips(int mouseX, int mouseY)
	{
		HoverChecker warriorCheck = new HoverChecker(warrior, 0);
		HoverChecker mageCheck = new HoverChecker(mage, 0);
		HoverChecker hunterCheck = new HoverChecker(hunter, 0);
		
		List<String> list = new ArrayList<String>();
		
		if (warriorCheck.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.RED + I18n.format("minehackslash.gui.class.warrior.title")); // title
			list.add(TextFormatting.GRAY + I18n.format("minehackslash.gui.class.warrior.info")); // information about class
			list.add("");
			list.add(TextFormatting.ITALIC  + "" + TextFormatting.GRAY + I18n.format("minehackslash.gui.class.abilities")); // Abilities title
			
			// add specific ability information.
			
			drawHoveringText(list, mouseX + 3, mouseY + 3); // draw tooltip box; offset from center of mouse.
		}
		else if (mageCheck.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.BLUE + I18n.format("minehackslash.gui.class.mage.title")); // title
			list.add(TextFormatting.GRAY + I18n.format("minehackslash.gui.class.mage.info")); // information about class
			list.add("");
			list.add(TextFormatting.ITALIC  + "" + TextFormatting.GRAY + I18n.format("minehackslash.gui.class.abilities")); // Abilities title
			
			// add specific ability information.
			
			drawHoveringText(list, mouseX + 3, mouseY + 3); // draw tooltip box; offset from center of mouse.
		}
		else if (hunterCheck.checkHover(mouseX, mouseY))
		{
			list.add(TextFormatting.DARK_GREEN + I18n.format("minehackslash.gui.class.hunter.title")); // title
			list.add(TextFormatting.GRAY + I18n.format("minehackslash.gui.class.hunter.info")); // information about class
			list.add("");
			list.add(TextFormatting.ITALIC  + "" + TextFormatting.GRAY + I18n.format("minehackslash.gui.class.abilities")); // Abilities title
			
			// add specific ability information.
			
			drawHoveringText(list, mouseX + 3, mouseY + 3); // draw tooltip box; offset from center of mouse.
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}

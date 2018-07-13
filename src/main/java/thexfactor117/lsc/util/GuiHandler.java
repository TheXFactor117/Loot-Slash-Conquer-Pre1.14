package thexfactor117.lsc.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import thexfactor117.lsc.client.gui.GuiClassSelection;
import thexfactor117.lsc.client.gui.GuiHealth;
import thexfactor117.lsc.client.gui.GuiMana;
import thexfactor117.lsc.client.gui.GuiPlayerInformation;

/**
 * 
 * @author TheXFactor117
 *
 */
public class GuiHandler implements IGuiHandler
{
	public static final int CLASS_SELECTION = 0;
	public static final int PLAYER_INFORMATION = 1;
	public static final int MANA = 2;
	public static final int HEALTH = 3;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (id == CLASS_SELECTION)
			return new GuiClassSelection();
		if (id == PLAYER_INFORMATION)
			return new GuiPlayerInformation();
		if (id == MANA)
			return new GuiMana();
		if (id == HEALTH)
			return new GuiHealth();
		
		return null;
	}	
}

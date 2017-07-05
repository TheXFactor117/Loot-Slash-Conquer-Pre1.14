package com.thexfactor117.minehackslash.util;

import com.thexfactor117.minehackslash.client.gui.GuiClassSelection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * 
 * @author TheXFactor117
 *
 */
public class GuiHandler implements IGuiHandler
{
	public static final int CLASS_SELECTION = 0;
	
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
		
		return null;
	}	
}

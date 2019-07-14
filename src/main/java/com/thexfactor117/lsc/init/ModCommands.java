package com.thexfactor117.lsc.init;

import com.thexfactor117.lsc.commands.CommandAddAttribute;
import com.thexfactor117.lsc.commands.CommandSetItemLevel;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 *
 * @author TheXFactor117
 *
 */
public class ModCommands
{
	public static void registerCommands(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandAddAttribute());
		event.registerServerCommand(new CommandSetItemLevel());
	}
}

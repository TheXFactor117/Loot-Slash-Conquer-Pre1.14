package com.thexfactor117.minehackslash.init;

import com.thexfactor117.minehackslash.events.EventInput;
import com.thexfactor117.minehackslash.events.EventLivingDeath;
import com.thexfactor117.minehackslash.events.EventPlayerLoggedIn;

import net.minecraftforge.common.MinecraftForge;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModEvents 
{
	public static void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new EventPlayerLoggedIn());
		MinecraftForge.EVENT_BUS.register(new EventLivingDeath());
		MinecraftForge.EVENT_BUS.register(new EventInput());
		//MinecraftForge.EVENT_BUS.register(new EventTest());
	}
}

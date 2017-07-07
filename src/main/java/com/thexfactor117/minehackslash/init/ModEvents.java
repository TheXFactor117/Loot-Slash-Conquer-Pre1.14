package com.thexfactor117.minehackslash.init;

import com.thexfactor117.minehackslash.events.EventInput;
import com.thexfactor117.minehackslash.events.EventItemTooltip;
import com.thexfactor117.minehackslash.events.EventLivingDeath;
import com.thexfactor117.minehackslash.events.EventLivingHurt;
import com.thexfactor117.minehackslash.events.EventPlayerLoggedIn;
import com.thexfactor117.minehackslash.events.EventPlayerTick;

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
		MinecraftForge.EVENT_BUS.register(new EventLivingHurt());
		MinecraftForge.EVENT_BUS.register(new EventItemTooltip());
		MinecraftForge.EVENT_BUS.register(new EventPlayerTick());
		//MinecraftForge.EVENT_BUS.register(new EventTest());
	}
}

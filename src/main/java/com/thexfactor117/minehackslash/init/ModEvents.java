package com.thexfactor117.minehackslash.init;

import com.thexfactor117.minehackslash.events.EventLivingDeath;
import com.thexfactor117.minehackslash.events.EventLivingHurtAttack;
import com.thexfactor117.minehackslash.events.misc.EventInput;
import com.thexfactor117.minehackslash.events.misc.EventItemTooltip;
import com.thexfactor117.minehackslash.events.misc.EventLoadLootTable;
import com.thexfactor117.minehackslash.events.misc.EventPlayerLoggedIn;

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
		MinecraftForge.EVENT_BUS.register(new EventLivingHurtAttack());
		MinecraftForge.EVENT_BUS.register(new EventItemTooltip());
		MinecraftForge.EVENT_BUS.register(new ModWeapons());
		MinecraftForge.EVENT_BUS.register(new EventLoadLootTable());
	}
}

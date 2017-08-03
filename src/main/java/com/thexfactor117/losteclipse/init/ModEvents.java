package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.events.EventInput;
import com.thexfactor117.losteclipse.events.EventItemTooltip;
import com.thexfactor117.losteclipse.events.EventLivingDeath;
import com.thexfactor117.losteclipse.events.EventLivingHurtAttack;
import com.thexfactor117.losteclipse.events.EventLoadLootTable;
import com.thexfactor117.losteclipse.events.EventPlayerLoggedIn;
import com.thexfactor117.losteclipse.events.EventPlayerTick;

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
		MinecraftForge.EVENT_BUS.register(new ModBlocks());
		MinecraftForge.EVENT_BUS.register(new ModWeapons());
		
		MinecraftForge.EVENT_BUS.register(new EventPlayerLoggedIn());
		MinecraftForge.EVENT_BUS.register(new EventLivingDeath());
		MinecraftForge.EVENT_BUS.register(new EventInput());
		MinecraftForge.EVENT_BUS.register(new EventLivingHurtAttack());
		MinecraftForge.EVENT_BUS.register(new EventItemTooltip());
		MinecraftForge.EVENT_BUS.register(new EventLoadLootTable());
		MinecraftForge.EVENT_BUS.register(new EventPlayerTick());
	}
}

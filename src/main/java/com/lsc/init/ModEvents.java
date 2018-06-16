package com.lsc.init;

import com.lsc.events.EventContainerOpen;
import com.lsc.events.EventPlayerStartTracking;
import com.lsc.events.EventLoadLootTable;
import com.lsc.events.EventPlayerLoggedIn;
import com.lsc.events.EventPlayerTick;
import com.lsc.events.combat.EventLivingDeath;
import com.lsc.events.combat.EventLivingHurtAttack;

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
		MinecraftForge.EVENT_BUS.register(new ModItems());
		MinecraftForge.EVENT_BUS.register(new ModWeapons());
		
		MinecraftForge.EVENT_BUS.register(new EventPlayerLoggedIn());
		MinecraftForge.EVENT_BUS.register(new EventLivingDeath());
		MinecraftForge.EVENT_BUS.register(new EventLivingHurtAttack());
		MinecraftForge.EVENT_BUS.register(new EventLoadLootTable());
		MinecraftForge.EVENT_BUS.register(new EventPlayerTick());
		MinecraftForge.EVENT_BUS.register(new EventPlayerStartTracking());
		MinecraftForge.EVENT_BUS.register(new EventContainerOpen());
	}
}

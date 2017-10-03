package com.thexfactor117.lootslashconquer.init;

import com.thexfactor117.lootslashconquer.events.EventEntityJoinWorld;
import com.thexfactor117.lootslashconquer.events.EventInput;
import com.thexfactor117.lootslashconquer.events.EventItemTooltip;
import com.thexfactor117.lootslashconquer.events.EventLoadLootTable;
import com.thexfactor117.lootslashconquer.events.EventPlayerLoggedIn;
import com.thexfactor117.lootslashconquer.events.EventPlayerTick;
import com.thexfactor117.lootslashconquer.events.combat.EventLivingDeath;
import com.thexfactor117.lootslashconquer.events.combat.EventLivingHurtAttack;

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
		MinecraftForge.EVENT_BUS.register(new EventInput());
		MinecraftForge.EVENT_BUS.register(new EventLivingHurtAttack());
		MinecraftForge.EVENT_BUS.register(new EventItemTooltip());
		MinecraftForge.EVENT_BUS.register(new EventLoadLootTable());
		MinecraftForge.EVENT_BUS.register(new EventPlayerTick());
		MinecraftForge.EVENT_BUS.register(new EventEntityJoinWorld());
	}
}

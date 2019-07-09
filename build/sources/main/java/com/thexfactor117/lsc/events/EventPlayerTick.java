package com.thexfactor117.lsc.events;

import com.thexfactor117.lsc.util.PlayerUtil;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventPlayerTick 
{
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START && !event.player.world.isRemote)
		{
			PlayerUtil.getLSCPlayer(event.player).tickPlayer();
		}
	}
}

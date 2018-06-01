package com.lsc.client.events;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventRenderPlayer 
{
	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Pre event)
	{		
		if (event.getEntityPlayer().isInvisible())
		{
			event.setCanceled(true);
		}
	}
}

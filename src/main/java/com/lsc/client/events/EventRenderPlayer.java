package com.lsc.client.events;

import com.lsc.config.Configs;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class EventRenderPlayer 
{
	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event)
	{		
		if (event.getEntityPlayer().isInvisible() && Configs.renderingCategory.renderInvisibilePlayer)
		{
			event.setCanceled(true);
		}
	}
}

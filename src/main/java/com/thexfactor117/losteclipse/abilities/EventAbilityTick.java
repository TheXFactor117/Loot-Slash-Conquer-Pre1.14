package com.thexfactor117.losteclipse.abilities;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
//@Mod.EventBusSubscriber
public class EventAbilityTick 
{
	@SubscribeEvent
	public static void onAbilityTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START && !event.player.getEntityWorld().isRemote)
		{
			for (Ability ability : AbilityHelper.abilities)
			{
				if (ability.isActive())
				{
					ability.onTick();
				}
			}
		}
	}
}

package com.thexfactor117.lsc.events;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
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

		LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(event.player);

		int playerClass = cap.getPlayerClass();

		switch(playerClass)
		{
			case 1:
				if(cap.getStrengthStat() < 10) cap.setStrengthStat(10);
				break;
			case 2:
				if(cap.getIntelligenceStat() < 10) cap.setIntelligenceStat(10);
				break;
			case 3:
				if(cap.getDexterityStat() < 10) cap.setDexterityStat(10);
				break;
		}

		if (cap.getFortitudeStat() == 0) cap.setFortitudeStat(5);
		if (cap.getWisdomStat() == 0) cap.setWisdomStat(5);
		if (cap.getIntelligenceStat() == 0) cap.setIntelligenceStat(5);
		if (cap.getDexterityStat() == 0) cap.setDexterityStat(5);
		if (cap.getAgilityStat() == 0) cap.setAgilityStat(5);
		if (cap.getStrengthStat() == 0) cap.setStrengthStat(5);

		PlayerUtil.updateAllStats(event.player);
	}
}

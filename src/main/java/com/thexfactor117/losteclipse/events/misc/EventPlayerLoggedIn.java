package com.thexfactor117.losteclipse.events.misc;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerStats;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.api.IStats;
import com.thexfactor117.losteclipse.capabilities.api.IPlayerInformation;
import com.thexfactor117.losteclipse.network.PacketClassGui;
import com.thexfactor117.losteclipse.network.PacketUpdateStats;
import com.thexfactor117.losteclipse.network.PacketUpdatePlayerInformation;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventPlayerLoggedIn 
{
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
	{
		IPlayerInformation playerInfo = event.player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		IStats statsCap = event.player.getCapability(CapabilityPlayerStats.STATS, null);

		if (playerInfo != null && playerInfo.getPlayerClass() == 0 && statsCap != null && statsCap.getMaxMana() == 0)
		{
			// send Class Selection gui to client on first join.
			LostEclipse.network.sendTo(new PacketClassGui(), (EntityPlayerMP) event.player);
			
			// setup max mana + send it to client.
			statsCap.setMaxMana(100);
			statsCap.setMana(statsCap.getMaxMana());
			statsCap.setManaPerSecond(5);
			LostEclipse.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) event.player);
		}
		else if (playerInfo != null && playerInfo.getPlayerClass() > 0)
			LostEclipse.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) event.player);
	}
}

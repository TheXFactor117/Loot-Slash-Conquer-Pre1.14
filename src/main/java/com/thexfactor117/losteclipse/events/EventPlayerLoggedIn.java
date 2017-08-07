package com.thexfactor117.losteclipse.events;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerinfo.IPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerstats.CapabilityPlayerStats;
import com.thexfactor117.losteclipse.capabilities.playerstats.IStats;
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
			statsCap.setHealthPerSecond(1);
			LostEclipse.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) event.player);
		}
		else if (playerInfo != null && playerInfo.getPlayerClass() > 0)
			LostEclipse.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) event.player);
	}
}

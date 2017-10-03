package com.thexfactor117.lootslashconquer.events;

import com.thexfactor117.lootslashconquer.LootSlashConquer;
import com.thexfactor117.lootslashconquer.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.lootslashconquer.capabilities.playerinfo.PlayerInformation;
import com.thexfactor117.lootslashconquer.capabilities.playerstats.CapabilityPlayerStats;
import com.thexfactor117.lootslashconquer.capabilities.playerstats.Stats;
import com.thexfactor117.lootslashconquer.network.PacketClassGui;
import com.thexfactor117.lootslashconquer.network.PacketUpdatePlayerInformation;
import com.thexfactor117.lootslashconquer.network.PacketUpdateStats;

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
		PlayerInformation playerInfo = (PlayerInformation) event.player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		Stats statsCap = (Stats) event.player.getCapability(CapabilityPlayerStats.STATS, null);

		if (playerInfo != null && playerInfo.getPlayerClass() == 0 && statsCap != null && statsCap.getMaxMana() == 0)
		{
			// send Class Selection gui to client on first join.
			LootSlashConquer.network.sendTo(new PacketClassGui(), (EntityPlayerMP) event.player);
			
			// setup max mana + send it to client.
			statsCap.setMaxMana(100);
			statsCap.setMana(statsCap.getMaxMana());
			statsCap.setManaPerSecond(5);
			statsCap.setHealthPerSecond(1);
			LootSlashConquer.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) event.player);
		}
		else if (playerInfo != null && playerInfo.getPlayerClass() > 0)
			LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) event.player);
	}
}

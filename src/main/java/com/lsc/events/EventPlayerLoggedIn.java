package com.lsc.events;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.cap.CapabilityPlayerStats;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.capabilities.implementation.Stats;
import com.lsc.network.PacketClassGui;
import com.lsc.network.PacketUpdatePlayerInformation;
import com.lsc.network.PacketUpdateStats;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventPlayerLoggedIn 
{
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
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
			statsCap.setMagicalPower(0);
			statsCap.setCriticalChance(0);
			statsCap.setCriticalDamage(0);
			LootSlashConquer.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) event.player);
		}
		else if (playerInfo != null && playerInfo.getPlayerClass() > 0)
			LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) event.player);
	}
}

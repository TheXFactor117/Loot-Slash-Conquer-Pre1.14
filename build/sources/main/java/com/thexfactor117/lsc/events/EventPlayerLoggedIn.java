package com.thexfactor117.lsc.events;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.network.client.PacketClassGui;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerInformation;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerStats;
import com.thexfactor117.lsc.util.PlayerUtil;

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
		LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(event.player);

		if (cap != null && cap.getPlayerClass() == 0 && cap.getMaxMana() == 0)
		{
			// send Class Selection gui to client on first join.
			LootSlashConquer.network.sendTo(new PacketClassGui(), (EntityPlayerMP) event.player);

			cap.setPlayerLevel(1);
			
			// setup max mana + send it to client.
			cap.setMaxMana(Configs.playerCategory.maxMana);
			cap.setMana(cap.getMaxMana());
			cap.setManaPerSecond(Configs.playerCategory.manaPer5);
			cap.setHealthPerSecond(Configs.playerCategory.healthPer5);
			cap.setMagicalPower(0);
			cap.setCriticalChance(0);
			cap.setCriticalDamage(0);
			LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) event.player);
			LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(cap), (EntityPlayerMP) event.player);
		}
		else if (cap != null && cap.getPlayerClass() > 0)
		{
			LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) event.player);
			LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(cap), (EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
	{
		LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(event.player);
		cap.removeBonusStats();
	}
}

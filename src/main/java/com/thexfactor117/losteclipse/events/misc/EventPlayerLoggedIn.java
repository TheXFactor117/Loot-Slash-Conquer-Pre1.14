package com.thexfactor117.losteclipse.events.misc;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityMana;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IMana;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;
import com.thexfactor117.losteclipse.network.PacketClassGui;
import com.thexfactor117.losteclipse.network.PacketUpdateMana;
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
		IMana manaCap = event.player.getCapability(CapabilityMana.MANA, null);

		if (playerInfo != null && playerInfo.getPlayerClass() == 0 && manaCap != null && manaCap.getMaxMana() == 0)
		{
			// send Class Selection gui to client on first join.
			LostEclipse.network.sendTo(new PacketClassGui(), (EntityPlayerMP) event.player);
			
			// setup max mana + send it to client.
			manaCap.setMaxMana(100);
			manaCap.setMana(manaCap.getMaxMana());
			manaCap.setManaPerSecond(5);
			LostEclipse.network.sendTo(new PacketUpdateMana(manaCap), (EntityPlayerMP) event.player);
			
			LostEclipse.LOGGER.info("Setting Max Mana!: " + manaCap.getMaxMana());
		}
		else if (playerInfo != null && playerInfo.getPlayerClass() > 0)
			LostEclipse.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) event.player);
	}
}

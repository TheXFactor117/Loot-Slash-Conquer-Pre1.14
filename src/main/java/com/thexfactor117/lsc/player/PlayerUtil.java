package com.thexfactor117.lsc.player;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.cap.CapabilityLSCPlayer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;

import net.minecraft.entity.player.EntityPlayer;

/**
 *
 * @author TheXFactor117
 *
 */
public class PlayerUtil
{
	public static LSCPlayerCapability getLSCPlayer(EntityPlayer player)
	{
		LSCPlayerCapability cap = (LSCPlayerCapability) player.getCapability(CapabilityLSCPlayer.PLAYER_CAP, null);
		
		if (cap != null)
		{
			return cap;
		}
		else
		{
			LootSlashConquer.LOGGER.info("ERROR: Player: " + player.getName() + " doesn't have an attached capability. Things might break.");
			return null;
		}
	}
}

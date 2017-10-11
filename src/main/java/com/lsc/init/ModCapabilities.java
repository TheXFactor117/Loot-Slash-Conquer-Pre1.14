package com.lsc.init;

import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.enemylevel.CapabilityEnemyLevel;
import com.lsc.capabilities.playerinfo.CapabilityPlayerInformation;
import com.lsc.capabilities.playerstats.CapabilityPlayerStats;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModCapabilities 
{
	public static void registerCapabilities()
	{
		CapabilityPlayerInformation.register();
		CapabilityPlayerStats.register();
		CapabilityChunkLevel.register();
		CapabilityEnemyLevel.register();
	}
}

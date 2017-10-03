package com.thexfactor117.lootslashconquer.init;

import com.thexfactor117.lootslashconquer.capabilities.chunk.CapabilityChunkLevel;
import com.thexfactor117.lootslashconquer.capabilities.enemylevel.CapabilityEnemyLevel;
import com.thexfactor117.lootslashconquer.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.lootslashconquer.capabilities.playerstats.CapabilityPlayerStats;

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

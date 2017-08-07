package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.capabilities.chunk.CapabilityChunkLevel;
import com.thexfactor117.losteclipse.capabilities.enemylevel.CapabilityEnemyLevel;
import com.thexfactor117.losteclipse.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerstats.CapabilityPlayerStats;

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

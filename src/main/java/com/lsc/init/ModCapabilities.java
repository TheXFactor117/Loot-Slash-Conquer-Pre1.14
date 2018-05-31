package com.lsc.init;

import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.enemyinfo.CapabilityEnemyInfo;
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
		CapabilityEnemyInfo.register();
	}
}

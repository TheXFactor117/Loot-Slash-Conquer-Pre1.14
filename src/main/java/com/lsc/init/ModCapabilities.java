package com.lsc.init;

import com.lsc.capabilities.cap.CapabilityChunkLevel;
import com.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.cap.CapabilityPlayerStats;

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

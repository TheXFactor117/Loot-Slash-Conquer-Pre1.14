package com.thexfactor117.lsc.init;

import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.thexfactor117.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.thexfactor117.lsc.capabilities.cap.CapabilityPlayerStats;

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

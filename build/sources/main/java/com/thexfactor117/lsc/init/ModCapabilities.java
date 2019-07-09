package com.thexfactor117.lsc.init;

import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.thexfactor117.lsc.capabilities.cap.CapabilityLSCPlayer;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModCapabilities 
{
	public static void registerCapabilities()
	{
		CapabilityLSCPlayer.register();
		CapabilityChunkLevel.register();
		CapabilityEnemyInfo.register();
	}
}

package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerStats;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;

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
	}
}

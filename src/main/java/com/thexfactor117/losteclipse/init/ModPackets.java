package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.network.PacketClassGui;
import com.thexfactor117.losteclipse.network.PacketClassSelection;
import com.thexfactor117.losteclipse.network.PacketUpdateIncreaseStat;
import com.thexfactor117.losteclipse.network.PacketUpdateMana;
import com.thexfactor117.losteclipse.network.PacketUpdatePlayerInformation;
import com.thexfactor117.losteclipse.network.PacketUpdatePlayerStats;
import com.thexfactor117.losteclipse.util.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModPackets 
{
	public static void registerPackets()
	{
		LostEclipse.network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
		LostEclipse.network.registerMessage(PacketClassGui.Handler.class, PacketClassGui.class, 0, Side.CLIENT);
		LostEclipse.network.registerMessage(PacketClassSelection.Handler.class, PacketClassSelection.class, 1, Side.SERVER);
		LostEclipse.network.registerMessage(PacketUpdatePlayerInformation.Handler.class, PacketUpdatePlayerInformation.class, 2, Side.CLIENT);
		LostEclipse.network.registerMessage(PacketUpdatePlayerStats.Handler.class, PacketUpdatePlayerStats.class, 3, Side.CLIENT);
		LostEclipse.network.registerMessage(PacketUpdateIncreaseStat.Handler.class, PacketUpdateIncreaseStat.class, 4, Side.SERVER);
		LostEclipse.network.registerMessage(PacketUpdateMana.Handler.class, PacketUpdateMana.class, 5, Side.CLIENT);
	}
}

package com.lsc.init;

import com.lsc.LootSlashConquer;
import com.lsc.network.PacketClassGui;
import com.lsc.network.PacketClassSelection;
import com.lsc.network.PacketUpdateChunkLevel;
import com.lsc.network.PacketUpdateCoreStats;
import com.lsc.network.PacketUpdateIncreaseStat;
import com.lsc.network.PacketUpdatePlayerInformation;
import com.lsc.network.PacketUpdateStats;
import com.lsc.util.Reference;

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
		LootSlashConquer.network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
		LootSlashConquer.network.registerMessage(PacketClassGui.Handler.class, PacketClassGui.class, 0, Side.CLIENT);
		LootSlashConquer.network.registerMessage(PacketClassSelection.Handler.class, PacketClassSelection.class, 1, Side.SERVER);
		LootSlashConquer.network.registerMessage(PacketUpdatePlayerInformation.Handler.class, PacketUpdatePlayerInformation.class, 2, Side.CLIENT);
		LootSlashConquer.network.registerMessage(PacketUpdateCoreStats.Handler.class, PacketUpdateCoreStats.class, 3, Side.CLIENT);
		LootSlashConquer.network.registerMessage(PacketUpdateIncreaseStat.Handler.class, PacketUpdateIncreaseStat.class, 4, Side.SERVER);
		LootSlashConquer.network.registerMessage(PacketUpdateStats.Handler.class, PacketUpdateStats.class, 5, Side.CLIENT);
		LootSlashConquer.network.registerMessage(PacketUpdateChunkLevel.Handler.class, PacketUpdateChunkLevel.class, 6, Side.CLIENT);
	}
}

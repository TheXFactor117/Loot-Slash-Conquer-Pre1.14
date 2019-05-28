package com.thexfactor117.lsc.init;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.network.client.PacketClassGui;
import com.thexfactor117.lsc.network.client.PacketUpdateChunkLevel;
import com.thexfactor117.lsc.network.client.PacketUpdateCoreStats;
import com.thexfactor117.lsc.network.client.PacketUpdateEnemyInfo;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerInformation;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerStats;
import com.thexfactor117.lsc.network.server.PacketClassSelection;
import com.thexfactor117.lsc.network.server.PacketUpdateIncreaseStat;
import com.thexfactor117.lsc.util.misc.Reference;

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
		LootSlashConquer.network.registerMessage(PacketUpdatePlayerStats.Handler.class, PacketUpdatePlayerStats.class, 5, Side.CLIENT);
		LootSlashConquer.network.registerMessage(PacketUpdateChunkLevel.Handler.class, PacketUpdateChunkLevel.class, 6, Side.CLIENT);
		LootSlashConquer.network.registerMessage(PacketUpdateEnemyInfo.Handler.class, PacketUpdateEnemyInfo.class, 7, Side.CLIENT);
	}
}

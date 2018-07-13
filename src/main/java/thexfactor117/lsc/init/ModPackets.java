package thexfactor117.lsc.init;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import thexfactor117.lsc.LootSlashConquer;
import thexfactor117.lsc.network.PacketClassGui;
import thexfactor117.lsc.network.PacketClassSelection;
import thexfactor117.lsc.network.PacketUpdateChunkLevel;
import thexfactor117.lsc.network.PacketUpdateCoreStats;
import thexfactor117.lsc.network.PacketUpdateEnemyInfo;
import thexfactor117.lsc.network.PacketUpdateIncreaseStat;
import thexfactor117.lsc.network.PacketUpdatePlayerInformation;
import thexfactor117.lsc.network.PacketUpdateStats;
import thexfactor117.lsc.util.Reference;

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
		LootSlashConquer.network.registerMessage(PacketUpdateEnemyInfo.Handler.class, PacketUpdateEnemyInfo.class, 7, Side.CLIENT);
	}
}

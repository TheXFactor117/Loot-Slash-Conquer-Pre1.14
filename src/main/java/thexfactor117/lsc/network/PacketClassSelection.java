package thexfactor117.lsc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thexfactor117.lsc.capabilities.cap.CapabilityPlayerInformation;
import thexfactor117.lsc.capabilities.implementation.PlayerInformation;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PacketClassSelection implements IMessage
{
	private int playerClass;
	
	public PacketClassSelection() {}
	
	public PacketClassSelection(int playerClass)
	{
		this.playerClass = playerClass;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		playerClass = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(playerClass);
	}
	
	public static class Handler implements IMessageHandler<PacketClassSelection, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketClassSelection message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					EntityPlayer player = ctx.getServerHandler().player;
					PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
					
					if (playerInfo != null && !player.getEntityWorld().isRemote)
					{
						if (message.playerClass == 1) playerInfo.setPlayerClass(1); // 1 for warrior
						else if (message.playerClass == 2) playerInfo.setPlayerClass(2); // 2 for mage
						else if (message.playerClass == 3) playerInfo.setPlayerClass(3); // 3 for hunter
						
						playerInfo.setPlayerLevel(1);
					}
				}
			});
			
			return null;
		}
	}
}

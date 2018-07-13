package thexfactor117.lsc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thexfactor117.lsc.LootSlashConquer;
import thexfactor117.lsc.capabilities.cap.CapabilityPlayerInformation;
import thexfactor117.lsc.capabilities.implementation.PlayerInformation;
import thexfactor117.lsc.util.GuiHandler;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PacketClassGui implements IMessage
{
	public PacketClassGui() {}

	@Override
	public void fromBytes(ByteBuf buf) 
	{

	}

	@Override
	public void toBytes(ByteBuf buf) 
	{

	}
	
	public static class Handler implements IMessageHandler<PacketClassGui, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketClassGui message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					EntityPlayer player = Minecraft.getMinecraft().player;
					PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
					
					if (playerInfo != null && playerInfo.getPlayerClass() == 0)
					{
						player.openGui(LootSlashConquer.instance, GuiHandler.CLASS_SELECTION, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
					}
				}
			});
			
			return null;
		}
	}
}

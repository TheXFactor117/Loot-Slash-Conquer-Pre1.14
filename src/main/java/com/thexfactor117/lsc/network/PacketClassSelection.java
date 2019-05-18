package com.thexfactor117.lsc.network;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.player.PlayerUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
					LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
					
					if (cap != null && !player.getEntityWorld().isRemote)
					{
						if (message.playerClass == 1) cap.setPlayerClass(1); // 1 for warrior
						else if (message.playerClass == 2) cap.setPlayerClass(2); // 2 for mage
						else if (message.playerClass == 3) cap.setPlayerClass(3); // 3 for hunter
						
						cap.setPlayerLevel(1);
					}
				}
			});
			
			return null;
		}
	}
}

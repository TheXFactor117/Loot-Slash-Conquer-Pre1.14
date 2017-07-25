package com.thexfactor117.losteclipse.network;

import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;
import com.thexfactor117.losteclipse.stats.PlayerStatHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateIncreaseStat implements IMessage
{	
	private int stat;
	
	public PacketUpdateIncreaseStat() {}
	
	public PacketUpdateIncreaseStat(int stat)
	{
		this.stat = stat;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		stat = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(stat);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateIncreaseStat, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateIncreaseStat message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					EntityPlayer player = ctx.getServerHandler().player;
					IPlayerInformation playerInfo = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
					
					if (playerInfo != null && !player.getEntityWorld().isRemote)
					{
						if (message.stat == 1) playerInfo.setStrengthStat(playerInfo.getStrengthStat() + 1);
						else if (message.stat == 2) playerInfo.setAgilityStat(playerInfo.getAgilityStat() + 1);
						else if (message.stat == 3) playerInfo.setDexterityStat(playerInfo.getDexterityStat() + 1);
						else if (message.stat == 4) playerInfo.setIntelligenceStat(playerInfo.getIntelligenceStat() + 1);
						else if (message.stat == 5) playerInfo.setWisdomStat(playerInfo.getWisdomStat() + 1);
						else if (message.stat == 6) playerInfo.setFortitudeStat(playerInfo.getFortitudeStat() + 1);
						
						playerInfo.setSkillPoints(playerInfo.getSkillPoints() - 1);
						
						PlayerStatHelper.updateAttributes(player);
					}
				}
			});
			
			return null;
		}
	}
}

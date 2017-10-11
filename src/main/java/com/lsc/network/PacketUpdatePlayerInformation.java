package com.lsc.network;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.playerinfo.CapabilityPlayerInformation;
import com.lsc.capabilities.playerinfo.PlayerInformation;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PacketUpdatePlayerInformation implements IMessage
{
	private int playerClass;
	private int level;
	private int experience;
	private int skillPoints;
	
	public PacketUpdatePlayerInformation() {}
	
	public PacketUpdatePlayerInformation(PlayerInformation info)
	{
		this.playerClass = info.getPlayerClass();
		this.level = info.getPlayerLevel();
		this.experience = info.getPlayerExperience();
		this.skillPoints = info.getSkillPoints();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		playerClass = buf.readInt();
		level = buf.readInt();
		experience = buf.readInt();
		skillPoints = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(playerClass);
		buf.writeInt(level);
		buf.writeInt(experience);
		buf.writeInt(skillPoints);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdatePlayerInformation, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdatePlayerInformation message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					EntityPlayer player = Minecraft.getMinecraft().player;
					PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
					
					if (playerInfo != null)
					{
						LootSlashConquer.LOGGER.info("setting client-values: " + message.playerClass);
						
						playerInfo.setPlayerClass(message.playerClass);
						LootSlashConquer.LOGGER.info(playerInfo.getPlayerClass());
						playerInfo.setPlayerLevel(message.level);
						playerInfo.setPlayerExperience(message.experience);
						playerInfo.setSkillPoints(message.skillPoints);
					}
				}
			});
			
			return null;
		}
	}
}

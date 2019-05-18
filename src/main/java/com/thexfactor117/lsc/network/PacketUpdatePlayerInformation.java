package com.thexfactor117.lsc.network;

import com.thexfactor117.lsc.capabilities.cap.CapabilityLSCPlayer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;

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
	
	public PacketUpdatePlayerInformation(LSCPlayerCapability playercap)
	{
		this.playerClass = playercap.getPlayerClass();
		this.level = playercap.getPlayerLevel();
		this.experience = playercap.getPlayerExperience();
		this.skillPoints = playercap.getSkillPoints();
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
					LSCPlayerCapability playercap = (LSCPlayerCapability) player.getCapability(CapabilityLSCPlayer.PLAYER_CAP, null);
					
					if (playercap != null)
					{
						playercap.setPlayerClass(message.playerClass);
						playercap.setPlayerLevel(message.level);
						playercap.setPlayerExperience(message.experience);
						playercap.setSkillPoints(message.skillPoints);
					}
				}
			});
			
			return null;
		}
	}
}

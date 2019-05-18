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
public class PacketUpdatePlayerStats implements IMessage
{
	private int maxMana;
	private int mana;
	private int manaPerSecond;
	
	private double magicalPower;
	
	private int healthPerSecond;
	
	private double criticalChance;
	private double criticalDamage;
	
	public PacketUpdatePlayerStats() {}
	
	public PacketUpdatePlayerStats(LSCPlayerCapability playercap)
	{
		this.maxMana = playercap.getMaxMana();
		this.mana = playercap.getMana();
		this.manaPerSecond = playercap.getManaPerSecond();
		
		this.magicalPower = playercap.getMagicalPower();
		
		this.healthPerSecond = playercap.getHealthPerSecond();
		
		this.criticalChance = playercap.getCriticalChance();
		this.criticalDamage = playercap.getCriticalDamage();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		maxMana = buf.readInt();
		mana = buf.readInt();
		manaPerSecond = buf.readInt();
		
		magicalPower = buf.readDouble();
		
		healthPerSecond = buf.readInt();
		
		criticalChance = buf.readDouble();
		criticalDamage = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(maxMana);
		buf.writeInt(mana);
		buf.writeInt(manaPerSecond);
		
		buf.writeDouble(magicalPower);
		
		buf.writeInt(healthPerSecond);
		
		buf.writeDouble(criticalChance);
		buf.writeDouble(criticalDamage);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdatePlayerStats, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdatePlayerStats message, final MessageContext ctx) 
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
						playercap.setMaxMana(message.maxMana);
						playercap.setMana(message.mana);
						playercap.setManaPerSecond(message.manaPerSecond);
						
						playercap.setMagicalPower(message.magicalPower);
						
						playercap.setHealthPerSecond(message.healthPerSecond);
						
						playercap.setCriticalChance(message.criticalChance);
						playercap.setCriticalDamage(message.criticalDamage);
					}
				}
			});
			
			return null;
		}
	}
}

package com.thexfactor117.lsc.network;

import com.thexfactor117.lsc.capabilities.cap.CapabilityPlayerStats;
import com.thexfactor117.lsc.capabilities.implementation.PlayerStats;

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
	
	public PacketUpdatePlayerStats(PlayerStats statsCap)
	{
		this.maxMana = statsCap.getMaxMana();
		this.mana = statsCap.getMana();
		this.manaPerSecond = statsCap.getManaPerSecond();
		
		this.magicalPower = statsCap.getMagicalPower();
		
		this.healthPerSecond = statsCap.getHealthPerSecond();
		
		this.criticalChance = statsCap.getCriticalChance();
		this.criticalDamage = statsCap.getCriticalDamage();
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
					PlayerStats statsCap = (PlayerStats) player.getCapability(CapabilityPlayerStats.PLAYER_STATS, null);
					
					if (statsCap != null)
					{
						statsCap.setMaxMana(message.maxMana);
						statsCap.setMana(message.mana);
						statsCap.setManaPerSecond(message.manaPerSecond);
						
						statsCap.setMagicalPower(message.magicalPower);
						
						statsCap.setHealthPerSecond(message.healthPerSecond);
						
						statsCap.setCriticalChance(message.criticalChance);
						statsCap.setCriticalDamage(message.criticalDamage);
					}
				}
			});
			
			return null;
		}
	}
}

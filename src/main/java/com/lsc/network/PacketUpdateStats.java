package com.lsc.network;

import com.lsc.capabilities.cap.CapabilityPlayerStats;
import com.lsc.capabilities.implementation.Stats;

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
public class PacketUpdateStats implements IMessage
{
	private int mana;
	private int maxMana;
	private int manaPerSecond;
	
	private double magicalPower;
	
	private int healthPerSecond;
	
	private double criticalChance;
	private double criticalDamage;
	
	public PacketUpdateStats() {}
	
	public PacketUpdateStats(Stats statsCap)
	{
		this.mana = statsCap.getMana();
		this.maxMana = statsCap.getMaxMana();
		this.manaPerSecond = statsCap.getManaPerSecond();
		
		this.magicalPower = statsCap.getMagicalPower();
		
		this.healthPerSecond = statsCap.getHealthPerSecond();
		
		this.criticalChance = statsCap.getCriticalChance();
		this.criticalDamage = statsCap.getCriticalDamage();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		mana = buf.readInt();
		maxMana = buf.readInt();
		manaPerSecond = buf.readInt();
		
		magicalPower = buf.readDouble();
		
		healthPerSecond = buf.readInt();
		
		criticalChance = buf.readDouble();
		criticalDamage = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(mana);
		buf.writeInt(maxMana);
		buf.writeInt(manaPerSecond);
		
		buf.writeDouble(magicalPower);
		
		buf.writeInt(healthPerSecond);
		
		buf.writeDouble(criticalChance);
		buf.writeDouble(criticalDamage);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateStats, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateStats message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					EntityPlayer player = Minecraft.getMinecraft().player;
					Stats statsCap = (Stats) player.getCapability(CapabilityPlayerStats.STATS, null);
					
					if (statsCap != null)
					{
						statsCap.setMana(message.mana);
						statsCap.setMaxMana(message.maxMana);
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

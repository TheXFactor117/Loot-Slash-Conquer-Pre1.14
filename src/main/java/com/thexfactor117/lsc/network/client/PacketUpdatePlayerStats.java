package com.thexfactor117.lsc.network.client;

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
	
	private int physicalResistance;
	private int magicalResistance;
	
	private double physicalPower;
	private double rangedPower;
	private double magicalPower;
	
	private double healthPerSecond;
	
	private double criticalChance;
	private double criticalDamage;
	
	public PacketUpdatePlayerStats() {}
	
	public PacketUpdatePlayerStats(LSCPlayerCapability cap)
	{
		this.maxMana = cap.getMaxMana();
		this.mana = cap.getMana();
		this.manaPerSecond = cap.getManaPerSecond();
		
		this.physicalResistance = cap.getPhysicalResistance();
		this.magicalResistance = cap.getMagicalResistance();
		
		this.physicalPower = cap.getPhysicalPower();
		this.rangedPower = cap.getRangedPower();
		this.magicalPower = cap.getMagicalPower();
		
		this.healthPerSecond = cap.getHealthPerSecond();
		
		this.criticalChance = cap.getCriticalChance();
		this.criticalDamage = cap.getCriticalDamage();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		maxMana = buf.readInt();
		mana = buf.readInt();
		manaPerSecond = buf.readInt();
		
		physicalResistance = buf.readInt();
		magicalResistance = buf.readInt();
		
		physicalPower = buf.readDouble();
		rangedPower = buf.readDouble();
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
		
		buf.writeInt(physicalResistance);
		buf.writeInt(magicalResistance);
		
		buf.writeDouble(physicalPower);
		buf.writeDouble(rangedPower);
		buf.writeDouble(magicalPower);
		
		buf.writeDouble(healthPerSecond);
		
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
					assert CapabilityLSCPlayer.PLAYER_CAP != null;
					LSCPlayerCapability cap = (LSCPlayerCapability) player.getCapability(CapabilityLSCPlayer.PLAYER_CAP, null);
					
					if (cap != null)
					{
						cap.setMaxMana(message.maxMana);
						cap.setMana(message.mana);
						cap.setManaPerSecond(message.manaPerSecond);
						
						cap.setPhysicalResistance(message.physicalResistance);
						cap.setMagicalResistance(message.magicalResistance);
						
						cap.setPhysicalPower(message.physicalPower);
						cap.setRangedPower(message.rangedPower);
						cap.setMagicalPower(message.magicalPower);
						
						cap.setHealthPerSecond(message.healthPerSecond);
						
						cap.setCriticalChance(message.criticalChance);
						cap.setCriticalDamage(message.criticalDamage);
					}
				}
			});
			
			return null;
		}
	}
}

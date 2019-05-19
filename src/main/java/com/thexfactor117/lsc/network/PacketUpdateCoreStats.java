package com.thexfactor117.lsc.network;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.player.PlayerStatUtils;
import com.thexfactor117.lsc.util.PlayerUtil;

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
public class PacketUpdateCoreStats implements IMessage
{
	private int strength;
	private int agility;
	private int dexterity;
	private int intelligence;
	private int wisdom;
	private int fortitude;
	
	private int strengthBonus;
	private int agilityBonus;
	private int dexterityBonus;
	private int intelligenceBonus;
	private int wisdomBonus;
	private int fortitudeBonus;
	
	public PacketUpdateCoreStats() {}
	
	public PacketUpdateCoreStats(LSCPlayerCapability cap)
	{	
		this.strength = cap.getStrengthStat();
		this.agility = cap.getAgilityStat();
		this.dexterity = cap.getDexterityStat();
		this.intelligence = cap.getIntelligenceStat();
		this.wisdom = cap.getWisdomStat();
		this.fortitude = cap.getFortitudeStat();
		
		this.strengthBonus = cap.getBonusStrengthStat();
		this.agilityBonus = cap.getBonusAgilityStat();
		this.dexterityBonus = cap.getBonusDexterityStat();
		this.intelligenceBonus = cap.getBonusIntelligenceStat();
		this.wisdomBonus = cap.getBonusWisdomStat();
		this.fortitudeBonus = cap.getBonusFortitudeStat();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		strength = buf.readInt();
		agility = buf.readInt();
		dexterity = buf.readInt();
		intelligence = buf.readInt();
		wisdom = buf.readInt();
		fortitude = buf.readInt();
		
		strengthBonus = buf.readInt();
		agilityBonus = buf.readInt();
		dexterityBonus = buf.readInt();
		intelligenceBonus = buf.readInt();
		wisdomBonus = buf.readInt();
		fortitudeBonus = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(strength);
		buf.writeInt(agility);
		buf.writeInt(dexterity);
		buf.writeInt(intelligence);
		buf.writeInt(wisdom);
		buf.writeInt(fortitude);
		
		buf.writeInt(strengthBonus);
		buf.writeInt(agilityBonus);
		buf.writeInt(dexterityBonus);
		buf.writeInt(intelligenceBonus);
		buf.writeInt(wisdomBonus);
		buf.writeInt(fortitudeBonus);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateCoreStats, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateCoreStats message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					EntityPlayer player = Minecraft.getMinecraft().player;
					LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
					
					if (cap != null)
					{
						cap.setStrengthStat(message.strength);
						cap.setAgilityStat(message.agility);
						cap.setDexterityStat(message.dexterity);
						cap.setIntelligenceStat(message.intelligence);
						cap.setWisdomStat(message.wisdom);
						cap.setFortitudeStat(message.fortitude);
						
						cap.setBonusStrengthStat(message.strengthBonus);
						cap.setBonusAgilityStat(message.agilityBonus);
						cap.setBonusDexterityStat(message.dexterityBonus);
						cap.setBonusIntelligenceStat(message.intelligenceBonus);
						cap.setBonusWisdomStat(message.wisdomBonus);
						cap.setBonusFortitudeStat(message.fortitudeBonus);
						
						PlayerStatUtils.updateAttributes(player);
					}
				}
			});
			
			return null;
		}
	}
}

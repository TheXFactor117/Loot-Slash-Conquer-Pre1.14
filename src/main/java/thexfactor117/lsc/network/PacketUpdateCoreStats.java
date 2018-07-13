package thexfactor117.lsc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thexfactor117.lsc.capabilities.cap.CapabilityPlayerInformation;
import thexfactor117.lsc.capabilities.implementation.PlayerInformation;
import thexfactor117.lsc.player.PlayerStatUtils;

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
	
	public PacketUpdateCoreStats(PlayerInformation info)
	{	
		this.strength = info.getStrengthStat();
		this.agility = info.getAgilityStat();
		this.dexterity = info.getDexterityStat();
		this.intelligence = info.getIntelligenceStat();
		this.wisdom = info.getWisdomStat();
		this.fortitude = info.getFortitudeStat();
		
		this.strengthBonus = info.getBonusStrengthStat();
		this.agilityBonus = info.getBonusAgilityStat();
		this.dexterityBonus = info.getBonusDexterityStat();
		this.intelligenceBonus = info.getBonusIntelligenceStat();
		this.wisdomBonus = info.getBonusWisdomStat();
		this.fortitudeBonus = info.getBonusFortitudeStat();
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
					PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
					
					if (playerInfo != null)
					{
						playerInfo.setStrengthStat(message.strength);
						playerInfo.setAgilityStat(message.agility);
						playerInfo.setDexterityStat(message.dexterity);
						playerInfo.setIntelligenceStat(message.intelligence);
						playerInfo.setWisdomStat(message.wisdom);
						playerInfo.setFortitudeStat(message.fortitude);
						
						playerInfo.setBonusStrengthStat(message.strengthBonus);
						playerInfo.setBonusAgilityStat(message.agilityBonus);
						playerInfo.setBonusDexterityStat(message.dexterityBonus);
						playerInfo.setBonusIntelligenceStat(message.intelligenceBonus);
						playerInfo.setBonusWisdomStat(message.wisdomBonus);
						playerInfo.setBonusFortitudeStat(message.fortitudeBonus);
						
						PlayerStatUtils.updateAttributes(player);
					}
				}
			});
			
			return null;
		}
	}
}

package com.thexfactor117.losteclipse.network;

import java.util.UUID;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateIncreaseStat implements IMessage
{
	public static final String STRENGTH = "ece5e91f-afc9-4883-a169-c7b5883c12dc";
	private static final String AGILITY = "50ac5b8b-00a6-436c-bdc3-9848393bb7b7";
	private static final String DEXTERITY = "e574e861-e5bd-4906-b72e-f6be6e4c9563";
	private static final String INTELLIGENCE = "8c865de8-f16e-43ea-a69f-8e77edd7a11c";
	private static final String WISDOM = "e89c87ff-3404-4aba-884d-3b93b6872e40";
	private static final String FORTITUDE = "e3762718-bbd8-4763-bfe9-1d18d70eaa76";
	
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
						if (message.stat == 1) 
						{
							playerInfo.setStrengthStat(playerInfo.getStrengthStat() + 1);
							AttributeModifier strengthModifier = new AttributeModifier(UUID.fromString(STRENGTH), "playerStrength", 2 + (playerInfo.getStrengthStat() + playerInfo.getBonusStrengthStat()), 0);

							if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(UUID.fromString(STRENGTH)) != null)
							{
								player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(UUID.fromString(STRENGTH));
								player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthModifier);
							}
							else
								player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthModifier);
						}
						else if (message.stat == 2) 
						{
							playerInfo.setAgilityStat(playerInfo.getAgilityStat() + 1);
							AttributeModifier agilityModifier = new AttributeModifier(UUID.fromString(AGILITY), "playerAgility", 0.05 * (playerInfo.getAgilityStat() + playerInfo.getBonusAgilityStat()), 0);
							
							if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(UUID.fromString(AGILITY)) != null)
							{
								player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString(AGILITY));
								player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityModifier);
							}
							else
								player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityModifier);
						}
						else if (message.stat == 3) 
						{
							playerInfo.setDexterityStat(playerInfo.getDexterityStat() + 1);
							AttributeModifier dexterityStat = new AttributeModifier(UUID.fromString(DEXTERITY), "playerDexterity", 0.5 * (playerInfo.getDexterityStat() + playerInfo.getBonusDexterityStat()), 1);
							
							if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getModifier(UUID.fromString(DEXTERITY)) != null)
							{
								player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(UUID.fromString(DEXTERITY));
								player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(dexterityStat);
							}
							else
								player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(dexterityStat);
						}
						else if (message.stat == 4) 
						{
							playerInfo.setIntelligenceStat(playerInfo.getIntelligenceStat() + 1);
						}
						else if (message.stat == 5) 
						{
							playerInfo.setWisdomStat(playerInfo.getWisdomStat() + 1);
						}
						else if (message.stat == 6) 
						{
							playerInfo.setFortitudeStat(playerInfo.getFortitudeStat() + 1);
							AttributeModifier healthModifier = new AttributeModifier(UUID.fromString(FORTITUDE), "maxHealth", 0.5 * (playerInfo.getFortitudeStat() + playerInfo.getBonusFortitudeStat()), 1);
							
							if (player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(UUID.fromString(FORTITUDE)) != null)
							{
								player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(UUID.fromString(FORTITUDE));
								player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(healthModifier);
							}
							else
								player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(healthModifier);
						}
						
						playerInfo.setSkillPoints(playerInfo.getSkillPoints() - 1);
					}
				}
			});
			
			return null;
		}
	}
}

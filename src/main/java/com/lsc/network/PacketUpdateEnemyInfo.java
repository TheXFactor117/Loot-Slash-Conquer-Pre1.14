package com.lsc.network;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.lsc.capabilities.implementation.EnemyInfo;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author TheXFactor117
 *
 */
public class PacketUpdateEnemyInfo implements IMessage
{
	private int level;
	private int tier;
	private int entityID;
	
	public PacketUpdateEnemyInfo() {}
	
	public PacketUpdateEnemyInfo(EnemyInfo info, int entityID)
	{
		this.level = info.getEnemyLevel();
		this.tier = info.getEnemyTier();
		this.entityID = entityID;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		level = buf.readInt();
		tier = buf.readInt();
		entityID = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(level);
		buf.writeInt(tier);
		buf.writeInt(entityID);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateEnemyInfo, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateEnemyInfo message, final MessageContext ctx)
		{
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run()
				{
					Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
					LootSlashConquer.LOGGER.info("Clientside entity..." + entity);
					
					if (entity != null)
					{
						EnemyInfo enemyInfo = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
						
						if (enemyInfo != null)
						{
							LootSlashConquer.LOGGER.info("Updating enemy info clientside...");
							enemyInfo.setEnemyLevel(message.level);
							enemyInfo.setEnemyTier(message.tier);
						}
					}
				}
			});
			
			return null;
		}
	}
}

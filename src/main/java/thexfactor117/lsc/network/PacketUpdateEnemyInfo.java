package thexfactor117.lsc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import thexfactor117.lsc.capabilities.implementation.EnemyInfo;

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
					
					if (entity != null)
					{
						EnemyInfo enemyInfo = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
						
						if (enemyInfo != null)
						{
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

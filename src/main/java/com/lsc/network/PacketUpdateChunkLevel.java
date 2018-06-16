package com.lsc.network;

import com.lsc.capabilities.api.IChunkLevel;
import com.lsc.capabilities.api.IChunkLevelHolder;
import com.lsc.capabilities.cap.CapabilityChunkLevel;
import com.lsc.capabilities.implementation.ChunkLevel;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PacketUpdateChunkLevel implements IMessage
{
	private int chunkX;
	private int chunkZ;
	private int level;
	
	public PacketUpdateChunkLevel() {}
	
	public PacketUpdateChunkLevel(int chunkX, int chunkZ, int level)
	{
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.level = level;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		chunkX = buf.readInt();
		chunkZ = buf.readInt();
		level = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(chunkX);
		buf.writeInt(chunkZ);
		buf.writeInt(level);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateChunkLevel, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateChunkLevel message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					World world = Minecraft.getMinecraft().world;
					ChunkPos chunkPos = new ChunkPos(message.chunkX, message.chunkZ);
					
					IChunkLevelHolder chunkLevelHolder = CapabilityChunkLevel.getChunkLevelHolder(world);
					
					if (chunkLevelHolder.getChunkLevel(chunkPos) != null) return;
					
					IChunkLevel chunkLevel = new ChunkLevel(world, chunkPos, message.level);
					chunkLevelHolder.setChunkLevel(chunkPos, chunkLevel);
				}
			});
			
			return null;
		}
	}
}

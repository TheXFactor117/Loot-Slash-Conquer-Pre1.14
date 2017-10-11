package com.lsc.capabilities.chunk;

import javax.annotation.Nullable;

import com.lsc.LootSlashConquer;
import com.lsc.network.PacketUpdateChunkLevel;
import com.lsc.util.CapabilityUtils;
import com.lsc.util.Reference;
import com.lsc.util.SimpleCapabilityProvider;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class CapabilityChunkLevel 
{
	@CapabilityInject(IChunkLevelHolder.class)
	public static final Capability<IChunkLevelHolder> CHUNK_LEVEL = null;
	public static final EnumFacing DEFAULT_FACING = null;
	private static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "ChunkLevel");
	
	public static void register() 
	{
		CapabilityManager.INSTANCE.register(IChunkLevelHolder.class, new Capability.IStorage<IChunkLevelHolder>() 
		{
			@Override
			public NBTBase writeNBT(final Capability<IChunkLevelHolder> capability, final IChunkLevelHolder instance, final EnumFacing side) 
			{
				return new NBTTagCompound();
			}

			@Override
			public void readNBT(final Capability<IChunkLevelHolder> capability, final IChunkLevelHolder instance, final EnumFacing side, final NBTBase nbt) 
			{

			}
		}, ChunkLevelHolder::new);
		
		//MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	@Nullable
	public static IChunkLevelHolder getChunkLevelHolder(final World world) 
	{
		return CapabilityUtils.getCapability(world, CHUNK_LEVEL, DEFAULT_FACING);
	}
	
	@Nullable
	public static IChunkLevel getChunkLevel(final World world, final ChunkPos chunkPos) 
	{
		final IChunkLevelHolder chunkLevelHolder = getChunkLevelHolder(world);
		if (chunkLevelHolder == null) return null;

		return chunkLevelHolder.getChunkLevel(chunkPos);
	}
	
	@Nullable
	public static IChunkLevel getChunkLevel(final Chunk chunk) 
	{
		return getChunkLevel(chunk.getWorld(), chunk.getPos());
	}
	
	public static ICapabilityProvider createProvider(IChunkLevelHolder chunkLevelHolder) 
	{
		return new SimpleCapabilityProvider<>(CHUNK_LEVEL, DEFAULT_FACING, chunkLevelHolder);
	}
	
	@Mod.EventBusSubscriber
	private static class EventHandler
	{
		@SubscribeEvent
		public static void attachCapabilities(final AttachCapabilitiesEvent<World> event) 
		{
			final IChunkLevelHolder chunkLevelHolder = new ChunkLevelHolder();
			event.addCapability(ID, createProvider(chunkLevelHolder));
		}
		
		@SubscribeEvent
		public static void onChunkLoad(ChunkDataEvent.Load event)
		{
			World world = event.getWorld();
			ChunkPos chunkPos = event.getChunk().getPos();
			
			IChunkLevelHolder chunkLevelHolder = getChunkLevelHolder(world);
			ChunkLevel chunkLevel = new ChunkLevel(world, chunkPos, getAreaLevel(world, chunkPos));
			
			NBTTagCompound nbt = event.getData();
			
			if (nbt.hasKey(ID.toString(), Constants.NBT.TAG_INT))
			{
				NBTTagInt levelTag = (NBTTagInt) nbt.getTag(ID.toString());
				chunkLevel.deserializeNBT(levelTag);
			}
			
			chunkLevelHolder.setChunkLevel(null, chunkLevel);
		}
		
		@SubscribeEvent
		public static void onChunkLoad(ChunkEvent.Load event)
		{
			World world = event.getWorld();
			ChunkPos chunkPos = event.getChunk().getPos();
			
			IChunkLevelHolder chunkLevelHolder = getChunkLevelHolder(world);
			
			if (chunkLevelHolder.getChunkLevel(chunkPos) != null) return;
			
			IChunkLevel chunkLevel = new ChunkLevel(world, chunkPos, getAreaLevel(world, chunkPos));
			chunkLevelHolder.setChunkLevel(chunkPos, chunkLevel);
		}
		
		@SubscribeEvent
		public static void onChunkSave(ChunkDataEvent.Save event)
		{
			IChunkLevel chunkLevel = getChunkLevel(event.getChunk());
			
			if (!(chunkLevel instanceof ChunkLevel)) return;
			
			event.getData().setTag(ID.toString(), ((ChunkLevel) chunkLevel).serializeNBT());
		}
		
		@SubscribeEvent
		public static void onChunkUnload(ChunkEvent.Unload event)
		{
			IChunkLevelHolder chunkLevelHolder = getChunkLevelHolder(event.getWorld());
			
			chunkLevelHolder.removeChunkLevel(event.getChunk().getPos());
		}
		
		@SubscribeEvent
		public static void onChunkWatch(ChunkWatchEvent.Watch event)
		{
			IChunkLevel chunkLevel = getChunkLevel(event.getPlayer().getEntityWorld(), event.getChunk());
			
			LootSlashConquer.network.sendTo(new PacketUpdateChunkLevel(chunkLevel.getChunkPos().x, chunkLevel.getChunkPos().z, chunkLevel.getChunkLevel()), event.getPlayer());
		}
		
		private static int getAreaLevel(World world, ChunkPos pos)
		{	
			ChunkPos spawnChunk = new ChunkPos(world.getSpawnPoint());
			
			double distance = Math.sqrt(Math.pow(pos.x - spawnChunk.x, 2) + Math.pow(pos.z - spawnChunk.z, 2));
			return (int) (distance / 10 + 1);
		}
	}
}

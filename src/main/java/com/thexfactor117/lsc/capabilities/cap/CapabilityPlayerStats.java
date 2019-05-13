package com.thexfactor117.lsc.capabilities.cap;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.api.IPlayerStats;
import com.thexfactor117.lsc.capabilities.implementation.PlayerStats;
import com.thexfactor117.lsc.network.PacketUpdatePlayerStats;
import com.thexfactor117.lsc.util.CapabilityUtils;
import com.thexfactor117.lsc.util.Reference;
import com.thexfactor117.lsc.util.SimpleCapabilityProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class CapabilityPlayerStats 
{
	@CapabilityInject(IPlayerStats.class)
	public static final Capability<IPlayerStats> PLAYER_STATS = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "PlayerStats");
	
	public static void register() 
	{
		CapabilityManager.INSTANCE.register(IPlayerStats.class, new Capability.IStorage<IPlayerStats>() 
		{
			@Override
			public NBTBase writeNBT(Capability<IPlayerStats> capability, IPlayerStats instance, EnumFacing side) 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				nbt.setInteger("MaxMana", instance.getMaxMana());
				nbt.setInteger("Mana", instance.getMana());
				nbt.setInteger("ManaPerSecond", instance.getManaPerSecond());
				
				nbt.setDouble("MagicalPower", instance.getMagicalPower());
				
				nbt.setInteger("HealthPerSecond", instance.getHealthPerSecond());
				
				nbt.setDouble("CriticalChance", instance.getCriticalChance());
				nbt.setDouble("CriticalDamage", instance.getCriticalDamage());
				
				nbt.setInteger("UpdateTicks", instance.getUpdateTicks());
				nbt.setInteger("RegenTicks", instance.getRegenTicks());
				
				return nbt;
			}

			@Override
			public void readNBT(Capability<IPlayerStats> capability, IPlayerStats instance, EnumFacing side, NBTBase nbt) 
			{
				NBTTagCompound compound = (NBTTagCompound) nbt;
				
				instance.setMaxMana(compound.getInteger("MaxMana"));
				instance.setMana(compound.getInteger("Mana"));
				instance.setManaPerSecond(compound.getInteger("ManaPerSecond"));
				
				instance.setMagicalPower(compound.getDouble("MagicalPower"));
				
				instance.setHealthPerSecond(compound.getInteger("HealthPerSecond"));
				
				instance.setCriticalChance(compound.getDouble("CriticalChance"));
				instance.setCriticalDamage(compound.getDouble("CriticalDamage"));
				
				instance.setUpdateTicks(compound.getInteger("UpdateTicks"));
				instance.setUpdateTicks(compound.getInteger("RegenTicks"));
			}
		}, () -> new PlayerStats(null));
	}
	
	@Nullable
	public static IPlayerStats getStats(EntityLivingBase entity) 
	{
		return CapabilityUtils.getCapability(entity, PLAYER_STATS, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(IPlayerStats stats) 
	{
		return new SimpleCapabilityProvider<>(PLAYER_STATS, DEFAULT_FACING, stats);
	}
	
	@Mod.EventBusSubscriber
	public static class EventHandler 
	{
		@SubscribeEvent
		public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof EntityPlayer) 
			{
				final PlayerStats stats = new PlayerStats((EntityPlayer) event.getObject());
				
				event.addCapability(ID, createProvider(stats));
			}
		}
		
		@SubscribeEvent
		public static void playerClone(PlayerEvent.Clone event) 
		{
			IPlayerStats oldStats = getStats(event.getOriginal());
			IPlayerStats newStats = getStats(event.getEntityLiving());

			if (newStats != null && oldStats != null)
			{
				newStats.setMaxMana(oldStats.getMaxMana());
				newStats.setMana(oldStats.getMana());
				newStats.setManaPerSecond(oldStats.getManaPerSecond());
				
				newStats.setMagicalPower(oldStats.getMagicalPower());
				
				newStats.setHealthPerSecond(oldStats.getHealthPerSecond());
				
				newStats.setCriticalChance(oldStats.getCriticalChance());
				newStats.setCriticalDamage(oldStats.getCriticalDamage());
				
				newStats.setUpdateTicks(oldStats.getUpdateTicks());
				newStats.setRegenTicks(oldStats.getRegenTicks());
			}
		}
		
		@SubscribeEvent
		public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event)
		{
			EntityPlayer player = event.player;
			PlayerStats playerstats = (PlayerStats) player.getCapability(CapabilityPlayerStats.PLAYER_STATS, null);
			
			if (playerstats != null)
			{
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(playerstats), (EntityPlayerMP) player);
			}
		}
	}
}

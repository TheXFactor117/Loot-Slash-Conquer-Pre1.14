package com.lsc.capabilities.cap;

import javax.annotation.Nullable;

import com.lsc.capabilities.api.IStats;
import com.lsc.capabilities.implementation.Stats;
import com.lsc.util.CapabilityUtils;
import com.lsc.util.Reference;
import com.lsc.util.SimpleCapabilityProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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

/**
 * 
 * @author TheXFactor117
 *
 */
public class CapabilityPlayerStats 
{
	@CapabilityInject(IStats.class)
	public static final Capability<IStats> STATS = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "Stats");
	
	public static void register() 
	{
		CapabilityManager.INSTANCE.register(IStats.class, new Capability.IStorage<IStats>() 
		{
			@Override
			public NBTBase writeNBT(Capability<IStats> capability, IStats instance, EnumFacing side) 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				nbt.setInteger("Mana", instance.getMana());
				nbt.setInteger("MaxMana", instance.getMaxMana());
				nbt.setInteger("ManaPerSecond", instance.getManaPerSecond());
				
				nbt.setDouble("MagicalPower", instance.getMagicalPower());
				
				nbt.setInteger("HealthPerSecond", instance.getHealthPerSecond());
				
				nbt.setDouble("CriticalChance", instance.getCriticalChance());
				nbt.setDouble("CriticalDamage", instance.getCriticalDamage());
				
				return nbt;
			}

			@Override
			public void readNBT(Capability<IStats> capability, IStats instance, EnumFacing side, NBTBase nbt) 
			{
				NBTTagCompound compound = (NBTTagCompound) nbt;
				
				instance.setMana(compound.getInteger("Mana"));
				instance.setMaxMana(compound.getInteger("MaxMana"));
				instance.setManaPerSecond(compound.getInteger("ManaPerSecond"));
				
				instance.setMagicalPower(compound.getDouble("MagicalPower"));
				
				instance.setHealthPerSecond(compound.getInteger("HealthPerSecond"));
				
				instance.setCriticalChance(compound.getDouble("CriticalChance"));
				instance.setCriticalDamage(compound.getDouble("CriticalDamage"));
			}
		}, () -> new Stats(null));
	}
	
	@Nullable
	public static IStats getStats(EntityLivingBase entity) 
	{
		return CapabilityUtils.getCapability(entity, STATS, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(IStats stats) 
	{
		return new SimpleCapabilityProvider<>(STATS, DEFAULT_FACING, stats);
	}
	
	@Mod.EventBusSubscriber
	public static class EventHandler 
	{
		@SubscribeEvent
		public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof EntityPlayer) 
			{
				final Stats stats = new Stats((EntityPlayer) event.getObject());
				
				event.addCapability(ID, createProvider(stats));
			}
		}
		
		@SubscribeEvent
		public static void playerClone(PlayerEvent.Clone event) 
		{
			IStats oldStats = getStats(event.getOriginal());
			IStats newStats = getStats(event.getEntityLiving());

			if (newStats != null && oldStats != null)
			{
				newStats.setMana(oldStats.getMana());
				newStats.setMaxMana(oldStats.getMaxMana());
				newStats.setManaPerSecond(oldStats.getManaPerSecond());
				
				newStats.setMagicalPower(oldStats.getMagicalPower());
				
				newStats.setHealthPerSecond(oldStats.getHealthPerSecond());
				
				newStats.setCriticalChance(oldStats.getCriticalChance());
				newStats.setCriticalDamage(oldStats.getCriticalDamage());
			}
		}
	}
}

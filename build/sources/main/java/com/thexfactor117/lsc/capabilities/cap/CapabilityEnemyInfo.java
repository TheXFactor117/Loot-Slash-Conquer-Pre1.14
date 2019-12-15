package com.thexfactor117.lsc.capabilities.cap;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.capabilities.api.IEnemyInfo;
import com.thexfactor117.lsc.capabilities.implementation.EnemyInfo;
import com.thexfactor117.lsc.util.CapabilityUtil;
import com.thexfactor117.lsc.util.misc.Reference;
import com.thexfactor117.lsc.util.misc.SimpleCapabilityProvider;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class CapabilityEnemyInfo 
{
	@CapabilityInject(IEnemyInfo.class)
	public static final Capability<IEnemyInfo> ENEMY_INFO = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "EnemyInfo");
	
	public static void register() 
	{
		CapabilityManager.INSTANCE.register(IEnemyInfo.class, new Capability.IStorage<IEnemyInfo>() 
		{
			@Override
			public NBTBase writeNBT(Capability<IEnemyInfo> capability, IEnemyInfo instance, EnumFacing side) 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				nbt.setInteger("EnemyLevel", instance.getEnemyLevel());
				nbt.setInteger("EnemyTier", instance.getEnemyTier());
				
				return nbt;
			}

			@Override
			public void readNBT(Capability<IEnemyInfo> capability, IEnemyInfo instance, EnumFacing side, NBTBase nbt) 
			{
				NBTTagCompound compound = (NBTTagCompound) nbt;
				
				instance.setEnemyLevel(compound.getInteger("EnemyLevel"));
				instance.setEnemyTier(compound.getInteger("EnemyTier"));
			}
		}, () -> new EnemyInfo(null));
	}
	
	@Nullable
	public static IEnemyInfo getEnemyLevel(Entity entity)
	{
		return CapabilityUtil.getCapability(entity, ENEMY_INFO, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(IEnemyInfo level) 
	{
		return new SimpleCapabilityProvider<>(ENEMY_INFO, DEFAULT_FACING, level);
	}
	
	@Mod.EventBusSubscriber
	public static class EventHandler 
	{
		@SubscribeEvent
		public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof Entity)
			{
				final EnemyInfo enemyLevel = new EnemyInfo((Entity) event.getObject());
				event.addCapability(ID, createProvider(enemyLevel));
			}
		}
	}
}

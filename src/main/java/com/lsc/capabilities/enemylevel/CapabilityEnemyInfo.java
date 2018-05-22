package com.lsc.capabilities.enemylevel;

import javax.annotation.Nullable;

import com.lsc.util.CapabilityUtils;
import com.lsc.util.Reference;
import com.lsc.util.SimpleCapabilityProvider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	@Nullable
	public static IEnemyInfo getEnemyLevel(EntityLivingBase entity) 
	{
		return CapabilityUtils.getCapability(entity, ENEMY_INFO, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(IEnemyInfo level) 
	{
		return new SimpleCapabilityProvider<>(ENEMY_INFO, DEFAULT_FACING, level);
	}
	
	public static class EventHandler 
	{
		@SubscribeEvent
		public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof EntityMob) 
			{
				final EnemyInfo enemyLevel = new EnemyInfo((EntityMob) event.getObject());
				event.addCapability(ID, createProvider(enemyLevel));
			}
		}
	}
}

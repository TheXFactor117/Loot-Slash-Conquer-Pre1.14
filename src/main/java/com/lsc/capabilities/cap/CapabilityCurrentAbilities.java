package com.lsc.capabilities.cap;

import javax.annotation.Nullable;

import com.lsc.capabilities.api.ICurrentAbilities;
import com.lsc.capabilities.implementation.CurrentAbilities;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class CapabilityCurrentAbilities 
{
	@CapabilityInject(ICurrentAbilities.class)
	public static final Capability<ICurrentAbilities> CURRENT_ABILITIES = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "CurrentAbilities");
	
	public static void register() 
	{
		CapabilityManager.INSTANCE.register(ICurrentAbilities.class, new Capability.IStorage<ICurrentAbilities>() 
		{
			@Override
			public NBTBase writeNBT(Capability<ICurrentAbilities> capability, ICurrentAbilities instance, EnumFacing side) 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				nbt.setInteger("Slot1Ability", instance.getAbilityFromSlot(1));
				nbt.setInteger("Slot2Ability", instance.getAbilityFromSlot(2));
				nbt.setInteger("Slot3Ability", instance.getAbilityFromSlot(3));
				nbt.setInteger("UltimateAbility", instance.getAbilityFromSlot(4));
				
				return nbt;
			}

			@Override
			public void readNBT(Capability<ICurrentAbilities> capability, ICurrentAbilities instance, EnumFacing side, NBTBase nbt) 
			{
				NBTTagCompound compound = (NBTTagCompound) nbt;

				instance.setAbilityInSlot(1, compound.getInteger("Slot1Ability"));
				instance.setAbilityInSlot(2, compound.getInteger("Slot2Ability"));
				instance.setAbilityInSlot(3, compound.getInteger("Slot3Ability"));
				instance.setAbilityInSlot(4, compound.getInteger("UltimateAbility"));
			}
		}, () -> new CurrentAbilities(null));

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	@Nullable
	public static ICurrentAbilities getCurrentAbilities(EntityLivingBase entity) 
	{
		return CapabilityUtils.getCapability(entity, CURRENT_ABILITIES, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(ICurrentAbilities currentAbilities) 
	{
		return new SimpleCapabilityProvider<>(CURRENT_ABILITIES, DEFAULT_FACING, currentAbilities);
	}
	
	public static class EventHandler 
	{
		@SubscribeEvent
		public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof EntityPlayer) 
			{
				final CurrentAbilities currentAbilities = new CurrentAbilities((EntityPlayer) event.getObject());
				
				event.addCapability(ID, createProvider(currentAbilities));
			}
		}
		
		@SubscribeEvent
		public void playerClone(PlayerEvent.Clone event) 
		{
			ICurrentAbilities oldAbilities = getCurrentAbilities(event.getOriginal());
			ICurrentAbilities newAbilities = getCurrentAbilities(event.getEntityLiving());

			if (newAbilities != null && oldAbilities != null)
			{
				newAbilities.setAbilityInSlot(1, oldAbilities.getAbilityFromSlot(1));
				newAbilities.setAbilityInSlot(2, oldAbilities.getAbilityFromSlot(2));
				newAbilities.setAbilityInSlot(3, oldAbilities.getAbilityFromSlot(3));
				newAbilities.setAbilityInSlot(4, oldAbilities.getAbilityFromSlot(4));
			}
		}
	}
}

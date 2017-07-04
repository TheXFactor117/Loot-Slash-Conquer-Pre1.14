package com.thexfactor117.hsm2.capabilities;

import javax.annotation.Nullable;

import com.thexfactor117.hsm2.util.CapabilityUtils;
import com.thexfactor117.hsm2.util.Reference;
import com.thexfactor117.hsm2.util.SimpleCapabilityProvider;

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
public class CapabilityPlayerInformation 
{
	@CapabilityInject(IPlayerInformation.class)
	public static final Capability<IPlayerInformation> PLAYER_INFORMATION = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "PlayerInformation");
	
	public static void register() 
	{
		CapabilityManager.INSTANCE.register(IPlayerInformation.class, new Capability.IStorage<IPlayerInformation>() 
		{
			@Override
			public NBTBase writeNBT(Capability<IPlayerInformation> capability, IPlayerInformation instance, EnumFacing side) 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				nbt.setString("PlayerClass", instance.getPlayerClass());
				nbt.setInteger("PlayerLevel", instance.getPlayerLevel());
				nbt.setInteger("PlayerExperience", instance.getPlayerExperience());
				nbt.setInteger("PlayerSkillPoints", instance.getSkillPoints());
				
				// stats
				nbt.setInteger("StrengthStat", instance.getStrengthStat());
				nbt.setInteger("AgilityStat", instance.getAgilityStat());
				nbt.setInteger("DexterityStat", instance.getDexterityStat());
				nbt.setInteger("IntelligenceStat", instance.getIntelligenceStat());
				nbt.setInteger("WisdomStat", instance.getWisdomStat());
				nbt.setInteger("FortitudeStat", instance.getFortitudeStat());
				
				return nbt;
			}

			@Override
			public void readNBT(Capability<IPlayerInformation> capability, IPlayerInformation instance, EnumFacing side, NBTBase nbt) 
			{
				NBTTagCompound compound = (NBTTagCompound) nbt;
				
				instance.setPlayerClass(compound.getString("PlayerClass"));
				instance.setPlayerLevel(compound.getInteger("PlayerLevel"));
				instance.setPlayerExperience(compound.getInteger("PlayerExperience"));
				instance.setSkillPoints(compound.getInteger("PlayerSkillPoints"));
				
				// stats
				instance.setStrengthStat(compound.getInteger("StrengthStat"));
				instance.setAgilityStat(compound.getInteger("AgilityStat"));
				instance.setDexterityStat(compound.getInteger("DexterityStat"));
				instance.setIntelligenceStat(compound.getInteger("IntelligenceStat"));
				instance.setWisdomStat(compound.getInteger("WisdomStat"));
				instance.setFortitudeStat(compound.getInteger("FortitudeStat"));
			}
		}, () -> new PlayerInformation(null));

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	@Nullable
	public static IPlayerInformation getCharacterLevel(EntityLivingBase entity) 
	{
		return CapabilityUtils.getCapability(entity, PLAYER_INFORMATION, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(IPlayerInformation characterLevel) 
	{
		return new SimpleCapabilityProvider<>(PLAYER_INFORMATION, DEFAULT_FACING, characterLevel);
	}
	
	public static class EventHandler 
	{
		@SubscribeEvent
		public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof EntityPlayer) 
			{
				final PlayerInformation playerInfo = new PlayerInformation((EntityPlayer) event.getObject());
				
				playerInfo.setPlayerClass("default");
				playerInfo.setPlayerLevel(1);
				
				event.addCapability(ID, createProvider(playerInfo));
			}
		}
		
		@SubscribeEvent
		public void playerClone(PlayerEvent.Clone event) 
		{
			IPlayerInformation oldInfo = getCharacterLevel(event.getOriginal());
			IPlayerInformation newInfo = getCharacterLevel(event.getEntityLiving());

			if (newInfo != null && oldInfo != null)
			{
				newInfo.setPlayerClass(oldInfo.getPlayerClass());
				newInfo.setPlayerLevel(oldInfo.getPlayerLevel());
				newInfo.setPlayerExperience(oldInfo.getPlayerExperience());
				newInfo.setSkillPoints(oldInfo.getSkillPoints());
				
				// stats
				newInfo.setStrengthStat(oldInfo.getStrengthStat());
				newInfo.setAgilityStat(oldInfo.getAgilityStat());
				newInfo.setDexterityStat(oldInfo.getDexterityStat());
				newInfo.setIntelligenceStat(oldInfo.getIntelligenceStat());
				newInfo.setWisdomStat(oldInfo.getWisdomStat());
				newInfo.setFortitudeStat(oldInfo.getFortitudeStat());
			}
		}
	}
}

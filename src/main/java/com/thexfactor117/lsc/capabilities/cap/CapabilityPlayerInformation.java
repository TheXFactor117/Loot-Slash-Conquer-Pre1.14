package com.thexfactor117.lsc.capabilities.cap;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.api.IPlayerInformation;
import com.thexfactor117.lsc.capabilities.implementation.PlayerInformation;
import com.thexfactor117.lsc.capabilities.implementation.Stats;
import com.thexfactor117.lsc.network.PacketUpdatePlayerInformation;
import com.thexfactor117.lsc.network.PacketUpdateStats;
import com.thexfactor117.lsc.player.PlayerStatUtils;
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
				
				nbt.setInteger("PlayerClass", instance.getPlayerClass());
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
				
				nbt.setInteger("StrengthBonusStat", instance.getBonusStrengthStat());
				nbt.setInteger("AgilityBonusStat", instance.getBonusAgilityStat());
				nbt.setInteger("DexterityBonusStat", instance.getBonusDexterityStat());
				nbt.setInteger("IntelligenceBonusStat", instance.getBonusIntelligenceStat());
				nbt.setInteger("WisdomBonusStat", instance.getBonusWisdomStat());
				nbt.setInteger("FortitudeBonusStat", instance.getBonusFortitudeStat());
				
				return nbt;
			}

			@Override
			public void readNBT(Capability<IPlayerInformation> capability, IPlayerInformation instance, EnumFacing side, NBTBase nbt) 
			{
				NBTTagCompound compound = (NBTTagCompound) nbt;
				
				instance.setPlayerClass(compound.getInteger("PlayerClass"));
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
				
				instance.setBonusStrengthStat(compound.getInteger("StrengthBonusStat"));
				instance.setBonusAgilityStat(compound.getInteger("AgilityBonusStat"));
				instance.setBonusDexterityStat(compound.getInteger("DexterityBonusStat"));
				instance.setBonusIntelligenceStat(compound.getInteger("IntelligenceBonusStat"));
				instance.setBonusWisdomStat(compound.getInteger("WisdomBonusStat"));
				instance.setBonusFortitudeStat(compound.getInteger("FortitudeBonusStat"));
			}
		}, () -> new PlayerInformation(null));
	}
	
	@Nullable
	public static IPlayerInformation getPlayerInformation(EntityLivingBase entity) 
	{
		return CapabilityUtils.getCapability(entity, PLAYER_INFORMATION, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(IPlayerInformation playerInfo)
	{
		return new SimpleCapabilityProvider<>(PLAYER_INFORMATION, DEFAULT_FACING, playerInfo);
	}
	
	@Mod.EventBusSubscriber
	public static class EventHandler 
	{
		@SubscribeEvent
		public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof EntityPlayer) 
			{
				final PlayerInformation playerInfo = new PlayerInformation((EntityPlayer) event.getObject());
				
				event.addCapability(ID, createProvider(playerInfo));
			}
		}
		
		// handles synchronizing data when the player dies and respawns (or comes back via the end gateway)
		@SubscribeEvent
		public static void playerClone(PlayerEvent.Clone event) 
		{
			IPlayerInformation oldInfo = getPlayerInformation(event.getOriginal());
			IPlayerInformation newInfo = getPlayerInformation(event.getEntityLiving());
			
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
				
				newInfo.setBonusStrengthStat(oldInfo.getBonusStrengthStat());
				newInfo.setBonusAgilityStat(oldInfo.getBonusAgilityStat());
				newInfo.setBonusDexterityStat(oldInfo.getBonusDexterityStat());
				newInfo.setBonusIntelligenceStat(oldInfo.getBonusIntelligenceStat());
				newInfo.setBonusWisdomStat(oldInfo.getBonusWisdomStat());
				newInfo.setBonusFortitudeStat(oldInfo.getBonusFortitudeStat());

				LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation((PlayerInformation) newInfo), (EntityPlayerMP) event.getEntityLiving());
			}
		}
		
		@SubscribeEvent
		public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event)
		{
			EntityPlayer player = event.player;
			PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (playerInfo != null)
			{
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) player);
			}
		}
		
		@SubscribeEvent
		public static void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
		{
			PlayerInformation playerInfo = (PlayerInformation) event.player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			Stats statsCap = (Stats) event.player.getCapability(CapabilityPlayerStats.STATS, null);
			
			if (playerInfo != null && statsCap != null)
			{
				statsCap.setMana(statsCap.getMaxMana());
				
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) event.player);
				LootSlashConquer.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) event.player);
				PlayerStatUtils.updateAttributes(event.player);
				
				event.player.setHealth(event.player.getMaxHealth());
			}
		}
	}
}

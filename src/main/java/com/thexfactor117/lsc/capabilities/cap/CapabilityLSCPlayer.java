package com.thexfactor117.lsc.capabilities.cap;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.api.ILSCPlayer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerInformation;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerStats;
import com.thexfactor117.lsc.util.CapabilityUtil;
import com.thexfactor117.lsc.util.PlayerUtil;
import com.thexfactor117.lsc.util.misc.Reference;
import com.thexfactor117.lsc.util.misc.SimpleCapabilityProvider;

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
public class CapabilityLSCPlayer
{
	@CapabilityInject(ILSCPlayer.class)
	public static final Capability<ILSCPlayer> PLAYER_CAP = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MODID, "LSCPlayerCap");
	
	public static void register() 
	{
		CapabilityManager.INSTANCE.register(ILSCPlayer.class, new Capability.IStorage<ILSCPlayer>() 
		{
			@Override
			public NBTBase writeNBT(Capability<ILSCPlayer> capability, ILSCPlayer instance, EnumFacing side) 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				// basic info
				nbt.setInteger("PlayerClass", instance.getPlayerClass());
				nbt.setInteger("PlayerLevel", instance.getPlayerLevel());
				nbt.setInteger("PlayerExperience", instance.getPlayerExperience());
				nbt.setInteger("PlayerSkillPoints", instance.getSkillPoints());
				
				// modifiers
				nbt.setDouble("PhysicalPower", instance.getPhysicalPower());
				nbt.setDouble("RangedPower", instance.getRangedPower());
				nbt.setDouble("MagicalPower", instance.getMagicalPower());
				
				nbt.setInteger("PhysicalResistance", instance.getPhysicalResistance());
				nbt.setInteger("MagicalResistance", instance.getMagicalResistance());
				
				nbt.setInteger("FireResistance", instance.getFireResistance());
				nbt.setInteger("FrostResistance", instance.getFrostResistance());
				nbt.setInteger("LightningResistance", instance.getLightningResistance());
				nbt.setInteger("PoisonResistance", instance.getPoisonResistance());
				
				nbt.setInteger("MaxMana", instance.getMaxMana());
				nbt.setInteger("Mana", instance.getMana());
				nbt.setInteger("ManaPerSecond", instance.getManaPerSecond());
				
				nbt.setDouble("HealthPerSecond", instance.getHealthPerSecond());
				
				nbt.setDouble("CriticalChance", instance.getCriticalChance());
				nbt.setDouble("CriticalDamage", instance.getCriticalDamage());
				
				nbt.setDouble("CooldownReduction", instance.getCooldownReduction());
				
				nbt.setInteger("UpdateTicks", instance.getUpdateTicks());
				nbt.setInteger("RegenTicks", instance.getRegenTicks());
				
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
			public void readNBT(Capability<ILSCPlayer> capability, ILSCPlayer instance, EnumFacing side, NBTBase nbt) 
			{
				NBTTagCompound compound = (NBTTagCompound) nbt;
				
				// basic info
				instance.setPlayerClass(compound.getInteger("PlayerClass"));
				instance.setPlayerLevel(compound.getInteger("PlayerLevel"));
				instance.setPlayerExperience(compound.getInteger("PlayerExperience"));
				instance.setSkillPoints(compound.getInteger("PlayerSkillPoints"));
				
				// modifiers
				instance.setPhysicalPower(compound.getDouble("PhysicalPower"));
				instance.setRangedPower(compound.getDouble("RangedPower"));
				instance.setMagicalPower(compound.getDouble("MagicalPower"));
				
				instance.setPhysicalResistance(compound.getInteger("PhysicalResistance"));
				instance.setMagicalResistance(compound.getInteger("MagicalResistance"));
				
				instance.setFireResistance(compound.getInteger("FireResistance"));
				instance.setFrostResistance(compound.getInteger("FrostResistance"));
				instance.setLightningResistance(compound.getInteger("LightningResistance"));
				instance.setPoisonResistance(compound.getInteger("PoisonResistance"));
				
				instance.setMaxMana(compound.getInteger("MaxMana"));
				instance.setMana(compound.getInteger("Mana"));
				instance.setManaPerSecond(compound.getInteger("ManaPerSecond"));
				
				instance.setHealthPerSecond(compound.getInteger("HealthPerSecond"));
				
				instance.setCriticalChance(compound.getDouble("CriticalChance"));
				instance.setCriticalDamage(compound.getDouble("CriticalDamage"));
				
				instance.setCooldownReduction(compound.getDouble("CooldownReduction"));
				
				instance.setUpdateTicks(compound.getInteger("UpdateTicks"));
				instance.setUpdateTicks(compound.getInteger("RegenTicks"));
				
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
		}, () -> new LSCPlayerCapability(null));
	}
	
	@Nullable
	public static ILSCPlayer getPlayerCapability(EntityLivingBase entity) 
	{
		return CapabilityUtil.getCapability(entity, PLAYER_CAP, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(ILSCPlayer playercap) 
	{
		return new SimpleCapabilityProvider<>(PLAYER_CAP, DEFAULT_FACING, playercap);
	}
	
	@Mod.EventBusSubscriber
	public static class EventHandler 
	{
		@SubscribeEvent
		public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) 
		{
			if (event.getObject() instanceof EntityPlayer) 
			{
				final LSCPlayerCapability playercap = new LSCPlayerCapability((EntityPlayer) event.getObject());
				
				event.addCapability(ID, createProvider(playercap));
			}
		}
		
		@SubscribeEvent
		public static void playerClone(PlayerEvent.Clone event) 
		{
			ILSCPlayer oldCap = getPlayerCapability(event.getOriginal());
			ILSCPlayer newCap = getPlayerCapability(event.getEntityLiving());

			if (newCap != null && oldCap != null)
			{
				// basic info
				newCap.setPlayerClass(oldCap.getPlayerClass());
				newCap.setPlayerLevel(oldCap.getPlayerLevel());
				newCap.setPlayerExperience(oldCap.getPlayerExperience());
				newCap.setSkillPoints(oldCap.getSkillPoints());
				
				// modifiers
				newCap.setPhysicalPower(oldCap.getPhysicalPower());
				newCap.setRangedPower(oldCap.getRangedPower());
				newCap.setMagicalPower(oldCap.getMagicalPower());
				
				newCap.setPhysicalResistance(oldCap.getPhysicalResistance());
				newCap.setMagicalResistance(oldCap.getMagicalResistance());
				
				newCap.setFireResistance(oldCap.getFireResistance());
				newCap.setFrostResistance(oldCap.getFrostResistance());
				newCap.setLightningResistance(oldCap.getLightningResistance());
				newCap.setPoisonResistance(oldCap.getPoisonResistance());
				
				newCap.setMaxMana(oldCap.getMaxMana());
				newCap.setMana(oldCap.getMana());
				newCap.setManaPerSecond(oldCap.getManaPerSecond());
				
				newCap.setHealthPerSecond(oldCap.getHealthPerSecond());
				
				newCap.setCriticalChance(oldCap.getCriticalChance());
				newCap.setCriticalDamage(oldCap.getCriticalDamage());
				
				newCap.setCooldownReduction(oldCap.getCooldownReduction());
				
				newCap.setUpdateTicks(oldCap.getUpdateTicks());
				newCap.setRegenTicks(oldCap.getRegenTicks());
				
				// stats
				newCap.setStrengthStat(oldCap.getStrengthStat());
				newCap.setAgilityStat(oldCap.getAgilityStat());
				newCap.setDexterityStat(oldCap.getDexterityStat());
				newCap.setIntelligenceStat(oldCap.getIntelligenceStat());
				newCap.setWisdomStat(oldCap.getWisdomStat());
				newCap.setFortitudeStat(oldCap.getFortitudeStat());
				
				newCap.setBonusStrengthStat(oldCap.getBonusStrengthStat());
				newCap.setBonusAgilityStat(oldCap.getBonusAgilityStat());
				newCap.setBonusDexterityStat(oldCap.getBonusDexterityStat());
				newCap.setBonusIntelligenceStat(oldCap.getBonusIntelligenceStat());
				newCap.setBonusWisdomStat(oldCap.getBonusWisdomStat());
				newCap.setBonusFortitudeStat(oldCap.getBonusFortitudeStat());
			}
		}
		
		@SubscribeEvent
		public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event)
		{
			EntityPlayer player = event.player;
			LSCPlayerCapability playercap = (LSCPlayerCapability) player.getCapability(CapabilityLSCPlayer.PLAYER_CAP, null);
			
			if (playercap != null)
			{
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playercap), (EntityPlayerMP) player);
			}
		}
		
		@SubscribeEvent
		public static void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
		{
			LSCPlayerCapability playercap = (LSCPlayerCapability) event.player.getCapability(CapabilityLSCPlayer.PLAYER_CAP, null);
			
			if (playercap != null)
			{
				playercap.setMana(playercap.getMaxMana());
				
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playercap), (EntityPlayerMP) event.player);
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(playercap), (EntityPlayerMP) event.player);
				PlayerUtil.updateAllStats(event.player);
				
				event.player.setHealth(event.player.getMaxHealth());
			}
		}
	}
}

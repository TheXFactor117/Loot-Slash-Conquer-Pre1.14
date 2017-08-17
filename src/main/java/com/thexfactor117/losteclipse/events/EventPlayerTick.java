package com.thexfactor117.losteclipse.events;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerinfo.PlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerstats.CapabilityPlayerStats;
import com.thexfactor117.losteclipse.capabilities.playerstats.Stats;
import com.thexfactor117.losteclipse.items.jewelry.ItemLEBauble;
import com.thexfactor117.losteclipse.items.magical.ItemLEMagical;
import com.thexfactor117.losteclipse.network.PacketUpdateCoreStats;
import com.thexfactor117.losteclipse.network.PacketUpdateStats;
import com.thexfactor117.losteclipse.stats.PlayerStatHelper;
import com.thexfactor117.losteclipse.stats.attributes.ArmorAttribute;
import com.thexfactor117.losteclipse.stats.attributes.JewelryAttribute;
import com.thexfactor117.losteclipse.stats.attributes.WeaponAttribute;
import com.thexfactor117.losteclipse.util.NBTHelper;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventPlayerTick 
{
	private int ticks;
	private int regenTicks;
	
	private ItemStack mainhand;
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	
	/* Called ever second to check for slots and update stat bonuses. Might need to be re-worked. */
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START && !event.player.getEntityWorld().isRemote)
		{
			if (ticks % 10 == 0)
			{
				PlayerInformation playerInfo = (PlayerInformation) event.player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
				
				if (playerInfo != null)
				{	
					if (event.player.inventory.getCurrentItem() != mainhand && playerInfo.getPlayerLevel() >= NBTHelper.loadStackNBT(event.player.inventory.getCurrentItem()).getInteger("Level"))
					{
						updateStats(event.player, playerInfo);
						mainhand = event.player.inventory.getCurrentItem();
					}
					
					for (ItemStack stack : event.player.inventory.armorInventory)
					{
						if (stack.getItem() instanceof ItemArmor && playerInfo.getPlayerLevel() >= NBTHelper.loadStackNBT(stack).getInteger("Level"))
						{
							if (((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.HEAD && stack != helmet)
							{
								updateStats(event.player, playerInfo);
								helmet = stack;
							}
							else if (((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.CHEST && stack != chestplate)
							{
								updateStats(event.player, playerInfo);
								chestplate = stack;
							}
							else if (((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.LEGS && stack != leggings)
							{
								updateStats(event.player, playerInfo);
								leggings = stack;
							}
							else if (((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.FEET && stack != boots)
							{
								updateStats(event.player, playerInfo);
								boots = stack;
							}
						}
					}
				}
				
				ticks = 0;
			}
			
			if (regenTicks % 100 == 0)
			{
				Stats statsCap = (Stats) event.player.getCapability(CapabilityPlayerStats.STATS, null);
				
				if (statsCap != null)
				{
					if (statsCap.getMana() < statsCap.getMaxMana())
					{
						statsCap.increaseMana(statsCap.getManaPerSecond());
					}
					
					if (event.player.getHealth() < event.player.getMaxHealth())
					{
						event.player.heal(statsCap.getHealthPerSecond());
					}
					
					LostEclipse.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) event.player);
				}
				
				regenTicks = 0;
			}
			
			ticks++;
			regenTicks++;
		}
	}
	
	public static void updateStats(EntityPlayer player, PlayerInformation info)
	{	
		info.removeBonusStats();
		
		// current weapon
		if (player.inventory.getCurrentItem().getItem() instanceof ItemSword || player.inventory.getCurrentItem().getItem() instanceof ItemLEMagical)
		{
			NBTTagCompound nbt = NBTHelper.loadStackNBT(player.inventory.getCurrentItem());
			
			if (WeaponAttribute.STRENGTH.hasAttribute(nbt)) info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) WeaponAttribute.STRENGTH.getAmount(nbt));
			if (WeaponAttribute.AGILITY.hasAttribute(nbt)) info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) WeaponAttribute.AGILITY.getAmount(nbt));
			if (WeaponAttribute.DEXTERITY.hasAttribute(nbt)) info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) WeaponAttribute.DEXTERITY.getAmount(nbt));
			if (WeaponAttribute.INTELLIGENCE.hasAttribute(nbt)) info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) WeaponAttribute.INTELLIGENCE.getAmount(nbt));
			if (WeaponAttribute.WISDOM.hasAttribute(nbt)) info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) WeaponAttribute.WISDOM.getAmount(nbt));
			if (WeaponAttribute.FORTITUDE.hasAttribute(nbt)) info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) WeaponAttribute.FORTITUDE.getAmount(nbt));
			if (WeaponAttribute.ALL_STATS.hasAttribute(nbt))
			{
				info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) WeaponAttribute.ALL_STATS.getAmount(nbt));
				info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) WeaponAttribute.ALL_STATS.getAmount(nbt));
				info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) WeaponAttribute.ALL_STATS.getAmount(nbt));
				info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) WeaponAttribute.ALL_STATS.getAmount(nbt));
				info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) WeaponAttribute.ALL_STATS.getAmount(nbt));
				info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) WeaponAttribute.ALL_STATS.getAmount(nbt));
			}
		}
		
		for (ItemStack stack : player.inventory.armorInventory)
		{
			if (stack.getItem() instanceof ItemArmor)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (ArmorAttribute.STRENGTH.hasAttribute(nbt)) info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) ArmorAttribute.STRENGTH.getAmount(nbt));
				if (ArmorAttribute.AGILITY.hasAttribute(nbt)) info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) ArmorAttribute.AGILITY.getAmount(nbt));
				if (ArmorAttribute.DEXTERITY.hasAttribute(nbt)) info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) ArmorAttribute.DEXTERITY.getAmount(nbt));
				if (ArmorAttribute.INTELLIGENCE.hasAttribute(nbt)) info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) ArmorAttribute.INTELLIGENCE.getAmount(nbt));
				if (ArmorAttribute.WISDOM.hasAttribute(nbt)) info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) ArmorAttribute.WISDOM.getAmount(nbt));
				if (ArmorAttribute.FORTITUDE.hasAttribute(nbt)) info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) ArmorAttribute.FORTITUDE.getAmount(nbt));
				if (ArmorAttribute.ALL_STATS.hasAttribute(nbt))
				{
					info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) ArmorAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) ArmorAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) ArmorAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) ArmorAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) ArmorAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) ArmorAttribute.ALL_STATS.getAmount(nbt));
				}
			}
		}
		
		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		
		for (int i = 0; i < baubles.getSlots(); i++)
		{
			if (baubles.getStackInSlot(i).getItem() instanceof ItemLEBauble)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(baubles.getStackInSlot(i));
				
				if (JewelryAttribute.STRENGTH.hasAttribute(nbt)) info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) JewelryAttribute.STRENGTH.getAmount(nbt));
				if (JewelryAttribute.AGILITY.hasAttribute(nbt)) info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) JewelryAttribute.AGILITY.getAmount(nbt));
				if (JewelryAttribute.DEXTERITY.hasAttribute(nbt)) info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) JewelryAttribute.DEXTERITY.getAmount(nbt));
				if (JewelryAttribute.INTELLIGENCE.hasAttribute(nbt)) info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) JewelryAttribute.INTELLIGENCE.getAmount(nbt));
				if (JewelryAttribute.WISDOM.hasAttribute(nbt)) info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) JewelryAttribute.WISDOM.getAmount(nbt));
				if (JewelryAttribute.FORTITUDE.hasAttribute(nbt)) info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) JewelryAttribute.FORTITUDE.getAmount(nbt));
				if (JewelryAttribute.ALL_STATS.hasAttribute(nbt))
				{
					info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) JewelryAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) JewelryAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) JewelryAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) JewelryAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) JewelryAttribute.ALL_STATS.getAmount(nbt));
					info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) JewelryAttribute.ALL_STATS.getAmount(nbt));
				}
			}
		}
		
		PlayerStatHelper.updateAttributes(player);
		
		LostEclipse.network.sendTo(new PacketUpdateCoreStats(info), (EntityPlayerMP) player);
	}
}

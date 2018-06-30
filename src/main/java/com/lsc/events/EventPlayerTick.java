package com.lsc.events;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.cap.CapabilityPlayerStats;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.capabilities.implementation.Stats;
import com.lsc.items.base.ItemBauble;
import com.lsc.items.base.ItemMagical;
import com.lsc.loot.ArmorAttribute;
import com.lsc.loot.JewelryAttribute;
import com.lsc.loot.WeaponAttribute;
import com.lsc.network.PacketUpdateCoreStats;
import com.lsc.network.PacketUpdateStats;
import com.lsc.player.PlayerStatUtils;
import com.lsc.util.NBTHelper;

import baubles.api.BaublesApi;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventPlayerTick 
{
	private static int ticks;
	private static int regenTicks;
	
	/* Called ever second to check for slots and update stat bonuses. Might need to be re-worked. */
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START && !event.player.getEntityWorld().isRemote)
		{
			ticks++;
			regenTicks++;
			
			if (ticks % 10 == 0)
			{
				PlayerInformation playerInfo = (PlayerInformation) event.player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
				
				if (event.player != null && playerInfo != null)
				{	
					updateStats(event.player, playerInfo, 4);
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
					
					LootSlashConquer.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) event.player);
				}
				
				regenTicks = 0;
			}
		}
	}
	
	/**
	 * Updates bonus stats.
	 * 
	 * Flag 1 = update mainhand only
	 * Flag 2 = update armor only
	 * Flag 3 = update baubles only
	 * Flag 4 = update all
	 * 
	 * @param player
	 * @param info
	 * @param flag
	 */
	public static void updateStats(EntityPlayer player, PlayerInformation info, int flag)
	{	
		info.removeBonusStats();
		
		if (flag == 1 || flag == 4)
		{
			// current weapon
			if (player.inventory.getCurrentItem().getItem() != null && (player.inventory.getCurrentItem().getItem() instanceof ItemSword || player.inventory.getCurrentItem().getItem() instanceof ItemMagical))
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
		}
		
		if (flag == 2 || flag == 4)
		{
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
		}
		
		if (flag == 3 || flag == 4)
		{
			if (player.hasCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null))
			{
				IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
				
				for (int i = 0; i < baubles.getSlots(); i++)
				{
					if (baubles.getStackInSlot(i).getItem() instanceof ItemBauble)
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
			}
		}
		
		PlayerStatUtils.updateAttributes(player);
		
		LootSlashConquer.network.sendTo(new PacketUpdateCoreStats(info), (EntityPlayerMP) player);
	}
}

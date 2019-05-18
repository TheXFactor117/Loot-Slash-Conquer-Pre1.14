package com.thexfactor117.lsc.events;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.loot.Attribute;
import com.thexfactor117.lsc.network.PacketUpdateCoreStats;
import com.thexfactor117.lsc.network.PacketUpdatePlayerStats;
import com.thexfactor117.lsc.player.PlayerStatUtils;
import com.thexfactor117.lsc.player.PlayerUtil;
import com.thexfactor117.lsc.util.NBTHelper;

import baubles.api.BaublesApi;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
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
	/* Called ever second to check for slots and update stat bonuses. Might need to be re-worked. */
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START && !event.player.getEntityWorld().isRemote)
		{
			LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(event.player);
			
			if (cap != null)
			{
				cap.incrementUpdateTicks();
				cap.incrementRegenTicks();
				
				// TODO: possibly optimize this? 3 packets get sent every second
				if (cap.getUpdateTicks() % Configs.ticksPerStatUpdate == 0)
				{	
					updateStats(event.player, cap, 3);
					
					cap.resetUpdateTicks();
				}
				
				if (cap.getRegenTicks() % 100 == 0)
				{	
					if (cap.getMana() < cap.getMaxMana())
					{
						cap.increaseMana(cap.getManaPerSecond());
					}
					
					if (event.player.getHealth() < event.player.getMaxHealth())
					{
						event.player.heal(cap.getHealthPerSecond());
					}
					
					LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) event.player);
					
					cap.resetRegenTicks();
				}
			}
		}
	}
	
	/**
	 * Updates bonus stats.
	 * 
	 * Flag 1 = update armor only
	 * Flag 2 = update baubles only
	 * Flag 3 = update all
	 * 
	 * @param player
	 * @param cap
	 * @param flag
	 */
	public static void updateStats(EntityPlayer player, LSCPlayerCapability cap, int flag)
	{	
		cap.removeBonusStats();
		
		if (flag == 1 || flag == 3)
		{
			for (ItemStack stack : player.inventory.armorInventory)
			{
				if (stack.getItem() instanceof ItemArmor)
				{
					NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
					
					if (Attribute.STRENGTH.hasAttribute(nbt)) cap.setBonusStrengthStat(cap.getBonusStrengthStat() + (int) Attribute.STRENGTH.getAmount(nbt));
					if (Attribute.AGILITY.hasAttribute(nbt)) cap.setBonusAgilityStat(cap.getBonusAgilityStat() + (int) Attribute.AGILITY.getAmount(nbt));
					if (Attribute.DEXTERITY.hasAttribute(nbt)) cap.setBonusDexterityStat(cap.getBonusDexterityStat() + (int) Attribute.DEXTERITY.getAmount(nbt));
					if (Attribute.INTELLIGENCE.hasAttribute(nbt)) cap.setBonusIntelligenceStat(cap.getBonusIntelligenceStat() + (int) Attribute.INTELLIGENCE.getAmount(nbt));
					if (Attribute.WISDOM.hasAttribute(nbt)) cap.setBonusWisdomStat(cap.getBonusWisdomStat() + (int) Attribute.WISDOM.getAmount(nbt));
					if (Attribute.FORTITUDE.hasAttribute(nbt)) cap.setBonusFortitudeStat(cap.getBonusFortitudeStat() + (int) Attribute.FORTITUDE.getAmount(nbt));
					if (Attribute.ALL_STATS.hasAttribute(nbt))
					{
						cap.setBonusStrengthStat(cap.getBonusStrengthStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
						cap.setBonusAgilityStat(cap.getBonusAgilityStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
						cap.setBonusDexterityStat(cap.getBonusDexterityStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
						cap.setBonusIntelligenceStat(cap.getBonusIntelligenceStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
						cap.setBonusWisdomStat(cap.getBonusWisdomStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
						cap.setBonusFortitudeStat(cap.getBonusFortitudeStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
					}
				}
			}
		}
		
		if (flag == 2 || flag == 3)
		{
			if (player.hasCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null))
			{
				IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
				
				for (int i = 0; i < baubles.getSlots(); i++)
				{
					if (baubles.getStackInSlot(i).getItem() instanceof ItemBauble)
					{
						NBTTagCompound nbt = NBTHelper.loadStackNBT(baubles.getStackInSlot(i));
						
						if (Attribute.STRENGTH.hasAttribute(nbt)) cap.setBonusStrengthStat(cap.getBonusStrengthStat() + (int) Attribute.STRENGTH.getAmount(nbt));
						if (Attribute.AGILITY.hasAttribute(nbt)) cap.setBonusAgilityStat(cap.getBonusAgilityStat() + (int) Attribute.AGILITY.getAmount(nbt));
						if (Attribute.DEXTERITY.hasAttribute(nbt)) cap.setBonusDexterityStat(cap.getBonusDexterityStat() + (int) Attribute.DEXTERITY.getAmount(nbt));
						if (Attribute.INTELLIGENCE.hasAttribute(nbt)) cap.setBonusIntelligenceStat(cap.getBonusIntelligenceStat() + (int) Attribute.INTELLIGENCE.getAmount(nbt));
						if (Attribute.WISDOM.hasAttribute(nbt)) cap.setBonusWisdomStat(cap.getBonusWisdomStat() + (int) Attribute.WISDOM.getAmount(nbt));
						if (Attribute.FORTITUDE.hasAttribute(nbt)) cap.setBonusFortitudeStat(cap.getBonusFortitudeStat() + (int) Attribute.FORTITUDE.getAmount(nbt));
						if (Attribute.ALL_STATS.hasAttribute(nbt))
						{
							cap.setBonusStrengthStat(cap.getBonusStrengthStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
							cap.setBonusAgilityStat(cap.getBonusAgilityStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
							cap.setBonusDexterityStat(cap.getBonusDexterityStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
							cap.setBonusIntelligenceStat(cap.getBonusIntelligenceStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
							cap.setBonusWisdomStat(cap.getBonusWisdomStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
							cap.setBonusFortitudeStat(cap.getBonusFortitudeStat() + (int) Attribute.ALL_STATS.getAmount(nbt));
						}
					}
				}
			}
		}
		
		PlayerStatUtils.updateAttributes(player);
		
		LootSlashConquer.network.sendTo(new PacketUpdateCoreStats(cap), (EntityPlayerMP) player);
	}
}

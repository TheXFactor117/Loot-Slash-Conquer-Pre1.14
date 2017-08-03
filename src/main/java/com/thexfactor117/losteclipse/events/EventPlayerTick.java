package com.thexfactor117.losteclipse.events;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.api.IPlayerInformation;
import com.thexfactor117.losteclipse.network.PacketUpdateCoreStats;
import com.thexfactor117.losteclipse.stats.PlayerStatHelper;
import com.thexfactor117.losteclipse.stats.weapons.ArmorAttribute;
import com.thexfactor117.losteclipse.stats.weapons.WeaponAttribute;
import com.thexfactor117.losteclipse.util.NBTHelper;

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
			if (ticks % 20 == 0)
			{
				IPlayerInformation playerInfo = event.player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
				
				if (playerInfo != null)
				{	
					if (event.player.inventory.getCurrentItem() != mainhand)
					{
						updateStats(event.player, playerInfo);
						mainhand = event.player.inventory.getCurrentItem();
					}
					
					for (ItemStack stack : event.player.inventory.armorInventory)
					{
						if (stack.getItem() instanceof ItemArmor)
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
			
			ticks++;
		}
	}
	
	public static void updateStats(EntityPlayer player, IPlayerInformation info)
	{	
		info.removeBonusStats();
		
		if (player.inventory.getCurrentItem().getItem() instanceof ItemSword)
		{
			NBTTagCompound nbt = NBTHelper.loadStackNBT(player.inventory.getCurrentItem());
			
			if (WeaponAttribute.STRENGTH.hasAttribute(nbt)) info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) WeaponAttribute.STRENGTH.getAmount(nbt));
			if (WeaponAttribute.AGILITY.hasAttribute(nbt)) info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) WeaponAttribute.AGILITY.getAmount(nbt));
			if (WeaponAttribute.DEXTERITY.hasAttribute(nbt)) info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) WeaponAttribute.DEXTERITY.getAmount(nbt));
			if (WeaponAttribute.INTELLIGENCE.hasAttribute(nbt)) info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) WeaponAttribute.INTELLIGENCE.getAmount(nbt));
			if (WeaponAttribute.WISDOM.hasAttribute(nbt)) info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) WeaponAttribute.WISDOM.getAmount(nbt));
			if (WeaponAttribute.FORTITUDE.hasAttribute(nbt)) info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) WeaponAttribute.FORTITUDE.getAmount(nbt));
		}
		
		for (ItemStack stack : player.inventory.armorInventory)
		{
			NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
			
			if (ArmorAttribute.STRENGTH.hasAttribute(nbt)) info.setBonusStrengthStat(info.getBonusStrengthStat() + (int) ArmorAttribute.STRENGTH.getAmount(nbt));
			if (ArmorAttribute.AGILITY.hasAttribute(nbt)) info.setBonusAgilityStat(info.getBonusAgilityStat() + (int) ArmorAttribute.AGILITY.getAmount(nbt));
			if (ArmorAttribute.DEXTERITY.hasAttribute(nbt)) info.setBonusDexterityStat(info.getBonusDexterityStat() + (int) ArmorAttribute.DEXTERITY.getAmount(nbt));
			if (ArmorAttribute.INTELLIGENCE.hasAttribute(nbt)) info.setBonusIntelligenceStat(info.getBonusIntelligenceStat() + (int) ArmorAttribute.INTELLIGENCE.getAmount(nbt));
			if (ArmorAttribute.WISDOM.hasAttribute(nbt)) info.setBonusWisdomStat(info.getBonusWisdomStat() + (int) ArmorAttribute.WISDOM.getAmount(nbt));
			if (ArmorAttribute.FORTITUDE.hasAttribute(nbt)) info.setBonusFortitudeStat(info.getBonusFortitudeStat() + (int) ArmorAttribute.FORTITUDE.getAmount(nbt));
		}
		
		PlayerStatHelper.updateAttributes(player);
		
		LostEclipse.network.sendTo(new PacketUpdateCoreStats(info), (EntityPlayerMP) player);
	}
}

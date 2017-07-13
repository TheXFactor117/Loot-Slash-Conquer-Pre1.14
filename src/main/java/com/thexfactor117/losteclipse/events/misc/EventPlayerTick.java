package com.thexfactor117.losteclipse.events.misc;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;
import com.thexfactor117.losteclipse.network.PacketUpdatePlayerStats;
import com.thexfactor117.losteclipse.stats.weapons.ArmorAttribute;
import com.thexfactor117.losteclipse.stats.weapons.WeaponAttribute;
import com.thexfactor117.losteclipse.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START && !event.player.getEntityWorld().isRemote)
		{
			IPlayerInformation playerInfo = event.player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (event.player.inventory.getCurrentItem().getItem() instanceof ItemSword && playerInfo != null)
			{	
				updateStats(event.player, playerInfo, event.player.inventory.getCurrentItem());		
			}
		}
	}
	
	private void updateStats(EntityPlayer player, IPlayerInformation info, ItemStack stack)
	{
		info.removeBonusStats();
		
		if (stack.getItem() instanceof ItemSword)
		{
			NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
			
			if (WeaponAttribute.STRENGTH.hasAttribute(nbt)) info.setBonusStrengthStat((int) WeaponAttribute.STRENGTH.getAmount(nbt));
			if (WeaponAttribute.AGILITY.hasAttribute(nbt)) info.setBonusAgilityStat((int) WeaponAttribute.AGILITY.getAmount(nbt));
			if (WeaponAttribute.DEXTERITY.hasAttribute(nbt)) info.setBonusDexterityStat((int) WeaponAttribute.DEXTERITY.getAmount(nbt));
			if (WeaponAttribute.INTELLIGENCE.hasAttribute(nbt)) info.setBonusIntelligenceStat((int) WeaponAttribute.INTELLIGENCE.getAmount(nbt));
			if (WeaponAttribute.WISDOM.hasAttribute(nbt)) info.setBonusWisdomStat((int) WeaponAttribute.WISDOM.getAmount(nbt));
			if (WeaponAttribute.FORTITUDE.hasAttribute(nbt)) info.setBonusFortitudeStat((int) WeaponAttribute.FORTITUDE.getAmount(nbt));
		}
		else if (stack.getItem() instanceof ItemArmor)
		{
			NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
			
			if (ArmorAttribute.STRENGTH.hasAttribute(nbt)) info.setBonusStrengthStat((int) ArmorAttribute.STRENGTH.getAmount(nbt));
			if (ArmorAttribute.AGILITY.hasAttribute(nbt)) info.setBonusAgilityStat((int) ArmorAttribute.AGILITY.getAmount(nbt));
			if (ArmorAttribute.DEXTERITY.hasAttribute(nbt)) info.setBonusDexterityStat((int) ArmorAttribute.DEXTERITY.getAmount(nbt));
			if (ArmorAttribute.INTELLIGENCE.hasAttribute(nbt)) info.setBonusIntelligenceStat((int) ArmorAttribute.INTELLIGENCE.getAmount(nbt));
			if (ArmorAttribute.WISDOM.hasAttribute(nbt)) info.setBonusWisdomStat((int) ArmorAttribute.WISDOM.getAmount(nbt));
			if (ArmorAttribute.FORTITUDE.hasAttribute(nbt)) info.setBonusFortitudeStat((int) ArmorAttribute.FORTITUDE.getAmount(nbt));
		}
		
		LostEclipse.network.sendTo(new PacketUpdatePlayerStats(info), (EntityPlayerMP) player);
	}
}

package com.thexfactor117.losteclipse.items.jewelry;

import com.thexfactor117.losteclipse.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerinfo.PlayerInformation;
import com.thexfactor117.losteclipse.events.EventPlayerTick;
import com.thexfactor117.losteclipse.init.ModTabs;
import com.thexfactor117.losteclipse.util.NBTHelper;
import com.thexfactor117.losteclipse.util.Reference;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemLEBauble extends Item implements IBauble
{
	private BaubleType type;
	
	public ItemLEBauble(String name, BaubleType type)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModTabs.tabLE);
		this.setMaxStackSize(1);
		this.type = type;
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack stack) 
	{
		return type;
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase entity) 
	{
		if (entity.getEntityWorld().isRemote) entity.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.1F, 1.3f);
		else
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;
				PlayerInformation info = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
				
				if (info != null && info.getPlayerLevel() >= NBTHelper.loadStackNBT(stack).getInteger("Level"))
				{
					EventPlayerTick.updateStats(player, info);
				}
				else player.sendMessage(new TextComponentString(TextFormatting.RED + "WARNING: You are using a high-leveled item. It will be useless and will not provide any bonuses."));
			}
		}
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase entity) 
	{
		if (!entity.getEntityWorld().isRemote)
		{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;
				PlayerInformation info = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
				
				if (info != null)
				{
					EventPlayerTick.updateStats(player, info);
				}
			}
		}
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {}
	
	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase entity) 
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase entity) 
	{
		return true;
	}
}

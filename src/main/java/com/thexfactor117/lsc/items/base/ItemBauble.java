package com.thexfactor117.lsc.items.base;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.init.ModTabs;
import com.thexfactor117.lsc.loot.attributes.AttributeBase;
import com.thexfactor117.lsc.loot.attributes.AttributeBaseArmor;
import com.thexfactor117.lsc.util.ItemUtil;
import com.thexfactor117.lsc.util.PlayerUtil;
import com.thexfactor117.lsc.util.misc.NBTHelper;
import com.thexfactor117.lsc.util.misc.Reference;

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
public class ItemBauble extends Item implements IBauble
{
	private BaubleType type;
	
	public ItemBauble(String name, BaubleType type)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModTabs.lscTab);
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
				LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
				
				if (cap != null && cap.getPlayerLevel() >= NBTHelper.loadStackNBT(stack).getInteger("Level"))
				{
					for (AttributeBase attribute : ItemUtil.getAllAttributes(stack))
					{
						if (attribute instanceof AttributeBaseArmor)
						{
							((AttributeBaseArmor) attribute).onEquip(cap, stack);
						}
					}
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
				LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
				
				if (cap != null)
				{
					for (AttributeBase attribute : ItemUtil.getAllAttributes(stack))
					{
						if (attribute instanceof AttributeBaseArmor)
						{
							((AttributeBaseArmor) attribute).onUnequip(cap, stack);
						}
					}
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

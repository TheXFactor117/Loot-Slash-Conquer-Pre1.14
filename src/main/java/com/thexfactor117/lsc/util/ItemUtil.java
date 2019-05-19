package com.thexfactor117.lsc.util;

import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.AttributeArmor;
import com.thexfactor117.lsc.loot.attributes.AttributeBase;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.item.ItemStack;

/**
 *
 * @author TheXFactor117
 *
 */
public class ItemUtil
{
	public static Rarity getItemRarity(ItemStack stack)
	{
		return Rarity.getRarity(NBTHelper.loadStackNBT(stack));
	}
	
	public static int getItemLevel(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("Level");
	}
	
	public static int getItemRequiredLevel(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("RequiredLevel");
	}
	
	public static AttributeBase[] getSecondaryAttributes(ItemStack stack)
	{
		return null;
	}
	
	public static AttributeBase[] getBonusAttributes(ItemStack stack)
	{
		return null;
	}
	
	
	
	// weapons
	public static double getItemDamage(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getDouble("DamageValue");
	}
	
	public static double getItemMinDamage(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getDouble("DamageMinValue");
	}
	
	public static double getItemMaxDamage(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getDouble("DamageMaxValue");
	}
	
	public static double getItemAttackSpeed(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getDouble("AttackSpeed");
	}
	
	
	
	// armor
	public static double getItemArmor(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getDouble("ArmorPoints");
	}
	
	public static void onEquip(ItemStack stack)
	{
		if (getSecondaryAttributes(stack) != null && getSecondaryAttributes(stack).length > 0)
		{
			for (AttributeBase attribute : getSecondaryAttributes(stack))
			{
				// attribute.onEquip
			}
		}
		
		if (getBonusAttributes(stack) != null && getBonusAttributes(stack).length > 0)
		{
			for (AttributeBase attribute : getBonusAttributes(stack))
			{
				// attribute.onEquip
			}
		}
	}
	
	public static void onUnequip(ItemStack stack)
	{
		if (getSecondaryAttributes(stack) != null && getSecondaryAttributes(stack).length > 0)
		{
			for (AttributeBase attribute : getSecondaryAttributes(stack))
			{
				// attribute.onUnequip
			}
		}
		
		if (getBonusAttributes(stack) != null && getBonusAttributes(stack).length > 0)
		{
			for (AttributeBase attribute : getBonusAttributes(stack))
			{
				// attribute.onUnequip
			}
		}
	}
}

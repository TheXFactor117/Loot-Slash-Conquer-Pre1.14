package com.thexfactor117.lsc.util;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.AttributeBase;
import com.thexfactor117.lsc.loot.attributes.AttributeBaseArmor;
import com.thexfactor117.lsc.loot.attributes.AttributeBaseWeapon;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
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
	
	public static ArrayList<AttributeBase> getSecondaryAttributes(ItemStack stack)
	{
		ArrayList<AttributeBase> list = Lists.newArrayList();
		
		for (AttributeBase attribute : AttributeBase.ALL_ATTRIBUTES)
		{
			if (attribute.hasAttribute(NBTHelper.loadStackNBT(stack)) && !attribute.isBonusAttribute())
			{
				list.add(attribute);
			}
		}
		
		return list;
	}
	
	public static ArrayList<AttributeBase> getBonusAttributes(ItemStack stack)
	{
		ArrayList<AttributeBase> list = Lists.newArrayList();
		
		for (AttributeBase attribute : AttributeBase.ALL_ATTRIBUTES)
		{
			if (attribute.hasAttribute(NBTHelper.loadStackNBT(stack)) && attribute.isBonusAttribute())
			{
				list.add(attribute);
			}
		}
		
		return list;
	}
	
	public static ArrayList<AttributeBase> getAllAttributes(ItemStack stack)
	{
		ArrayList<AttributeBase> list = Lists.newArrayList();
		
		for (AttributeBase attribute : AttributeBase.ALL_ATTRIBUTES)
		{
			if (attribute.hasAttribute(NBTHelper.loadStackNBT(stack)))
			{
				list.add(attribute);
			}
		}
		
		return list;
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
	
	public static void useWeaponAttributes(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy)
	{
		for (AttributeBase attributeBase : getAllAttributes(stack))
		{
			if (attributeBase instanceof AttributeBaseWeapon)
			{
				AttributeBaseWeapon attribute = (AttributeBaseWeapon) attributeBase;
				
				if (attribute.isActive())
				{
					attribute.onHit(stack, damage, attacker, enemy);
				}
			}
		}
	}
	
	
	
	// armor
	public static double getItemArmor(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getDouble("ArmorPoints");
	}
	
	public static void onEquip(ItemStack stack)
	{
		if (getAllAttributes(stack) != null && getAllAttributes(stack).size() > 0)
		{
			for (AttributeBase attribute : getAllAttributes(stack))
			{
				if (attribute instanceof AttributeBaseArmor)
				{
					((AttributeBaseArmor) attribute).onEquip();
				}
			}
		}
	}
	
	public static void onUnequip(ItemStack stack)
	{
		if (getAllAttributes(stack) != null && getAllAttributes(stack).size() > 0)
		{
			for (AttributeBase attribute : getAllAttributes(stack))
			{
				if (attribute instanceof AttributeBaseArmor)
				{
					((AttributeBaseArmor) attribute).onUnequip();
				}
			}
		}
	}
}

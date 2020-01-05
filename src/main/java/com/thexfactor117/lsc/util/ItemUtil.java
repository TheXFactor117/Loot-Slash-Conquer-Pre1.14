package com.thexfactor117.lsc.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.AttributeArmor;
import com.thexfactor117.lsc.loot.attributes.AttributeWeapon;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class ItemUtil
{
	public static final UUID VANILLA_ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	public static final UUID VANILLA_ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
	public static final UUID VANILLA_ARMOR_MODIFIER = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
	public static final DecimalFormat FORMAT = new DecimalFormat("#.##");
	
	// common
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
	
	public static String getItemOriginalName(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getString("ItemType");
	}
	
	public static ArrayList<Attribute> getSecondaryAttributes(ItemStack stack)
	{
		ArrayList<Attribute> list = Lists.newArrayList();
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		
		for (Attribute attribute : Attribute.ALL_ATTRIBUTES)
		{
			if (attribute.hasAttribute(nbt))
			{
				list.add(attribute);
			}
		}
		
		return list;
	}
	
	public static ArrayList<Attribute> getAllAttributes(ItemStack stack)
	{
		ArrayList<Attribute> list = Lists.newArrayList();
		
		for (Attribute attribute : Attribute.ALL_ATTRIBUTES)
		{
			if (attribute.hasAttribute(NBTHelper.loadStackNBT(stack)))
			{
				list.add(attribute);
			}
		}
		
		return list;
	}
	
	
	
	// weapons
	public static int getItemDamage(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("DamageValue");
	}
	
	public static int getItemMinDamage(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("DamageMinValue");
	}
	
	public static int getItemMaxDamage(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("DamageMaxValue");
	}
	
	public static double getItemAttackSpeed(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getDouble("AttackSpeed");
	}
	
	public static double getItemDamagePerSecond(ItemStack stack, LSCPlayerCapability cap)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		
		double criticalChance = cap.getCriticalChance();
		double criticalDamage = cap.getCriticalDamage();
		double elementalDamage = 0;

		if(criticalChance < 0) criticalChance = 0;
		
		if (Attribute.CRITICAL_CHANCE.hasAttribute(nbt)) criticalChance += Attribute.CRITICAL_CHANCE.getAttributeValue(nbt);
		if (Attribute.CRITICAL_DAMAGE.hasAttribute(nbt)) criticalDamage += Attribute.CRITICAL_DAMAGE.getAttributeValue(nbt);
		if (Attribute.FIRE_DAMAGE.hasAttribute(nbt)) elementalDamage += Attribute.FIRE_DAMAGE.getAttributeValue(nbt);
		if (Attribute.FROST_DAMAGE.hasAttribute(nbt)) elementalDamage += Attribute.FROST_DAMAGE.getAttributeValue(nbt);
		if (Attribute.LIGHTNING_DAMAGE.hasAttribute(nbt)) elementalDamage += Attribute.LIGHTNING_DAMAGE.getAttributeValue(nbt);
		if (Attribute.POISON_DAMAGE.hasAttribute(nbt)) elementalDamage += Attribute.POISON_DAMAGE.getAttributeValue(nbt);
		
		double totalDamage = getItemDamage(stack) + elementalDamage;
		
		return totalDamage * (getItemAttackSpeed(stack) + 4) * (1 + (criticalChance * criticalDamage));
	}
	
	public static void useWeaponAttributes(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy)
	{
		for (Attribute attributeBase : getAllAttributes(stack))
		{
			if (attributeBase instanceof AttributeWeapon)
			{
				AttributeWeapon attribute = (AttributeWeapon) attributeBase;
				
				if (attribute.isActive())
				{
					attribute.onHit(stack, damage, attacker, enemy);
				}
			}
		}
	}
	
	
	
	// armor
	public static int getItemArmor(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("ArmorValue");
	}
	
	public static int getItemMinArmor(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("ArmorMinValue");
	}
	
	public static int getItemMaxArmor(ItemStack stack)
	{
		return NBTHelper.loadStackNBT(stack).getInteger("ArmorMaxValue");
	}
	
	public static void onEquip(LSCPlayerCapability cap, ItemStack stack)
	{
		if (getAllAttributes(stack) != null && getAllAttributes(stack).size() > 0)
		{
			for (Attribute attribute : getAllAttributes(stack))
			{
				if (attribute instanceof AttributeArmor)
				{
					((AttributeArmor) attribute).onEquip(cap, stack);
				}
			}
		}
	}
	
	public static void onUnequip(LSCPlayerCapability cap, ItemStack stack)
	{
		if (getAllAttributes(stack) != null && getAllAttributes(stack).size() > 0)
		{
			for (Attribute attribute : getAllAttributes(stack))
			{
				if (attribute instanceof AttributeArmor)
				{
					((AttributeArmor) attribute).onUnequip(cap, stack);
				}
			}
		}
	}
	
	
	
	// misc
	
	public static void setAttributeModifierValue(Multimap<String, AttributeModifier> map, IAttribute attribute, UUID id, double value)
	{
		final Collection<AttributeModifier> modifiers = map.get(attribute.getName());
		final Optional<AttributeModifier> mod = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();
		
		if (mod.isPresent())
		{
			final AttributeModifier existingMod = mod.get();
			modifiers.remove(existingMod);
			modifiers.add(new AttributeModifier(existingMod.getID(), existingMod.getName(), value, existingMod.getOperation()));
		}
	}
	
	public static double getAttributeModifierValue(ItemStack stack, IAttribute attribute, EntityEquipmentSlot slot, UUID attributeID)
	{
		for (Map.Entry<String, AttributeModifier> entry : stack.getItem().getAttributeModifiers(slot, stack).entries())
		{
			AttributeModifier mod = entry.getValue();
			
			if (mod.getID().equals(attributeID))
			{
				double value = mod.getAmount();
				
				return value;
			}
		}

		return 1;
	}
}

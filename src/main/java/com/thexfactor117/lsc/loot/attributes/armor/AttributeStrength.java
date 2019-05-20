package com.thexfactor117.lsc.loot.attributes.armor;

import java.util.Random;

import com.thexfactor117.lsc.loot.attributes.AttributeBaseArmor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeStrength extends AttributeBaseArmor
{
	public AttributeStrength()
	{
		super("strength", "attributes.armor.strength", 2, false, false);
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(stack, nbt, rand);
		this.addStatAttribute(nbt, rand);
	}
}

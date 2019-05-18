package com.thexfactor117.lsc.loot.attributes.armor;

import java.util.Random;

import com.thexfactor117.lsc.loot.attributes.AttributeArmor;

import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeStrength extends AttributeArmor
{
	public AttributeStrength()
	{
		super("strength", "attributes.armor.strength", 1, 4, false);
	}
	
	@Override
	public void addAttribute(NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(nbt, rand);
		this.addStatAttribute(nbt, rand);
	}
}

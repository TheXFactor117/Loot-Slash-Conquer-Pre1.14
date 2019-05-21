package com.thexfactor117.lsc.loot.attributes;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeBaseArmor extends AttributeBase
{
	public AttributeBaseArmor(String name, String key, double baseValue, boolean upgradeable, boolean isBonus)
	{
		super(name, key, baseValue, upgradeable, isBonus);
	}

	public void onEquip() { }
	
	public void onUnequip() { }
	
	// e.g. Strength
	public void addStatAttribute(NBTTagCompound nbt, Random rand)
	{
		// add scaling for stat bonuses??
		nbt.setDouble(this.getName() + "_value", this.getBaseValue());
	}
	
	private void setStatMinMax(ItemStack stack, NBTTagCompound nbt, double startingValue)
	{
		double minRandomFactor = 0.1;
		double maxRandomFactor = 0.4;
		double randomMultiplier = Math.random() * (maxRandomFactor - minRandomFactor) + minRandomFactor;
		
		
	}
}

package com.thexfactor117.lsc.loot.attributes;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeBaseArmor extends AttributeBase
{
	public AttributeBaseArmor(String name, String key, double min, double max, boolean upgradeable)
	{
		super(name, key, min, max, upgradeable);
	}

	public void onEquip() { }
	
	public void onUnequip() { }
	
	// e.g. Strength
	public void addStatAttribute(NBTTagCompound nbt, Random rand)
	{
		int randomizedBase = (int) (Math.random() * (this.getMaxBaseValue() - this.getMinBaseValue()) + this.getMinBaseValue());
		// add scaling for stat bonuses??
		nbt.setDouble(this.getName() + "_value", randomizedBase);
	}
}

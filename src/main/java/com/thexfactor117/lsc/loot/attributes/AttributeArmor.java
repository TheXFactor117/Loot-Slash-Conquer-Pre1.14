package com.thexfactor117.lsc.loot.attributes;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeArmor extends AttributeBase
{
	public AttributeArmor(String name, String key, double min, double max, boolean upgradeable)
	{
		super(name, key, min, max, upgradeable);
	}

	// e.g. Strength
	public void addStatAttribute(NBTTagCompound nbt, Random rand)
	{
		int randomizedBase = (int) (Math.random() * (this.getMaxBaseValue() - this.getMinBaseValue()) + this.getMinBaseValue());
		// add scaling for stat bonuses??
		nbt.setDouble(this.getName() + "_value", randomizedBase);
	}

	public void updateStat()
	{

	}
}

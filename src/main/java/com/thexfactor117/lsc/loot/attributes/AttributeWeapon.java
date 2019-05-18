package com.thexfactor117.lsc.loot.attributes;

import java.util.Random;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.generation.ItemGeneratorHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeWeapon extends AttributeBase
{
	public AttributeWeapon(String name, String key, double min, double max, boolean upgradeable)
	{
		super(name, key, min, max, upgradeable);
	}

	// e.g. Elemental Damage
	public void addDamageAttribute(NBTTagCompound nbt, Random rand)
	{
		int randomizedBase = (int) (Math.random() * (this.getMaxBaseValue() - this.getMinBaseValue()) + this.getMinBaseValue());
		double weightedBase = ItemGeneratorHelper.getWeightedDamage(nbt.getInteger("Level"), this.getAttributeRarity(nbt), randomizedBase);
		this.setMinMaxIntegers(nbt, weightedBase, Configs.weaponCategory.rangeMultiplier - 0.1);
		double trueDamage = Math.random() * (this.getAttributeMaxValue(nbt) - this.getAttributeMinValue(nbt)) + this.getAttributeMinValue(nbt);

		nbt.setDouble(this.getName() + "_value", trueDamage);
	}

	public void executeAttribute(float damage, EntityLivingBase attacker, EntityLivingBase enemy, ItemStack stack, NBTTagCompound nbt)
	{
		
	}
}

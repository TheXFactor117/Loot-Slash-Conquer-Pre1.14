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
public class AttributeBaseWeapon extends AttributeBase
{
	private boolean isActive;

	public AttributeBaseWeapon(String name, String key, double baseValue, boolean upgradeable, boolean isBonus, boolean isActive)
	{
		super(name, key, baseValue, upgradeable, isBonus);
		this.isActive = isActive;
	}

	/**
	 * Called every time a weapon strikes and deals damage. Each attribute will implement its own effect.
	 * 
	 * @param damage
	 * @param attacker
	 * @param enemy
	 * @param stack
	 * @param nbt
	 */
	public void onHit(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy) { }
	
	public double getPassiveValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(this.getName() + "_value");
	}

	// e.g. Elemental Damage
	public void addDamageAttribute(NBTTagCompound nbt, Random rand)
	{
		double weightedBase = ItemGeneratorHelper.getWeightedDamage(nbt.getInteger("Level"), this.getAttributeRarity(nbt), this.getBaseValue());
		this.setMinMaxIntegers(nbt, weightedBase, Configs.weaponCategory.rangeMultiplier - 0.1);
		int trueDamage = (int) (Math.random() * (this.getAttributeMaxValue(nbt) - this.getAttributeMinValue(nbt)) + this.getAttributeMinValue(nbt));

		nbt.setDouble(this.getName() + "_value", trueDamage);
	}
	
	public void addPercentageAttribute(NBTTagCompound nbt, Random rand, double rangeMultiplier)
	{
		double weightedPercentage = this.getWeightedPercentage(this.getAttributeRarity(nbt), this.getBaseValue());
		this.setMinMaxPercentages(nbt, weightedPercentage, rangeMultiplier);
		double truePercentage = Math.random() * (this.getAttributeMaxValue(nbt) - this.getAttributeMinValue(nbt)) + this.getAttributeMinValue(nbt);
		
		nbt.setDouble(this.getName() + "_value", truePercentage);
	}

	/**
	 * Returns whether or not this is an active or passive attribute.
	 * 
	 * @return
	 */
	public boolean isActive()
	{
		return isActive;
	}
}

package com.thexfactor117.lsc.loot.attributes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeWeapon extends Attribute
{
	private boolean isActive;

	public AttributeWeapon(String name, String key, double baseValue, boolean upgradeable, boolean isActive)
	{
		super(name, key, baseValue, upgradeable);
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

package com.thexfactor117.lsc.loot.attributes.weapons;

import java.util.Random;

import com.thexfactor117.lsc.loot.attributes.AttributeWeapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeFireDamage extends AttributeWeapon
{
	public AttributeFireDamage()
	{
		super("fire_damage", "attributes.weapon.fire_damage", 1, 5, true);
	}
	
	@Override
	public void executeAttribute(float damage, EntityLivingBase attacker, EntityLivingBase enemy, ItemStack stack, NBTTagCompound nbt)
	{
		enemy.hurtResistantTime = 0;
		enemy.attackEntityFrom(DamageSource.ON_FIRE, (float) this.getAttributeValue(nbt));
	}
	
	@Override
	public void addAttribute(NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(nbt, rand);
		this.addDamageAttribute(nbt, rand);
	}
}

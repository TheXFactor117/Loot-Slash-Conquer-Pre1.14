package com.thexfactor117.lsc.loot.attributes.weapons;

import java.util.Random;

import com.thexfactor117.lsc.loot.attributes.AttributeBaseWeapon;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeFireDamage extends AttributeBaseWeapon
{
	public AttributeFireDamage()
	{
		super("fire_damage", "attributes.weapon.fire_damage", 1, 5, true, true);
	}
	
	@Override
	public void onHit(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy)
	{
		enemy.hurtResistantTime = 0;
		enemy.attackEntityFrom(DamageSource.ON_FIRE, (float) this.getAttributeValue(NBTHelper.loadStackNBT(stack)));
	}
	
	@Override
	public void addAttribute(NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(nbt, rand);
		this.addDamageAttribute(nbt, rand);
	}
}

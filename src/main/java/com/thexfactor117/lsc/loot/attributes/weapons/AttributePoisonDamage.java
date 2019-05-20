package com.thexfactor117.lsc.loot.attributes.weapons;

import java.util.Random;

import com.thexfactor117.lsc.loot.attributes.AttributeBaseWeapon;
import com.thexfactor117.lsc.util.misc.LSCDamageSource;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributePoisonDamage extends AttributeBaseWeapon
{
	public AttributePoisonDamage()
	{
		super("poison_damage", "attributes.weapon.poison_damage", 2, true, false, true);
	}
	
	@Override
	public void onHit(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy)
	{
		enemy.hurtResistantTime = 0;
		enemy.attackEntityFrom(LSCDamageSource.causePoisonDamage(attacker), (float) this.getAttributeValue(NBTHelper.loadStackNBT(stack)));
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(stack, nbt, rand);
		this.addDamageAttribute(nbt, rand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		int value = (int) this.getAttributeValue(nbt);
		int minValue = (int) this.getAttributeMinValue(nbt);
		int maxValue = (int) this.getAttributeMaxValue(nbt);
		
		return TextFormatting.RED + " * +" + value + " " + I18n.format(this.getKey()) + TextFormatting.GRAY + " [" + minValue + " - " + maxValue + "]";
	}
}

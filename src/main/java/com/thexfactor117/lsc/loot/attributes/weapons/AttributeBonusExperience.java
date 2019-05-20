package com.thexfactor117.lsc.loot.attributes.weapons;

import java.util.Random;

import com.thexfactor117.lsc.loot.attributes.AttributeBaseWeapon;

import net.minecraft.client.resources.I18n;
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
public class AttributeBonusExperience extends AttributeBaseWeapon
{
	public AttributeBonusExperience()
	{
		super("bonus_experience", "attributes.weapon.bonus_experience", 0.1, false, false, false);
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(stack, nbt, rand);
		this.addPercentageAttribute(nbt, rand, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		int value = (int) (this.getAttributeValue(nbt) * 100);
		
		return TextFormatting.RED + " * +" + value + "% " + I18n.format(this.getKey());
	}
}

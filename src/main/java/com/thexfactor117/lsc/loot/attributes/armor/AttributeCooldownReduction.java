package com.thexfactor117.lsc.loot.attributes.armor;

import java.util.Random;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.loot.attributes.AttributeBaseArmor;
import com.thexfactor117.lsc.util.AttributeUtil;
import com.thexfactor117.lsc.util.misc.NBTHelper;

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
public class AttributeCooldownReduction extends AttributeBaseArmor
{
	public AttributeCooldownReduction()
	{
		super("cooldown_reduction", "attributes.armor.cooldown_reduction", 0.03, false);
	}
	
	@Override
	public void onEquip(LSCPlayerCapability cap, ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		double value = this.getAttributeValue(nbt);
		
		cap.setCooldownReduction(cap.getCooldownReduction() + value);
	}
	
	@Override
	public void onUnequip(LSCPlayerCapability cap, ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		double value = this.getAttributeValue(nbt);
		
		cap.setCooldownReduction(cap.getCooldownReduction() - value);
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(stack, nbt, rand);
		AttributeUtil.addPercentageAttribute(this, stack, nbt, rand, 1);
	}
	
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		int value = (int) (this.getAttributeValue(nbt) * 100);
		
		return TextFormatting.BLUE + " * +" + value + "% " + I18n.format(this.getKey());
	}
}

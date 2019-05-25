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
public class AttributeAllStats extends AttributeBaseArmor
{
	public AttributeAllStats()
	{
		super("strength", "attributes.armor.strength", 2, false);
	}
	
	@Override
	public void onEquip(LSCPlayerCapability cap, ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		int value = (int) this.getAttributeValue(nbt);
		
		cap.setBonusStrengthStat(cap.getBonusStrengthStat() + value);
		cap.setBonusAgilityStat(cap.getBonusAgilityStat() + value);
		cap.setBonusDexterityStat(cap.getBonusDexterityStat() + value);
		cap.setBonusIntelligenceStat(cap.getBonusIntelligenceStat() + value);
		cap.setBonusWisdomStat(cap.getBonusWisdomStat() + value);
		cap.setBonusFortitudeStat(cap.getBonusFortitudeStat() + value);
	}
	
	@Override
	public void onUnequip(LSCPlayerCapability cap, ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		int value = (int) this.getAttributeValue(nbt);
		
		cap.setBonusStrengthStat(cap.getBonusStrengthStat() - value);
		cap.setBonusAgilityStat(cap.getBonusAgilityStat() - value);
		cap.setBonusDexterityStat(cap.getBonusDexterityStat() - value);
		cap.setBonusIntelligenceStat(cap.getBonusIntelligenceStat() - value);
		cap.setBonusWisdomStat(cap.getBonusWisdomStat() - value);
		cap.setBonusFortitudeStat(cap.getBonusFortitudeStat() - value);
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(stack, nbt, rand);
		AttributeUtil.addStatAttribute(this, stack, nbt, rand);
	}
	
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		int value = (int) this.getAttributeValue(nbt);
		
		return TextFormatting.BLUE + " * +" + value + " " + I18n.format(this.getKey());
	}
}

package com.thexfactor117.lsc.loot.attributes.weapons;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.thexfactor117.lsc.loot.attributes.AttributeWeapon;
import com.thexfactor117.lsc.util.AttributeUtil;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeVoid extends AttributeWeapon
{
	public AttributeVoid()
	{
		super("void", "attributes.weapon.void", 0.01, true, true);
	}
	
	@Override
	public void onHit(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy)
	{
		if (Math.random() < this.getAttributeValue(NBTHelper.loadStackNBT(stack)))
		{
			enemy.setHealth(0.1F);
		}
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(stack, nbt, rand);
		AttributeUtil.addPercentageAttribute(this, stack, nbt, rand, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		int value = (int) (this.getAttributeValue(nbt) * 100);
		String tooltip = " * " + value + "% chance to instantly kill.";
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			return this.getAttributeRarity(nbt).getColor() + tooltip;
		}
		
		return ATTRIBUTE_COLOR + tooltip;
	}
}

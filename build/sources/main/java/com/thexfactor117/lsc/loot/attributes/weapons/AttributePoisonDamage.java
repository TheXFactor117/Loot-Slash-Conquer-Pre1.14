package com.thexfactor117.lsc.loot.attributes.weapons;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.thexfactor117.lsc.loot.attributes.AttributeWeapon;
import com.thexfactor117.lsc.util.AttributeUtil;
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
public class AttributePoisonDamage extends AttributeWeapon
{
	public AttributePoisonDamage()
	{
		super("poison_damage", "attributes.weapon.poison_damage", 2, true, true);
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
		AttributeUtil.addDamageAttribute(this, stack, nbt, rand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		int value = (int) this.getAttributeValue(nbt);
		int minValue = (int) this.getAttributeMinValue(nbt);
		int maxValue = (int) this.getAttributeMaxValue(nbt);
		String tooltip = " * " + value + " " + I18n.format(this.getKey()) + TextFormatting.GRAY + " [" + minValue + " - " + maxValue + "]";
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			return this.getAttributeRarity(nbt).getColor() + tooltip;
		}
		
		return ATTRIBUTE_COLOR + tooltip;
	}
}

package com.thexfactor117.losteclipse.items.melee;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/**
 * 
 * @author TheXFactor117
 *
 */
public interface ISpecial 
{
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, BlockPos pos);
}

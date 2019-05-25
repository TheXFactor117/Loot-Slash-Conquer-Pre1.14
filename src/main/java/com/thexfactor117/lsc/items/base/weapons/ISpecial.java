package com.thexfactor117.lsc.items.base.weapons;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public interface ISpecial 
{
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos);
}

package com.thexfactor117.lsc.util;

import com.thexfactor117.lsc.init.ModWeapons;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LSCTab extends CreativeTabs
{
	private String name;
	
	public LSCTab(int index, String name)
	{
		super(index, name);
		this.name = name;
	}
	
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		if (this.name == "tab_lsc") 
		{
			return new ItemStack(ModWeapons.DIVINE_RAPIER);
		} 
		else if (this.name == "tab_lsc_dev")
		{
			return new ItemStack(ModWeapons.GAZE_OF_TRUTH);
		}
		return null;
	}
}

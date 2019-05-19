package com.thexfactor117.lsc.items.base;

import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
	}
	
	public ItemBase(String name, CreativeTabs tab)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}

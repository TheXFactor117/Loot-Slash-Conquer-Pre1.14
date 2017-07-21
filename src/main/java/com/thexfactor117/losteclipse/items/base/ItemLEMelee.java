package com.thexfactor117.losteclipse.items.base;

import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemLEMelee extends ItemSword
{
	public ItemLEMelee(ToolMaterial material, String name) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
	}
	
	public ItemLEMelee(ToolMaterial material, String name, CreativeTabs tab) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}

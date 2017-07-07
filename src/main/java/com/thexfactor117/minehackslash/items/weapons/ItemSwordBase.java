package com.thexfactor117.minehackslash.items.weapons;

import com.thexfactor117.minehackslash.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemSwordBase extends ItemSword
{
	
	
	public ItemSwordBase(ToolMaterial material, String name) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
	}
	
	public ItemSwordBase(ToolMaterial material, String name, CreativeTabs tab) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}

package com.thexfactor117.lsc.items.base;

import com.thexfactor117.lsc.init.ModTabs;
import com.thexfactor117.lsc.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemMelee extends ItemSword
{
	private String type; // used in name.
	
	public ItemMelee(ToolMaterial material, String name, String type) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModTabs.lscTab);
		this.type = type;
	}
	
	public ItemMelee(ToolMaterial material, String name, String type, CreativeTabs tab) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
}

package com.lsc.blocks;

import com.lsc.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * 
 * @author TheXFactor117
 *
 */
public class BlockBase extends Block
{
	/**
	 * Creates a simple block without a Creative Tab or any attributes.
	 */
	public BlockBase(Material material, String name)
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
	}
	
	/**
	 * Creates a block with the specified tab and no attributes.
	 */
	public BlockBase(Material material, String name, CreativeTabs tab)
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
	
	/**
	 * Creates a block according to the following parameters.
	 */
	public BlockBase(Material material, String name, CreativeTabs tab, SoundType soundType, float hardness, float resistance, float lightValue, String harvestType, int harvestLevel)
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.setSoundType(soundType);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setLightLevel(lightValue);
		this.setHarvestLevel(harvestType, harvestLevel);
	}
}

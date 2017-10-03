package com.thexfactor117.lootslashconquer.blocks;

import java.util.Random;

import com.thexfactor117.lootslashconquer.init.ModTabs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockOre extends BlockBase
{
	public Item item;
	
	/**
	 * Creates an ore with an item drop instead of block drop. You need to edit the getItemDropped method for this to work correctly.
	 * @param name
	 * @param hardness
	 * @param resistance
	 * @param lightValue
	 * @param harvestLevel
	 * @param item
	 */
	public BlockOre(String name, float hardness, float resistance, float lightValue, int harvestLevel, Item item)
	{
		super(Material.ROCK, name);
		this.setCreativeTab(ModTabs.tabLE);
		this.setSoundType(SoundType.STONE);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setLightLevel(lightValue);
		this.setHarvestLevel("pickaxe", harvestLevel);
		this.item = item;
	}
	
	/**
	 * Creates a simple ore.
	 * @param name
	 * @param hardness
	 * @param resistance
	 * @param lightValue
	 * @param harvestLevel
	 */
	public BlockOre(String name, float hardness, float resistance, float lightValue, int harvestLevel)
	{
		super(Material.ROCK, name);
		this.setCreativeTab(ModTabs.tabLE);
		this.setSoundType(SoundType.STONE);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setLightLevel(lightValue);
		this.setHarvestLevel("pickaxe", harvestLevel);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		//if (state.getBlock() == ModBlocks.VEXAL_ORE || state.getBlock() == ModBlocks.ASTRILL_ORE) return item;
		return Item.getItemFromBlock(this);
	}
}

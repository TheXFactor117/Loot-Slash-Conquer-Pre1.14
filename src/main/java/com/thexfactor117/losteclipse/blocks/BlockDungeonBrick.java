package com.thexfactor117.losteclipse.blocks;

import com.thexfactor117.losteclipse.init.ModTabs;

import net.minecraft.block.material.Material;

public class BlockDungeonBrick extends BlockBase
{
	public BlockDungeonBrick(Material material, String name)
	{
		super(material, name, ModTabs.tabLE);
		this.setBlockUnbreakable();
		this.setResistance(100000);
	}
}

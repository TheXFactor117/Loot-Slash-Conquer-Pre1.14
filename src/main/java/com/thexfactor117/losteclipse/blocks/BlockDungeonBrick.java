package com.thexfactor117.losteclipse.blocks;

import com.thexfactor117.losteclipse.init.ModTabs;

import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;

public class BlockDungeonBrick extends BlockBase
{
	public BlockDungeonBrick(Material material, String name)
	{
		super(material, name, ModTabs.tabLE);
		this.setBlockUnbreakable();
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosion) 
	{
		return false;
	}
}

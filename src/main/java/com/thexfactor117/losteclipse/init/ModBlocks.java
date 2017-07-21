package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.blocks.BlockOre;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModBlocks 
{
	public static final Block VERANTIUM_ORE = new BlockOre("verantium_ore", 3F, 5F, 0F, 1);
	public static final Block VEXAL_ORE = new BlockOre("vexal_ore", 3F, 5F, 0F, 2);
	public static final Block ASTRILL_ORE = new BlockOre("astrill_ore", 3F, 5F, 0F, 3);
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(VERANTIUM_ORE);
		event.getRegistry().register(VEXAL_ORE);
		event.getRegistry().register(ASTRILL_ORE);
	}
}

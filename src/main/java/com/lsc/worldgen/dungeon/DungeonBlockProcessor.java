package com.lsc.worldgen.dungeon;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

/**
 * 
 * @author TheXFactor117
 *
 */
public class DungeonBlockProcessor implements ITemplateProcessor
{
		private final float chance;
		private final Random random;
		private final Block outerBlock;
		private final Block innerBlock;
		
		public DungeonBlockProcessor(BlockPos pos, PlacementSettings settings)
	    {
	        this.chance = settings.getIntegrity();
	        this.random = settings.getRandom(pos);
	        this.outerBlock = null;
	        this.innerBlock = null;
	    }
		
		public DungeonBlockProcessor(BlockPos pos, PlacementSettings settings, Block outer, Block inner)
	    {
	        this.chance = settings.getIntegrity();
	        this.random = settings.getRandom(pos);
	        this.outerBlock = outer;
	        this.innerBlock = inner;
	    }

	    @Nullable
	    public Template.BlockInfo processBlock(World world, BlockPos pos, Template.BlockInfo blockInfo)
	    {
	    	Template.BlockInfo newBlockInfo;
	    	
	        //if (blockInfo.blockState.getBlock() == ModBlocks.DUNGEON_BRICK)
	        //{
	        //	newBlockInfo = new Template.BlockInfo(pos, Blocks.PLANKS.getDefaultState(), blockInfo.tileentityData);
	        //}
	        //else
	        //{
	        	newBlockInfo = blockInfo;
	        //}
	    	
	    	return this.chance < 1.0F && this.random.nextFloat() > this.chance ? null : newBlockInfo;
	    }
}

package com.lsc.worldgen;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
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
public class StructureBlockProcessor implements ITemplateProcessor
{
	public static final String TOWER_FLOOR = "towerFloor";
	
	private final float chance;
	private final Random rand;
	private final String identifier;
	
	public StructureBlockProcessor(BlockPos pos, PlacementSettings settings, String identifier)
	{
		this.chance = settings.getIntegrity();
		this.rand = settings.getRandom(pos);
		this.identifier = identifier;
	}

    @Nullable
    public Template.BlockInfo processBlock(World world, BlockPos pos, Template.BlockInfo oldBlockInfo)
    {
    	Template.BlockInfo newBlockInfo = oldBlockInfo;
		
		if (TOWER_FLOOR.equals(identifier)) newBlockInfo = processTowerFloor(oldBlockInfo);

    	return this.chance < 1.0F && this.rand.nextFloat() > this.chance ? null : newBlockInfo;
    }
    
    /**
     * Processes all of the blocks within the specific tower floor. This will remove random blocks, change different types of blocks,
     * and much more.
     * @param oldBlockInfo
     * @return
     */
    @SuppressWarnings("deprecation")
	private Template.BlockInfo processTowerFloor(Template.BlockInfo oldBlockInfo)
    {
    	Template.BlockInfo newBlockInfo = oldBlockInfo;
    	int removeChance = 30; // 1 in 30 chance
    	int crackedChance = 15; // 1 in 15 chance
    	int mossyChance = 20; // 1 in 20 chance
    	
    	// chance to remove the particular block
    	if (oldBlockInfo.blockState.getBlock() == Blocks.STONEBRICK && (int) (Math.random() * removeChance) == 0)
    	{
    		newBlockInfo = new Template.BlockInfo(oldBlockInfo.pos, Blocks.AIR.getDefaultState(), oldBlockInfo.tileentityData);
    	}
    	
    	if (oldBlockInfo.blockState.getBlock() == Blocks.STONEBRICK && (int) (Math.random() * crackedChance) == 0)
    	{
    		newBlockInfo = new Template.BlockInfo(oldBlockInfo.pos, Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.CRACKED_META), oldBlockInfo.tileentityData);
    	}
    	
    	if (oldBlockInfo.blockState.getBlock() == Blocks.STONEBRICK && (int) (Math.random() * mossyChance) == 0)
    	{
    		newBlockInfo = new Template.BlockInfo(oldBlockInfo.pos, Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.MOSSY_META), oldBlockInfo.tileentityData);
    	}
    	
    	if (oldBlockInfo.blockState.getBlock() == Blocks.WOOL && oldBlockInfo.blockState.equals(Blocks.WOOL.getStateFromMeta(8)))
    	{
    		newBlockInfo = new Template.BlockInfo(oldBlockInfo.pos, Blocks.STONEBRICK.getDefaultState(), oldBlockInfo.tileentityData);
    	}
    	
    	if (oldBlockInfo.blockState.getBlock() == Blocks.WOOL && oldBlockInfo.blockState.equals(Blocks.WOOL.getStateFromMeta(14)))
    	{
    		int lavaChance = 2; // 1 in 5
    		
    		if ((int) (Math.random() * lavaChance) == 0)
    		{
    			newBlockInfo = new Template.BlockInfo(oldBlockInfo.pos, Blocks.LAVA.getDefaultState(), oldBlockInfo.tileentityData);
    		}
    		else
    		{
    			newBlockInfo = new Template.BlockInfo(oldBlockInfo.pos, Blocks.STONEBRICK.getDefaultState(), oldBlockInfo.tileentityData);
    		}
    	}
    	
    	return newBlockInfo;
    }
}

package com.thexfactor117.lsc.blocks;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.init.ModTabs;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.tileentity.TileEntityLootChest;
import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

/**
 *
 * @author TheXFactor117
 *
 */
// TODO: fix bounding box
public class BlockLootChest extends BlockChest
{
	public Rarity rarity;
	
	public BlockLootChest(String name, Rarity rarity)
	{
		super(BlockChest.Type.BASIC);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModTabs.lscDevTab);
		this.setBlockUnbreakable();
		this.setResistance(100000); // TODO: find a less hacky way?
		this.rarity = rarity;
	}
	
	/**
	 * Overrides the inability to place chests next to each other.
	 * @param worldIn
	 * @param pos
	 * @return
	 */
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		return true;
    }
	
	/**
	 * Removes opening double chest Gui since Loot Chests shouldn't be double chests.
	 */
	@Nullable
	@Override
    public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking)
    {
		TileEntity tileentity = worldIn.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityChest))
        {
            return null;
        }
        else
        {
        	ILockableContainer ilockablecontainer = (TileEntityChest)tileentity;
        	return ilockablecontainer;
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityLootChest(rarity);
    }
}

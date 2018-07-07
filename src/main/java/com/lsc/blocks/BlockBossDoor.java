package com.lsc.blocks;

import com.lsc.entities.bosses.EntityCorruptedKnight;
import com.lsc.init.ModItems;
import com.lsc.init.ModTabs;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author TheXFactor117
 *
 */
public class BlockBossDoor extends BlockBase
{
	public BlockBossDoor(String name)
	{
		super(Material.ROCK, name, ModTabs.lscDevTab);
		this.setBlockUnbreakable();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote)
		{
			if (hand == EnumHand.MAIN_HAND && player.getHeldItemMainhand().getItem() == ModItems.CORRUPTED_TOWER_KEY)
			{
				BlockPos bossPos = pos.add(7, 5, 4);
				EntityCorruptedKnight boss = new EntityCorruptedKnight(world);
				boss.setPosition(bossPos.getX(), bossPos.getY(), bossPos.getZ());
				world.spawnEntity(boss);
				
				player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
				world.setBlockToAir(pos);
				
				return true;
			}
		}
		
		return false;
    }
}

package com.thexfactor117.losteclipse.items.base;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.util.Reference;
import com.thexfactor117.losteclipse.worldgen.procedural.ProceduralDungeon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemTest extends Item
{
	public ItemTest(String name)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (!player.getEntityWorld().isRemote)
		{
			LostEclipse.LOGGER.info("Hello?");
			ProceduralDungeon dungeon = new ProceduralDungeon(5, 5);
			dungeon.generate(worldIn, player.getRNG(), pos);
			
	        return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.FAIL;
    }
}

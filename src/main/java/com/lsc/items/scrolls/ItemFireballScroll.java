package com.lsc.items.scrolls;

import java.util.List;

import com.lsc.capabilities.playerstats.CapabilityPlayerStats;
import com.lsc.capabilities.playerstats.Stats;
import com.lsc.entities.projectiles.EntityFireball;
import com.lsc.init.ModItems;
import com.lsc.init.ModTabs;
import com.lsc.items.base.ItemBase;
import com.lsc.loot.Rarity;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemFireballScroll extends ItemBase
{
	private Rarity rarity;
	
	public ItemFireballScroll(String name, Rarity rarity)
	{
		super(name, ModTabs.tabLE);
		this.rarity = rarity;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{		
		ItemStack stack = player.inventory.getCurrentItem();
		Stats stats = (Stats) player.getCapability(CapabilityPlayerStats.STATS, null);
		
		if (player.inventory.getCurrentItem().getItem() == ModItems.FIREBALL_SCROLL && stats != null)
		{
			if (!world.isRemote && stats.getMana() >= 10)
			{
				Vec3d look = player.getLookVec();
				double x = look.x;
				double y = look.y;
				double z = look.z;
				EntityFireball entity = new EntityFireball(world, x, y, z, 1.0F, 0F, player, stack, 5);
				entity.setPosition(player.posX + look.x, player.posY + look.y + 1.5, player.posZ + look.z);
				world.spawnEntity(entity);
				stack.shrink(1); // decrease stack size by 1
				stats.decreaseMana(10);
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced)
    {		
		tooltip.add(rarity.getColor() + rarity.getName());
    }
}

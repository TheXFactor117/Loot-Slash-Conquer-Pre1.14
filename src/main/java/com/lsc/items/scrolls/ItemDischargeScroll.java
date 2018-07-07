package com.lsc.items.scrolls;

import java.util.Iterator;
import java.util.List;

import com.lsc.capabilities.cap.CapabilityPlayerStats;
import com.lsc.capabilities.implementation.Stats;
import com.lsc.init.ModItems;
import com.lsc.init.ModTabs;
import com.lsc.items.base.ItemBase;
import com.lsc.loot.Rarity;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemDischargeScroll extends ItemBase
{
	private Rarity rarity;
	
	public ItemDischargeScroll(String name, Rarity rarity)
	{
		super(name, ModTabs.lscTab);
		this.rarity = rarity;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{		
		ItemStack stack = player.inventory.getCurrentItem();
		Stats stats = (Stats) player.getCapability(CapabilityPlayerStats.STATS, null);
		
		if (player.inventory.getCurrentItem().getItem() == ModItems.DISCHARGE_SCROLL && stats != null)
		{
			if (!world.isRemote && stats.getMana() >= 10)
			{
				int radius = 10;
				List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius));
				Iterator<EntityLivingBase> iterator = entityList.iterator();
				
				while (iterator.hasNext())
				{
	                Entity entity = (Entity) iterator.next();
					
					if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime))
					{
						entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) (stats.getMagicalPower()));
						EntityLivingBase enemy = (EntityLivingBase) entity;
						
						// remove half the lightning damage dealt from mana.
						if (enemy instanceof EntityPlayer)
						{
							Stats statsCap = (Stats) enemy.getCapability(CapabilityPlayerStats.STATS, null);
							
							if (statsCap != null)
							{
								statsCap.decreaseMana((int) (stats.getMagicalPower() / 2));
							}
						}
					}
				}
				
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

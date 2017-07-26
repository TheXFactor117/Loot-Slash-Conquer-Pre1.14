package com.thexfactor117.losteclipse.items.base;

import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.api.IPlayerInformation;
import com.thexfactor117.losteclipse.events.misc.EventPlayerTick;
import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemLEMelee extends ItemSword
{
	private boolean isBonusActive = false; // controls when the item should update player bonus stats.
	
	public ItemLEMelee(ToolMaterial material, String name) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
	}
	
	public ItemLEMelee(ToolMaterial material, String name, CreativeTabs tab) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			IPlayerInformation info = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (!world.isRemote && info != null)
			{
				if (selected && !isBonusActive)
				{
					EventPlayerTick.updateStats(player, info);
					isBonusActive = true;
				}
				
				if (!selected && isBonusActive)
				{
					EventPlayerTick.updateStats(player, info);
					isBonusActive = false;
				}
			}
		}
	}
}

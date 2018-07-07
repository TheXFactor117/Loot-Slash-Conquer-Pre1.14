package com.lsc.items.base;

import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.events.EventPlayerTick;
import com.lsc.init.ModTabs;
import com.lsc.util.Reference;

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
public class ItemMelee extends ItemSword
{
	private boolean isBonusActive = false; // controls when the item should update player bonus stats.
	private String type; // used in name.
	
	public ItemMelee(ToolMaterial material, String name, String type) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModTabs.lscTab);
		this.type = type;
	}
	
	public ItemMelee(ToolMaterial material, String name, String type, CreativeTabs tab) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
		this.type = type;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			PlayerInformation info = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (!world.isRemote && info != null)
			{
				if (selected && !isBonusActive)
				{
					EventPlayerTick.updateStats(player, info, 1);
					isBonusActive = true;
				}
				
				if (!selected && isBonusActive)
				{
					EventPlayerTick.updateStats(player, info, 1);
					isBonusActive = false;
				}
			}
		}
	}
	
	public String getType()
	{
		return type;
	}
}

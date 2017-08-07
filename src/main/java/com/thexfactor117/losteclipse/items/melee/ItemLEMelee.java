package com.thexfactor117.losteclipse.items.melee;

import com.thexfactor117.losteclipse.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.playerinfo.IPlayerInformation;
import com.thexfactor117.losteclipse.events.EventPlayerTick;
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
	private String type; // used in name.
	
	public ItemLEMelee(ToolMaterial material, String name, String type) 
	{
		super(material);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.type = type;
	}
	
	public ItemLEMelee(ToolMaterial material, String name, String type, CreativeTabs tab) 
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
	
	public String getType()
	{
		return type;
	}
}

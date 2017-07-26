package com.thexfactor117.losteclipse.items.base;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityMana;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.api.IMana;
import com.thexfactor117.losteclipse.capabilities.api.IPlayerInformation;
import com.thexfactor117.losteclipse.events.misc.EventPlayerTick;
import com.thexfactor117.losteclipse.network.PacketUpdateMana;
import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemLEWand extends Item
{
	private boolean isBonusActive = false; // controls whether or not player stats should update. See onUpdate
	
	private int baseDamage;
	private int manaPerUse;
	
	public ItemLEWand(String name, int baseDamage, int manaPerUse, int durability)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setMaxDamage(durability);
		this.manaPerUse = manaPerUse;
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
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		IMana manaCap = player.getCapability(CapabilityMana.MANA, null);
		
		if (manaCap != null)
		{
			if (manaCap.getMana() - this.manaPerUse >= 0)
			{	
				player.setActiveHand(hand);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.inventory.getCurrentItem());
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.inventory.getCurrentItem());
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int count)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			IMana manaCap = player.getCapability(CapabilityMana.MANA, null);
			
			if (manaCap != null)
			{
				world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				
				if (!world.isRemote)
				{
					//Vec3d look = player.getLookVec();
					// spawn entity
					// set position
					// world spawn entity
					
					// update mana and send to client
					manaCap.setMana(manaCap.getMana() - this.manaPerUse);
					LostEclipse.network.sendTo(new PacketUpdateMana(manaCap), (EntityPlayerMP) player);
					
					// damage item
					stack.damageItem(1, player);
				}
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 300;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.NONE;
	}
	
	/** Returns the base damage. DO NOT use this value when calculating damage. */
	public int getBaseDamage()
	{
		return baseDamage;
	}
	
	/** Returns the mana per use of the weapon. */
	public int getManaPerUse()
	{
		return manaPerUse;
	}
}

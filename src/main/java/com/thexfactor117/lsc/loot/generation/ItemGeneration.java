package com.thexfactor117.lsc.loot.generation;

import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.items.base.weapons.ItemDagger;
import com.thexfactor117.lsc.items.base.weapons.ItemMace;
import com.thexfactor117.lsc.items.base.weapons.ItemMagical;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.util.ItemGenerationUtil;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import baubles.api.BaubleType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemGeneration 
{
	/** Creates a melee weapon/armor with randomized stats. */
	public static void create(ItemStack stack, World world, int level)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
		{	
			if (stack.getItem() instanceof ItemSword)
			{	
				if (stack.getItem() instanceof ItemDagger) nbt.setString("ItemType", "Dagger");
				else if (stack.getItem() instanceof ItemMace) nbt.setString("ItemType", "Mace");
				else nbt.setString("ItemType", "Sword");
				
				nbt.setInteger("Level", level);
				ItemGenerationUtil.setRandomWeaponAttributes(stack);
				ItemGenerationUtil.setPrimaryAttributes(stack);
				ItemGenerationUtil.hideFlags(nbt);
			}
			else if (stack.getItem() instanceof ItemArmor)
			{
				ItemArmor item = (ItemArmor) stack.getItem();
				
				if (item.armorType == EntityEquipmentSlot.HEAD) nbt.setString("ItemType", "Helmet");
				else if (item.armorType == EntityEquipmentSlot.CHEST) nbt.setString("ItemType", "Chestplate");
				else if (item.armorType == EntityEquipmentSlot.LEGS) nbt.setString("ItemType", "Leggings");
				else if (item.armorType == EntityEquipmentSlot.FEET) nbt.setString("ItemType", "Boots");
				
				nbt.setInteger("Level", level);
				ItemGenerationUtil.setRandomArmorAttributes(stack);
				ItemGenerationUtil.setPrimaryAttributes(stack);
				ItemGenerationUtil.hideFlags(nbt);
			}
			else if (stack.getItem() instanceof ItemBow)
			{
				nbt.setString("ItemType", "Bow");
				nbt.setInteger("Level", level);
				ItemGenerationUtil.setRandomWeaponAttributes(stack);
				ItemGenerationUtil.setPrimaryAttributes(stack);
				ItemGenerationUtil.hideFlags(nbt);
			}
			else if (stack.getItem() instanceof ItemMagical)
			{
				nbt.setString("ItemType", "Staff");
				nbt.setInteger("Level", level);
				ItemGenerationUtil.setRandomWeaponAttributes(stack);
				ItemGenerationUtil.setPrimaryAttributes(stack);
				ItemGenerationUtil.setRune(nbt);
				ItemGenerationUtil.hideFlags(nbt);
			}
			else if (stack.getItem() instanceof ItemBauble)
			{
				ItemBauble item = (ItemBauble) stack.getItem();
				
				if (item.getBaubleType(stack) == BaubleType.RING) nbt.setString("ItemType", "Ring");
				else if (item.getBaubleType(stack) == BaubleType.AMULET) nbt.setString("ItemType", "Amulet");
				else if (item.getBaubleType(stack) == BaubleType.BELT) nbt.setString("ItemType", "Belt");
				
				nbt.setInteger("Level", level);
				ItemGenerationUtil.setRandomArmorAttributes(stack);
			}
		}
	}
}

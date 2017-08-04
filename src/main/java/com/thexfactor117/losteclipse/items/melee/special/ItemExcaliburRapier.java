package com.thexfactor117.losteclipse.items.melee.special;

import com.thexfactor117.losteclipse.init.ModTabs;
import com.thexfactor117.losteclipse.items.melee.ISpecial;
import com.thexfactor117.losteclipse.items.melee.ItemLEMelee;
import com.thexfactor117.losteclipse.loot.ItemGeneratorHelper;
import com.thexfactor117.losteclipse.stats.weapons.Rarity;
import com.thexfactor117.losteclipse.stats.weapons.WeaponAttribute;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemExcaliburRapier extends ItemLEMelee implements ISpecial
{
	public ItemExcaliburRapier(ToolMaterial material, String name)
	{
		super(material, name);
		this.setCreativeTab(ModTabs.tabLE);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, BlockPos pos) 
	{
		Rarity.setRarity(nbt, Rarity.EXOTIC);
		nbt.setInteger("Level", (int) (Math.random() * 10 + 1));
		
		// Attributes
		WeaponAttribute.FIRE.addAttribute(nbt, 3);
		WeaponAttribute.FROST.addAttribute(nbt, 3);
		WeaponAttribute.LIGHTNING.addAttribute(nbt, 3);
		WeaponAttribute.STRENGTH.addAttribute(nbt, 8);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

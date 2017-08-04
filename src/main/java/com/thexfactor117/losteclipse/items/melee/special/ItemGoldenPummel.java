package com.thexfactor117.losteclipse.items.melee.special;

import com.thexfactor117.losteclipse.init.ModTabs;
import com.thexfactor117.losteclipse.items.melee.ISpecial;
import com.thexfactor117.losteclipse.items.melee.ItemLEAdvancedMelee;
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
public class ItemGoldenPummel extends ItemLEAdvancedMelee implements ISpecial
{
	public ItemGoldenPummel(ToolMaterial material, String name, double damageMultiplier, double speedMultiplier)
	{
		super(material, name, damageMultiplier, speedMultiplier);
		this.setCreativeTab(ModTabs.tabLE);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, BlockPos pos) 
	{
		Rarity.setRarity(nbt, Rarity.LEGENDARY);
		nbt.setInteger("Level", (int) (Math.random() * 10 + 1));
		
		// Attributes
		WeaponAttribute.FORTITUDE.addAttribute(nbt, 10);
		WeaponAttribute.LIGHTNING.addAttribute(nbt, 7);
		WeaponAttribute.MAGICAL.addAttribute(nbt, 0.2);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

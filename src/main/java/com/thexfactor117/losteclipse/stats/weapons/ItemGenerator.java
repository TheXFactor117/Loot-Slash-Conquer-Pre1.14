package com.thexfactor117.losteclipse.stats.weapons;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemGenerator 
{
	/**
	 * Creates the given item with randomized attributes and such.
	 * @param stack
	 * @param player
	 */
	public static void create(ItemStack stack, NBTTagCompound nbt, BlockPos pos)
	{
		/*
		 * Set rarity
		 * Set level
		 * Generate attributes based on Rarity
		 * 		- Common: 0-1 attributes
		 * 		- Uncommon: 1-2 attributes
		 * 		- Rare: 2-3 attributes
		 * 		- Legendary: 3-4 attributes
		 * 		- Mythic: 4-5 attributes
		 * Generate base damage and base attack speed
		 * Generate name based on attributes + material/type
		 */
		
		if (Rarity.getRarity(nbt) == Rarity.DEFAULT)
		{
			//Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, player.getEntityWorld().rand)); // sets a random rarity
			Rarity.setRarity(nbt, Rarity.MYTHIC); // sets a random rarity
			nbt.setInteger("Level", 1); // set level to current player level
			ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
			ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
			nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags			
		}
	}
}

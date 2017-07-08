package com.thexfactor117.minehackslash.stats.weapons;

import com.thexfactor117.minehackslash.MineHackSlash;
import com.thexfactor117.minehackslash.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.minehackslash.capabilities.IPlayerInformation;
import com.thexfactor117.minehackslash.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
	public static void create(ItemStack stack, EntityPlayer player)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		IPlayerInformation playerInfo = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);

		if (playerInfo != null)
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
			
			// had to split this up so it would run properly
			if (Rarity.getRarity(nbt) == Rarity.DEFAULT)
			{
				//Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, player.getEntityWorld().rand)); // sets a random rarity
				Rarity.setRarity(nbt, Rarity.MYTHIC); // sets a random rarity
				
				MineHackSlash.LOGGER.info("Rarity: " + Rarity.getRarity(nbt));
				nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
				NBTHelper.saveStackNBT(stack, nbt);
			}
			else if (!nbt.hasKey("Level"))
			{
				nbt.setInteger("Level", 1); // set level to current player level
				ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt), player);
				ItemGeneratorHelper.setAttributeModifiers(nbt, stack, player);
				stack.setStackDisplayName(Rarity.getRarity(nbt).getColor() + stack.getDisplayName());
				NBTHelper.saveStackNBT(stack, nbt);
			}
		}
	}
}

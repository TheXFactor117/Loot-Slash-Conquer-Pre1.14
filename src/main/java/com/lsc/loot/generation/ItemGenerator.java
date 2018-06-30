package com.lsc.loot.generation;

import com.lsc.items.base.ItemBauble;
import com.lsc.items.base.ItemMagical;
import com.lsc.items.base.ItemRanged;
import com.lsc.loot.Rarity;

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
public class ItemGenerator 
{
	/** Creates a melee weapon/armor with randomized stats. */
	public static void create(ItemStack stack, NBTTagCompound nbt, World world, int level)
	{
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
		{
			if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor)
			{
				ItemGeneratorHelper.setTypes(stack, nbt);
				nbt.setInteger("Level", level); // set level to current player level
				ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
				ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
				nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
			}
			else if (stack.getItem() instanceof ItemBow)
			{
				ItemGeneratorHelper.setTypes(stack, nbt);
				nbt.setInteger("Level", level);
				ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
				
				double baseDamage = 0;
				double baseDrawSpeed = 0;
				
				// handle custom bows
				if (stack.getItem() instanceof ItemRanged)
				{
					ItemRanged ranged = (ItemRanged) stack.getItem();
					baseDamage = ranged.getBaseDamage();
					baseDrawSpeed = ranged.getBaseDrawSpeed();
				}
				else // handle vanilla/modded bows
				{
					baseDamage = 3;
					baseDrawSpeed = 2;
				}
				
				double weightedDamage = ItemGeneratorHelper.getWeightedDamage(level, Rarity.getRarity(nbt), baseDamage);
				double weightedDrawSpeed = ItemGeneratorHelper.getWeightedAttackSpeed(Rarity.getRarity(nbt), baseDrawSpeed);
				
				ItemGeneratorHelper.setMinMaxDamage(nbt, weightedDamage);
				nbt.setDouble("AttackSpeed", weightedDrawSpeed);
				nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
			}
			else if (stack.getItem() instanceof ItemMagical)
			{
				ItemMagical magical = (ItemMagical) stack.getItem();
				
				ItemGeneratorHelper.setTypes(stack, nbt);
				nbt.setInteger("Level", level);
				ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
				
				// handles setting weighted damage/attack speed and min/max damage
				double baseDamage = magical.getBaseDamage();
				double baseAttackSpeed = magical.getBaseAttackSpeed();
				double weightedDamage = ItemGeneratorHelper.getWeightedDamage(level, Rarity.getRarity(nbt), baseDamage);
				double weightedAttackSpeed = ItemGeneratorHelper.getWeightedAttackSpeed(Rarity.getRarity(nbt), baseAttackSpeed);
				
				ItemGeneratorHelper.setMinMaxDamage(nbt, weightedDamage);
				nbt.setDouble("AttackSpeed", weightedAttackSpeed);
				ItemGeneratorHelper.setRune(nbt);
				nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
			}
			else if (stack.getItem() instanceof ItemBauble)
			{
				ItemGeneratorHelper.setTypes(stack, nbt);
				nbt.setInteger("Level", level);
				ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
			}
		}
	}
}

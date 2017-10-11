package com.lsc.loot;

import com.lsc.api.Rarity;
import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.items.jewelry.ItemLEBauble;
import com.lsc.items.magical.ItemLEMagical;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemGenerator 
{
	/** Creates a melee weapon/armor with randomized stats. */
	public static void create(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos)
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
		
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
		{
			IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
			IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
			int level = chunkLevel.getChunkLevel();
			
			//Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, ItemGeneratorHelper.rand)); // sets a random rarity
			ItemGeneratorHelper.setTypes(stack, nbt);
			nbt.setInteger("Level", level); // set level to current player level
			ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
			ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
			nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
		}
	}
	
	/** Creates a magical weapon with randomized stats. */
	public static void createMagical(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos)
	{
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT && stack.getItem() instanceof ItemLEMagical)
		{
			IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
			IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
			int level = chunkLevel.getChunkLevel();
			ItemLEMagical wand = (ItemLEMagical) stack.getItem();
			
			ItemGeneratorHelper.setTypes(stack, nbt);
			Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, ItemGeneratorHelper.rand));
			nbt.setInteger("Level", level);
			ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
			
			// handles setting weighted damage/attack speed and min/max damage
			double baseDamage = wand.getBaseDamage();
			double baseAttackSpeed = wand.getBaseAttackSpeed();
			double weightedDamage = ItemGeneratorHelper.getWeightedDamage(nbt, Rarity.getRarity(nbt), baseDamage);
			double weightedAttackSpeed = ItemGeneratorHelper.getWeightedAttackSpeed(Rarity.getRarity(nbt), baseAttackSpeed);
			
			ItemGeneratorHelper.setMinMaxDamage(nbt, weightedDamage);
			nbt.setDouble("AttackSpeed", weightedAttackSpeed);
			ItemGeneratorHelper.setRune(nbt);
			nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
		}
	}
	
	public static void createJewelry(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos)
	{
		if (Rarity.getRarity(nbt) != Rarity.DEFAULT && stack.getItem() instanceof ItemLEBauble)
		{
			IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
			IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
			int level = chunkLevel.getChunkLevel();
			
			ItemGeneratorHelper.setTypes(stack, nbt);
			Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, ItemGeneratorHelper.rand));
			nbt.setInteger("Level", level);
			ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
		}
	}
}

package com.lsc.items.magical;

import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.items.base.ISpecial;
import com.lsc.items.base.ItemMagical;
import com.lsc.loot.ItemGeneratorHelper;
import com.lsc.loot.Rarity;
import com.lsc.loot.WeaponAttribute;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemMoonlitRod extends ItemMagical implements ISpecial
{
	public ItemMoonlitRod(String name, boolean isStaff, double baseDamage, double attackSpeed, int manaPerUse, int durability) 
	{
		super(name, isStaff, baseDamage, attackSpeed, manaPerUse, durability);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos) 
	{
		IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
		int level = chunkLevel.getChunkLevel();
		
		Rarity.setRarity(nbt, Rarity.LEGENDARY);
		nbt.setInteger("Level", level);
		ItemGeneratorHelper.setRune(nbt);
		
		// Attributes
		WeaponAttribute.INTELLIGENCE.addAttribute(nbt, 3);
		WeaponAttribute.WISDOM.addAttribute(nbt, 3);
		WeaponAttribute.LIGHTNING.addAttribute(nbt, 5);
		
		// Damage and Attack Speed
		double baseDamage = this.getBaseDamage();
		double baseAttackSpeed = this.getBaseAttackSpeed();
		double weightedDamage = ItemGeneratorHelper.getWeightedDamage(nbt, Rarity.getRarity(nbt), baseDamage);
		double weightedAttackSpeed = ItemGeneratorHelper.getWeightedAttackSpeed(Rarity.getRarity(nbt), baseAttackSpeed);
		
		ItemGeneratorHelper.setMinMaxDamage(nbt, weightedDamage);
		nbt.setDouble("AttackSpeed", weightedAttackSpeed);
	}
}

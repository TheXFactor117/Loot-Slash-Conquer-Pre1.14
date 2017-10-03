package com.thexfactor117.lootslashconquer.items.magical.special;

import com.thexfactor117.lootslashconquer.api.ISpecial;
import com.thexfactor117.lootslashconquer.api.Rarity;
import com.thexfactor117.lootslashconquer.capabilities.chunk.CapabilityChunkLevel;
import com.thexfactor117.lootslashconquer.capabilities.chunk.IChunkLevel;
import com.thexfactor117.lootslashconquer.capabilities.chunk.IChunkLevelHolder;
import com.thexfactor117.lootslashconquer.items.magical.ItemLEMagical;
import com.thexfactor117.lootslashconquer.loot.ItemGeneratorHelper;
import com.thexfactor117.lootslashconquer.stats.attributes.WeaponAttribute;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemEpilogue extends ItemLEMagical implements ISpecial
{
	public ItemEpilogue(String name, boolean isStaff, double baseDamage, double attackSpeed, int manaPerUse, int durability) 
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
		WeaponAttribute.ALL_STATS.addAttribute(nbt, 2);
		WeaponAttribute.MANA_STEAL.addAttribute(nbt, 0.25);
		
		// Damage and Attack Speed
		double baseDamage = this.getBaseDamage();
		double baseAttackSpeed = this.getBaseAttackSpeed();
		double weightedDamage = ItemGeneratorHelper.getWeightedDamage(nbt, Rarity.getRarity(nbt), baseDamage);
		double weightedAttackSpeed = ItemGeneratorHelper.getWeightedAttackSpeed(Rarity.getRarity(nbt), baseAttackSpeed);
		
		ItemGeneratorHelper.setMinMaxDamage(nbt, weightedDamage);
		nbt.setDouble("AttackSpeed", weightedAttackSpeed);
	}
}

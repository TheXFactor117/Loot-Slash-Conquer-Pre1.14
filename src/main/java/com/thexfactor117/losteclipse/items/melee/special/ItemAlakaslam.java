package com.thexfactor117.losteclipse.items.melee.special;

import com.thexfactor117.losteclipse.capabilities.api.IChunkLevel;
import com.thexfactor117.losteclipse.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.losteclipse.capabilities.chunk.CapabilityChunkLevel;
import com.thexfactor117.losteclipse.init.ModTabs;
import com.thexfactor117.losteclipse.items.base.ISpecial;
import com.thexfactor117.losteclipse.items.melee.ItemLEAdvancedMelee;
import com.thexfactor117.losteclipse.loot.ItemGeneratorHelper;
import com.thexfactor117.losteclipse.stats.weapons.Rarity;
import com.thexfactor117.losteclipse.stats.weapons.WeaponAttribute;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemAlakaslam extends ItemLEAdvancedMelee implements ISpecial
{
	public ItemAlakaslam(ToolMaterial material, String name, double damageMultiplier, double speedMultiplier)
	{
		super(material, name, damageMultiplier, speedMultiplier);
		this.setCreativeTab(ModTabs.tabLE);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos) 
	{
		IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
		int level = chunkLevel.getChunkLevel();
		
		Rarity.setRarity(nbt, Rarity.EXOTIC);
		nbt.setInteger("Level", level);
		
		// Attributes
		WeaponAttribute.STRENGTH.addAttribute(nbt, 10);
		WeaponAttribute.FORTITUDE.addAttribute(nbt, 10);
		WeaponAttribute.FROST.addAttribute(nbt, 7);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

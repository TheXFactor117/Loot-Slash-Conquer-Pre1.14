package com.thexfactor117.losteclipse.items.melee.special;

import com.thexfactor117.losteclipse.capabilities.api.IChunkLevel;
import com.thexfactor117.losteclipse.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.losteclipse.capabilities.chunk.CapabilityChunkLevel;
import com.thexfactor117.losteclipse.init.ModTabs;
import com.thexfactor117.losteclipse.items.base.ISpecial;
import com.thexfactor117.losteclipse.items.melee.ItemLEMelee;
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
public class ItemDivineRapier extends ItemLEMelee implements ISpecial
{
	public ItemDivineRapier(ToolMaterial material, String name)
	{
		super(material, name);
		this.setCreativeTab(ModTabs.tabLE);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos) 
	{
		IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
		int level = chunkLevel.getChunkLevel();
		
		Rarity.setRarity(nbt, Rarity.LEGENDARY);
		nbt.setInteger("Level", level);
		
		// Attributes
		WeaponAttribute.FIRE.addAttribute(nbt, 5);
		WeaponAttribute.STRENGTH.addAttribute(nbt, 3);
		WeaponAttribute.DURABLE.addAttribute(nbt, 0.3);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

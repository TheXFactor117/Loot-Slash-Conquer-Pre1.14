package com.lsc.items.melee;

import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.init.ModTabs;
import com.lsc.items.base.ISpecial;
import com.lsc.items.base.ItemMelee;
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
public class ItemExcaliburRapier extends ItemMelee implements ISpecial
{
	public ItemExcaliburRapier(ToolMaterial material, String name, String type)
	{
		super(material, name, type);
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
		WeaponAttribute.FIRE.addAttribute(nbt, 3);
		WeaponAttribute.FROST.addAttribute(nbt, 3);
		WeaponAttribute.LIGHTNING.addAttribute(nbt, 3);
		WeaponAttribute.STRENGTH.addAttribute(nbt, 8);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

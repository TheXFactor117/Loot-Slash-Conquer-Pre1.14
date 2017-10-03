package com.thexfactor117.lootslashconquer.items.melee.special;

import com.thexfactor117.lootslashconquer.api.ISpecial;
import com.thexfactor117.lootslashconquer.api.Rarity;
import com.thexfactor117.lootslashconquer.capabilities.chunk.CapabilityChunkLevel;
import com.thexfactor117.lootslashconquer.capabilities.chunk.IChunkLevel;
import com.thexfactor117.lootslashconquer.capabilities.chunk.IChunkLevelHolder;
import com.thexfactor117.lootslashconquer.init.ModTabs;
import com.thexfactor117.lootslashconquer.items.melee.ItemLEMelee;
import com.thexfactor117.lootslashconquer.loot.ItemGeneratorHelper;
import com.thexfactor117.lootslashconquer.stats.attributes.WeaponAttribute;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class ItemRequiem extends ItemLEMelee implements ISpecial
{
	public ItemRequiem(ToolMaterial material, String name, String type)
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
		WeaponAttribute.AGILITY.addAttribute(nbt, 5);
		WeaponAttribute.DEXTERITY.addAttribute(nbt, 5);
		WeaponAttribute.LIFE_STEAL.addAttribute(nbt, 0.05);
		WeaponAttribute.MANA_STEAL.addAttribute(nbt, 0.05);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

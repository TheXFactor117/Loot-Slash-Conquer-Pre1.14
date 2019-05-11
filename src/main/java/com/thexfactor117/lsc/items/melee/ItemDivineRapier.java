package com.thexfactor117.lsc.items.melee;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.init.ModTabs;
import com.thexfactor117.lsc.items.base.ISpecial;
import com.thexfactor117.lsc.items.base.ItemMelee;
import com.thexfactor117.lsc.loot.Attribute;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.generation.ItemGeneratorHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemDivineRapier extends ItemMelee implements ISpecial
{
	public ItemDivineRapier(ToolMaterial material, String name, String type)
	{
		super(material, name, type);
		this.setCreativeTab(ModTabs.lscTab);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos) 
	{
		IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
		int level = chunkLevel.getChunkLevel();
		
		nbt.setBoolean("IsSpecial", true);
		Rarity.setRarity(nbt, Rarity.EPIC);
		nbt.setInteger("Level", level);
		
		// Attributes
		Attribute.FIRE.addAttribute(nbt, world.rand, 5);
		Attribute.STRENGTH.addAttribute(nbt, world.rand, 3);
		Attribute.DURABLE.addAttribute(nbt, world.rand, 0.3);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

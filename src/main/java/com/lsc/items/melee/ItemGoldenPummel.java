package com.lsc.items.melee;

import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.init.ModTabs;
import com.lsc.items.base.ISpecial;
import com.lsc.items.base.ItemAdvancedMelee;
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
public class ItemGoldenPummel extends ItemAdvancedMelee implements ISpecial
{
	public ItemGoldenPummel(ToolMaterial material, String name, String type, double damageMultiplier, double speedMultiplier)
	{
		super(material, name, type, damageMultiplier, speedMultiplier);
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
		WeaponAttribute.FORTITUDE.addAttribute(nbt, 10);
		WeaponAttribute.LIGHTNING.addAttribute(nbt, 7);
		WeaponAttribute.MANA_STEAL.addAttribute(nbt, 0.2);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}
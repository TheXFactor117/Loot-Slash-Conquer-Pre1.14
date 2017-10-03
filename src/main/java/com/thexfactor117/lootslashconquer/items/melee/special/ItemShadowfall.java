package com.thexfactor117.lootslashconquer.items.melee.special;

import com.thexfactor117.lootslashconquer.api.ISpecial;
import com.thexfactor117.lootslashconquer.api.Rarity;
import com.thexfactor117.lootslashconquer.capabilities.chunk.CapabilityChunkLevel;
import com.thexfactor117.lootslashconquer.capabilities.chunk.IChunkLevel;
import com.thexfactor117.lootslashconquer.capabilities.chunk.IChunkLevelHolder;
import com.thexfactor117.lootslashconquer.init.ModTabs;
import com.thexfactor117.lootslashconquer.items.melee.ItemLEAdvancedMelee;
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
public class ItemShadowfall extends ItemLEAdvancedMelee implements ISpecial
{
	public ItemShadowfall(ToolMaterial material, String name, String type, double damageMultiplier, double speedMultiplier)
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
		WeaponAttribute.DEXTERITY.addAttribute(nbt, 10);
		WeaponAttribute.MAX_DAMAGE.addAttribute(nbt, 3);
		WeaponAttribute.LIFE_STEAL.addAttribute(nbt, 0.15);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

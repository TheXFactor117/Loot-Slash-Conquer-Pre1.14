package com.thexfactor117.lsc.items.melee;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.items.base.weapons.ISpecial;
import com.thexfactor117.lsc.items.base.weapons.ItemMelee;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeChained;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeVoid;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class ItemDoomshadow extends ItemMelee implements ISpecial
{
	public ItemDoomshadow(ToolMaterial material, String name, double damageMultiplier, double speedMultiplier)
	{
		super(material, name, damageMultiplier, speedMultiplier);
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
		
		Attribute chained = new AttributeChained();
		chained.setBaseValue(0.25);
		chained.addAttribute(stack, nbt, world.rand);
		Attribute voidDamage = new AttributeVoid();
		voidDamage.setBaseValue(0.05);
		voidDamage.addAttribute(stack, nbt, world.rand);
	}
}

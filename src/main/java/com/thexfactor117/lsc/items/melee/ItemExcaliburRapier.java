package com.thexfactor117.lsc.items.melee;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.items.base.weapons.ISpecial;
import com.thexfactor117.lsc.items.base.weapons.ItemMelee;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFireDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFrostDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeLightningDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeNausea;

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
	public ItemExcaliburRapier(ToolMaterial material, String name)
	{
		super(material, name, 1, 1);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos) 
	{
		IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
		int level = chunkLevel.getChunkLevel();
		
		nbt.setBoolean("IsSpecial", true);
		Rarity.setRarity(nbt, Rarity.LEGENDARY);
		nbt.setInteger("Level", level);
		
		Attribute fire = new AttributeFireDamage();
		fire.setBaseValue(4);
		fire.addAttribute(stack, nbt, world.rand);
		Attribute frost = new AttributeFrostDamage();
		frost.setBaseValue(4);
		frost.addAttribute(stack, nbt, world.rand);
		Attribute lightning = new AttributeLightningDamage();
		lightning.setBaseValue(4);
		lightning.addAttribute(stack, nbt, world.rand);
		Attribute nausea = new AttributeNausea();
		nausea.setBaseValue(0.15);
		nausea.addAttribute(stack, nbt, world.rand);
	}
}

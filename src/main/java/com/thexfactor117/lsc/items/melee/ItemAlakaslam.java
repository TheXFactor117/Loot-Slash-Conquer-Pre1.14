package com.thexfactor117.lsc.items.melee;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.items.base.weapons.ISpecial;
import com.thexfactor117.lsc.items.base.weapons.ItemMelee;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFrostDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeSlow;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeStun;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemAlakaslam extends ItemMelee implements ISpecial
{
	public ItemAlakaslam(ToolMaterial material, String name, double damageMultiplier, double speedMultiplier)
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
		Rarity.setRarity(nbt, Rarity.LEGENDARY);
		nbt.setInteger("Level", level);
		
		Attribute frost = new AttributeFrostDamage();
		frost.setBaseValue(7);
		frost.addAttribute(stack, nbt, world.rand);
		Attribute stun = new AttributeStun();
		stun.setBaseValue(0.2);
		stun.addAttribute(stack, nbt, world.rand);
		Attribute slow = new AttributeSlow();
		slow.setBaseValue(0.2);
		slow.addAttribute(stack, nbt, world.rand);
	}
}

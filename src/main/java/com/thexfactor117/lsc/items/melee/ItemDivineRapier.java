package com.thexfactor117.lsc.items.melee;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.items.base.weapons.ISpecial;
import com.thexfactor117.lsc.items.base.weapons.ItemMelee;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeAttackSpeed;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeCriticalDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFireDamage;

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
	public ItemDivineRapier(ToolMaterial material, String name)
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
		Rarity.setRarity(nbt, Rarity.EPIC);
		nbt.setInteger("Level", level);
		
		Attribute fire = new AttributeFireDamage();
		fire.setBaseValue(5);
		fire.addAttribute(stack, nbt, world.rand);
		Attribute attackSpeed = new AttributeAttackSpeed();
		attackSpeed.setBaseValue(0.2);
		attackSpeed.addAttribute(stack, nbt, world.rand);
		Attribute criticalDamage = new AttributeCriticalDamage();
		criticalDamage.setBaseValue(0.3);
		criticalDamage.addAttribute(stack, nbt, world.rand);
	}
}

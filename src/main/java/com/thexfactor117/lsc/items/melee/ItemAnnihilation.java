package com.thexfactor117.lsc.items.melee;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.items.base.weapons.ISpecial;
import com.thexfactor117.lsc.items.base.weapons.ItemMelee;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeBonusExperience;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeLifeSteal;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeManaSteal;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributePoisonDamage;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemAnnihilation extends ItemMelee implements ISpecial
{
	public ItemAnnihilation(ToolMaterial material, String name, double damageMultiplier, double speedMultiplier)
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
		
		Attribute lifeSteal = new AttributeLifeSteal();
		lifeSteal.setBaseValue(0.15);
		lifeSteal.addAttribute(stack, nbt, world.rand);
		Attribute manaSteal = new AttributeManaSteal();
		manaSteal.setBaseValue(0.15);
		manaSteal.addAttribute(stack, nbt, world.rand);
		Attribute bonusXP = new AttributeBonusExperience();
		bonusXP.setBaseValue(0.1);
		bonusXP.addAttribute(stack, nbt, world.rand);
		Attribute poison = new AttributePoisonDamage();
		poison.setBaseValue(5);
		poison.addAttribute(stack, nbt, world.rand);
	}
}

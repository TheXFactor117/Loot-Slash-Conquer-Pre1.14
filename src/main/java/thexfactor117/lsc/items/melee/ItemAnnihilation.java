package thexfactor117.lsc.items.melee;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import thexfactor117.lsc.capabilities.api.IChunkLevel;
import thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import thexfactor117.lsc.init.ModTabs;
import thexfactor117.lsc.items.base.ISpecial;
import thexfactor117.lsc.items.base.ItemAdvancedMelee;
import thexfactor117.lsc.loot.Attribute;
import thexfactor117.lsc.loot.Rarity;
import thexfactor117.lsc.loot.generation.ItemGeneratorHelper;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemAnnihilation extends ItemAdvancedMelee implements ISpecial
{
	public ItemAnnihilation(ToolMaterial material, String name, String type, double damageMultiplier, double speedMultiplier)
	{
		super(material, name, type, damageMultiplier, speedMultiplier);
		this.setCreativeTab(ModTabs.lscTab);
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
		
		// Attributes
		Attribute.AGILITY.addAttribute(nbt, world.rand, 10);
		Attribute.DEXTERITY.addAttribute(nbt, world.rand, 10);
		Attribute.GOLD.addAttribute(nbt, world.rand, 10);
		Attribute.LIFE_STEAL.addAttribute(nbt, world.rand, 0.1);
		Attribute.MANA_STEAL.addAttribute(nbt, world.rand, 0.1);
		
		ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
	}
}

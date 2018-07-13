package thexfactor117.lsc.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import thexfactor117.lsc.loot.Rarity;
import thexfactor117.lsc.util.Reference;

/**
 *
 * @author TheXFactor117
 *
 */
public class TileEntityLootChest extends TileEntityChest
{
	public Rarity rarity;
	
	public TileEntityLootChest() {}
	
	public TileEntityLootChest(Rarity rarity)
	{
		super(BlockChest.Type.BASIC);
		this.rarity = rarity;
		this.setInitialLootTable(rarity);
	}
	
	/**
	 * Override this because we don't want to check for adjacent chests.
	 */
	@Nullable
	@Override
	protected TileEntityChest getAdjacentChest(EnumFacing side)
    {
		return null;
    }
	
	/**
	 * Override this because we don't want to check for adjacent chests.
	 */
	@Override
	public void checkForAdjacentChests()
    {
		
    }
	
	/**
	 * Sets the loot table of the Loot Chest.
	 * @param rarity
	 */
	public void setInitialLootTable(Rarity rarity)
	{
		switch (rarity)
		{
			case COMMON:
				this.lootTable = new ResourceLocation(Reference.MODID, "chests/common_chest");
				break;
			case UNCOMMON:
				this.lootTable = new ResourceLocation(Reference.MODID, "chests/uncommon_chest");
				break;
			case RARE:
				this.lootTable = new ResourceLocation(Reference.MODID, "chests/rare_chest");
				break;
			case EPIC:
				this.lootTable = new ResourceLocation(Reference.MODID, "chests/epic_chest");
				break;
			case LEGENDARY:
				this.lootTable = new ResourceLocation(Reference.MODID, "chests/legendary_chest");
				break;
			default:
				break;
		}
	}
}

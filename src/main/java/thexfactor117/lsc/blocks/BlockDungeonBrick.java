package thexfactor117.lsc.blocks;

import net.minecraft.block.material.Material;
import thexfactor117.lsc.init.ModTabs;

/**
 * 
 * @author TheXFactor117
 *
 */
public class BlockDungeonBrick extends BlockBase
{
	public BlockDungeonBrick(Material material, String name)
	{
		super(material, name, ModTabs.lscTab);
		this.setBlockUnbreakable();
		this.setResistance(100000); // TODO: find a less hacky way?
	}
}

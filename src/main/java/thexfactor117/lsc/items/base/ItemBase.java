package thexfactor117.lsc.items.base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import thexfactor117.lsc.util.Reference;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemBase extends Item
{
	public ItemBase(String name)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
	}
	
	public ItemBase(String name, CreativeTabs tab)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(tab);
	}
}

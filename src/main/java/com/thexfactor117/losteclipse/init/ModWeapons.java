package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.items.base.ItemTest;
import com.thexfactor117.losteclipse.items.melee.ItemMHSAdvancedMelee;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModWeapons 
{
	public static Item test = new ItemTest("test");
	
	// vanilla
	public static Item woodDagger = new ItemMHSAdvancedMelee(ToolMaterial.WOOD, "wood_dagger", 0.5, 0.5, 90);
	public static Item woodMace = new ItemMHSAdvancedMelee(ToolMaterial.WOOD, "wood_mace", 1.25, 1.25, 45);
	public static Item stoneDagger = new ItemMHSAdvancedMelee(ToolMaterial.STONE, "stone_dagger", 0.5, 0.5, 195);
	public static Item stoneMace = new ItemMHSAdvancedMelee(ToolMaterial.STONE, "stone_mace", 1.25, 1.25, 97);
	public static Item goldDagger = new ItemMHSAdvancedMelee(ToolMaterial.GOLD, "gold_dagger", 0.5, 0.5, 45);
	public static Item goldMace = new ItemMHSAdvancedMelee(ToolMaterial.GOLD, "gold_mace", 1.25, 1.25, 22);
	public static Item ironDagger = new ItemMHSAdvancedMelee(ToolMaterial.IRON, "iron_dagger", 0.5, 0.5, 376);
	public static Item ironMace = new ItemMHSAdvancedMelee(ToolMaterial.IRON, "iron_mace", 1.25, 1.25, 188);
	public static Item diamondDagger = new ItemMHSAdvancedMelee(ToolMaterial.DIAMOND, "diamond_dagger", 0.5, 0.5, 2343);
	public static Item diamondMace = new ItemMHSAdvancedMelee(ToolMaterial.DIAMOND, "diamond_mace", 1.25, 1.25, 1171);
	
	@SubscribeEvent
	public void registerItem(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(test);
		
		event.getRegistry().register(woodDagger);
		event.getRegistry().register(woodMace);
		event.getRegistry().register(stoneDagger);
		event.getRegistry().register(stoneMace);
		event.getRegistry().register(goldDagger);
		event.getRegistry().register(goldMace);
		event.getRegistry().register(ironDagger);
		event.getRegistry().register(ironMace);
		event.getRegistry().register(diamondDagger);
		event.getRegistry().register(diamondMace);
		
		ModelLoader.setCustomModelResourceLocation(woodDagger, 0, new ModelResourceLocation(woodDagger.getRegistryName(), "inventory"));
	}
}

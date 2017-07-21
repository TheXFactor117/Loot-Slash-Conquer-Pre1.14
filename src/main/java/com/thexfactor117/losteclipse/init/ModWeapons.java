package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.items.base.ItemLEMelee;
import com.thexfactor117.losteclipse.items.base.ItemTest;
import com.thexfactor117.losteclipse.items.melee.ItemLEAdvancedMelee;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ModWeapons 
{
	/* Tool Materials */
	public static final ToolMaterial VERANTIUM = EnumHelper.addToolMaterial("verantium", 2, 250, 6F, 2F, 14);
	public static final ToolMaterial VEXAL = EnumHelper.addToolMaterial("vexal", 2, 648, 6.5F, 2.5F, 10);
	public static final ToolMaterial ASTRILL = EnumHelper.addToolMaterial("astrill", 3, 2000, 7.0F, 4.0F, 15);
	
	
	/* Weapons */
	public static Item test = new ItemTest("test");
	
	// melee
	public static final Item VERANTIUM_DAGGER = new ItemLEAdvancedMelee(VERANTIUM, "verantium_dagger", 0.5, 0.5, 376);
	public static final Item VERANTIUM_SWORD = new ItemLEMelee(VERANTIUM, "verantium_sword");
	public static final Item VERANTIUM_MACE = new ItemLEAdvancedMelee(VERANTIUM, "verantium_mace", 1.25, 1.25, 188);
	public static final Item VEXAL_DAGGER = new ItemLEAdvancedMelee(VEXAL, "vexal_dagger", 0.5, 0.5, 972);
	public static final Item VEXAL_SWORD = new ItemLEMelee(VEXAL, "vexal_sword");
	public static final Item VEXAL_MACE = new ItemLEAdvancedMelee(VEXAL, "vexal_mace", 1.25, 1.25, 486);
	public static final Item ASTRILL_DAGGER = new ItemLEAdvancedMelee(ASTRILL, "astrill_dagger", 0.5, 0.5, 1500);
	public static final Item ASTRILL_SWORD = new ItemLEMelee(ASTRILL, "astrill_sword");
	public static final Item ASTRILL_MACE = new ItemLEAdvancedMelee(ASTRILL, "astrill_mace", 1.25, 1.25, 2500);
	
	// vanilla
	public static final Item WOOD_DAGGER = new ItemLEAdvancedMelee(ToolMaterial.WOOD, "wood_dagger", 0.5, 0.5, 90);
	public static final Item WOOD_MACE = new ItemLEAdvancedMelee(ToolMaterial.WOOD, "wood_mace", 1.25, 1.25, 45);
	public static final Item STONE_DAGGER = new ItemLEAdvancedMelee(ToolMaterial.STONE, "stone_dagger", 0.5, 0.5, 195);
	public static final Item STONE_MACE = new ItemLEAdvancedMelee(ToolMaterial.STONE, "stone_mace", 1.25, 1.25, 97);
	public static final Item GOLD_DAGGER = new ItemLEAdvancedMelee(ToolMaterial.GOLD, "gold_dagger", 0.5, 0.5, 45);
	public static final Item GOLD_MACE = new ItemLEAdvancedMelee(ToolMaterial.GOLD, "gold_mace", 1.25, 1.25, 22);
	public static final Item IRON_DAGGER = new ItemLEAdvancedMelee(ToolMaterial.IRON, "iron_dagger", 0.5, 0.5, 376);
	public static final Item IRON_MACE = new ItemLEAdvancedMelee(ToolMaterial.IRON, "iron_mace", 1.25, 1.25, 188);
	public static final Item DIAMOND_DAGGER = new ItemLEAdvancedMelee(ToolMaterial.DIAMOND, "diamond_dagger", 0.5, 0.5, 2343);
	public static final Item DIAMOND_MACE = new ItemLEAdvancedMelee(ToolMaterial.DIAMOND, "diamond_mace", 1.25, 1.25, 1171);
	
	
	/* Armors */
	
	@SubscribeEvent
	public void registerItem(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(test);
		
		// vanilla
		event.getRegistry().register(WOOD_DAGGER);
		event.getRegistry().register(WOOD_MACE);
		event.getRegistry().register(STONE_DAGGER);
		event.getRegistry().register(STONE_MACE);
		event.getRegistry().register(GOLD_DAGGER);
		event.getRegistry().register(GOLD_MACE);
		event.getRegistry().register(IRON_DAGGER);
		event.getRegistry().register(IRON_MACE);
		event.getRegistry().register(DIAMOND_DAGGER);
		event.getRegistry().register(DIAMOND_MACE);
	}
	
	@SubscribeEvent
	public void registerItemModel(ModelRegistryEvent event)
	{
		registerItemModel(WOOD_DAGGER);
		registerItemModel(WOOD_MACE);
		registerItemModel(STONE_DAGGER);
		registerItemModel(STONE_MACE);
		registerItemModel(GOLD_DAGGER);
		registerItemModel(GOLD_MACE);
		registerItemModel(IRON_DAGGER);
		registerItemModel(IRON_MACE);
		registerItemModel(DIAMOND_DAGGER);
		registerItemModel(DIAMOND_MACE);
	}
	
	private void registerItemModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}

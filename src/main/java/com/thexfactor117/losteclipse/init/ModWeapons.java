package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.items.base.ItemLEMelee;
import com.thexfactor117.losteclipse.items.base.ItemTest;
import com.thexfactor117.losteclipse.items.melee.ItemLEAdvancedMelee;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
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
	// craftable
	public static final ToolMaterial VERANTIUM = EnumHelper.addToolMaterial("verantium", 2, 250, 6F, 2F, 14);
	public static final ToolMaterial CRYSTAL = EnumHelper.addToolMaterial("crystal", 2, 256, 6.0F, 3.0F, 25);
	public static final ToolMaterial SHADOW = EnumHelper.addToolMaterial("shadow", 2, 384, 6.0F, 4.0F, 20);
	public static final ToolMaterial VEXAL = EnumHelper.addToolMaterial("vexal", 2, 648, 6.5F, 2.5F, 10);
	public static final ToolMaterial MALICE = EnumHelper.addToolMaterial("malice", 2, 448, 6.0F, 4.0F, 10);
	public static final ToolMaterial GYRO = EnumHelper.addToolMaterial("gyro", 2, 198, 6.0F, 6.0F, 15);
	public static final ToolMaterial VOID = EnumHelper.addToolMaterial("void", 2, 448, 6.0F, 12.0F, 15);
	public static final ToolMaterial ASTRILL = EnumHelper.addToolMaterial("astrill", 3, 2000, 7.0F, 4.0F, 15);
	
	// special
	public static final ToolMaterial DARK_MALICE = EnumHelper.addToolMaterial("dark_malice", 2, 480, 6.0F, 7.0F, 25);
	public static final ToolMaterial DIVINE = EnumHelper.addToolMaterial("divine", 2, 512, 6.0F, 6.0F, 25);
	public static final ToolMaterial EXCALIBUR = EnumHelper.addToolMaterial("excalibur", 2, 648, 6.0F, 8.0F, 15);
	
	
	/* Weapons */
	public static Item test = new ItemTest("test");
	
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
	
	// melee craftable
	public static final Item VERANTIUM_DAGGER = new ItemLEAdvancedMelee(VERANTIUM, "verantium_dagger", 0.5, 0.5, 376);
	public static final Item VERANTIUM_SWORD = new ItemLEMelee(VERANTIUM, "verantium_sword");
	public static final Item VERANTIUM_MACE = new ItemLEAdvancedMelee(VERANTIUM, "verantium_mace", 1.25, 1.25, 188);
	public static final Item LIGHT_CRYSTAL_SWORD = new ItemLEMelee(CRYSTAL, "light_crystal_sword");
	public static final Item DARK_CRYSTAL_SWORD = new ItemLEMelee(CRYSTAL, "dark_crystal_sword");
	public static final Item SHADOW_BLADE = new ItemLEMelee(SHADOW, "shadow_blade");
	public static final Item VEXAL_DAGGER = new ItemLEAdvancedMelee(VEXAL, "vexal_dagger", 0.5, 0.5, 972);
	public static final Item VEXAL_SWORD = new ItemLEMelee(VEXAL, "vexal_sword");
	public static final Item VEXAL_MACE = new ItemLEAdvancedMelee(VEXAL, "vexal_mace", 1.25, 1.25, 486);
	public static final Item MALICE_BLADE = new ItemLEMelee(MALICE, "malice_blade");
	public static final Item GYRO_MACE = new ItemLEAdvancedMelee(GYRO, "gyro_mace", 1, 1.25);
	public static final Item VOID_HAMMER = new ItemLEAdvancedMelee(VOID, "void_hammer", 1, 1.25);
	public static final Item ASTRILL_DAGGER = new ItemLEAdvancedMelee(ASTRILL, "astrill_dagger", 0.5, 0.5, 1500);
	public static final Item ASTRILL_SWORD = new ItemLEMelee(ASTRILL, "astrill_sword");
	public static final Item ASTRILL_MACE = new ItemLEAdvancedMelee(ASTRILL, "astrill_mace", 1.25, 1.25, 2500);
	
	// melee special
	public static final Item DARK_MALICE_BLADE = new ItemLEMelee(DARK_MALICE, "dark_malice_blade"); // add Legendary Rarity
	public static final Item DIVINE_RAPIER = new ItemLEMelee(DIVINE, "divine_rapier"); // add Legendary Rarity
	public static final Item EXCALIBUR_RAPIER = new ItemLEMelee(EXCALIBUR, "excalibur_rapier"); // add Exotic Rarity
	
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
		
		// melee craftable
		event.getRegistry().register(VERANTIUM_DAGGER);
		event.getRegistry().register(VERANTIUM_SWORD);
		event.getRegistry().register(VERANTIUM_MACE);
		event.getRegistry().register(LIGHT_CRYSTAL_SWORD);
		event.getRegistry().register(DARK_CRYSTAL_SWORD);
		event.getRegistry().register(SHADOW_BLADE);
		event.getRegistry().register(VEXAL_DAGGER);
		event.getRegistry().register(VEXAL_SWORD);
		event.getRegistry().register(VEXAL_MACE);
		event.getRegistry().register(MALICE_BLADE);
		event.getRegistry().register(GYRO_MACE);
		event.getRegistry().register(VOID_HAMMER);
		event.getRegistry().register(ASTRILL_DAGGER);
		event.getRegistry().register(ASTRILL_SWORD);
		event.getRegistry().register(ASTRILL_MACE);
		
		// melee special
		event.getRegistry().register(DARK_MALICE_BLADE);
		event.getRegistry().register(DIVINE_RAPIER);
		event.getRegistry().register(EXCALIBUR_RAPIER);
	}
}

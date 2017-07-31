package com.thexfactor117.losteclipse.init;

import com.thexfactor117.losteclipse.items.base.ItemTest;
import com.thexfactor117.losteclipse.items.magical.ItemLEWand;
import com.thexfactor117.losteclipse.items.melee.ItemLEAdvancedMelee;
import com.thexfactor117.losteclipse.items.melee.ItemLEMelee;

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
	public static class ToolMaterials
	{
		/* Tool Materials */
		// special
		public static final ToolMaterial DIVINE = EnumHelper.addToolMaterial("divine", 3, 512, 6F, 6F, 25); // sword
		public static final ToolMaterial REQUIEM = EnumHelper.addToolMaterial("requiem", 3, 512, 6F, 7F, 15); // sword
		public static final ToolMaterial SHADOWFALL = EnumHelper.addToolMaterial("shadowfall", 3, 960, 6F, 4F, 20); // dagger
		public static final ToolMaterial DOOMSHADOW = EnumHelper.addToolMaterial("doomshadow", 3, 310, 6F, 10F, 17); // mace
		public static final ToolMaterial GOLDEN_PUMMEL = EnumHelper.addToolMaterial("golden_pummel", 3, 250, 6F, 12F, 10); // mace
		
		public static final ToolMaterial EXCALIBUR = EnumHelper.addToolMaterial("excalibur", 3, 648, 6.0F, 10.0F, 15); // sword
		public static final ToolMaterial ALAKASLAM = EnumHelper.addToolMaterial("alakaslam", 3, 450, 6F, 15F, 12); // mace
		public static final ToolMaterial ANNIHILATION = EnumHelper.addToolMaterial("annihilation", 3, 1200, 6F, 6F, 20); // dagger
	}
	
	
	/* Weapons */
	public static Item test = new ItemTest("test");
	
	// melee
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
	
	// melee special
	public static final Item DIVINE_RAPIER = new ItemLEMelee(ToolMaterials.DIVINE, "divine_rapier"); // add Legendary Rarity
	public static final Item REQUIEM = new ItemLEMelee(ToolMaterials.REQUIEM, "requiem");
	public static final Item SHADOWFALL = new ItemLEAdvancedMelee(ToolMaterials.SHADOWFALL, "shadowfall", 1, 0.5);
	public static final Item DOOMSHADOW = new ItemLEAdvancedMelee(ToolMaterials.DOOMSHADOW, "doomshadow", 1, 1.25);
	public static final Item GOLDEN_PUMMEL = new ItemLEAdvancedMelee(ToolMaterials.GOLDEN_PUMMEL, "golden_pummel", 1, 1.25);
	
	public static final Item EXCALIBUR_RAPIER = new ItemLEMelee(ToolMaterials.EXCALIBUR, "excalibur_rapier"); // add Exotic Rarity
	public static final Item ALAKASLAM = new ItemLEAdvancedMelee(ToolMaterials.ALAKASLAM, "alakaslam", 1, 1.25);
	public static final Item ANNIHILATION = new ItemLEAdvancedMelee(ToolMaterials.ANNIHILATION, "annihilation", 1, 0.5);
	
	
	// ranged
	// (name, damage, attack speed, mana per use, durability)
	public static final Item WOODEN_WAND = new ItemLEWand("wooden_wand", 4, 2, 5, 200);
	
	
	/* Armors */
	
	@SubscribeEvent
	public void registerItem(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(test);
		
		// melee
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
		
		// melee special
		event.getRegistry().register(DIVINE_RAPIER);
		event.getRegistry().register(REQUIEM);
		event.getRegistry().register(SHADOWFALL);
		event.getRegistry().register(DOOMSHADOW);
		event.getRegistry().register(GOLDEN_PUMMEL);
		
		event.getRegistry().register(EXCALIBUR_RAPIER);
		event.getRegistry().register(ALAKASLAM);
		event.getRegistry().register(ANNIHILATION);
		
		
		// ranged
		event.getRegistry().register(WOODEN_WAND);
	}
}

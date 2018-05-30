 package com.lsc.init;

import com.lsc.items.base.ItemAdvancedMelee;
import com.lsc.items.base.ItemMagical;
import com.lsc.items.base.ItemRanged;
import com.lsc.items.base.ItemTest;
import com.lsc.items.magical.ItemBlazefury;
import com.lsc.items.magical.ItemEpilogue;
import com.lsc.items.magical.ItemGazeOfTruth;
import com.lsc.items.magical.ItemMoonlitRod;
import com.lsc.items.magical.ItemVisageOfWizardry;
import com.lsc.items.melee.ItemAlakaslam;
import com.lsc.items.melee.ItemAnnihilation;
import com.lsc.items.melee.ItemDivineRapier;
import com.lsc.items.melee.ItemDoomshadow;
import com.lsc.items.melee.ItemExcaliburRapier;
import com.lsc.items.melee.ItemGoldenPummel;
import com.lsc.items.melee.ItemRequiem;
import com.lsc.items.melee.ItemShadowfall;

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
	public static final Item WOOD_DAGGER = new ItemAdvancedMelee(ToolMaterial.WOOD, "wood_dagger", "dagger", 0.5, 0.5, 90);
	public static final Item WOOD_MACE = new ItemAdvancedMelee(ToolMaterial.WOOD, "wood_mace", "mace", 1.25, 1.25, 45);
	public static final Item STONE_DAGGER = new ItemAdvancedMelee(ToolMaterial.STONE, "stone_dagger", "dagger", 0.5, 0.5, 195);
	public static final Item STONE_MACE = new ItemAdvancedMelee(ToolMaterial.STONE, "stone_mace", "mace", 1.25, 1.25, 97);
	public static final Item GOLD_DAGGER = new ItemAdvancedMelee(ToolMaterial.GOLD, "gold_dagger", "dagger", 0.5, 0.5, 45);
	public static final Item GOLD_MACE = new ItemAdvancedMelee(ToolMaterial.GOLD, "gold_mace", "mace", 1.25, 1.25, 22);
	public static final Item IRON_DAGGER = new ItemAdvancedMelee(ToolMaterial.IRON, "iron_dagger", "dagger", 0.5, 0.5, 376);
	public static final Item IRON_MACE = new ItemAdvancedMelee(ToolMaterial.IRON, "iron_mace", "mace", 1.25, 1.25, 188);
	public static final Item DIAMOND_DAGGER = new ItemAdvancedMelee(ToolMaterial.DIAMOND, "diamond_dagger", "dagger", 0.5, 0.5, 2343);
	public static final Item DIAMOND_MACE = new ItemAdvancedMelee(ToolMaterial.DIAMOND, "diamond_mace", "mace", 1.25, 1.25, 1171);
	
	// melee special
	public static final Item DIVINE_RAPIER = new ItemDivineRapier(ToolMaterials.DIVINE, "divine_rapier", "sword"); // add Legendary Rarity
	public static final Item REQUIEM = new ItemRequiem(ToolMaterials.REQUIEM, "requiem", "sword");
	public static final Item SHADOWFALL = new ItemShadowfall(ToolMaterials.SHADOWFALL, "shadowfall", "dagger", 1, 0.5);
	public static final Item DOOMSHADOW = new ItemDoomshadow(ToolMaterials.DOOMSHADOW, "doomshadow", "mace", 1, 1.25);
	public static final Item GOLDEN_PUMMEL = new ItemGoldenPummel(ToolMaterials.GOLDEN_PUMMEL, "golden_pummel", "mace", 1, 1.25);
	
	public static final Item EXCALIBUR_RAPIER = new ItemExcaliburRapier(ToolMaterials.EXCALIBUR, "excalibur_rapier", "sword"); // add Exotic Rarity
	public static final Item ALAKASLAM = new ItemAlakaslam(ToolMaterials.ALAKASLAM, "alakaslam", "mace", 1, 1.25);
	public static final Item ANNIHILATION = new ItemAnnihilation(ToolMaterials.ANNIHILATION, "annihilation", "dagger", 1, 0.5);
	
	
	
	// physical ranged
	public static final Item GOLDEN_BOW = new ItemRanged("golden_bow", 4, 2, 100);
	public static final Item IRON_BOW = new ItemRanged("iron_bow", 5, 2, 500);
	public static final Item DIAMOND_BOW = new ItemRanged("diamond_bow", 6, 2, 1000);
	
	
	// magical ranged
	// (name, damage, attack speed, mana per use, durability)
	public static final Item WOODEN_WAND = new ItemMagical("wooden_wand", false, 3, 2, 5, 200);
	public static final Item GOLDEN_WAND = new ItemMagical("golden_wand", false, 4, 2, 5, 100);
	public static final Item DIAMOND_WAND = new ItemMagical("diamond_wand", false, 5, 2, 5, 500);
	public static final Item WOODEN_STAFF = new ItemMagical("wooden_staff", true, 5, 1.25, 10, 200);
	public static final Item GOLDEN_STAFF = new ItemMagical("golden_staff", true, 6, 1.25, 10, 100);
	public static final Item DIAMOND_STAFF = new ItemMagical("diamond_staff", true, 7, 1.25, 10, 500);
	
	// magical special
	public static final Item BLAZEFURY = new ItemBlazefury("blazefury", false, 7, 2, 5, 400);
	public static final Item MOONLIT_ROD = new ItemMoonlitRod("moonlit_rod", false, 8, 2, 5, 375);
	public static final Item EPILOGUE = new ItemEpilogue("epilogue", true, 11, 1.25, 10, 600);
	
	public static final Item GAZE_OF_TRUTH = new ItemGazeOfTruth("gaze_of_truth", true, 15, 1.25, 10, 800);
	public static final Item VISAGE_OF_WIZARDRY = new ItemVisageOfWizardry("visage_of_wizardry", false, 11, 2, 5, 700);
	
	
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
		
		
		
		// physical ranged
		event.getRegistry().register(GOLDEN_BOW);
		event.getRegistry().register(IRON_BOW);
		event.getRegistry().register(DIAMOND_BOW);
		
		
		
		// magical ranged
		event.getRegistry().register(WOODEN_WAND);
		event.getRegistry().register(GOLDEN_WAND);
		event.getRegistry().register(DIAMOND_WAND);
		event.getRegistry().register(WOODEN_STAFF);
		event.getRegistry().register(GOLDEN_STAFF);
		event.getRegistry().register(DIAMOND_STAFF);
		
		// magical special
		event.getRegistry().register(BLAZEFURY);
		event.getRegistry().register(MOONLIT_ROD);
		event.getRegistry().register(EPILOGUE);
		
		event.getRegistry().register(GAZE_OF_TRUTH);
		event.getRegistry().register(VISAGE_OF_WIZARDRY);
	}
}

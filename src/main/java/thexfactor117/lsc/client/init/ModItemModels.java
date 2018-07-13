package thexfactor117.lsc.client.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thexfactor117.lsc.init.ModBlocks;
import thexfactor117.lsc.init.ModItems;
import thexfactor117.lsc.init.ModWeapons;

/**
 * 
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ModItemModels 
{
	@SubscribeEvent
	public static void registerItemModel(ModelRegistryEvent event)
	{
		/*
		 * ITEMS
		 */
		
		// jewelry
		registerItemModel(ModItems.GOLDEN_RING);
		registerItemModel(ModItems.DIAMOND_RING);
		registerItemModel(ModItems.GOLDEN_AMULET);
		registerItemModel(ModItems.DIAMOND_AMULET);
		registerItemModel(ModItems.LEATHER_SASH);
		
		// scrolls
		registerItemModel(ModItems.FIREBALL_SCROLL);
		registerItemModel(ModItems.FROSTBITE_SCROLL);
		registerItemModel(ModItems.MINOR_ETHEREAL_SCROLL);
		registerItemModel(ModItems.FIRESTORM_SCROLL);
		registerItemModel(ModItems.BLIZZARD_SCROLL);
		registerItemModel(ModItems.DISCHARGE_SCROLL);
		registerItemModel(ModItems.INVISIBILITY_SCROLL);
		registerItemModel(ModItems.MAJOR_ETHEREAL_SCROLL);
		registerItemModel(ModItems.VOID_SCROLL);
		
		// misc
		registerItemModel(ModItems.CORRUPTED_TOWER_KEY);
		
		
		
		/*
		 * WEAPONS
		 */
		
		// melee
		registerItemModel(ModWeapons.WOOD_DAGGER);
		registerItemModel(ModWeapons.WOOD_MACE);
		registerItemModel(ModWeapons.STONE_DAGGER);
		registerItemModel(ModWeapons.STONE_MACE);
		registerItemModel(ModWeapons.GOLD_DAGGER);
		registerItemModel(ModWeapons.GOLD_MACE);
		registerItemModel(ModWeapons.IRON_DAGGER);
		registerItemModel(ModWeapons.IRON_MACE);
		registerItemModel(ModWeapons.DIAMOND_DAGGER);
		registerItemModel(ModWeapons.DIAMOND_MACE);

		// melee special
		registerItemModel(ModWeapons.DIVINE_RAPIER);
		registerItemModel(ModWeapons.REQUIEM);
		registerItemModel(ModWeapons.SHADOWFALL);
		registerItemModel(ModWeapons.DOOMSHADOW);
		registerItemModel(ModWeapons.GOLDEN_PUMMEL);
		
		registerItemModel(ModWeapons.EXCALIBUR_RAPIER);
		registerItemModel(ModWeapons.ALAKASLAM);
		registerItemModel(ModWeapons.ANNIHILATION);
		
		// physical ranged
		registerItemModel(ModWeapons.GOLDEN_BOW);
		registerItemModel(ModWeapons.IRON_BOW);
		registerItemModel(ModWeapons.DIAMOND_BOW);
		
		// magical ranged
		registerItemModel(ModWeapons.WOODEN_STAFF);
		registerItemModel(ModWeapons.GOLDEN_STAFF);
		registerItemModel(ModWeapons.DIAMOND_STAFF);
		
		// magical special
		registerItemModel(ModWeapons.BLAZEFURY);
		registerItemModel(ModWeapons.MOONLIT_ROD);
		registerItemModel(ModWeapons.EPILOGUE);
		
		registerItemModel(ModWeapons.GAZE_OF_TRUTH);
		registerItemModel(ModWeapons.VISAGE_OF_WIZARDRY);
		
		
		
		/*
		 * BLOCKS
		 */
		registerItemModel(Item.getItemFromBlock(ModBlocks.DUNGEON_BRICK));
		registerItemModel(Item.getItemFromBlock(ModBlocks.BOSS_DOOR));
		
		if ((boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))
		{
			//registerItemModel(Item.getItemFromBlock(ModBlocks.COMMON_LOOT_CHEST));
			//registerItemModel(Item.getItemFromBlock(ModBlocks.UNCOMMON_LOOT_CHEST));
			//registerItemModel(Item.getItemFromBlock(ModBlocks.RARE_LOOT_CHEST));
			//registerItemModel(Item.getItemFromBlock(ModBlocks.EPIC_LOOT_CHEST));
			//registerItemModel(Item.getItemFromBlock(ModBlocks.LEGENDARY_LOOT_CHEST));
			registerItemModel(Item.getItemFromBlock(ModBlocks.COMMON_JAR));
			registerItemModel(Item.getItemFromBlock(ModBlocks.UNCOMMON_JAR));
			registerItemModel(Item.getItemFromBlock(ModBlocks.RARE_JAR));
			registerItemModel(Item.getItemFromBlock(ModBlocks.EPIC_JAR));
			registerItemModel(Item.getItemFromBlock(ModBlocks.LEGENDARY_JAR));
			registerItemModel(Item.getItemFromBlock(ModBlocks.COMMON_BARREL));
			registerItemModel(Item.getItemFromBlock(ModBlocks.UNCOMMON_BARREL));
			registerItemModel(Item.getItemFromBlock(ModBlocks.RARE_BARREL));
			registerItemModel(Item.getItemFromBlock(ModBlocks.EPIC_BARREL));
			registerItemModel(Item.getItemFromBlock(ModBlocks.LEGENDARY_BARREL));
			registerItemModel(Item.getItemFromBlock(ModBlocks.COMMON_CRATE));
			registerItemModel(Item.getItemFromBlock(ModBlocks.UNCOMMON_CRATE));
			registerItemModel(Item.getItemFromBlock(ModBlocks.RARE_CRATE));
			registerItemModel(Item.getItemFromBlock(ModBlocks.EPIC_CRATE));
			registerItemModel(Item.getItemFromBlock(ModBlocks.LEGENDARY_CRATE));
		}
	}
	
	private static void registerItemModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}

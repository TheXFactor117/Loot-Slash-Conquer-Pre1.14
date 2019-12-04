package com.thexfactor117.lsc.init;

import baubles.api.BaubleType;
import com.thexfactor117.lsc.items.base.ItemBase;
import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.items.scrolls.*;
import com.thexfactor117.lsc.loot.Rarity;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class ModItems
{
	// jewelry
	public static final Item GOLDEN_RING = new ItemBauble("golden_ring", BaubleType.RING);
	public static final Item DIAMOND_RING = new ItemBauble("diamond_ring", BaubleType.RING);
	public static final Item GOLDEN_AMULET = new ItemBauble("golden_amulet", BaubleType.AMULET);
	public static final Item DIAMOND_AMULET = new ItemBauble("diamond_amulet", BaubleType.AMULET);
	public static final Item LEATHER_SASH = new ItemBauble("leather_sash", BaubleType.BELT);
	
	// scrolls
	public static final Item FIREBALL_SCROLL = new ItemFireballScroll("fireball_scroll", Rarity.UNCOMMON);
	public static final Item FROSTBITE_SCROLL = new ItemFrostbiteScroll("frostbite_scroll", Rarity.UNCOMMON);
	public static final Item MINOR_ETHEREAL_SCROLL = new ItemMinorEtherealScroll("minor_ethereal_scroll", Rarity.RARE);
	public static final Item FIRESTORM_SCROLL = new ItemFirestormScroll("firestorm_scroll", Rarity.RARE);
	public static final Item BLIZZARD_SCROLL = new ItemBlizzardScroll("blizzard_scroll", Rarity.RARE);
	public static final Item DISCHARGE_SCROLL = new ItemDischargeScroll("discharge_scroll", Rarity.RARE);
	public static final Item INVISIBILITY_SCROLL = new ItemInvisibilityScroll("invisibility_scroll", Rarity.EPIC);
	public static final Item MAJOR_ETHEREAL_SCROLL = new ItemMajorEtherealScroll("major_ethereal_scroll", Rarity.EPIC);
	public static final Item VOID_SCROLL = new ItemVoidScroll("void_scroll", Rarity.LEGENDARY);
	
	// miscellaneous
	public static final Item CORRUPTED_TOWER_KEY = new ItemBase("corrupted_tower_key", ModTabs.lscTab);
		
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		// jewelry
		event.getRegistry().register(GOLDEN_RING);
		event.getRegistry().register(DIAMOND_RING);
		event.getRegistry().register(GOLDEN_AMULET);
		event.getRegistry().register(DIAMOND_AMULET);
		event.getRegistry().register(LEATHER_SASH);
		
		// scrolls
		event.getRegistry().register(FIREBALL_SCROLL);
		event.getRegistry().register(FROSTBITE_SCROLL);
		event.getRegistry().register(MINOR_ETHEREAL_SCROLL);
		event.getRegistry().register(FIRESTORM_SCROLL);
		event.getRegistry().register(BLIZZARD_SCROLL);
		event.getRegistry().register(DISCHARGE_SCROLL);
		event.getRegistry().register(INVISIBILITY_SCROLL);
		event.getRegistry().register(MAJOR_ETHEREAL_SCROLL);
		event.getRegistry().register(VOID_SCROLL);
		
		// miscellaneous
		event.getRegistry().register(CORRUPTED_TOWER_KEY);
	}
}

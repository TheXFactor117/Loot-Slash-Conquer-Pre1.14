package com.thexfactor117.losteclipse.loot;

/**
 * 
 * @author TheXFactor117
 *
 */
public class NameHelper 
{
	public static final String[] PREFIXES = new String[] { "Fine", "Pure", "Sacred", "Unearthly", "Holy" };
	
	public static final String[] DAGGER_SUFFIXES = new String[] { "Dagger", "Shortblade", "Reaver", "Sabre", "Slicer", "Razor", "Knife", "Sculptor" };
	public static final String[] SWORD_SUFFIXES = new String[] { "Sword", "Blade", "Rapier", "Skewer", "Scimitar", "Broadsword", "Katana" };
	public static final String[] MACE_SUFFIXES = new String[] { "Mace", "Rock", "Impaler", "Smasher", "Pummel", "Hammer", "Bludgeon", "Crusher" };
	public static final String[] WAND_SUFFIXES = new String[] { "Wand", "Stick", "Spell", "Baton", "Crystal", "Harp", "Charm" };
	public static final String[] STAFF_SUFFIXES = new String[] { "Staff", "Spire", "Branch", "Visage", "Cane", "Pole", "War Staff" };
	
	public static final String[] HELMET_SUFFIXES = new String[] { "Helmet", "Helm", "Cap", "Hood", "Facemask", "Bandana" };
	public static final String[] CHESTPLATE_SUFFIXES = new String[] { "Chestplate", "Plate", "Tunic", "Wrap" };
	public static final String[] LEGGINGS_SUFFIXES = new String[] { "Leggings", "Pants", "Skirt", "Breeches", "Legwraps", "Robe" };
	public static final String[] BOOT_SUFFIXES = new String[] { "Boots", "Shoes", "Footguards", "Stompers", "Treads", "Sandals" };

	public static final String[] AMULET_SUFFIXES = new String[] { "Amulet", "Necklace" };
	public static final String[] RING_SUFFIXES = new String[] { "Ring", "Band" };
	public static final String[] BELT_SUFFIXES = new String[] { "Belt", "Sash", "Girdle" };
	
	public static String getRandomPrefix()
	{
		return PREFIXES[(int) (Math.random() * PREFIXES.length)];
	}
	
	public static String getDaggerSuffix()
	{
		return DAGGER_SUFFIXES[(int) (Math.random() * DAGGER_SUFFIXES.length)];
	}
	
	public static String getSwordSuffix()
	{
		return SWORD_SUFFIXES[(int) (Math.random() * SWORD_SUFFIXES.length)];
	}
	
	public static String getMaceSuffix()
	{
		return MACE_SUFFIXES[(int) (Math.random() * MACE_SUFFIXES.length)];
	}
	
	public static String getWandSuffix()
	{
		return WAND_SUFFIXES[(int) (Math.random() * WAND_SUFFIXES.length)];
	}
	
	public static String getStaffSuffix()
	{
		return STAFF_SUFFIXES[(int) (Math.random() * STAFF_SUFFIXES.length)];
	}
	
	public static String getHelmetSuffix()
	{
		return HELMET_SUFFIXES[(int) (Math.random() * HELMET_SUFFIXES.length)];
	}
	
	public static String getChestplateSuffix()
	{
		return CHESTPLATE_SUFFIXES[(int) (Math.random() * CHESTPLATE_SUFFIXES.length)];
	}
	
	public static String getLeggingsSuffix()
	{
		return LEGGINGS_SUFFIXES[(int) (Math.random() * LEGGINGS_SUFFIXES.length)];
	}
	
	public static String getBootSuffix()
	{
		return BOOT_SUFFIXES[(int) (Math.random() * BOOT_SUFFIXES.length)];
	}
	
	public static String getAmuletSuffix()
	{
		return AMULET_SUFFIXES[(int) (Math.random() * AMULET_SUFFIXES.length)];
	}
	
	public static String getRingSuffix()
	{
		return RING_SUFFIXES[(int) (Math.random() * RING_SUFFIXES.length)];
	}
	
	public static String getBeltSuffix()
	{
		return BELT_SUFFIXES[(int) (Math.random() * BELT_SUFFIXES.length)];
	}
}

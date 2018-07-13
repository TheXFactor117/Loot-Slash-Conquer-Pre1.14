package thexfactor117.lsc.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import thexfactor117.lsc.util.Reference;

public class ModLootTables 
{
	public static final ResourceLocation COMMON_CHEST = new ResourceLocation(Reference.MODID, "chests/common_chest");
	public static final ResourceLocation UNCOMMON_CHEST = new ResourceLocation(Reference.MODID, "chests/uncommon_chest");
	public static final ResourceLocation RARE_CHEST = new ResourceLocation(Reference.MODID, "chests/rare_chest");
	public static final ResourceLocation EPIC_CHEST = new ResourceLocation(Reference.MODID, "chests/epic_chest");
	public static final ResourceLocation LEGENDARY_CHEST = new ResourceLocation(Reference.MODID, "chests/legendary_chest");
	public static final ResourceLocation COMMON_JAR = new ResourceLocation(Reference.MODID, "blocks/common_jar");
	public static final ResourceLocation UNCOMMON_JAR = new ResourceLocation(Reference.MODID, "blocks/uncommon_jar");
	public static final ResourceLocation RARE_JAR = new ResourceLocation(Reference.MODID, "blocks/rare_jar");
	public static final ResourceLocation EPIC_JAR = new ResourceLocation(Reference.MODID, "blocks/epic_jar");
	public static final ResourceLocation LEGENDARY_JAR = new ResourceLocation(Reference.MODID, "blocks/legendary_jar");
	public static final ResourceLocation COMMON_BARREL = new ResourceLocation(Reference.MODID, "blocks/common_barrel");
	public static final ResourceLocation UNCOMMON_BARREL = new ResourceLocation(Reference.MODID, "blocks/uncommon_barrel");
	public static final ResourceLocation RARE_BARREL = new ResourceLocation(Reference.MODID, "blocks/rare_barrel");
	public static final ResourceLocation EPIC_BARREL = new ResourceLocation(Reference.MODID, "blocks/epic_barrel");
	public static final ResourceLocation LEGENDARY_BARREL = new ResourceLocation(Reference.MODID, "blocks/legendary_barrel");
	public static final ResourceLocation COMMON_CRATE = new ResourceLocation(Reference.MODID, "blocks/common_crate");
	public static final ResourceLocation UNCOMMON_CRATE = new ResourceLocation(Reference.MODID, "blocks/uncommon_crate");
	public static final ResourceLocation RARE_CRATE = new ResourceLocation(Reference.MODID, "blocks/rare_crate");
	public static final ResourceLocation EPIC_CRATE = new ResourceLocation(Reference.MODID, "blocks/epic_crate");
	public static final ResourceLocation LEGENDARY_CRATE = new ResourceLocation(Reference.MODID, "blocks/legendary_crate");
	
	public static void register()
	{
		LootTableList.register(COMMON_CHEST);
		LootTableList.register(UNCOMMON_CHEST);
		LootTableList.register(RARE_CHEST);
		LootTableList.register(EPIC_CHEST);
		LootTableList.register(LEGENDARY_CHEST);
		LootTableList.register(COMMON_JAR);
		LootTableList.register(UNCOMMON_JAR);
		LootTableList.register(RARE_JAR);
		LootTableList.register(EPIC_JAR);
		LootTableList.register(LEGENDARY_JAR);
		LootTableList.register(COMMON_BARREL);
		LootTableList.register(UNCOMMON_BARREL);
		LootTableList.register(RARE_BARREL);
		LootTableList.register(EPIC_BARREL);
		LootTableList.register(LEGENDARY_BARREL);
		LootTableList.register(COMMON_CRATE);
		LootTableList.register(UNCOMMON_CRATE);
		LootTableList.register(RARE_CRATE);
		LootTableList.register(EPIC_CRATE);
		LootTableList.register(LEGENDARY_CRATE);
	}
}

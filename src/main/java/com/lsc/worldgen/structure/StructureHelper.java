package com.lsc.worldgen.structure;

import java.util.Random;

import com.lsc.util.RandomCollection;
import com.lsc.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;

/**
 * 
 * @author TheXFactor117
 *
 */
public class StructureHelper 
{
	public static final double TIER1_COMMON = 70;
	public static final double TIER1_UNCOMMON = 15;
	public static final double TIER1_RARE = 8;
	public static final double TIER1_LEGENDARY = 5;
	public static final double TIER1_EXOTIC = 2;
	
	public static final double TIER2_COMMON = 35;
	public static final double TIER2_UNCOMMON = 35;
	public static final double TIER2_RARE = 17;
	public static final double TIER2_LEGENDARY = 9;
	public static final double TIER2_EXOTIC = 4;
	
	public static final double TIER3_COMMON = 10;
	public static final double TIER3_UNCOMMON = 30;
	public static final double TIER3_RARE = 30;
	public static final double TIER3_LEGENDARY = 20;
	public static final double TIER3_EXOTIC = 10;
	
	private static final RandomCollection<Structure> STRUCTURES = new RandomCollection<Structure>();
	private static Random rand = new Random(); 
	
	public static Structure getRandomStructure()
	{
		return STRUCTURES.next(rand);
	}
	
	/** 
	 * Adjusts the position of the template for certain templates. For example,
	 * basements need to be adjusted properly.
	 */
	public static void adjustTemplate(Template template, BlockPos pos)
	{
		
	}
	
	static
	{
		STRUCTURES.add(6, new Structure(new ResourceLocation(Reference.MODID, "abandoned_house_1"), 1));
		STRUCTURES.add(6, new Structure(new ResourceLocation(Reference.MODID, "abandoned_house_2"), 1));
		STRUCTURES.add(6, new Structure(new ResourceLocation(Reference.MODID, "tower_1"), 1));
		STRUCTURES.add(3, new Structure(new ResourceLocation(Reference.MODID, "ruins_1"), 2));
		STRUCTURES.add(3, new Structure(new ResourceLocation(Reference.MODID, "spawner_1"), 2));
	}
}

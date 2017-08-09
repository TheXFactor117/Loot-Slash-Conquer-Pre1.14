package com.thexfactor117.losteclipse.loot;

import java.util.Random;

import com.thexfactor117.losteclipse.util.RandomCollection;

import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author TheXFactor117
 *
 */
public class LootHelper 
{
	public static ResourceLocation getLootTable(Random rand, int tier, int depth)
	{
		RandomCollection<ResourceLocation> collection = populateCollection(tier, depth);
		
		return collection.next(rand);
	}
	
	private static RandomCollection<ResourceLocation> populateCollection(int tier, int depth)
	{
		RandomCollection<ResourceLocation> collection = new RandomCollection<ResourceLocation>();
		
		switch (tier)
		{
			case 0:
				
				break;
		}
		
		
		return collection;
	}
}

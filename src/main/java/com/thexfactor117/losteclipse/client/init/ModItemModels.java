package com.thexfactor117.losteclipse.client.init;

import com.thexfactor117.losteclipse.init.ModWeapons;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
public class ModItemModels 
{
	@SubscribeEvent
	public void registerItemModel(ModelRegistryEvent event)
	{
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
	}
	
	private void registerItemModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}

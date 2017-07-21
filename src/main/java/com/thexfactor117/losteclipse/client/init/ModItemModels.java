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
		// vanilla
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
		
		// melee craftable
		registerItemModel(ModWeapons.VERANTIUM_DAGGER);
		registerItemModel(ModWeapons.VERANTIUM_SWORD);
		registerItemModel(ModWeapons.VERANTIUM_MACE);
		registerItemModel(ModWeapons.LIGHT_CRYSTAL_SWORD);
		registerItemModel(ModWeapons.DARK_CRYSTAL_SWORD);
		registerItemModel(ModWeapons.SHADOW_BLADE);
		registerItemModel(ModWeapons.VEXAL_DAGGER);
		registerItemModel(ModWeapons.VEXAL_SWORD);
		registerItemModel(ModWeapons.VEXAL_MACE);
		registerItemModel(ModWeapons.MALICE_BLADE);
		registerItemModel(ModWeapons.GYRO_MACE);
		registerItemModel(ModWeapons.VOID_HAMMER);
		registerItemModel(ModWeapons.ASTRILL_DAGGER);
		registerItemModel(ModWeapons.ASTRILL_SWORD);
		registerItemModel(ModWeapons.ASTRILL_MACE);
		
		// melee special
		registerItemModel(ModWeapons.DARK_MALICE_BLADE);
		registerItemModel(ModWeapons.DIVINE_RAPIER);
		registerItemModel(ModWeapons.EXCALIBUR_RAPIER);
	}
	
	private void registerItemModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}

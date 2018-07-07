package com.lsc.proxies;

import org.lwjgl.input.Keyboard;

import com.lsc.client.gui.GuiHealth;
import com.lsc.client.gui.GuiMana;
import com.lsc.client.render.blocks.TileEntityLootChestRenderer;
import com.lsc.client.render.entities.RenderBanshee;
import com.lsc.client.render.entities.RenderBarbarian;
import com.lsc.client.render.entities.RenderCorruptedKnight;
import com.lsc.client.render.entities.RenderGhost;
import com.lsc.client.render.entities.RenderGolem;
import com.lsc.client.render.entities.RenderMummy;
import com.lsc.client.render.entities.RenderSpectralKnight;
import com.lsc.entities.bosses.EntityCorruptedKnight;
import com.lsc.entities.monsters.EntityBanshee;
import com.lsc.entities.monsters.EntityBarbarian;
import com.lsc.entities.monsters.EntityGhost;
import com.lsc.entities.monsters.EntityGolem;
import com.lsc.entities.monsters.EntityMummy;
import com.lsc.entities.monsters.EntitySpectralKnight;
import com.lsc.tileentity.TileEntityLootChest;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ClientProxy extends ServerProxy
{
	public static KeyBinding bindingP;
	public static KeyBinding openClass;
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new GuiMana());
		MinecraftForge.EVENT_BUS.register(new GuiHealth());
		
		registerRenderers();
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		bindingP = new KeyBinding("Player Information", Keyboard.KEY_P, "Loot Slash Conquer");
		openClass = new KeyBinding("Open Class Gui", Keyboard.KEY_O, "Loot Slash Conquer");
		
		ClientRegistry.registerKeyBinding(bindingP);
		ClientRegistry.registerKeyBinding(openClass);		
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	private static void registerRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityBanshee.class, RenderBanshee::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBarbarian.class, RenderBarbarian::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, RenderGhost::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGolem.class, RenderGolem::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMummy.class, RenderMummy::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCorruptedKnight.class, RenderCorruptedKnight::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySpectralKnight.class, RenderSpectralKnight::new);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLootChest.class, new TileEntityLootChestRenderer());
	}
}

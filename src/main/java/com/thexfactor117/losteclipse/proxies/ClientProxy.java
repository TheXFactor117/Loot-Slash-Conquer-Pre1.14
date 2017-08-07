package com.thexfactor117.losteclipse.proxies;

import org.lwjgl.input.Keyboard;

import com.thexfactor117.losteclipse.client.events.EventRenderOverlayText;
import com.thexfactor117.losteclipse.client.gui.GuiHealth;
import com.thexfactor117.losteclipse.client.gui.GuiMana;
import com.thexfactor117.losteclipse.client.init.ModItemModels;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new ModItemModels());
		MinecraftForge.EVENT_BUS.register(new GuiMana());
		MinecraftForge.EVENT_BUS.register(new GuiHealth());
		MinecraftForge.EVENT_BUS.register(new EventRenderOverlayText());
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		bindingP = new KeyBinding("Player Information", Keyboard.KEY_P, "Lost Eclipse");
		
		ClientRegistry.registerKeyBinding(bindingP);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}

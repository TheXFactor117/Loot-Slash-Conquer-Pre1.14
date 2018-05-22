package com.lsc.proxies;

import org.lwjgl.input.Keyboard;

import com.lsc.client.events.EventRenderOverlayText;
import com.lsc.client.gui.GuiHealth;
import com.lsc.client.gui.GuiMana;
import com.lsc.client.init.ModItemModels;
import com.lsc.events.EventInput;
import com.lsc.events.EventItemTooltip;

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
		MinecraftForge.EVENT_BUS.register(new EventInput());
		MinecraftForge.EVENT_BUS.register(new EventItemTooltip());
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		bindingP = new KeyBinding("Player Information", Keyboard.KEY_P, "Loot Slash Conquer");
		
		ClientRegistry.registerKeyBinding(bindingP);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}

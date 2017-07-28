package com.thexfactor117.losteclipse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thexfactor117.losteclipse.init.ModCapabilities;
import com.thexfactor117.losteclipse.init.ModEntities;
import com.thexfactor117.losteclipse.init.ModEvents;
import com.thexfactor117.losteclipse.init.ModLootTables;
import com.thexfactor117.losteclipse.init.ModPackets;
import com.thexfactor117.losteclipse.loot.functions.CreateStats;
import com.thexfactor117.losteclipse.proxies.ServerProxy;
import com.thexfactor117.losteclipse.util.GuiHandler;
import com.thexfactor117.losteclipse.util.Reference;
import com.thexfactor117.losteclipse.worldgen.MHSWorldGenerator;

import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 
 * @author TheXFactor117
 *
 * A Hack/Mine-like Minecraft mod.
 *
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class LostEclipse 
{
	@Instance(Reference.MODID)
	public static LostEclipse instance;
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static ServerProxy proxy;
	public static final Logger LOGGER = LogManager.getLogger(Reference.NAME);
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModLootTables.register();
		ModCapabilities.registerCapabilities();
		ModEvents.registerEvents();
		ModEntities.registerEntities();
		
		LootFunctionManager.registerFunction(new CreateStats.Serializer());
		
		proxy.preInit(event);
		
		ModPackets.registerPackets();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		GameRegistry.registerWorldGenerator(new MHSWorldGenerator(), 100);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}

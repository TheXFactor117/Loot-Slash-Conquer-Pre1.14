package thexfactor117.lsc.proxies;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thexfactor117.lsc.client.gui.GuiHealth;
import thexfactor117.lsc.client.gui.GuiMana;
import thexfactor117.lsc.client.render.blocks.TileEntityLootChestRenderer;
import thexfactor117.lsc.client.render.entities.RenderBanshee;
import thexfactor117.lsc.client.render.entities.RenderBarbarian;
import thexfactor117.lsc.client.render.entities.RenderCorruptedKnight;
import thexfactor117.lsc.client.render.entities.RenderGhost;
import thexfactor117.lsc.client.render.entities.RenderGolem;
import thexfactor117.lsc.client.render.entities.RenderMummy;
import thexfactor117.lsc.client.render.entities.RenderSpectralKnight;
import thexfactor117.lsc.entities.bosses.EntityCorruptedKnight;
import thexfactor117.lsc.entities.monsters.EntityBanshee;
import thexfactor117.lsc.entities.monsters.EntityBarbarian;
import thexfactor117.lsc.entities.monsters.EntityGhost;
import thexfactor117.lsc.entities.monsters.EntityGolem;
import thexfactor117.lsc.entities.monsters.EntityMummy;
import thexfactor117.lsc.entities.monsters.EntitySpectralKnight;
import thexfactor117.lsc.tileentity.TileEntityLootChest;

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

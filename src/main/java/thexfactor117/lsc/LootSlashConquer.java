package thexfactor117.lsc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import thexfactor117.lsc.init.ModCapabilities;
import thexfactor117.lsc.init.ModEntities;
import thexfactor117.lsc.init.ModLootFunctions;
import thexfactor117.lsc.init.ModLootTables;
import thexfactor117.lsc.init.ModPackets;
import thexfactor117.lsc.init.ModWorldGenerators;
import thexfactor117.lsc.proxies.ServerProxy;
import thexfactor117.lsc.util.GuiHandler;
import thexfactor117.lsc.util.Reference;
import thexfactor117.lsc.util.ReflectionUtils;

/**
 * 
 * @author TheXFactor117
 *
 * A Hack/Mine-like Minecraft mod.
 *
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:baubles", updateJSON = "https://raw.githubusercontent.com/TheXFactor117/Loot-Slash-Conquer/master/info/update_checker.json")
public class LootSlashConquer 
{
	@Instance(Reference.MODID)
	public static LootSlashConquer instance;
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static ServerProxy proxy;
	public static final Logger LOGGER = LogManager.getLogger(Reference.NAME);
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{	
		LootSlashConquer.LOGGER.info("Starting initialization process...");
		
		ModLootTables.register();
		ModCapabilities.registerCapabilities();
		ModEntities.registerEntities();
		ModLootFunctions.registerFunctions();
		ModPackets.registerPackets();
		
		// change the max health and attack damage cap.
		ReflectionUtils.setPrivateFinalValue(RangedAttribute.class, (RangedAttribute) SharedMonsterAttributes.MAX_HEALTH, 10000000, "maximumValue", "field_111118_b");
		ReflectionUtils.setPrivateFinalValue(RangedAttribute.class, (RangedAttribute) SharedMonsterAttributes.ATTACK_DAMAGE, 100000, "maximumValue", "field_111118_b");
		
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		ModWorldGenerators.registerWorldGenerators();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
		
		LootSlashConquer.LOGGER.info("Loot Slash Conquer has finished initializing successfully!");
	}
}

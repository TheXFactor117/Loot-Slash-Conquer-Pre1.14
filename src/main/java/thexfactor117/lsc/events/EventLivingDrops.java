package thexfactor117.lsc.events;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import thexfactor117.lsc.capabilities.implementation.EnemyInfo;
import thexfactor117.lsc.items.base.ItemBauble;
import thexfactor117.lsc.items.base.ItemMagical;
import thexfactor117.lsc.util.NBTHelper;
import thexfactor117.lsc.util.Reference;

/**
 *
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventLivingDrops
{
	@SubscribeEvent
	public static void onLivingDrops(LivingDropsEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		EnemyInfo enemyInfo = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
		
		if (enemyInfo != null)
		{
			int tier = enemyInfo.getEnemyTier();
			List<ItemStack> stacks = null;
			LootTable table;
			LootContext context;
			
			switch (tier)
			{
				case 1: // Normal
					table = entity.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "entities/common_entity"));
					context = new LootContext.Builder((WorldServer) entity.world).withLootedEntity(entity).build();
					stacks = table.generateLootForPools(entity.getRNG(), context);
					break;
				case 2: // Hardened
					table = entity.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "entities/uncommon_entity"));
					context = new LootContext.Builder((WorldServer) entity.world).withLootedEntity(entity).build();
					stacks = table.generateLootForPools(entity.getRNG(), context);
					break;
				case 3: // Superior
					table = entity.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "entities/rare_entity"));
					context = new LootContext.Builder((WorldServer) entity.world).withLootedEntity(entity).build();
					stacks = table.generateLootForPools(entity.getRNG(), context);
					break;
				case 4: // Elite
					table = entity.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "entities/epic_entity"));
					context = new LootContext.Builder((WorldServer) entity.world).withLootedEntity(entity).build();
					stacks = table.generateLootForPools(entity.getRNG(), context);
					break;
				case 5: // Legendary
					table = entity.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "entities/legendary_entity"));
					context = new LootContext.Builder((WorldServer) entity.world).withLootedEntity(entity).build();
					stacks = table.generateLootForPools(entity.getRNG(), context);
					break;
			}
			
			if (stacks != null)
			{
				for (ItemStack stack : stacks)
				{
					if (stack != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemBow 
							|| stack.getItem() instanceof ItemMagical || stack.getItem() instanceof ItemBauble))
					{
						NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
						stack.setTagCompound(nbt);

						if (nbt != null)
						{
							if (nbt.getInteger("Level") == 0)
							{
								if (nbt.hasKey("TagLevel"))
								{
									EventContainerOpen.generate(stack, nbt, entity.world, nbt.getInteger("TagLevel"));
								}
							}
						}
					}
					
					EntityItem item = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, stack);
					event.getDrops().add(item);
				}
			}
		}
	}
}

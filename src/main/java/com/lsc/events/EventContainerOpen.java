package com.lsc.events;

import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.items.base.ItemBauble;
import com.lsc.items.base.ItemMagical;
import com.lsc.loot.ItemGenerator;
import com.lsc.loot.NameGenerator;
import com.lsc.loot.Rarity;
import com.lsc.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventContainerOpen 
{
	@SubscribeEvent
	public void onContainerOpen(PlayerContainerEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();

		if (player != null && !world.isRemote);
		{
			for (ItemStack stack : event.getContainer().getInventory())
			{
				if (stack != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemMagical || stack.getItem() instanceof ItemBauble))
				{
					NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
					
					if (nbt != null)
					{
						if (Rarity.getRarity(nbt) == Rarity.DEFAULT)
						{
							Rarity.setRarity(nbt, Rarity.getWeightedRarity(nbt, world.rand, Rarity.COMMON));
							
							ChunkPos chunkPos = new ChunkPos(new BlockPos(player.posX, player.posY, player.posZ));
							IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
							IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(chunkPos);
							
							ItemGenerator.create(stack, nbt, world, chunkLevel.getChunkLevel());
							
							stack.setTagCompound(nbt);
							NameGenerator.generateName(stack, stack.getTagCompound());
						}
					}
				}
			}
		}
	}
}

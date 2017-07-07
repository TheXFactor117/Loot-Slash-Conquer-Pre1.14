package com.thexfactor117.minehackslash.events;

import com.thexfactor117.minehackslash.items.generation.ItemGenerator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventPlayerTick 
{
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (!event.player.getEntityWorld().isRemote && event.phase == Phase.START)
		{
			for (ItemStack stack : event.player.inventory.mainInventory)
			{
				if (stack != null && stack.getItem() instanceof ItemSword)
				{
					ItemGenerator.create(stack, event.player);
				}
			}
		}
	}
}

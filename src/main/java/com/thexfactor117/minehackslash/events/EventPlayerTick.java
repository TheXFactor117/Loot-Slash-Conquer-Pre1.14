package com.thexfactor117.minehackslash.events;

import com.thexfactor117.minehackslash.stats.weapons.ItemGenerator;
import com.thexfactor117.minehackslash.util.NBTHelper;

import net.minecraft.item.ItemArmor;
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
		if (event.phase == Phase.START && !event.player.getEntityWorld().isRemote)
		{
			if ((event.player.inventory.getCurrentItem().getItem() instanceof ItemSword || event.player.inventory.getCurrentItem().getItem() instanceof ItemArmor) && !NBTHelper.loadStackNBT(event.player.inventory.getCurrentItem()).hasKey("Level"))
			{
				ItemGenerator.create(event.player.inventory.getCurrentItem(), event.player);
			}
		}
	}
}

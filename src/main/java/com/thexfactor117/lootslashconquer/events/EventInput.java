package com.thexfactor117.lootslashconquer.events;

import com.thexfactor117.lootslashconquer.LootSlashConquer;
import com.thexfactor117.lootslashconquer.proxies.ClientProxy;
import com.thexfactor117.lootslashconquer.util.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventInput 
{
	@SubscribeEvent
	public void onInput(InputEvent event)
	{
		KeyBinding p = ClientProxy.bindingP;
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		if (player != null && p.isPressed())
		{
			player.openGui(LootSlashConquer.instance, GuiHandler.PLAYER_INFORMATION, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
		}
		
		// if ability key is pressed.
		// send server packet calling ability start.
	}
}
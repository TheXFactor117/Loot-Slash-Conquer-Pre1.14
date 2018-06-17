package com.lsc.client.events;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.proxies.ClientProxy;
import com.lsc.util.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class EventInput 
{
	@SubscribeEvent
	public static void onInput(InputEvent event)
	{
		KeyBinding p = ClientProxy.bindingP;
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		if (player != null && p.isPressed())
		{
			player.openGui(LootSlashConquer.instance, GuiHandler.PLAYER_INFORMATION, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
		}
		
		if (ClientProxy.openClass.isPressed())
		{
			PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (playerInfo != null && playerInfo.getPlayerClass() == 0)
			{
				player.openGui(LootSlashConquer.instance, GuiHandler.CLASS_SELECTION, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
			}
		}
		
		// if ability key is pressed.
		// send server packet calling ability start.
	}
}

package com.lsc.client.events;

import com.lsc.capabilities.api.IChunkLevel;
import com.lsc.capabilities.api.IChunkLevelHolder;
import com.lsc.capabilities.cap.CapabilityChunkLevel;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class EventRenderOverlayText 
{
	@SubscribeEvent
	public static void onRenderOverlayText(RenderGameOverlayEvent.Text event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		World world = Minecraft.getMinecraft().world;
		IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(new ChunkPos(player.getPosition()));
		
		if (chunkLevelHolder != null && chunkLevel != null)
		{
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Area Level: " + chunkLevel.getChunkLevel(), 10, 10, 0xFFFFFF);
		}
	}
}

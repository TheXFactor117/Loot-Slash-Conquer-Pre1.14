package com.lsc.client.events;

import com.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.lsc.capabilities.implementation.EnemyInfo;
import com.lsc.entities.EnemyTier;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class EventRenderLiving
{
	@SubscribeEvent
	public static void onRenderLiving(RenderLivingEvent.Post<EntityLivingBase> event)
	{
		EntityLivingBase entity = event.getEntity();
		EnemyInfo enemyInfo = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
		
		if (entity instanceof IMob && enemyInfo != null)
		{
			Entity viewingEntity = event.getRenderer().getRenderManager().renderViewEntity;
			//String level = "Level: " + enemyInfo.getEnemyLevel();
			//String tier = EnemyTier.getEnemyTier(enemyInfo).getColor() + EnemyTier.getEnemyTier(enemyInfo).getName();
			double entityX = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.getPartialRenderTick());
			double entityY = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.getPartialRenderTick());
			double entityZ = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.getPartialRenderTick());
			double viewingX = (viewingEntity.lastTickPosX + (viewingEntity.posX - viewingEntity.lastTickPosX) * (double) event.getPartialRenderTick());
			double viewingY = (viewingEntity.lastTickPosY + (viewingEntity.posY - viewingEntity.lastTickPosY) * (double) event.getPartialRenderTick());
			double viewingZ = (viewingEntity.lastTickPosZ + (viewingEntity.posZ - viewingEntity.lastTickPosZ) * (double) event.getPartialRenderTick());
			double x = entityX - viewingX;
			double y = entityY - viewingY;
			double z = entityZ - viewingZ;
			
			//this.renderNameplate(entity, event.getRenderer().getRenderManager(), level, x, y + entity.height + 2, z, 16);
			//this.renderNameplate(entity, event.getRenderer().getRenderManager(), tier, x, y + entity.height + 1.75, z, 16);
			String test = EnemyTier.getEnemyTier(enemyInfo).getColor() + EnemyTier.getEnemyTier(enemyInfo).getName() + " Level " + enemyInfo.getEnemyLevel();
			
			if (!entity.isInvisible())
			{
				renderNameplate(entity, event.getRenderer().getRenderManager(), test, x, y + entity.height + 0.5, z, 16);
			}
		}
	}
	
	/**
	 * Renders text above an entity to show their level and tier.
	 * @param entity
	 * @param render
	 * @param str
	 * @param x
	 * @param y
	 * @param z
	 * @param maxDistance
	 */
	private static void renderNameplate(EntityLivingBase entity, RenderManager render, String str, double x, double y, double z, int maxDistance)
	{
		double distance = entity.getDistance(render.renderViewEntity);
		
		// don't render if we are too far away
		if (distance > maxDistance) return;
		
		FontRenderer font = render.getFontRenderer();
		float playerYaw = render.playerViewY;
		float playerPitch = render.playerViewX;
		
		EntityRenderer.drawNameplate(font, str, (float) x, (float) y, (float) z, 0, playerYaw, playerPitch, false, false);
	}
}

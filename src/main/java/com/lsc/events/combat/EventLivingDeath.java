package com.lsc.events.combat;

import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.player.ExperienceHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventLivingDeath 
{
	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		/*
		 * Update player experience when they kill a monster. Experience gained is determined from how much health/damage the monsters has.
		 */
		if (event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			EntityLivingBase enemy = event.getEntityLiving();
			PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (!player.getEntityWorld().isRemote && playerInfo != null)
			{
				ExperienceHelper.addExperience(player, playerInfo, enemy);
			}
		}
	}
}

package com.thexfactor117.lsc.events.combat;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.util.ExperienceUtil;
import com.thexfactor117.lsc.util.PlayerUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
		if (event.getSource().getTrueSource() instanceof EntityPlayer && event.getEntityLiving() instanceof IMob)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			EntityLivingBase enemy = event.getEntityLiving();
			LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
			
			if (!player.getEntityWorld().isRemote && cap != null && player.getClass() == EntityPlayerMP.class)
			{
				ExperienceUtil.addExperience(player, cap, enemy);
			}
		}
	}
}

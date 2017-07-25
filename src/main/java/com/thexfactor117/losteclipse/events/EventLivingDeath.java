package com.thexfactor117.losteclipse.events;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;
import com.thexfactor117.losteclipse.network.PacketUpdatePlayerInformation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventLivingDeath 
{
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		/*
		 * Update player experience when they kill a monster. Experience gained is determined from how much health/damage the monsters has.
		 */
		
		if (event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			EntityLivingBase enemy = event.getEntityLiving();
			IPlayerInformation playerInfo = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (playerInfo != null)
			{
				int experience = 0;
				
				if (enemy instanceof EntityPlayer) experience = 50; // if entity is a player, award more experience than usual.
				else experience = (int) (enemy.getMaxHealth() * 0.2); // experience = 10% of max health.
				
				// update experience on client AND server; increase level if need be.
				playerInfo.setPlayerExperience(playerInfo.getPlayerExperience() + experience + 1000);
				while (playerInfo.getPlayerExperience() > playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())) 
				{
					playerInfo.setPlayerLevel(playerInfo.getPlayerLevel() + 1); // increase level
					playerInfo.setSkillPoints(playerInfo.getSkillPoints() + 1); // increase skill points
				}
				LostEclipse.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) player); 
			}
		}
	}
}

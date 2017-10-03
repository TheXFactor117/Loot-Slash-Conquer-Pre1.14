package com.thexfactor117.lootslashconquer.events.combat;

import com.thexfactor117.lootslashconquer.LootSlashConquer;
import com.thexfactor117.lootslashconquer.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.lootslashconquer.capabilities.playerinfo.PlayerInformation;
import com.thexfactor117.lootslashconquer.network.PacketUpdatePlayerInformation;

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
			PlayerInformation playerInfo = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (!player.getEntityWorld().isRemote && playerInfo != null)
			{
				addExperience(player, playerInfo, enemy);
			}
		}
	}
	
	private void addExperience(EntityPlayer player, PlayerInformation playerInfo, EntityLivingBase enemy)
	{
		int experience = 0;
		
		if (enemy instanceof EntityPlayer) experience = 50; // if entity is a player, award more experience than usual.
		else experience = (int) (enemy.getMaxHealth() * 0.2); // experience = 10% of max health.
		
		// update experience on client AND server; increase level if need be.
		playerInfo.setPlayerExperience(playerInfo.getPlayerExperience() + experience);
		
		while (playerInfo.getPlayerExperience() > playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())) 
		{
			playerInfo.setPlayerLevel(playerInfo.getPlayerLevel() + 1); // increase level
			playerInfo.setSkillPoints(playerInfo.getSkillPoints() + 1); // increase skill points
		}
		
		LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) player); 
	}
}

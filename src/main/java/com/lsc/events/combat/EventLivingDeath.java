package com.lsc.events.combat;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.playerinfo.CapabilityPlayerInformation;
import com.lsc.capabilities.playerinfo.PlayerInformation;
import com.lsc.network.PacketUpdatePlayerInformation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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
		else experience = (int) ((enemy.getMaxHealth() * 0.2) + (enemy.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.2)); // experience = 20% of max health.
		
		// update experience on client AND server; increase level if need be.
		playerInfo.setPlayerExperience(playerInfo.getPlayerExperience() + experience);
		
		while (playerInfo.getPlayerExperience() > playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())) 
		{
			playerInfo.setPlayerLevel(playerInfo.getPlayerLevel() + 1); // increase level
			
			/*
			 * Every level add 1 skill point.
			 * Every 5 levels add an additional 2 skill points (total 3)
			 * Every 10 levels add an additional 4 skill points (total 5)
			 */
			if (playerInfo.getPlayerLevel() % 10 == 0) playerInfo.setSkillPoints(playerInfo.getSkillPoints() + 5); // increase skill points
			else if (playerInfo.getPlayerLevel() % 5 == 0) playerInfo.setSkillPoints(playerInfo.getSkillPoints() + 3); // increase skill points
			else playerInfo.setSkillPoints(playerInfo.getSkillPoints() + 1); // increase skill points
		}
		
		LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(playerInfo), (EntityPlayerMP) player); 
	}
}

package com.lsc.player;

import java.util.Random;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.playerinfo.PlayerInformation;
import com.lsc.network.PacketUpdatePlayerInformation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ExperienceHelper 
{
	public static void addExperience(EntityPlayer player, PlayerInformation playerInfo, EntityLivingBase enemy)
	{
		int experience = 0;
		
		if (enemy instanceof EntityPlayer) experience = 50; // if entity is a player, award more experience than usual.
		else experience = (int) ((enemy.getMaxHealth() * 0.2) + (enemy.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.2)); // experience = 20% of max health.
		
		// update experience on client AND server; increase level if need be.
		playerInfo.setPlayerExperience(playerInfo.getPlayerExperience() + experience + 100);
		
		while (playerInfo.getPlayerExperience() > playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())) 
		{
			playerInfo.setPlayerLevel(playerInfo.getPlayerLevel() + 1); // increase level
			
			spawnLevelUpParticles(player.getEntityWorld(), player, playerInfo.getPlayerLevel()); // bugged
			
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
	
	private static void spawnLevelUpParticles(World world, EntityPlayer player, int level)
	{
		Random rand = world.rand;
		
		if (level % 10 == 0)
		{
			for (int i = 0; i < 100; i++)
			{
				world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX, player.posY + 2, player.posZ, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.5D, rand.nextGaussian() * 0.05D);
			}
		}
		else if (level % 5 == 0)
		{
			for (int i = 0; i < 100; i++)
			{
				world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX, player.posY + 2, player.posZ, rand.nextGaussian() * 0.05D, rand.nextGaussian() * 0.5D, rand.nextGaussian() * 0.05D);
			}
		}
		else
		{
			for (int i = 0; i < 100; i++)
			{
				world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX, player.posY + 2, player.posZ, rand.nextGaussian() * 0.05D, 0, rand.nextGaussian() * 0.05D);
			}
		}
	}
}

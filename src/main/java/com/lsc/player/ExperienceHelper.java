package com.lsc.player;

import java.util.Random;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.lsc.capabilities.implementation.EnemyInfo;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.entities.EntityMonster;
import com.lsc.network.PacketUpdatePlayerInformation;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
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
		
		EnemyInfo enemyInfo = (EnemyInfo) enemy.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
		
		// calculates how much experience a monster should drop
		// based on the level, tier, and rarity of the monster
		// ALL vanilla and non-LSC monsters have a rarity of 1.
		if (enemyInfo != null)
		{
			int enemyLevel = enemyInfo.getEnemyLevel();
			int enemyTier = enemyInfo.getEnemyTier();
			int rarity = 1;
			
			if (enemy instanceof EntityMonster)
			{
				rarity = EntityMonster.rarity;
			}
			
			// calculates the different multipliers and multiplies them together to get the total multiplier
			int tierMultiplier = (int) (Math.pow(enemyTier, 2.25) / 3 + 1);
			int rarityMultiplier = (int) (Math.pow(rarity, 1.75) / 2.5 + 1);
			int multiplier = (int) ((tierMultiplier * rarityMultiplier + 1) / 1.5);
			
			// base experience is 5 for now...
			// formula: 5 * (level+1 ^ 1.5) * multiplier
			experience = (int) (5 * (Math.pow(enemyLevel + 1, 1.5)) * multiplier);
		}
		
		// update experience on client AND server; increase level if need be.
		playerInfo.setPlayerExperience(playerInfo.getPlayerExperience() + experience);
		
		// Minecraft bug with actionbar times - it doesn't actually handle specifying times for the actionbar.
		SPacketTitle packetActionbar = new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentString("You killed " + enemy.getName() + " and gained " + experience + " experience!"), -1, -1, -1);
		((EntityPlayerMP) player).connection.sendPacket(packetActionbar);
		
		while (playerInfo.getPlayerExperience() > playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel())) 
		{
			int leftOverExperience = playerInfo.getPlayerExperience() - playerInfo.getLevelUpExperience(playerInfo.getPlayerLevel());
			int skillPoints = 0;
			
			playerInfo.setPlayerLevel(playerInfo.getPlayerLevel() + 1); // increase level
			playerInfo.setPlayerExperience(leftOverExperience);
			
			// Every level add 1 skill point.
			// Every 5 levels add an additional 2 skill points (total 3)
			// Every 10 levels add an additional 4 skill points (total 5)
			if (playerInfo.getPlayerLevel() % 10 == 0) skillPoints = 5;
			else if (playerInfo.getPlayerLevel() % 5 == 0) skillPoints = 3;
			else skillPoints = 1;
			
			SPacketTitle packetTitle = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentString("Level " + playerInfo.getPlayerLevel()));
			SPacketTitle packetSubtitle = new SPacketTitle(SPacketTitle.Type.SUBTITLE, new TextComponentString("You have leveled up! You've gained " + skillPoints + " Skill Points."));
			((EntityPlayerMP) player).connection.sendPacket(packetTitle);
			((EntityPlayerMP) player).connection.sendPacket(packetSubtitle);
			
			spawnLevelUpParticles(player.getEntityWorld(), player, playerInfo.getPlayerLevel()); // bugged
			
			playerInfo.setSkillPoints(playerInfo.getSkillPoints() + skillPoints);
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

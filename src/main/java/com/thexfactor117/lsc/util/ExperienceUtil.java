package com.thexfactor117.lsc.util;

import java.util.Random;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.thexfactor117.lsc.capabilities.implementation.EnemyInfo;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.entities.EntityRarity;
import com.thexfactor117.lsc.items.base.weapons.ItemMagical;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.AttributeWeapon;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerInformation;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ExperienceUtil 
{
	/** Returns the amount of experience needed to level up given the current level. */
	public static int getLevelUpExperience(int currentLevel) 
	{
		return (int) (Math.pow(Configs.playerCategory.levelUpExpPower, currentLevel) + Configs.playerCategory.levelUpAdditive);
	}
	
	public static void addExperience(EntityPlayer player, LSCPlayerCapability cap, Entity enemy)
	{
		double experience = 0;
		double expRestrictor = 1;
		double playerLevel = cap.getPlayerLevel();
		
		EnemyInfo enemyInfo = (EnemyInfo) enemy.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
		
		// calculates how much experience a monster should drop
		// based on the level, tier, and rarity of the monster
		// ALL vanilla and non-LSC monsters have a rarity of 1.
		if (enemyInfo != null)
		{
			int enemyLevel = enemyInfo.getEnemyLevel();
			int enemyTier = enemyInfo.getEnemyTier();
			int rarity = 1;
			double lowLevelRistr = Configs.playerCategory.lowerLevelRestrictionRange;
			double upLevelRistr = Configs.playerCategory.upperLevelRestrictionRange;

			
			if (enemy instanceof EntityRarity)
			{
				rarity = EntityRarity.rarity;
			}

			if (Configs.playerCategory.playerLevelingRestriction){
				if (enemyLevel < playerLevel && (playerLevel - enemyLevel) < lowLevelRistr + 1 && lowLevelRistr != -1){
					expRestrictor = 1 - ((playerLevel - enemyLevel) / lowLevelRistr);
				}else if (enemyLevel > playerLevel && enemyLevel - playerLevel < upLevelRistr + 1 && upLevelRistr != -1){
					expRestrictor = 1 - ((enemyLevel - playerLevel) / upLevelRistr);
				}else expRestrictor = 0;

				if (expRestrictor < 0) expRestrictor = 0;

			}else expRestrictor = 1;
			
			// calculates the different multipliers and multiplies them together to get the total multiplier
			double baseFactor = Configs.monsterLevelTierCategory.experienceBaseFactor;
			double tierMultiplier = (Math.pow(enemyTier, Configs.monsterLevelTierCategory.experienceTierPower));
			double rarityMultiplier = (Math.pow(rarity, Configs.monsterLevelTierCategory.experienceRarityPower));
			double multiplier = ((tierMultiplier * rarityMultiplier) + 1 / Configs.monsterLevelTierCategory.experienceDivisor);

			experience = ((enemyLevel * expRestrictor) * (baseFactor * multiplier));
			
			double bonusExperience = 0;
			ItemStack stack = player.getHeldItem(player.getActiveHand());
			
			if (stack != ItemStack.EMPTY && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemMagical))
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (Attribute.BONUS_EXPERIENCE.hasAttribute(nbt))
				{
					bonusExperience = ((AttributeWeapon) Attribute.BONUS_EXPERIENCE).getPassiveValue(nbt) * experience;
				}
			}
			
			experience += bonusExperience;
		}
		
		// update experience on client AND server; increase level if need be.
		cap.setPlayerExperience(cap.getPlayerExperience() + (int) experience);
		
		// Minecraft bug with actionbar times - it doesn't actually handle specifying times for the actionbar.
		SPacketTitle packetActionbar = new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentString("You killed " + enemy.getName() + " and gained " + (int) experience + " experience!"), -1, -1, -1);
		((EntityPlayerMP) player).connection.sendPacket(packetActionbar);
		
		while (cap.getPlayerExperience() > getLevelUpExperience(cap.getPlayerLevel())) 
		{
			int leftOverExperience = cap.getPlayerExperience() - getLevelUpExperience(cap.getPlayerLevel());
			int skillPoints = 0;
			
			cap.setPlayerLevel(cap.getPlayerLevel() + 1); // increase level
			cap.setPlayerExperience(leftOverExperience);
			
			if (Configs.playerCategory.useTieredSkillPointDistribution)
			{
				// Every level add 1 skill point.
				// Every 5 levels add an additional 2 skill points (total 3)
				// Every 10 levels add an additional 4 skill points (total 5)
				if (cap.getPlayerLevel() % 10 == 0) skillPoints = Configs.playerCategory.skillPointsPer10Levels;
				else if (cap.getPlayerLevel() % 5 == 0) skillPoints = Configs.playerCategory.skillPointsPer5Levels;
				else skillPoints = Configs.playerCategory.skillPointsPerLevel;
			}
			else
			{
				skillPoints = Configs.playerCategory.skillPointsPerLevel;
			}
			
			SPacketTitle packetTitle = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentString("Level " + cap.getPlayerLevel()));
			SPacketTitle packetSubtitle = new SPacketTitle(SPacketTitle.Type.SUBTITLE, new TextComponentString("You have leveled up! You've gained " + skillPoints + " Skill Points."));
			((EntityPlayerMP) player).connection.sendPacket(packetTitle);
			((EntityPlayerMP) player).connection.sendPacket(packetSubtitle);
			
			if (Configs.playerCategory.spawnLevelUpParticles)
			{
				spawnLevelUpParticles(player.getEntityWorld(), player, cap.getPlayerLevel()); // bugged
			}
			
			cap.setSkillPoints(cap.getSkillPoints() + skillPoints);
		}
		
		LootSlashConquer.network.sendTo(new PacketUpdatePlayerInformation(cap), (EntityPlayerMP) player); 
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

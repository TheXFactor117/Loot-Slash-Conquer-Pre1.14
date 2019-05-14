package com.thexfactor117.lsc.player;

import java.util.UUID;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.thexfactor117.lsc.capabilities.cap.CapabilityPlayerStats;
import com.thexfactor117.lsc.capabilities.implementation.PlayerInformation;
import com.thexfactor117.lsc.capabilities.implementation.PlayerStats;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.network.PacketUpdatePlayerStats;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PlayerStatUtils 
{
	//private static final String ATTACK_DAMAGE = "ece5e91f-afc9-4883-a169-c7b5883c12dc";
	private static final String MOVEMENT_SPEED = "50ac5b8b-00a6-436c-bdc3-9848393bb7b7";
	private static final String ATTACK_SPEED = "e574e861-e5bd-4906-b72e-f6be6e4c9563";
	private static final String MAX_HEALTH = "e3762718-bbd8-4763-bfe9-1d18d70eaa76";
	
	/**
	 * Helper method to update player attributes based on current stats.
	 */
	public static void updateAttributes(EntityPlayer player)
	{
		PlayerInformation info = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		PlayerStats stats = (PlayerStats) player.getCapability(CapabilityPlayerStats.PLAYER_STATS, null);
		
		if (info != null && stats != null)
		{
			/* 
			 * STRENGTH
			 */
			// TODO: convert new damage to add to the player's attribute...look into this more, the whole system is all sorts of fucked up.
			// increase attack damage
			/*AttributeModifier strengthAttackDamage = new AttributeModifier(UUID.fromString(ATTACK_DAMAGE), "playerStrength", ATTACK_DAMAGE_MULTIPLIER * (info.getTotalStrength()), 0);

			if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(UUID.fromString(ATTACK_DAMAGE)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(UUID.fromString(ATTACK_DAMAGE));
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthAttackDamage);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthAttackDamage);*/
			
			
			/*
			 * AGILITY
			 */
			// increase agility
			AttributeModifier agilityMovementSpeed = new AttributeModifier(UUID.fromString(MOVEMENT_SPEED), "agilityMovementSpeed", Configs.playerCategory.movementSpeedMultiplier * (info.getTotalAgility()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(UUID.fromString(MOVEMENT_SPEED)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString(MOVEMENT_SPEED));
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			
			// increase attack speed
			AttributeModifier agilityAttackSpeed = new AttributeModifier(UUID.fromString(ATTACK_SPEED), "agilityAttackSpeed", Configs.playerCategory.attackSpeedMultiplier * (info.getTotalAgility()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getModifier(UUID.fromString(ATTACK_SPEED)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(UUID.fromString(ATTACK_SPEED));
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
			
			
			if (!player.getEntityWorld().isRemote)
			{
				/*
				 * DEXTERITY
				 */
				int bonus = info.getTotalDexterity() > 0 ? 1 : 0;
				
				stats.setCriticalChance(Configs.playerCategory.critChanceMultiplier * ((info.getTotalDexterity() / 5) + bonus));
				stats.setCriticalDamage(Configs.playerCategory.critDamageMultiplier * ((info.getTotalDexterity() / 2) + bonus));
				
				
				/*
				 * INTELLIGENCE
				 */
				//stats.setMagicalPower(MAGICAL_POWER_MULTIPLIER * (info.getTotalIntelligence()));
				
				/*
				 * WISDOM
				 */			
				stats.setMaxMana((int) ((Configs.playerCategory.maxManaMultiplier * info.getTotalWisdom()) + 100));
				
				stats.setManaPerSecond(Configs.playerCategory.manaPer5 * info.getTotalWisdom());
				
				//fortitude
				stats.setHealthPerSecond(Configs.playerCategory.healthPer5 * info.getTotalFortitude());
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(stats), (EntityPlayerMP) player);
			}
			
			
			/*
			 * FORTITUDE
			 */
			// increases max health
			AttributeModifier fortitudeMaxHealth = new AttributeModifier(UUID.fromString(MAX_HEALTH), "maxHealth", Configs.playerCategory.maxHealthMultiplier * (info.getTotalFortitude()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(UUID.fromString(MAX_HEALTH)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(UUID.fromString(MAX_HEALTH));
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(fortitudeMaxHealth);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(fortitudeMaxHealth);
			
			if (player.getHealth() > player.getMaxHealth())
			{
				player.setHealth(player.getMaxHealth());
			}
		}
	}
}

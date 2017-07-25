package com.thexfactor117.losteclipse.stats;

import java.util.UUID;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PlayerStatHelper 
{
	private static final String STRENGTH = "ece5e91f-afc9-4883-a169-c7b5883c12dc";
	private static final String AGILITY = "50ac5b8b-00a6-436c-bdc3-9848393bb7b7";
	private static final String AGILITY_1 = "e574e861-e5bd-4906-b72e-f6be6e4c9563";
	private static final String INTELLIGENCE = "8c865de8-f16e-43ea-a69f-8e77edd7a11c";
	private static final String WISDOM = "e89c87ff-3404-4aba-884d-3b93b6872e40";
	private static final String FORTITUDE = "e3762718-bbd8-4763-bfe9-1d18d70eaa76";
	
	private static final double ATTACK_DAMAGE_MULTIPLIER = 2;
	private static final double MOVEMENT_SPEED_MULTIPLIER = 0.01;
	public static final double ATTACK_SPEED_MULTIPLIER = 0.1;
	private static final double MAX_HEALTH_MULTIPLIER = 2;
	
	/**
	 * Helper method to update player attributes based on current stats.
	 */
	public static void updateAttributes(EntityPlayer player)
	{
		IPlayerInformation info = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		
		if (info != null)
		{
			/* 
			 * STRENGTH
			 */
			// increase attack damage
			AttributeModifier strengthModifier = new AttributeModifier(UUID.fromString(STRENGTH), "playerStrength", ATTACK_DAMAGE_MULTIPLIER + (info.getStrengthStat() + info.getBonusStrengthStat()), 0);

			if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(UUID.fromString(STRENGTH)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(UUID.fromString(STRENGTH));
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthModifier);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthModifier);
			
			
			/*
			 * AGILITY
			 */
			LostEclipse.LOGGER.info("BEFORE: " + player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue());
			
			// increase agility
			AttributeModifier agilityMovementSpeed = new AttributeModifier(UUID.fromString(AGILITY), "agilityMovementSpeed", MOVEMENT_SPEED_MULTIPLIER * (info.getAgilityStat() + info.getBonusAgilityStat()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(UUID.fromString(AGILITY)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString(AGILITY));
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			
			// increase attack speed
			AttributeModifier agilityAttackSpeed = new AttributeModifier(UUID.fromString(AGILITY_1), "agilityAttackSpeed", ATTACK_SPEED_MULTIPLIER * (info.getAgilityStat() + info.getBonusAgilityStat()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getModifier(UUID.fromString(AGILITY_1)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(UUID.fromString(AGILITY_1));
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
			
			LostEclipse.LOGGER.info("AFTER: " + player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue());
			
			
			/*
			 * DEXTERITY
			 */
			
			
			
			/*
			 * INTELLIGENCE
			 */
			
			
			
			/*
			 * WISDOM
			 */
			
			
			
			/*
			 * FORTITUDE
			 */
			// increases max health
			AttributeModifier healthModifier = new AttributeModifier(UUID.fromString(FORTITUDE), "maxHealth", MAX_HEALTH_MULTIPLIER * (info.getFortitudeStat() + info.getBonusFortitudeStat()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(UUID.fromString(FORTITUDE)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(UUID.fromString(FORTITUDE));
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(healthModifier);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(healthModifier);
		}
	}
}

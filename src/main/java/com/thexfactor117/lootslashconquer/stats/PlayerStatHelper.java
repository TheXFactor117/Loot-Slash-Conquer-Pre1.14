package com.thexfactor117.lootslashconquer.stats;

import java.util.UUID;

import com.thexfactor117.lootslashconquer.LootSlashConquer;
import com.thexfactor117.lootslashconquer.capabilities.playerinfo.CapabilityPlayerInformation;
import com.thexfactor117.lootslashconquer.capabilities.playerinfo.PlayerInformation;
import com.thexfactor117.lootslashconquer.capabilities.playerstats.CapabilityPlayerStats;
import com.thexfactor117.lootslashconquer.capabilities.playerstats.Stats;
import com.thexfactor117.lootslashconquer.network.PacketUpdateStats;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PlayerStatHelper 
{
	private static final String ATTACK_DAMAGE = "ece5e91f-afc9-4883-a169-c7b5883c12dc";
	private static final String MOVEMENT_SPEED = "50ac5b8b-00a6-436c-bdc3-9848393bb7b7";
	private static final String ATTACK_SPEED = "e574e861-e5bd-4906-b72e-f6be6e4c9563";
	private static final String MAX_HEALTH = "e3762718-bbd8-4763-bfe9-1d18d70eaa76";
	
	public static final double ATTACK_DAMAGE_MULTIPLIER = 2;
	public static final double MOVEMENT_SPEED_MULTIPLIER = 0.001;
	public static final double ATTACK_SPEED_MULTIPLIER = 0.1;
	public static final double MAX_HEALTH_MULTIPLIER = 2;
	public static final double MAX_MANA_MULTIPLIER = 2;
	public static final double MAGICAL_POWER_MULTIPLIER = 2;
	
	/**
	 * Helper method to update player attributes based on current stats.
	 */
	public static void updateAttributes(EntityPlayer player)
	{
		PlayerInformation info = (PlayerInformation) player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
		Stats statsCap = (Stats) player.getCapability(CapabilityPlayerStats.STATS, null);
		
		if (info != null)
		{
			/* 
			 * STRENGTH
			 */
			// increase attack damage
			AttributeModifier strengthAttackDamage = new AttributeModifier(UUID.fromString(ATTACK_DAMAGE), "playerStrength", ATTACK_DAMAGE_MULTIPLIER + (info.getTotalStrength()), 0);

			if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(UUID.fromString(ATTACK_DAMAGE)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(UUID.fromString(ATTACK_DAMAGE));
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthAttackDamage);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthAttackDamage);
			
			
			/*
			 * AGILITY
			 */
			// increase agility
			AttributeModifier agilityMovementSpeed = new AttributeModifier(UUID.fromString(MOVEMENT_SPEED), "agilityMovementSpeed", MOVEMENT_SPEED_MULTIPLIER * (info.getTotalAgility()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(UUID.fromString(MOVEMENT_SPEED)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString(MOVEMENT_SPEED));
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			
			// increase attack speed
			AttributeModifier agilityAttackSpeed = new AttributeModifier(UUID.fromString(ATTACK_SPEED), "agilityAttackSpeed", ATTACK_SPEED_MULTIPLIER * (info.getTotalAgility()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getModifier(UUID.fromString(ATTACK_SPEED)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(UUID.fromString(ATTACK_SPEED));
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
			
			
			/*
			 * DEXTERITY
			 */
			
			
			
			/*
			 * INTELLIGENCE
			 */
			if (!player.getEntityWorld().isRemote)
			{
				statsCap.setMagicalPower(MAGICAL_POWER_MULTIPLIER * (info.getTotalIntelligence()));
				
				LootSlashConquer.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) player);
			}
			
			/*
			 * WISDOM
			 */			
			if (!player.getEntityWorld().isRemote)
			{
				statsCap.setMaxMana((int) ((MAX_MANA_MULTIPLIER * (info.getTotalWisdom())) + 100));
				
				LootSlashConquer.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) player);
			}
			
			
			/*
			 * FORTITUDE
			 */
			// increases max health
			AttributeModifier fortitudeMaxHealth = new AttributeModifier(UUID.fromString(MAX_HEALTH), "maxHealth", MAX_HEALTH_MULTIPLIER * (info.getTotalFortitude()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(UUID.fromString(MAX_HEALTH)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(UUID.fromString(MAX_HEALTH));
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(fortitudeMaxHealth);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(fortitudeMaxHealth);
		}
	}
}

package com.thexfactor117.lsc.player;

import java.util.UUID;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.network.PacketUpdatePlayerStats;
import com.thexfactor117.lsc.util.PlayerUtil;

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
	 * Helper method to update player attributes based on current cap.
	 */
	public static void updateAttributes(EntityPlayer player)
	{
		LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
		
		if (cap != null)
		{
			/* 
			 * STRENGTH
			 */
			// TODO: convert new damage to add to the player's attribute...look into this more, the whole system is all sorts of fucked up.
			// increase attack damage
			/*AttributeModifier strengthAttackDamage = new AttributeModifier(UUID.fromString(ATTACK_DAMAGE), "playerStrength", ATTACK_DAMAGE_MULTIPLIER * (cap.getTotalStrength()), 0);

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
			AttributeModifier agilityMovementSpeed = new AttributeModifier(UUID.fromString(MOVEMENT_SPEED), "agilityMovementSpeed", Configs.playerCategory.movementSpeedMultiplier * (cap.getTotalAgility()), 0);
			
			if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(UUID.fromString(MOVEMENT_SPEED)) != null)
			{
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString(MOVEMENT_SPEED));
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			}
			else
				player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
			
			// increase attack speed
			AttributeModifier agilityAttackSpeed = new AttributeModifier(UUID.fromString(ATTACK_SPEED), "agilityAttackSpeed", Configs.playerCategory.attackSpeedMultiplier * (cap.getTotalAgility()), 0);
			
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
				int bonus = cap.getTotalDexterity() > 0 ? 1 : 0;
				
				cap.setCriticalChance(Configs.playerCategory.critChanceMultiplier * ((cap.getTotalDexterity() / 5) + bonus));
				cap.setCriticalDamage(Configs.playerCategory.critDamageMultiplier * ((cap.getTotalDexterity() / 2) + bonus));
				
				
				/*
				 * INTELLIGENCE
				 */
				//cap.setMagicalPower(MAGICAL_POWER_MULTIPLIER * (cap.getTotalIntelligence()));
				
				/*
				 * WISDOM
				 */			
				cap.setMaxMana((int) ((Configs.playerCategory.maxManaMultiplier * cap.getTotalWisdom()) + 100));
				
				cap.setManaPerSecond(Configs.playerCategory.manaPer5 * cap.getTotalWisdom());
				
				//fortitude
				cap.setHealthPerSecond(Configs.playerCategory.healthPer5 * cap.getTotalFortitude());
				LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) player);
			}
			
			
			/*
			 * FORTITUDE
			 */
			// increases max health
			AttributeModifier fortitudeMaxHealth = new AttributeModifier(UUID.fromString(MAX_HEALTH), "maxHealth", Configs.playerCategory.maxHealthMultiplier * (cap.getTotalFortitude()), 0);
			
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

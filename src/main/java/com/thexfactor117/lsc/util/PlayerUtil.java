package com.thexfactor117.lsc.util;

import java.util.UUID;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.cap.CapabilityLSCPlayer;
import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
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
public class PlayerUtil
{
	// private static final String ATTACK_DAMAGE = "ece5e91f-afc9-4883-a169-c7b5883c12dc";
	private static final String MOVEMENT_SPEED = "50ac5b8b-00a6-436c-bdc3-9848393bb7b7";
	private static final String ATTACK_SPEED = "e574e861-e5bd-4906-b72e-f6be6e4c9563";
	private static final String MAX_HEALTH = "e3762718-bbd8-4763-bfe9-1d18d70eaa76";

	/**
	 * Returns the LSC player capability.
	 * 
	 * @param player
	 * @return
	 */
	public static LSCPlayerCapability getLSCPlayer(EntityPlayer player)
	{
		LSCPlayerCapability cap = (LSCPlayerCapability) player.getCapability(CapabilityLSCPlayer.PLAYER_CAP, null);

		if (cap != null)
		{
			return cap;
		}
		else
		{
			LootSlashConquer.LOGGER.info("ERROR: Player: " + player.getName() + " doesn't have an attached capability. Things might break.");
			return null;
		}
	}

	/**
	 * Updates all of the players stats.
	 * 
	 * @param player
	 */
	public static void updateAllStats(EntityPlayer player)
	{
		updateStrengthStat(player);
		updateAgilityStat(player);
		updateDexterityStat(player);
		updateIntelligenceStat(player);
		updateWisdomStat(player);
		updateFortitudeStat(player);
	}

	/**
	 * Updates the player's strength stat. Does nothing right now...re-work how strength works?
	 * 
	 * @param player
	 */
	public static void updateStrengthStat(EntityPlayer player)
	{
		// TODO: convert new damage to add to the player's attribute...look into this more, the whole system is all sorts of fucked up.
		// increase attack damage
		/*
		 * AttributeModifier strengthAttackDamage = new AttributeModifier(UUID.fromString(ATTACK_DAMAGE), "playerStrength", ATTACK_DAMAGE_MULTIPLIER * (cap.getTotalStrength()), 0);
		 * 
		 * if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(UUID.fromString(ATTACK_DAMAGE)) != null) {
		 * player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(UUID.fromString(ATTACK_DAMAGE));
		 * player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthAttackDamage); } else
		 * player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(strengthAttackDamage);
		 */
	}

	/**
	 * Updates the player's Agility stat. Increase movement speed and attack speed.
	 * 
	 * @param player
	 */
	public static void updateAgilityStat(EntityPlayer player)
	{
		LSCPlayerCapability cap = getLSCPlayer(player);

		// increase movement speed
		AttributeModifier agilityMovementSpeed = new AttributeModifier(UUID.fromString(MOVEMENT_SPEED), "agilityMovementSpeed",
				Configs.playerCategory.movementSpeedMultiplier * (cap.getTotalAgility()), 0);

		if (player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(UUID.fromString(MOVEMENT_SPEED)) != null)
		{
			player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(UUID.fromString(MOVEMENT_SPEED));
			player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);
		}
		else player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(agilityMovementSpeed);

		// increase attack speed
		AttributeModifier agilityAttackSpeed = new AttributeModifier(UUID.fromString(ATTACK_SPEED), "agilityAttackSpeed", Configs.playerCategory.attackSpeedMultiplier * (cap.getTotalAgility()), 0);

		if (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getModifier(UUID.fromString(ATTACK_SPEED)) != null)
		{
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(UUID.fromString(ATTACK_SPEED));
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
		}
		else player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(agilityAttackSpeed);
	}

	/**
	 * Updates the player's Dexterity stat. Increase critical chance and critical damage.
	 * 
	 * @param player
	 */
	public static void updateDexterityStat(EntityPlayer player)
	{
		if (!player.getEntityWorld().isRemote)
		{
			LSCPlayerCapability cap = getLSCPlayer(player);

			int bonus = cap.getTotalDexterity() > 0 ? 1 : 0;

			cap.setCriticalChance(Configs.playerCategory.critChanceMultiplier * ((cap.getTotalDexterity() / 5) + bonus));
			cap.setCriticalDamage(Configs.playerCategory.critDamageMultiplier * ((cap.getTotalDexterity() / 2) + bonus));

			LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) player);
		}
	}

	/**
	 * Updates the player's Intelligence stat. Doesn't currently do anything...needs re-working.
	 * 
	 * @param player
	 */
	public static void updateIntelligenceStat(EntityPlayer player)
	{
		if (!player.getEntityWorld().isRemote)
		{
			// LSCPlayerCapability cap = getLSCPlayer(player);

			// cap.setMagicalPower(MAGICAL_POWER_MULTIPLIER * (cap.getTotalIntelligence()));

			// LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) player);
		}
	}
	
	/**
	 * Updates the player's Wisdom stat. Increases max mana and mana regen.
	 * @param player
	 */
	public static void updateWisdomStat(EntityPlayer player)
	{
		if (!player.getEntityWorld().isRemote)
		{
			LSCPlayerCapability cap = getLSCPlayer(player);

			cap.setMaxMana((int) ((Configs.playerCategory.maxManaMultiplier * cap.getTotalWisdom()) + 100));
			cap.setManaPerSecond(Configs.playerCategory.manaPer5 * cap.getTotalWisdom());

			LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) player);
		}
	}

	/**
	 * Updates the player's Fortitude stat. Increases max health and health regen.
	 * @param player
	 */
	public static void updateFortitudeStat(EntityPlayer player)
	{
		LSCPlayerCapability cap = getLSCPlayer(player);
		
		// increases max health
		AttributeModifier fortitudeMaxHealth = new AttributeModifier(UUID.fromString(MAX_HEALTH), "maxHealth", Configs.playerCategory.maxHealthMultiplier * (cap.getTotalFortitude()), 0);

		if (player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(UUID.fromString(MAX_HEALTH)) != null)
		{
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(UUID.fromString(MAX_HEALTH));
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(fortitudeMaxHealth);
		}
		else player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(fortitudeMaxHealth);

		if (!player.getEntityWorld().isRemote)
		{
			cap.setHealthPerSecond(Configs.playerCategory.healthPer5 * cap.getTotalFortitude());
			
			if (player.getHealth() > player.getMaxHealth())
			{
				player.setHealth(player.getMaxHealth());
			}
			
			LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) player);
		}
	}
}

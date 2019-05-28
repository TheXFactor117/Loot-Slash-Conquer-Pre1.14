package com.thexfactor117.lsc.capabilities.implementation;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.api.ILSCPlayer;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.loot.attributes.AttributeArmor;
import com.thexfactor117.lsc.network.client.PacketUpdateCoreStats;
import com.thexfactor117.lsc.network.client.PacketUpdatePlayerStats;
import com.thexfactor117.lsc.util.ItemUtil;
import com.thexfactor117.lsc.util.PlayerUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

/**
 *
 * @author TheXFactor117
 * 
 * Implementation of the player capability.
 *
 */
public class LSCPlayerCapability implements ILSCPlayer
{
	// basic info
	private int playerClass;
	private int level;
	private int experience;
	private int skillPoints;

	// modifiers
	private double physicalPower;
	private double rangedPower;
	private double magicalPower;

	private int physicalResistance;
	private int magicalResistance;
	
	private int fireResistance;
	private int frostResistance;
	private int lightningResistance;
	private int poisonResistance;

	private int mana;
	private int maxMana;
	private int manaPerSecond;

	private int healthPerSecond;

	private double criticalChance;
	private double criticalDamage;

	private double cooldownReduction;

	private int updateTicks;
	private int regenTicks;

	// stats
	private int strengthStat;
	private int agilityStat;
	private int dexterityStat;
	private int intelligenceStat;
	private int wisdomStat;
	private int fortitudeStat;

	private int strengthBonusStat;
	private int agilityBonusStat;
	private int dexterityBonusStat;
	private int intelligenceBonusStat;
	private int wisdomBonusStat;
	private int fortitudeBonusStat;

	private final EntityPlayer player;

	private ItemStack helmet = ItemStack.EMPTY;
	private ItemStack chestplate = ItemStack.EMPTY;
	private ItemStack leggings = ItemStack.EMPTY;
	private ItemStack boots = ItemStack.EMPTY;

	public LSCPlayerCapability(@Nullable EntityPlayer player)
	{
		this.player = player;
	}

	/**
	 * Updates the LSC player state.
	 */
	public void tickPlayer()
	{
		if (player == null) return;

		LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);

		// current armor stacks
		ItemStack stackHelmet = player.inventory.armorInventory.get(3);
		ItemStack stackChestplate = player.inventory.armorInventory.get(2);
		ItemStack stackLeggings = player.inventory.armorInventory.get(1);
		ItemStack stackBoots = player.inventory.armorInventory.get(0);

		boolean armorChanged = false;

		// update armor stacks if we find new ones
		if (!ItemStack.areItemStacksEqual(stackHelmet, helmet))
		{
			armorChanged = true;
			updateArmorAttributes(helmet, stackHelmet, cap);
			this.helmet = stackHelmet;
		}
		if (!ItemStack.areItemStacksEqual(stackChestplate, chestplate))
		{
			armorChanged = true;
			updateArmorAttributes(chestplate, stackChestplate, cap);
			this.chestplate = stackChestplate;
		}
		if (!ItemStack.areItemStacksEqual(stackLeggings, leggings))
		{
			armorChanged = true;
			updateArmorAttributes(leggings, stackLeggings, cap);
			this.leggings = stackLeggings;
		}
		if (!ItemStack.areItemStacksEqual(stackBoots, boots))
		{
			armorChanged = true;
			updateArmorAttributes(boots, stackBoots, cap);
			this.boots = stackBoots;
		}

		if (armorChanged)
		{
			// send update packet?
			LootSlashConquer.network.sendTo(new PacketUpdateCoreStats(cap), (EntityPlayerMP) player);
		}

		// health and mana regeneration
		if (cap.getRegenTicks() % 100 == 0)
		{
			if (cap.getMana() < cap.getMaxMana())
			{
				cap.increaseMana(cap.getManaPerSecond());
			}

			if (player.getHealth() < player.getMaxHealth())
			{
				player.heal(cap.getHealthPerSecond());
			}

			LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) player);

			cap.resetRegenTicks();
		}

		cap.incrementRegenTicks();
	}
	
	/**
	 * Iterates through all of the attributes on the oldStack and newStack, unequipping effects of
	 * the oldStack and equipping effects on the newStack.
	 * @param oldStack
	 * @param newStack
	 * @param cap
	 */
	private void updateArmorAttributes(ItemStack oldStack, ItemStack newStack, LSCPlayerCapability cap)
	{
		for (Attribute attribute : ItemUtil.getAllAttributes(oldStack))
		{
			if (attribute instanceof AttributeArmor)
			{
				((AttributeArmor) attribute).onUnequip(cap, oldStack);
			}
		}

		for (Attribute attribute : ItemUtil.getAllAttributes(newStack))
		{
			if (attribute instanceof AttributeArmor)
			{
				((AttributeArmor) attribute).onEquip(cap, newStack);
			}
		}

		updatePlayerPower();
		updatePlayerResistance();
		LootSlashConquer.network.sendTo(new PacketUpdatePlayerStats(cap), (EntityPlayerMP) player);
	}
	
	/**
	 * Updates the player's physical, ranged, and magical power.
	 */
	public void updatePlayerPower()
	{
		double physicalPower = (Math.pow(Configs.weaponCategory.damageBaseFactor, getPlayerLevel()) + getTotalStrength()) * (0.85 * 0.8);
		double rangedPower = (Math.pow(Configs.weaponCategory.damageBaseFactor, getPlayerLevel()) + getTotalDexterity()) * (0.85 * 0.8);
		double magicalPower = (Math.pow(Configs.weaponCategory.damageBaseFactor, getPlayerLevel()) + getTotalIntelligence()) * (0.85 * 0.8);

		this.physicalPower = getTotalStrength() != 0 ? physicalPower : 0;
		this.rangedPower = getTotalDexterity() != 0 ? rangedPower : 0;
		this.magicalPower = getTotalIntelligence() != 0 ? magicalPower : 0;
	}
	
	public void updatePlayerResistance()
	{
		int physicalResistance = (int) ((Math.pow(1.05, getPlayerLevel()) + getTotalStrength()) * (0.85 * 0.8));
		int magicalResistance = (int) ((Math.pow(1.05, getPlayerLevel()) + getTotalIntelligence()) * (0.85 * 0.8));
		
		this.physicalResistance = getTotalStrength() != 0 ? physicalResistance : 0;
		this.magicalResistance = getTotalIntelligence() != 0 ? magicalResistance : 0;
	}

	/*
	 * 
	 * Miscellaneous Getters and Setters
	 * 
	 */

	public ItemStack getHelmet()
	{
		return helmet;
	}

	public ItemStack getChestplate()
	{
		return chestplate;
	}

	public ItemStack getLeggings()
	{
		return leggings;
	}

	public ItemStack getBoots()
	{
		return boots;
	}

	/*
	 * 
	 * Interface Getters and Setters
	 * 
	 */

	@Override
	public int getPlayerClass()
	{
		return playerClass;
	}

	@Override
	public void setPlayerClass(int playerClass)
	{
		this.playerClass = playerClass;
	}

	@Override
	public int getPlayerLevel()
	{
		return level;
	}

	@Override
	public void setPlayerLevel(int level)
	{
		this.level = level;
	}

	@Override
	public int getPlayerExperience()
	{
		return experience;
	}

	@Override
	public void setPlayerExperience(int experience)
	{
		this.experience = experience;
	}

	@Override
	public int getSkillPoints()
	{
		return skillPoints;
	}

	@Override
	public void setSkillPoints(int skillPoints)
	{
		this.skillPoints = skillPoints;
	}

	/*
	 * POWER
	 */

	@Override
	public void setPhysicalPower(double power)
	{
		this.physicalPower = power;
	}

	@Override
	public double getPhysicalPower()
	{
		return physicalPower;
	}

	@Override
	public void setRangedPower(double power)
	{
		this.rangedPower = power;
	}

	@Override
	public double getRangedPower()
	{
		return rangedPower;
	}

	@Override
	public void setMagicalPower(double power)
	{
		this.magicalPower = power;
	}

	@Override
	public double getMagicalPower()
	{
		return magicalPower;
	}

	/*
	 * RESISTANCE
	 */
	
	@Override
	public void setPhysicalResistance(int resistance)
	{
		this.physicalResistance = resistance;
	}
	
	@Override
	public int getPhysicalResistance()
	{
		return physicalResistance;
	}
	
	@Override
	public void setMagicalResistance(int resistance)
	{
		this.magicalResistance = resistance;
	}
	
	@Override
	public int getMagicalResistance()
	{
		return magicalResistance;
	}

	@Override
	public void setFireResistance(int resistance)
	{
		this.fireResistance = resistance;
	}

	@Override
	public int getFireResistance()
	{
		return fireResistance;
	}

	@Override
	public void setFrostResistance(int resistance)
	{
		this.frostResistance = resistance;
	}

	@Override
	public int getFrostResistance()
	{
		return frostResistance;
	}

	@Override
	public void setLightningResistance(int resistance)
	{
		this.lightningResistance = resistance;
	}

	@Override
	public int getLightningResistance()
	{
		return lightningResistance;
	}

	@Override
	public void setPoisonResistance(int resistance)
	{
		this.poisonResistance = resistance;
	}

	@Override
	public int getPoisonResistance()
	{
		return poisonResistance;
	}

	/*
	 * MANA
	 */

	/** Increases the current mana count by the given amount. */
	public void increaseMana(int mana)
	{
		this.setMana(this.getMana() + mana);

		if (this.mana > this.maxMana) this.mana = this.maxMana;
	}

	/** Decreases the current mana count by the given amount. */
	public void decreaseMana(int mana)
	{
		this.setMana(this.getMana() - mana);

		if (this.mana < 0) this.mana = 0;
	}

	@Override
	public void setMana(int mana)
	{
		this.mana = mana;

		if (mana > getMaxMana()) this.mana = getMaxMana();
		else if (mana < 0) this.mana = 0;
	}

	@Override
	public int getMana()
	{
		return mana;
	}

	@Override
	public void setMaxMana(int maxMana)
	{
		this.maxMana = maxMana;
	}

	@Override
	public int getMaxMana()
	{
		return maxMana;
	}

	@Override
	public void setManaPerSecond(int manaPerSecond)
	{
		this.manaPerSecond = manaPerSecond;
	}

	@Override
	public int getManaPerSecond()
	{
		return manaPerSecond;
	}

	/*
	 * HEALTH
	 */

	@Override
	public void setHealthPerSecond(int healthPerSecond)
	{
		this.healthPerSecond = healthPerSecond;
	}

	@Override
	public int getHealthPerSecond()
	{
		return healthPerSecond;
	}

	/*
	 * CRITICAL
	 */

	@Override
	public void setCriticalChance(double criticalChance)
	{
		this.criticalChance = criticalChance;
	}

	@Override
	public double getCriticalChance()
	{
		return criticalChance;
	}

	@Override
	public void setCriticalDamage(double criticalDamage)
	{
		this.criticalDamage = criticalDamage;
	}

	@Override
	public double getCriticalDamage()
	{
		return criticalDamage;
	}

	/*
	 * COOLDOWN
	 */

	public void setCooldownReduction(double cooldown)
	{
		this.cooldownReduction = cooldown;
	}

	public double getCooldownReduction()
	{
		return cooldownReduction;
	}

	/*
	 * TICKS
	 */

	public void incrementUpdateTicks()
	{
		this.updateTicks += 1;
	}

	public void incrementRegenTicks()
	{
		this.regenTicks += 1;
	}

	public void resetUpdateTicks()
	{
		this.updateTicks = 0;
	}

	public void resetRegenTicks()
	{
		this.regenTicks = 0;
	}

	@Override
	public void setUpdateTicks(int ticks)
	{
		this.updateTicks = ticks;
	}

	@Override
	public int getUpdateTicks()
	{
		return updateTicks;
	}

	@Override
	public void setRegenTicks(int ticks)
	{
		this.regenTicks = ticks;
	}

	@Override
	public int getRegenTicks()
	{
		return regenTicks;
	}

	/*
	 * Stats
	 */

	/** Sets all bonus stats to zero. */
	public void removeBonusStats()
	{
		this.strengthBonusStat = 0;
		this.agilityBonusStat = 0;
		this.dexterityBonusStat = 0;
		this.intelligenceBonusStat = 0;
		this.wisdomBonusStat = 0;
		this.fortitudeBonusStat = 0;
	}

	public int getTotalStrength()
	{
		return this.strengthStat + this.strengthBonusStat;
	}

	public int getTotalAgility()
	{
		return this.agilityStat + this.agilityBonusStat;
	}

	public int getTotalDexterity()
	{
		return this.dexterityStat + this.dexterityBonusStat;
	}

	public int getTotalIntelligence()
	{
		return this.intelligenceStat + this.intelligenceBonusStat;
	}

	public int getTotalWisdom()
	{
		return this.wisdomStat + this.wisdomBonusStat;
	}

	public int getTotalFortitude()
	{
		return this.fortitudeStat + this.fortitudeBonusStat;
	}

	@Override
	public int getStrengthStat()
	{
		return strengthStat;
	}

	@Override
	public void setStrengthStat(int stat)
	{
		this.strengthStat = stat;
	}

	@Override
	public int getAgilityStat()
	{
		return agilityStat;
	}

	@Override
	public void setAgilityStat(int stat)
	{
		this.agilityStat = stat;
	}

	@Override
	public int getDexterityStat()
	{
		return dexterityStat;
	}

	@Override
	public void setDexterityStat(int stat)
	{
		this.dexterityStat = stat;
	}

	@Override
	public int getIntelligenceStat()
	{
		return intelligenceStat;
	}

	@Override
	public void setIntelligenceStat(int stat)
	{
		this.intelligenceStat = stat;
	}

	@Override
	public int getWisdomStat()
	{
		return wisdomStat;
	}

	@Override
	public void setWisdomStat(int stat)
	{
		this.wisdomStat = stat;
	}

	@Override
	public int getFortitudeStat()
	{
		return fortitudeStat;
	}

	@Override
	public void setFortitudeStat(int stat)
	{
		this.fortitudeStat = stat;
	}

	// bonuses

	@Override
	public int getBonusStrengthStat()
	{
		return strengthBonusStat;
	}

	@Override
	public void setBonusStrengthStat(int stat)
	{
		this.strengthBonusStat = stat;
	}

	@Override
	public int getBonusAgilityStat()
	{
		return agilityBonusStat;
	}

	@Override
	public void setBonusAgilityStat(int stat)
	{
		this.agilityBonusStat = stat;
	}

	@Override
	public int getBonusDexterityStat()
	{
		return dexterityBonusStat;
	}

	@Override
	public void setBonusDexterityStat(int stat)
	{
		this.dexterityBonusStat = stat;
	}

	@Override
	public int getBonusIntelligenceStat()
	{
		return intelligenceBonusStat;
	}

	@Override
	public void setBonusIntelligenceStat(int stat)
	{
		this.intelligenceBonusStat = stat;
	}

	@Override
	public int getBonusWisdomStat()
	{
		return wisdomBonusStat;
	}

	@Override
	public void setBonusWisdomStat(int stat)
	{
		this.wisdomBonusStat = stat;
	}

	@Override
	public int getBonusFortitudeStat()
	{
		return fortitudeBonusStat;
	}

	@Override
	public void setBonusFortitudeStat(int stat)
	{
		this.fortitudeBonusStat = stat;
	}
}

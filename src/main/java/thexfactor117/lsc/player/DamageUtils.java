package thexfactor117.lsc.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thexfactor117.lsc.capabilities.implementation.PlayerInformation;
import thexfactor117.lsc.capabilities.implementation.Stats;
import thexfactor117.lsc.config.Configs;
import thexfactor117.lsc.loot.Attribute;
import thexfactor117.lsc.util.LSCDamageSource;
import thexfactor117.lsc.util.NBTHelper;

/**
 *
 * @author TheXFactor117
 *
 */
public class DamageUtils
{
	/**
	 * Applies damage modifiers to the damage passed in. This adds additional damage based on
	 * stats like Strength, Dexterity, and Intelligence, where applicable.
	 * @param playerInfo
	 * @param damage
	 * @param type
	 * @return
	 */
	public static double applyDamageModifiers(PlayerInformation playerInfo, double damage, DamageType type)
	{
		switch (type)
		{
			case PHYSICAL_MELEE:
				return damage + getMeleePower(playerInfo);
			case PHYSICAL_RANGED:
				return damage + getRangedPower(playerInfo);
			case MAGICAL:
				return damage + getMagicalPower(playerInfo);
			default:
				return damage;
		}
	}
	
	/**
	 * Applies critical damage to the passed in damage value. This effectively increases the damage
	 * of the attack based on the player's critical stats.
	 * @param stats
	 * @param damage
	 * @param nbt
	 * @return
	 */
	public static double applyCriticalModifier(Stats stats, double damage, NBTTagCompound nbt)
	{
		double damageBeforeCrit = damage;
		
		if (stats.getCriticalChance() > 0)
		{
			if (Math.random() < stats.getCriticalChance())
			{
				damage = (stats.getCriticalDamage() * damageBeforeCrit) + damageBeforeCrit;
			}
		}
		
		return damage;
	}
	
	public static double applyArmorReductions(double damage, EntityPlayer player, PlayerInformation playerInfo)
	{	
		return damage * (damage / (damage + getTotalArmor(player, playerInfo)));
	}
	
	public static double applyElementalResistance(double damage, LSCDamageSource source, EntityPlayer player)
	{
		double reducedDamage = damage;
		
		for (ItemStack stack : player.getArmorInventoryList())
		{
			if (stack.getItem() instanceof ItemArmor)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);

				if (source.isFireDamage() && Attribute.FIRE_RESIST.hasAttribute(nbt)) reducedDamage *= Attribute.FIRE_RESIST.getAmount(nbt);
				else if (source.isFrostDamage() && Attribute.FROST_RESIST.hasAttribute(nbt)) reducedDamage *= Attribute.FROST_RESIST.getAmount(nbt);
				else if (source.isLightningDamage() && Attribute.LIGHTNING_RESIST.hasAttribute(nbt)) reducedDamage *= Attribute.LIGHTNING_RESIST.getAmount(nbt);
			}
		}
		
		return reducedDamage;
	}
	
	// TODO: if power is less than 1 (decimal), set to zero to prevent the decimal being added on as extra damage.
	// not too important but still a thing.
	
	public static double getMeleePower(PlayerInformation playerInfo)
	{
		double multiplier = (Math.random() * (Configs.weaponCategory.damageMaxRandFactor - Configs.weaponCategory.damageMinRandFactor) + Configs.weaponCategory.damageMinRandFactor);
		return (Math.pow(Configs.weaponCategory.damageBaseFactor, playerInfo.getPlayerLevel()) + playerInfo.getTotalStrength()) * (0.85 * multiplier);
	}
	
	public static double getRangedPower(PlayerInformation playerInfo)
	{
		double multiplier = (Math.random() * (Configs.weaponCategory.damageMaxRandFactor - Configs.weaponCategory.damageMinRandFactor) + Configs.weaponCategory.damageMinRandFactor);
		return (Math.pow(Configs.weaponCategory.damageBaseFactor, playerInfo.getPlayerLevel()) + playerInfo.getBonusDexterityStat()) * (0.85 * multiplier);
	}
	
	public static double getMagicalPower(PlayerInformation playerInfo)
	{
		double multiplier = (Math.random() * (Configs.weaponCategory.damageMaxRandFactor - Configs.weaponCategory.damageMinRandFactor) + Configs.weaponCategory.damageMinRandFactor);
		return (Math.pow(Configs.weaponCategory.damageBaseFactor, playerInfo.getPlayerLevel()) + playerInfo.getTotalIntelligence()) * (0.85 * multiplier);
	}
	
	public static double getTotalArmor(EntityPlayer player, PlayerInformation playerInfo)
	{
		double totalArmorPoints = 0;
		
		// checks total armor points
		for (ItemStack stack : player.getArmorInventoryList())
		{
			if (stack.getItem() instanceof ItemArmor)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				totalArmorPoints += nbt.getDouble("ArmorPoints");
			}
		}
		
		return totalArmorPoints;
	}
}

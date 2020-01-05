package com.thexfactor117.lsc.util;

import java.util.Random;


import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.entities.projectiles.Rune;
import com.thexfactor117.lsc.items.base.weapons.ItemMagical;
import com.thexfactor117.lsc.items.base.weapons.ItemRanged;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;


/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemGenerationUtil
{
	// private static final UUID ATTACK_DAMAGE = UUID.fromString("06dbc47d-eaf1-4604-9b91-926e475012c2");
	// private static final UUID ATTACK_SPEED = UUID.fromString("335ede30-242d-41b6-a4f7-dd24ed2adce5");
	// private static final UUID ARMOR = UUID.fromString("81a2ee21-fe83-41fb-8b2f-bf5ef33a71a8");

	public static Random rand = new Random();

	/**
	 * Sets a certain amount of random attributes to the stack depending on the Rarity.
	 * 
	 * @param stack
	 * @param nbt
	 * @param rarity
	 * @param player
	 */
	public static void setRandomWeaponAttributes(ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		int amount = getAttributeAmounts(stack);

		for (int i = 0; i < amount; i++)
		{
			Attribute attribute = AttributeUtil.getRandomWeaponAttribute();

			if (attribute.hasAttribute(nbt)) i--;
			else attribute.addAttribute(stack, nbt, rand);
		}
	}

	public static void setRandomArmorAttributes(ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		int amount = getAttributeAmounts(stack);

		for (int i = 0; i < amount; i++)
		{
			Attribute attribute = AttributeUtil.getRandomArmorAttribute();

			if (attribute.hasAttribute(nbt)) i--;
			else attribute.addAttribute(stack, nbt, rand);
		}
	}

	public static int getAttributeAmounts(ItemStack stack)
	{
		Rarity rarity = ItemUtil.getItemRarity(stack);
		int amount = 0;

		// determine how many attributes should be applied.
		switch (rarity)
		{
			case COMMON:
				amount = (int) (1); // 1 guaranteed attribute
				break;
			case UNCOMMON:
				amount = (int) (2); // 1 guaranteed attribute, 50% chance for an additional one.
				break;
			case RARE:
				amount = (int) (3); // 2 guaranteed attributes, 50% chance for an additional one.
				break;
			case EPIC:
				amount = (int) (Math.random() * 2 + 3); // 2 guaranteed attributes, 33% chance for an additional one, 33% for an additional two
				break;
			case LEGENDARY:
				amount = (int) (Math.random() * 2 + 4); // 3 guaranteed attributes, 33% chance for an additional one, 33% for an additional two
				break;
			default:
				break;
		}

		return amount;
	}

	/**
	 * Sets the item's primary attributes, such as damage and attack speed.
	 *
	 * @param stack
	 */
	public static void setPrimaryAttributes(ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);

		if (stack.getItem() instanceof ItemSword)
		{
			double baseDamage = ItemUtil.getAttributeModifierValue(stack, SharedMonsterAttributes.ATTACK_DAMAGE, EntityEquipmentSlot.MAINHAND, ItemUtil.VANILLA_ATTACK_DAMAGE_MODIFIER);
			double baseAttackSpeed = ItemUtil.getAttributeModifierValue(stack, SharedMonsterAttributes.ATTACK_SPEED, EntityEquipmentSlot.MAINHAND, ItemUtil.VANILLA_ATTACK_SPEED_MODIFIER);
			// LootSlashConquer.LOGGER.info("Base Damage: " + baseDamage);
			double weightedDamage = getWeightedDamage(ItemUtil.getItemLevel(stack), ItemUtil.getItemRarity(stack), baseDamage);
			double weightedAttackSpeed = getWeightedAttackSpeed(ItemUtil.getItemRarity(stack), baseAttackSpeed);

			setMinMaxDamage(nbt, weightedDamage);
			nbt.setDouble("AttackSpeed", weightedAttackSpeed);

			ItemUtil.setAttributeModifierValue(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND), SharedMonsterAttributes.ATTACK_DAMAGE, ItemUtil.VANILLA_ATTACK_DAMAGE_MODIFIER, ItemUtil.getItemDamage(stack));
			ItemUtil.setAttributeModifierValue(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND), SharedMonsterAttributes.ATTACK_SPEED, ItemUtil.VANILLA_ATTACK_SPEED_MODIFIER, weightedAttackSpeed);
		}
		else if (stack.getItem() instanceof ItemArmor)
		{
			double baseArmor = ((ItemArmor) stack.getItem()).damageReduceAmount;
			double weightedArmor = getWeightedArmor(ItemUtil.getItemRarity(stack), ItemUtil.getItemLevel(stack), baseArmor);

			setMinMaxArmor(stack, weightedArmor);

			ItemUtil.setAttributeModifierValue(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND), SharedMonsterAttributes.ARMOR, ItemUtil.VANILLA_ARMOR_MODIFIER, ItemUtil.getItemArmor(stack));
		}
		else if (stack.getItem() instanceof ItemBow)
		{
			double baseDamage;
			double baseAttackSpeed;

			if (stack.getItem() instanceof ItemRanged) // handle our bows
			{
				baseDamage = ((ItemRanged) stack.getItem()).getBaseDamage();
				baseAttackSpeed = ((ItemRanged) stack.getItem()).getBaseDrawSpeed();
			}
			// TODO: fix this implementation?
			else // handle vanilla/modded bows
			{
				baseDamage = 3;
				baseAttackSpeed = 2;
			}

			double weightedDamage = getWeightedDamage(ItemUtil.getItemLevel(stack), ItemUtil.getItemRarity(stack), baseDamage);
			double weightedAttackSpeed = getWeightedAttackSpeed(ItemUtil.getItemRarity(stack), baseAttackSpeed);

			setMinMaxDamage(nbt, weightedDamage);
			if (Attribute.ATTACK_SPEED.hasAttribute(nbt)) weightedAttackSpeed += (weightedAttackSpeed * Attribute.ATTACK_SPEED.getAttributeValue(nbt));
			nbt.setDouble("AttackSpeed", weightedAttackSpeed);
		}
		else if (stack.getItem() instanceof ItemMagical)
		{
			ItemMagical item = (ItemMagical) stack.getItem();

			double weightedDamage = getWeightedDamage(ItemUtil.getItemLevel(stack), ItemUtil.getItemRarity(stack), item.getBaseDamage());
			double weightedAttackSpeed = getWeightedAttackSpeed(ItemUtil.getItemRarity(stack), item.getBaseAttackSpeed());

			setMinMaxDamage(nbt, weightedDamage);
			nbt.setDouble("AttackSpeed", weightedAttackSpeed);
		}
	}

	/** Sets the rune of the current magical weapon. Only used for magical weapons. */
	public static void setRune(NBTTagCompound nbt)
	{
		Rune.setRune(nbt, Rune.getRandomRune(nbt, rand));
	}

	/**
	 * Sets the minimum and maximum damage an item can deal and sets it to NBT.
	 *
	 * @param nbt
	 * @param damage
	 */
	public static void setMinMaxDamage(NBTTagCompound nbt, double damage)
	{
		// min/max rand factor control the range of the random decimal. The higher the factors, the bigger range
		double minRandFactor = Configs.weaponCategory.rangeMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.rangeMaxRandFactor;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);
		// scales the range by level (higher level items have greater ranges
		double rangeMultiplier = 1.0 + nbt.getInteger("Level") * Configs.weaponCategory.rangeMultiplier * 0.01;

		// set the min/max by adding range multiplied by level and user-defined range multiplier to the minimum damage value and adding the level multiplied by the user-defined range multiplier to the max value.
		double minDamage = damage * multiplier * rangeMultiplier;
		double maxDamage = damage * rangeMultiplier;

		if (Attribute.MINIMUM_DAMAGE.hasAttribute(nbt)) minDamage += Attribute.MINIMUM_DAMAGE.getAttributeValue(nbt);
		if (Attribute.MAXIMUM_DAMAGE.hasAttribute(nbt)) maxDamage += Attribute.MAXIMUM_DAMAGE.getAttributeValue(nbt);

		nbt.setDouble("DamageMinValue", minDamage);
		nbt.setDouble("DamageMaxValue", maxDamage);
		nbt.setDouble("DamageValue", Math.random() * (maxDamage - minDamage) + minDamage);
	}

	public static void setMinMaxArmor(ItemStack stack, double armor)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		double minRandFactor = Configs.weaponCategory.rangeMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.rangeMaxRandFactor;
		double multiplier = Math.random() * (maxRandFactor - minRandFactor) + minRandFactor;

		double levelMultiplier = ItemUtil.getItemLevel(stack) * Configs.weaponCategory.rangeMultiplier;
		double range = multiplier * levelMultiplier;

		double minArmor = armor + armor * range;
		double maxArmor = armor + armor * levelMultiplier;

		nbt.setDouble("ArmorMinValue", minArmor);
		nbt.setDouble("ArmorMaxValue", maxArmor);
		nbt.setInteger("ArmorValue", (int) (Math.random() * (maxArmor - minArmor) + minArmor));
	}

	/**
	 * Returns a weighted damage value dependent on the Rarity and Level of the item.
	 *
	 * @param nbt
	 * @param rarity
	 * @param base
	 * @return
	 */
	public static double getWeightedDamage(int level, Rarity rarity, double base)
	{
		double baseFactor = Configs.weaponCategory.damageBaseFactor;
		double tier = 0.0;

		// set the new damage equal to the base multiplied by the multiplier and the rarity factor.
		switch (rarity)
		{
			case COMMON:
				tier = 1.05;
				break;
			case UNCOMMON:
				tier = 1.1;
				break;
			case RARE:
				tier = 1.15;
				break;
			case EPIC:
				tier = 1.2;
				break;
			case LEGENDARY:
				tier = 1.25;
				break;
		}

		return baseFactor * (base + Math.pow(tier, base));
	}

	// TODO: add attack speed values to config.
	public static double getWeightedAttackSpeed(Rarity rarity, double base)
	{
		double speed = base;

		switch (rarity)
		{
			case COMMON:
				speed = base + Configs.weaponCategory.commonFactor;
				break;
			case UNCOMMON:
				speed = base + Configs.weaponCategory.uncommonFactor;
				break;
			case RARE:
				speed = base + Configs.weaponCategory.rareFactor;
				break;
			case EPIC:
				speed = base + Configs.weaponCategory.epicFactor;
				break;
			case LEGENDARY:
				speed = base + Configs.weaponCategory.legendaryFactor;
				break;
			default:
				break;
		}

		return speed;
	}

	// TODO: add armor values to config.
	public static double getWeightedArmor(Rarity rarity, int level, double base)
	{
		double baseFactor = Configs.weaponCategory.damageBaseFactor;
		double tier = 0.0;

		switch (rarity)
		{
			case COMMON:
				tier = 1.1;
				break;
			case UNCOMMON:
				tier = 1.2;
				break;
			case RARE:
				tier = 1.3;
				break;
			case EPIC:
				tier = 1.4;
				break;
			case LEGENDARY:
				tier = 1.5;
				break;
		}

		return baseFactor * (Math.pow(base, tier));
	}

	/**
	 * Hides different flags from appearing on the item's tooltip.
	 *
	 * @param nbt
	 */
	public static void hideFlags(NBTTagCompound nbt)
	{
		nbt.setInteger("HideFlags", 6);
	}

	/**
	 * Helper method for writing new attribute modifiers.
	 *
	 * @param attribute
	 * @param modifier
	 * @param slot
	 * @return
	 */
	public static NBTTagCompound writeAttributeModifierToNBT(IAttribute attribute, AttributeModifier modifier, EntityEquipmentSlot slot)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("AttributeName", attribute.getName());
		nbt.setString("Name", modifier.getName());
		nbt.setString("Slot", slot.getName());
		nbt.setDouble("Amount", modifier.getAmount());
		nbt.setInteger("Operation", modifier.getOperation());
		nbt.setLong("UUIDMost", modifier.getID().getMostSignificantBits());
		nbt.setLong("UUIDLeast", modifier.getID().getLeastSignificantBits());

		return nbt;
	}
}

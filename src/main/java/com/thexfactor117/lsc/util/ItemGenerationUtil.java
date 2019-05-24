package com.thexfactor117.lsc.util;

import java.util.Random;
import java.util.UUID;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.entities.projectiles.Rune;
import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.items.base.ItemMagical;
import com.thexfactor117.lsc.items.base.ItemMelee;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.AttributeBase;
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
	private static final UUID ATTACK_DAMAGE = UUID.fromString("06dbc47d-eaf1-4604-9b91-926e475012c2");
	private static final UUID ATTACK_SPEED = UUID.fromString("335ede30-242d-41b6-a4f7-dd24ed2adce5");
	private static final UUID ARMOR = UUID.fromString("81a2ee21-fe83-41fb-8b2f-bf5ef33a71a8");

	public static Random rand = new Random();

	/** Set the type of the item to NBT for use in generating names. */
	public static void setTypes(ItemStack stack, NBTTagCompound nbt)
	{
		if (stack.getItem() instanceof ItemSword)
		{
			if (stack.getItem() instanceof ItemMelee)
			{
				ItemMelee item = (ItemMelee) stack.getItem();

				nbt.setString("Type", item.getType());
			}
			else nbt.setString("Type", "sword");
		}
		else if (stack.getItem() instanceof ItemArmor)
		{
			if (((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.HEAD) nbt.setString("Type", "helmet");
			else if (((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.CHEST) nbt.setString("Type", "chestplate");
			else if (((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.LEGS) nbt.setString("Type", "leggings");
			else if (((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.FEET) nbt.setString("Type", "boots");
		}
		else if (stack.getItem() instanceof ItemBow)
		{
			nbt.setString("Type", "bow");
		}
		else if (stack.getItem() instanceof ItemMagical)
		{
			nbt.setString("Type", "staff");
		}
		else if (stack.getItem() instanceof ItemBauble)
		{
			ItemBauble item = (ItemBauble) stack.getItem();

			nbt.setString("Type", item.getBaubleType(stack).toString().toLowerCase());
		}
	}

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
		int[] amounts = getAttributeAmounts(stack);
		int amount = amounts[0];
		int bonusAmount = amounts[1];
		
		for (int i = 0; i < amount; i++)
		{
			AttributeBase attribute = AttributeUtil.getRandomWeaponAttribute();
			
			if (attribute.hasAttribute(nbt)) i--;
			else attribute.addAttribute(stack, nbt, rand);
		}

		for (int j = 0; j < bonusAmount; j++)
		{
			AttributeBase attribute = AttributeUtil.getRandomWeaponBonusAttribute();

			if (attribute.hasAttribute(nbt)) j--;
			else attribute.addAttribute(stack, nbt, rand);
		}
	}
	
	public static void setRandomArmorAttributes(ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		int[] amounts = getAttributeAmounts(stack);
		int amount = amounts[0];
		int bonusAmount = amounts[1];
		
		for (int i = 0; i < amount; i++)
		{
			AttributeBase attribute = AttributeUtil.getRandomArmorAttribute();
			
			if (attribute.hasAttribute(nbt)) i--;
			else attribute.addAttribute(stack, nbt, rand);
		}

		for (int j = 0; j < bonusAmount; j++)
		{
			AttributeBase attribute = AttributeUtil.getRandomArmorBonusAttribute();

			if (attribute.hasAttribute(nbt)) j--;
			else attribute.addAttribute(stack, nbt, rand);
		}
	}
	
	private static int[] getAttributeAmounts(ItemStack stack)
	{
		Rarity rarity = ItemUtil.getItemRarity(stack);
		int amount = 0;
		int bonusAmount = 0;

		// determine how many attributes should be applied.
		switch (rarity)
		{
			case COMMON:
				amount = (int) (Math.random() * 2); // 50% chance for 1 attribute (no bonuses)
				break;
			case UNCOMMON:
				amount = (int) (Math.random() * 2 + 1); // 1 guaranteed attribute, 50% chance for an additional 1 (no bonuses)
				break;
			case RARE:
				amount = (int) (Math.random() * 2 + 2); // 2 guaranteed attributes, 50% chance for an additional 1 (no bonuses)
				break;
			case EPIC:
				amount = (int) (Math.random() * 3 + 3); // 3 guaranteed attributes, 33% chance for an additional one, 33% for an additional two
				bonusAmount = Math.random() < 0.1 ? 1 : 0; // 10% chance for a bonus attribute
				break;
			case LEGENDARY:
				amount = (int) (Math.random() * 5 + 3); // 3 guaranteed attributes, chance for up to 4 additional attributes
				bonusAmount = Math.random() < 0.5 ? 1 : 0; // 50% chance for a bonus attribute
				break;
			default:
				break;
		}
		
		return new int[] { amount, bonusAmount };
	}

	public static void setPrimaryAttributes(ItemStack stack)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);

		if (stack.getItem() instanceof ItemSword)
		{
			double baseDamage = ItemUtil.getAttributeModifierValue(stack, SharedMonsterAttributes.ATTACK_DAMAGE, EntityEquipmentSlot.MAINHAND, ItemUtil.VANILLA_ATTACK_DAMAGE_MODIFIER);
			double baseAttackSpeed = ItemUtil.getAttributeModifierValue(stack, SharedMonsterAttributes.ATTACK_SPEED, EntityEquipmentSlot.MAINHAND, ItemUtil.VANILLA_ATTACK_SPEED_MODIFIER);
			double weightedDamage = getWeightedDamage(ItemUtil.getItemLevel(stack), ItemUtil.getItemRarity(stack), baseDamage);
			double weightedAttackSpeed = getWeightedAttackSpeed(ItemUtil.getItemRarity(stack), baseAttackSpeed);

			setMinMaxDamage(nbt, weightedDamage);
			nbt.setDouble("AttackSpeed", weightedAttackSpeed);

			ItemUtil.setAttributeModifierValue(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND), SharedMonsterAttributes.ATTACK_DAMAGE, ItemUtil.VANILLA_ATTACK_DAMAGE_MODIFIER, ItemUtil.getItemDamage(stack));
			ItemUtil.setAttributeModifierValue(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND), SharedMonsterAttributes.ATTACK_SPEED, ItemUtil.VANILLA_ATTACK_SPEED_MODIFIER, weightedAttackSpeed);
		}
		else if (stack.getItem() instanceof ItemArmor)
		{
			double baseArmor = ItemUtil.getAttributeModifierValue(stack, SharedMonsterAttributes.ARMOR, EntityEquipmentSlot.MAINHAND, ItemUtil.VANILLA_ARMOR_MODIFIER);
			double weightedArmor = getWeightedArmor(ItemUtil.getItemRarity(stack), ItemUtil.getItemLevel(stack), baseArmor);
			
			setMinMaxArmor(stack, weightedArmor);
			
			ItemUtil.setAttributeModifierValue(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND), SharedMonsterAttributes.ARMOR, ItemUtil.VANILLA_ARMOR_MODIFIER, ItemUtil.getItemArmor(stack));
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
		double rangeMultiplier = nbt.getInteger("Level") * Configs.weaponCategory.rangeMultiplier;
		// the actual range is calculated here
		// take the random multiplier and multiply it by some value (the higher the value, the bigger the range)
		// add 0.5 so it can't be zero
		// multiply it by the range multiplier (increases the range dependent on the level)
		int range = (int) ((multiplier * 4 + 0.5) * rangeMultiplier);
		// set the min/max by subtracting/adding half the range to the new damage value.
		int minDamage = (int) (damage - (range / 2));
		int maxDamage = (int) (damage + (range / 2));

		if (AttributeBase.MINIMUM_DAMAGE.hasAttribute(nbt)) minDamage += AttributeBase.MINIMUM_DAMAGE.getAttributeValue(nbt);
		if (AttributeBase.MAXIMUM_DAMAGE.hasAttribute(nbt)) maxDamage += AttributeBase.MAXIMUM_DAMAGE.getAttributeValue(nbt);

		if (minDamage == maxDamage) minDamage -= 1;
		if (minDamage <= 0) minDamage = 1;
		while (minDamage >= maxDamage)
			maxDamage += 1;

		nbt.setInteger("DamageMinValue", minDamage);
		nbt.setInteger("DamageMaxValue", maxDamage);
		nbt.setInteger("DamageValue", (int) (Math.random() * (maxDamage - minDamage) + minDamage));
	}
	
	public static void setMinMaxArmor(ItemStack stack, double armor)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		double minRandFactor = Configs.weaponCategory.rangeMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.rangeMaxRandFactor;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);
		
		double rangeMultiplier = nbt.getInteger("Level") * Configs.weaponCategory.rangeMultiplier;
		
		int range = (int) ((multiplier * 4 + 0.5) * rangeMultiplier);
		
		int minArmor = (int) (armor - (range / 2));
		int maxArmor = (int) (armor + (range / 2));
		
		if (minArmor == maxArmor) minArmor -= 1;
		if (minArmor <= 0) minArmor = 1;
		while (minArmor >= maxArmor) maxArmor += 1;
		
		nbt.setInteger("ArmorMinValue", minArmor);
		nbt.setInteger("ArmorMaxValue", maxArmor);
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
		// min/max rand factor controls the range of the random decimal (this creates a sort of range for the damage to fall in,
		// based on the base damage.
		double minRandFactor = Configs.weaponCategory.damageMinRandFactor;
		double maxRandFactor = Configs.weaponCategory.damageMaxRandFactor;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);

		// set the new damage equal to the base multiplied by the multiplier and the rarity factor.
		switch (rarity)
		{
			case COMMON:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.commonFactor * multiplier)));
			case UNCOMMON:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.uncommonFactor * multiplier)));
			case RARE:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.rareFactor * multiplier)));
			case EPIC:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.epicFactor * multiplier)));
			case LEGENDARY:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.legendaryFactor * multiplier)));
			default:
				return base;
		}
	}

	// TODO: add attack speed values to config.
	public static double getWeightedAttackSpeed(Rarity rarity, double base)
	{
		double speed = base;
		double range = 0;

		switch (rarity)
		{
			case COMMON:
				range = 0.1;
				speed = Math.random() * range + (base - 0.1);
				break;
			case UNCOMMON:
				range = 0.15;
				speed = Math.random() * range + (base - 0.05);
				break;
			case RARE:
				range = 0.25;
				speed = Math.random() * range + (base);
				break;
			case EPIC:
				range = 0.4;
				speed = Math.random() * range + (base + 0.1);
				break;
			case LEGENDARY:
				range = 0.65;
				speed = Math.random() * range + (base + 0.2);
				break;
			default:
				break;
		}

		return speed;
	}

	// TODO: add armor values to config.
	public static double getWeightedArmor(Rarity rarity, int level, double base)
	{
		double baseFactor = 1.05;
		double minRandFactor = 0.5;
		double maxRandFactor = 0.7;
		double multiplier = (Math.random() * (maxRandFactor - minRandFactor) + minRandFactor);

		switch (rarity)
		{
			case COMMON:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.commonFactor * multiplier)));
			case UNCOMMON:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.uncommonFactor * multiplier)));
			case RARE:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.rareFactor * multiplier)));
			case EPIC:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.epicFactor * multiplier)));
			case LEGENDARY:
				return (Math.pow(baseFactor, level) * (base * (Configs.weaponCategory.legendaryFactor * multiplier)));
			default:
				return base;
		}
	}
	
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

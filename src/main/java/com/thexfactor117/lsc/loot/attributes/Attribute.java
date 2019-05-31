package com.thexfactor117.lsc.loot.attributes;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Lists;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeAgility;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeAllStats;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeCooldownReduction;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeDexterity;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeFireResistance;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeFortitude;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeFrostResistance;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeIntelligence;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeLightningResistance;
import com.thexfactor117.lsc.loot.attributes.armor.AttributePoisonResistance;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeStrength;
import com.thexfactor117.lsc.loot.attributes.armor.AttributeWisdom;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeAttackSpeed;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeBlind;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeBonusExperience;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeChained;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeCriticalChance;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeCriticalDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFireDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeFrostDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeLifeSteal;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeLightningDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeManaSteal;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeMaximumDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeMinimumDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeNausea;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributePoisonDamage;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeSlow;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeStun;
import com.thexfactor117.lsc.loot.attributes.weapons.AttributeVoid;
import com.thexfactor117.lsc.util.ItemUtil;
import com.thexfactor117.lsc.util.misc.RandomCollection;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
public class Attribute
{
	private String name;
	private String key;
	private double baseValue;
	private boolean upgradeable;
	
	// weapons
	public static final Attribute FIRE_DAMAGE = new AttributeFireDamage();
	public static final Attribute FROST_DAMAGE = new AttributeFrostDamage();
	public static final Attribute LIGHTNING_DAMAGE = new AttributeLightningDamage();
	public static final Attribute POISON_DAMAGE = new AttributePoisonDamage();
	public static final Attribute ATTACK_SPEED = new AttributeAttackSpeed();
	public static final Attribute BONUS_EXPERIENCE = new AttributeBonusExperience();
	public static final Attribute CRITICAL_DAMAGE = new AttributeCriticalDamage();
	public static final Attribute CRITICAL_CHANCE = new AttributeCriticalChance();
	public static final Attribute LIFE_STEAL = new AttributeLifeSteal();
	public static final Attribute MANA_STEAL = new AttributeManaSteal();
	public static final Attribute MINIMUM_DAMAGE = new AttributeMinimumDamage();
	public static final Attribute MAXIMUM_DAMAGE = new AttributeMaximumDamage();
	public static final Attribute STUN = new AttributeStun();
	public static final Attribute SLOW = new AttributeSlow();
	public static final Attribute BLIND = new AttributeBlind();
	public static final Attribute NAUSEA = new AttributeNausea();
	// bonus
	public static final Attribute CHAINED = new AttributeChained();
	public static final Attribute VOID = new AttributeVoid();
	
	// armor
	public static final Attribute STRENGTH = new AttributeStrength();
	public static final Attribute AGILITY = new AttributeAgility();
	public static final Attribute DEXTERITY = new AttributeDexterity();
	public static final Attribute INTELLIGENCE = new AttributeIntelligence();
	public static final Attribute WISDOM = new AttributeWisdom();
	public static final Attribute FORTITUDE = new AttributeFortitude();
	public static final Attribute FIRE_RESISTANCE = new AttributeFireResistance();
	public static final Attribute FROST_RESISTANCE = new AttributeFrostResistance();
	public static final Attribute LIGHTNING_RESISTANCE = new AttributeLightningResistance();
	public static final Attribute POISON_RESISTANCE = new AttributePoisonResistance();
	public static final Attribute COOLDOWN_REDUCTION = new AttributeCooldownReduction();
	//bonus
	public static final Attribute ALL_STATS = new AttributeAllStats();
	
	public static final ArrayList<Attribute> ALL_ATTRIBUTES = Lists.newArrayList();
	public static RandomCollection<Attribute> WEAPON_ATTRIBUTES = new RandomCollection<Attribute>();
	public static RandomCollection<Attribute> ARMOR_ATTRIBUTES = new RandomCollection<Attribute>();
	public static RandomCollection<Attribute> WEAPON_BONUS_ATTRIBUTES = new RandomCollection<Attribute>();
	public static RandomCollection<Attribute> ARMOR_BONUS_ATTRIBUTES = new RandomCollection<Attribute>();
	
	public static final TextFormatting ATTRIBUTE_COLOR = TextFormatting.BLUE;
	
	public Attribute(String name, String key, double baseValue, boolean upgradeable)
	{
		this.name = name;
		this.key = key;
		this.baseValue = baseValue;
		this.upgradeable = upgradeable;
	}

	/**
	 * Returns true if the NBTTagCompound has the specified Attribute.
	 * @param nbt
	 * @return
	 */
	public boolean hasAttribute(NBTTagCompound nbt)
	{
		return nbt.getBoolean(name);
	}
	
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		nbt.setBoolean(name, true);
		nbt.setInteger(name + "_rarity", Rarity.getWeightedRarity(rand, ItemUtil.getItemRarity(stack)).ordinal());
	}
	
	public void removeAttribute(NBTTagCompound nbt)
	{
		nbt.removeTag(name);
	}
	
	/**
	 * Returns the value of the specified Attribute.
	 * @param nbt
	 * @return
	 */
	public double getAttributeValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(name + "_value");
	}
	
	public double getAttributeMinValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(name + "_minvalue");
	}
	
	public double getAttributeMaxValue(NBTTagCompound nbt)
	{
		return nbt.getDouble(name + "_maxvalue");
	}
	
	public Rarity getAttributeRarity(NBTTagCompound nbt)
	{
		switch (nbt.getInteger(name + "_rarity"))
		{
			case 1:
				return Rarity.COMMON;
			case 2:
				return Rarity.UNCOMMON;
			case 3:
				return Rarity.RARE;
			case 4:
				return Rarity.EPIC;
			case 5:
				return Rarity.LEGENDARY;
			default:
				return Rarity.DEFAULT;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		return "";
	}
	
	static
	{
		// weapon
		ALL_ATTRIBUTES.add(FIRE_DAMAGE);
		ALL_ATTRIBUTES.add(FROST_DAMAGE);
		ALL_ATTRIBUTES.add(LIGHTNING_DAMAGE);
		ALL_ATTRIBUTES.add(POISON_DAMAGE);
		ALL_ATTRIBUTES.add(ATTACK_SPEED);
		ALL_ATTRIBUTES.add(BONUS_EXPERIENCE);
		ALL_ATTRIBUTES.add(CRITICAL_DAMAGE);
		ALL_ATTRIBUTES.add(CRITICAL_CHANCE);
		ALL_ATTRIBUTES.add(LIFE_STEAL);
		ALL_ATTRIBUTES.add(MANA_STEAL);
		ALL_ATTRIBUTES.add(MINIMUM_DAMAGE);
		ALL_ATTRIBUTES.add(MAXIMUM_DAMAGE);
		ALL_ATTRIBUTES.add(STUN);
		ALL_ATTRIBUTES.add(SLOW);
		ALL_ATTRIBUTES.add(BLIND);
		ALL_ATTRIBUTES.add(NAUSEA);
		// bonus
		ALL_ATTRIBUTES.add(CHAINED);
		ALL_ATTRIBUTES.add(VOID);
		
		// armor
		ALL_ATTRIBUTES.add(STRENGTH);
		ALL_ATTRIBUTES.add(AGILITY);
		ALL_ATTRIBUTES.add(DEXTERITY);
		ALL_ATTRIBUTES.add(INTELLIGENCE);
		ALL_ATTRIBUTES.add(WISDOM);
		ALL_ATTRIBUTES.add(FORTITUDE);
		ALL_ATTRIBUTES.add(FIRE_RESISTANCE);
		ALL_ATTRIBUTES.add(FROST_RESISTANCE);
		ALL_ATTRIBUTES.add(LIGHTNING_RESISTANCE);
		ALL_ATTRIBUTES.add(POISON_RESISTANCE);
		ALL_ATTRIBUTES.add(COOLDOWN_REDUCTION);
		// bonus
		ALL_ATTRIBUTES.add(ALL_STATS);
		
		for (String name : Configs.weaponCategory.weaponAttributes)
		{
			if (getAttributeFromString(name) != null)
			{
				WEAPON_ATTRIBUTES.add(1, getAttributeFromString(name));
			}
		}
		
		for (String name : Configs.weaponCategory.armorAttributes)
		{
			if (getAttributeFromString(name) != null)
			{
				ARMOR_ATTRIBUTES.add(1, getAttributeFromString(name));
			}
		}
	}
	
	public enum AttributeType
	{
		RANDOM_INTEGER(),
		DAMAGE_INTEGER(),
		ROUNDED_PERCENTAGE(),
		PERCENTAGE();
	}
	
	public static Attribute getAttributeFromString(String name)
	{
		for (Attribute attribute : ALL_ATTRIBUTES)
		{
			if (attribute.getName().equals(name))
			{
				return attribute;
			}
		}
		
		return null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public double getBaseValue()
	{
		return baseValue;
	}
	
	public void setBaseValue(double newBaseValue)
	{
		this.baseValue = newBaseValue;
	}
	
	public boolean isUpgradeable()
	{
		return upgradeable;
	}
}

package com.thexfactor117.lsc.loot;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.thexfactor117.lsc.loot.generation.ItemGeneratorHelper;
import com.thexfactor117.lsc.util.RandomCollection;

import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author TheXFactor117
 *
 */
public enum Attribute
{
	// active attributes
	FIRE("Fire Damage", "fire", Rarity.COMMON, 1, 6, AttributeType.DAMAGE_INTEGER),
	FROST("Frost Damage", "frost", Rarity.COMMON, 1, 6, AttributeType.DAMAGE_INTEGER),
	LIGHTNING("Lightning Damage", "lightning", Rarity.COMMON, 1, 6, AttributeType.DAMAGE_INTEGER),
	POISON("Poison Damage", "poison", Rarity.COMMON, 1, 6, AttributeType.DAMAGE_INTEGER),
	LIFE_STEAL("Life Steal", "lifesteal", Rarity.UNCOMMON, 0.05, 0.5, AttributeType.ROUNDED_PERCENTAGE),
	MANA_STEAL("Mana Steal", "manasteal", Rarity.UNCOMMON, 0.05, 0.5, AttributeType.ROUNDED_PERCENTAGE),
	CHAINED("Chained", "chained", Rarity.RARE, 3, 10, AttributeType.RANDOM_INTEGER),
	VOID("Void", "void", Rarity.RARE, 0.01, 0.1, AttributeType.PERCENTAGE),
	
	// passive attributes
	MIN_DAMAGE("Minimum Damage", "min_damage", Rarity.UNCOMMON, 1, 4, AttributeType.DAMAGE_INTEGER),
	MAX_DAMAGE("Maximum Damage", "max_damage", Rarity.UNCOMMON, 1, 4, AttributeType.DAMAGE_INTEGER),
	DURABLE("Durable", "durable", Rarity.COMMON, 0.05, 0.5, AttributeType.ROUNDED_PERCENTAGE),
	GOLD("Gold", "gold", Rarity.UNCOMMON, 0.05, 0.5, AttributeType.ROUNDED_PERCENTAGE),
	FIRE_RESIST("Fire Resistance", "fireresist", Rarity.UNCOMMON, 0.05, 0.75, AttributeType.ROUNDED_PERCENTAGE),
	FROST_RESIST("Frost Resistance", "frostresist", Rarity.UNCOMMON, 0.05, 0.75, AttributeType.ROUNDED_PERCENTAGE),
	LIGHTNING_RESIST("Lightning Resistance", "lightningresist", Rarity.UNCOMMON, 0.05, 0.75, AttributeType.ROUNDED_PERCENTAGE),
	POISON_RESIST("Poison Resistance", "poisonresist", Rarity.UNCOMMON, 0.05, 0.75, AttributeType.ROUNDED_PERCENTAGE),
	
	// stat attributes
	STRENGTH("Strength", "strength", Rarity.COMMON, 1, 6, AttributeType.RANDOM_INTEGER),
	AGILITY("Agility", "agility", Rarity.COMMON, 1, 6, AttributeType.RANDOM_INTEGER),
	DEXTERITY("Dexterity", "dexterity", Rarity.COMMON, 1, 6, AttributeType.RANDOM_INTEGER),
	INTELLIGENCE("Intelligence", "intelligence", Rarity.COMMON, 1, 6, AttributeType.RANDOM_INTEGER),
	WISDOM("Wisdom", "wisdom", Rarity.COMMON, 1, 6, AttributeType.RANDOM_INTEGER),
	FORTITUDE("Fortitude", "fortitude", Rarity.COMMON, 1, 6, AttributeType.RANDOM_INTEGER),
	ALL_STATS("All Stats", "all_stats", Rarity.RARE, 1, 6, AttributeType.RANDOM_INTEGER);
	
	private String name;
	private String localizedName;
	private Rarity baseRarity;
	private double minAmount;
	private double maxAmount;
	private AttributeType type;
	
	public static final RandomCollection<Attribute> WEAPON_ATTRIBUTES = new RandomCollection<Attribute>();
	public static final RandomCollection<Attribute> ARMOR_ATTRIBUTES = new RandomCollection<Attribute>();
	
	Attribute(String name, String localizedName, Rarity baseRarity, double min, double max, AttributeType type)
	{
		this.name = name;
		this.localizedName = localizedName;
		this.baseRarity = baseRarity;
		this.minAmount = min;
		this.maxAmount = max;
		this.type = type;
	}
	
	public static Attribute getRandomWeaponAttribute(Random rand)
	{
		return WEAPON_ATTRIBUTES.next(rand);
	}
	
	public static Attribute getRandomArmorAttribute(Random rand)
	{
		return ARMOR_ATTRIBUTES.next(rand);
	}
	
	/**
	 * Returns a random attribute that the NBTTagCompound has.
	 * @param nbt
	 * @return
	 */
	public static Attribute getRandomAttribute(NBTTagCompound nbt)
	{
		List<Attribute> list = Lists.newArrayList();
		
		for (Attribute attribute : Attribute.values())
		{
			if (attribute.hasAttribute(nbt))
			{
				list.add(attribute);
			}
		}
		
		return list.size() > 0 ? list.get((int) (Math.random() * list.size())) : null;
	}
	
	public static Attribute getAttributeFromName(String name)
	{
		for (Attribute attribute : Attribute.values())
		{
			if (attribute.getLocalizedName().equals(name))
			{
				return attribute;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns true if the NBT tag compound has the specified Attribute.
	 * @param nbt
	 * @return
	 */
	public boolean hasAttribute(NBTTagCompound nbt)
	{
		return nbt.getBoolean(toString());
	}
	
	/**
	 * Returns the amount of the specific attribute.
	 * @param nbt
	 * @return
	 */
	public double getAmount(NBTTagCompound nbt)
	{
		return nbt.getDouble(localizedName + "_amount");
	}
	
	/**
	 * Adds the specific attribute to the NBTTagCompound.
	 * @param nbt
	 * @param rand
	 */
	public void addAttribute(NBTTagCompound nbt, Random rand)
	{
		nbt.setBoolean(this.toString(), true);
		nbt.setInteger(localizedName + "_rarity", Rarity.getRandomRarity(nbt, rand).ordinal());
		
		// TODO: weight the amount according to getAttributeRarity
		
		switch (type)
		{
			case RANDOM_INTEGER:
				int amount = (int) ((Math.random() * (maxAmount - minAmount)) + minAmount);
				nbt.setDouble(localizedName + "_amount", amount);
				break;
			case DAMAGE_INTEGER:
				int baseDamage = (int) ((Math.random() * (maxAmount - minAmount)) + minAmount) + 1; // add one so base damage doesn't become zero.
				double trueDamage = ItemGeneratorHelper.getWeightedDamage(nbt.getInteger("Level"), this.getAttributeRarity(nbt), baseDamage);
				nbt.setDouble(localizedName + "_amount", (int) trueDamage);
				break;
			case ROUNDED_PERCENTAGE:
				double base = (Math.random() * (maxAmount - minAmount)) + minAmount;
				double percentage = (Math.round(base * 20)) / 20.0;
				nbt.setDouble(localizedName + "_amount", percentage);
				break;
			case PERCENTAGE:
				double base1 = (Math.random() * (maxAmount - minAmount)) + minAmount;
				nbt.setDouble(localizedName + "_amount", base1);
				break;
		}
	}
	
	public void addAttribute(NBTTagCompound nbt, Random rand, double amount)
	{
		nbt.setBoolean(this.toString(), true);
		nbt.setInteger(localizedName + "_rarity", Rarity.getRandomRarity(nbt, rand).ordinal());
		
		if (amount < 1) nbt.setDouble(localizedName + "_amount", amount);
		else nbt.setDouble(localizedName + "_amount", (int) amount);
	}
	
	/**
	 * Removes the specific attribute from the NBTTagCompound.
	 * @param nbt
	 */
	public void removeAttribute(NBTTagCompound nbt)
	{
		nbt.removeTag(this.toString());
	}
	
	/**
	 * Returns the randomly applied Rarity for the specific attribute.
	 * @param nbt
	 * @return
	 */
	public Rarity getAttributeRarity(NBTTagCompound nbt)
	{
		switch (nbt.getInteger(localizedName + "_rarity"))
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
	
	static
	{
		// TODO: add config options for disabling specific attributes
		
		// weapon attributes
		WEAPON_ATTRIBUTES.add(FIRE.getBaseRarity().getChance(), FIRE);
		WEAPON_ATTRIBUTES.add(FROST.getBaseRarity().getChance(), FROST);
		WEAPON_ATTRIBUTES.add(LIGHTNING.getBaseRarity().getChance(), LIGHTNING);
		WEAPON_ATTRIBUTES.add(POISON.getBaseRarity().getChance(), POISON);
		WEAPON_ATTRIBUTES.add(LIFE_STEAL.getBaseRarity().getChance(), LIFE_STEAL);
		WEAPON_ATTRIBUTES.add(MANA_STEAL.getBaseRarity().getChance(), MANA_STEAL);
		WEAPON_ATTRIBUTES.add(CHAINED.getBaseRarity().getChance(), CHAINED);
		WEAPON_ATTRIBUTES.add(VOID.getBaseRarity().getChance(), VOID);
		WEAPON_ATTRIBUTES.add(MIN_DAMAGE.getBaseRarity().getChance(), MIN_DAMAGE);
		WEAPON_ATTRIBUTES.add(MAX_DAMAGE.getBaseRarity().getChance(), MAX_DAMAGE);
		WEAPON_ATTRIBUTES.add(DURABLE.getBaseRarity().getChance(), DURABLE);
		WEAPON_ATTRIBUTES.add(GOLD.getBaseRarity().getChance(), GOLD);
		
		// armor attributes
		ARMOR_ATTRIBUTES.add(FIRE_RESIST.getBaseRarity().getChance(), FIRE_RESIST);
		ARMOR_ATTRIBUTES.add(FROST_RESIST.getBaseRarity().getChance(), FROST_RESIST);
		ARMOR_ATTRIBUTES.add(LIGHTNING_RESIST.getBaseRarity().getChance(), LIGHTNING_RESIST);
		ARMOR_ATTRIBUTES.add(POISON_RESIST.getBaseRarity().getChance(), POISON_RESIST);
		ARMOR_ATTRIBUTES.add(STRENGTH.getBaseRarity().getChance(), STRENGTH);
		ARMOR_ATTRIBUTES.add(AGILITY.getBaseRarity().getChance(), AGILITY);
		ARMOR_ATTRIBUTES.add(DEXTERITY.getBaseRarity().getChance(), DEXTERITY);
		ARMOR_ATTRIBUTES.add(INTELLIGENCE.getBaseRarity().getChance(), INTELLIGENCE);
		ARMOR_ATTRIBUTES.add(WISDOM.getBaseRarity().getChance(), WISDOM);
		ARMOR_ATTRIBUTES.add(FORTITUDE.getBaseRarity().getChance(), FORTITUDE);
		ARMOR_ATTRIBUTES.add(ALL_STATS.getBaseRarity().getChance(), ALL_STATS);
		ARMOR_ATTRIBUTES.add(DURABLE.getBaseRarity().getChance(), DURABLE);
	}
	
	/*
	 * Helper methods
	 */
	public String getName()
	{
		return name;
	}
	
	public String getLocalizedName()
	{
		return localizedName;
	}
	
	public Rarity getBaseRarity()
	{
		return baseRarity;
	}
	
	public double getMinAmount()
	{
		return minAmount;
	}
	
	public double getMaxAmount()
	{
		return maxAmount;
	}
	
	public AttributeType getAttributeType()
	{
		return type;
	}
	
	public enum AttributeType
	{
		RANDOM_INTEGER(),
		DAMAGE_INTEGER(),
		ROUNDED_PERCENTAGE(),
		PERCENTAGE();
	}
}

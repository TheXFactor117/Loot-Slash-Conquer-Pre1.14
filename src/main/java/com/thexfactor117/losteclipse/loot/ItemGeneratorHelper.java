package com.thexfactor117.losteclipse.loot;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.entities.projectiles.Rune;
import com.thexfactor117.losteclipse.items.jewelry.ItemLEBauble;
import com.thexfactor117.losteclipse.items.magical.ItemLEMagical;
import com.thexfactor117.losteclipse.stats.weapons.ArmorAttribute;
import com.thexfactor117.losteclipse.stats.weapons.JewelryAttribute;
import com.thexfactor117.losteclipse.stats.weapons.Rarity;
import com.thexfactor117.losteclipse.stats.weapons.WeaponAttribute;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemGeneratorHelper 
{
	private static final UUID ATTACK_DAMAGE = UUID.fromString("06dbc47d-eaf1-4604-9b91-926e475012c2");
	private static final UUID ATTACK_SPEED = UUID.fromString("335ede30-242d-41b6-a4f7-dd24ed2adce5");
	private static final UUID ARMOR = UUID.fromString("81a2ee21-fe83-41fb-8b2f-bf5ef33a71a8");
	private static final UUID ARMOR_TOUGHNESS = UUID.fromString("2cdb1e5e-3937-41e1-98a4-6a6eac2cf458");
	
	public static Random rand = new Random();
	
	public static void generateName(ItemStack stack, NBTTagCompound nbt)
	{
		// return if Common
		if (Rarity.getRarity(nbt) == Rarity.COMMON) return;
		
		String prefix = NameHelper.getRandomPrefix();
		String suffix = "";
		
		if (stack.getItem() instanceof ItemSword && !nbt.hasKey("Type")) suffix = NameHelper.getSwordSuffix();
		else if (stack.getItem() instanceof ItemSword && nbt.hasKey("Type") && nbt.getString("Type").equals("Dagger")) suffix = NameHelper.getDaggerSuffix();
		else if (stack.getItem() instanceof ItemSword && nbt.hasKey("Type") && nbt.getString("Type").equals("Mace")) suffix = NameHelper.getMaceSuffix();
		else if (stack.getItem() instanceof ItemLEMagical) suffix = NameHelper.getWandSuffix();
		else if (stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.HEAD) suffix = NameHelper.getHelmetSuffix();
		else if (stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.CHEST) suffix = NameHelper.getChestplateSuffix();
		else if (stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.LEGS) suffix = NameHelper.getLeggingsSuffix();
		else if (stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.FEET) suffix = NameHelper.getBootSuffix();

		stack.setStackDisplayName(Rarity.getRarity(nbt).getColor() + prefix + " " + suffix);
	}
	
	/**
	 * Sets a certain amount of random attributes to the stack depending on the Rarity.
	 * @param stack
	 * @param nbt
	 * @param rarity
	 * @param player
	 */
	public static void setRandomAttributes(ItemStack stack, NBTTagCompound nbt, Rarity rarity)
	{
		int amount = 0;
		// sets the amount of attributes should be generated depending on rarity.
		if (rarity == Rarity.COMMON) amount = (int) (Math.random() * 2);
		else if (rarity == Rarity.UNCOMMON) amount = (int) (Math.random() * 2 + 1);
		else if (rarity == Rarity.RARE) amount = (int) (Math.random() * 2 + 2);
		else if (rarity == Rarity.LEGENDARY) amount = (int) (Math.random() * 2 + 3);
		else if (rarity == Rarity.EXOTIC) amount = (int) (Math.random() * 2 + 4);
		
		for (int i = 0; i < amount; i++)
		{
			if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemLEMagical)
			{
				WeaponAttribute attribute = WeaponAttribute.getRandomAttribute(rand); // generate random rarity.
				
				if (attribute.hasAttribute(nbt))
					i--; // subtract 1 if we already have an attribute to "re-roll"
				else
					attribute.addAttribute(nbt); // add attribute - this method will handle setting up all the attribute unique modifiers, such as custom rarity, values, etc...
			}
			else if (stack.getItem() instanceof ItemArmor)
			{
				ArmorAttribute attribute = ArmorAttribute.getRandomAttribute(rand); // generate random rarity.
				
				if (attribute.hasAttribute(nbt))
					i--; // subtract 1 if we already have an attribute to "re-roll"
				else
					attribute.addAttribute(nbt); // add attribute - this method will handle setting up all the attribute unique modifiers, such as custom rarity, values, etc...
			}
			else if (stack.getItem() instanceof ItemLEBauble)
			{
				JewelryAttribute attribute = JewelryAttribute.getRandomAttribute(rand); // generate random rarity.
				
				if (attribute.hasAttribute(nbt))
					i--; // subtract 1 if we already have an attribute to "re-roll"
				else
					attribute.addAttribute(nbt); // add attribute - this method will handle setting up all the attribute unique modifiers, such as custom rarity, values, etc...
			}
		}
	}
	
	/**
	 * Creates a new Attribute Modifier tag list and adds it to the NBTTagCompound. Overrides default vanilla implementation.
	 * @param nbt
	 * @param sword
	 */
	public static void setAttributeModifiers(NBTTagCompound nbt, ItemStack stack)
	{
		Item item = stack.getItem();
		
		if (item instanceof ItemSword)
		{
			// retrieves the default attributes, like damage and attack speed.
			@SuppressWarnings("deprecation")
			Multimap<String, AttributeModifier> map = item.getItemAttributeModifiers(EntityEquipmentSlot.MAINHAND);
			Collection<AttributeModifier> damageCollection = map.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
			Collection<AttributeModifier> speedCollection = map.get(SharedMonsterAttributes.ATTACK_SPEED.getName());
			AttributeModifier damageModifier = (AttributeModifier) damageCollection.toArray()[0];
			AttributeModifier speedModifier = (AttributeModifier) speedCollection.toArray()[0];

			double baseDamage = damageModifier.getAmount();
			double baseSpeed = speedModifier.getAmount();
			double damage = getWeightedDamage(nbt, Rarity.getRarity(nbt), baseDamage);
			double speed = getWeightedAttackSpeed(Rarity.getRarity(nbt), baseSpeed);

			LostEclipse.LOGGER.info("Attack Speed: " + speed);
			
			setMinMaxDamage(nbt, damage);
			
			// Creates new AttributeModifier's and applies them to the stack's NBT tag compound.
			AttributeModifier attackDamage = new AttributeModifier(ATTACK_DAMAGE, "attackDamage", damage, 0);
			AttributeModifier attackSpeed = new AttributeModifier(ATTACK_SPEED, "attackSpeed", speed, 0);
			NBTTagCompound damageNbt = writeAttributeModifierToNBT(SharedMonsterAttributes.ATTACK_DAMAGE, attackDamage, EntityEquipmentSlot.MAINHAND);
			NBTTagCompound speedNbt = writeAttributeModifierToNBT(SharedMonsterAttributes.ATTACK_SPEED, attackSpeed, EntityEquipmentSlot.MAINHAND);
			NBTTagList list = new NBTTagList();
			list.appendTag(damageNbt);
			list.appendTag(speedNbt);
			nbt.setTag("AttributeModifiers", list);
		}
		else if (item instanceof ItemArmor)
		{
			Multimap<String, AttributeModifier> map = ((ItemArmor) item).getAttributeModifiers(((ItemArmor) item).armorType, stack);
			Collection<AttributeModifier> armorCollection = map.get(SharedMonsterAttributes.ARMOR.getName());
			Collection<AttributeModifier> toughnessCollection = map.get(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName());
			AttributeModifier armorModifier = (AttributeModifier) armorCollection.toArray()[0];
			AttributeModifier toughnessModifier = (AttributeModifier) toughnessCollection.toArray()[0];
			
			double baseArmor = armorModifier.getAmount();
			double baseToughness = toughnessModifier.getAmount();
			double newArmor = getWeightedArmor(Rarity.getRarity(nbt), baseArmor);
			double newToughness = getWeightedArmorToughness(Rarity.getRarity(nbt), baseToughness);
			
			// Creates new AttributeModifier's and applies them to the stack's NBT tag compound.
			AttributeModifier armor = new AttributeModifier(ARMOR, "armor", newArmor, 0);
			AttributeModifier toughness = new AttributeModifier(ARMOR_TOUGHNESS, "armorToughness", newToughness, 0);
			NBTTagCompound armorNbt = writeAttributeModifierToNBT(SharedMonsterAttributes.ARMOR, armor, ((ItemArmor) item).armorType);
			NBTTagCompound toughnessNbt = writeAttributeModifierToNBT(SharedMonsterAttributes.ARMOR_TOUGHNESS, toughness, ((ItemArmor) item).armorType);
			NBTTagList list = new NBTTagList();
			list.appendTag(armorNbt);
			list.appendTag(toughnessNbt);
			nbt.setTag("AttributeModifiers", list);
		}
	}
	
	public static void setMinMaxDamage(NBTTagCompound nbt, double damage)
	{
		int range = ((int) (Math.random() * 4 + 4)) / 2;
		int minDamage = (int) (damage - range);
		int maxDamage = (int) (damage + range);

		if (WeaponAttribute.MIN_DAMAGE.hasAttribute(nbt))
			minDamage += WeaponAttribute.MIN_DAMAGE.getAmount(nbt);
		else if (WeaponAttribute.MAX_DAMAGE.hasAttribute(nbt))
			maxDamage += WeaponAttribute.MAX_DAMAGE.getAmount(nbt);
		
		nbt.setInteger("MinDamage", minDamage);
		nbt.setInteger("MaxDamage", maxDamage);
	}
	
	public static void setRune(NBTTagCompound nbt)
	{
		Rune.setRune(nbt, Rune.getRandomRune(nbt, rand));
	}
	
	public static double getWeightedDamage(NBTTagCompound nbt, Rarity rarity, double base)
	{
		double damage = base;
		int range = 0;
		
		if (rarity == Rarity.COMMON)
		{
			range = 2;
			damage = Math.random() * range + (base - 1);
		}
		else if (rarity == Rarity.UNCOMMON)
		{
			range = 2;
			damage = Math.random() * range + (base);
		}
		else if (rarity == Rarity.RARE)
		{
			range = 3;
			damage = Math.random() * range + (base);
		}
		else if (rarity == Rarity.LEGENDARY)
		{
			range = 3;
			damage = Math.random() * range + (base + 1);
		}
		else if (rarity == Rarity.EXOTIC)
		{
			range = 4;
			damage = Math.random() * range + (base + 2);
		}
		
		return damage * Math.pow(nbt.getInteger("Level"), 0.75);
	}
	
	public static double getWeightedAttackSpeed(Rarity rarity, double base)
	{
		double speed = base;
		double range = 0;
		
		if (rarity == Rarity.COMMON)
		{
			range = 0.1;
			speed = Math.random() * range + (base - 0.1);
		}
		else if (rarity == Rarity.UNCOMMON)
		{
			range = 0.15;
			speed = Math.random() * range + (base - 0.05);
		}
		else if (rarity == Rarity.RARE)
		{
			range = 0.2;
			speed = Math.random() * range + (base);
		}
		else if (rarity == Rarity.LEGENDARY)
		{
			range = 0.25;
			speed = Math.random() * range + (base + 0.1);
		}
		else if (rarity == Rarity.EXOTIC)
		{
			range = 0.3;
			speed = Math.random() * range + (base + 0.2);
		}
		
		return speed;
	}
	
	public static double getWeightedArmor(Rarity rarity, double base)
	{
		double armor = base;
		double range = 0;
		
		if (rarity == Rarity.COMMON)
		{
			range = 0.2;
			armor = Math.random() * range + (base - 0.2);
		}
		else if (rarity == Rarity.UNCOMMON)
		{
			range = 0.3;
			armor = Math.random() * range + (base - 0.1);
		}
		else if (rarity == Rarity.RARE)
		{
			range = 0.4;
			armor = Math.random() * range + (base);
		}
		else if (rarity == Rarity.LEGENDARY)
		{
			range = 0.5;
			armor = Math.random() * range + (base + 0.1);
		}
		else if (rarity == Rarity.EXOTIC)
		{
			range = 0.6;
			armor = Math.random() * range + (base + 0.2);
		}
		
		return armor;
	}
	
	public static double getWeightedArmorToughness(Rarity rarity, double base)
	{
		double toughness = base;
		double range = 0;
		
		if (rarity == Rarity.COMMON)
		{
			toughness = 0;
		}
		else if (rarity == Rarity.UNCOMMON)
		{
			range = 0.2;
			toughness = Math.random() * range + (base - 0.1);
		}
		else if (rarity == Rarity.RARE)
		{
			range = 0.3;
			toughness = Math.random() * range + (base);
		}
		else if (rarity == Rarity.LEGENDARY)
		{
			range = 0.4;
			toughness = Math.random() * range + (base + 0.1);
		}
		else if (rarity == Rarity.EXOTIC)
		{
			range = 0.5;
			toughness = Math.random() * range + (base + 0.2);
		}
		
		if (toughness < 0)
			toughness = 0;
		
		return toughness;
	}
	
	/**
	 * Helper method for writing new attribute modifiers.
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

package com.thexfactor117.minehackslash.items.generation;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.thexfactor117.minehackslash.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.minehackslash.capabilities.IPlayerInformation;
import com.thexfactor117.minehackslash.util.NBTHelper;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
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
public class ItemGenerator 
{
	private static final UUID ATTACK_DAMAGE = UUID.fromString("06dbc47d-eaf1-4604-9b91-926e475012c2");
	private static final UUID ATTACK_SPEED = UUID.fromString("335ede30-242d-41b6-a4f7-dd24ed2adce5");
	//private static final UUID ARMOR = UUID.fromString("");
	//private static final UUID ARMOR_TOUGHNESS = UUID.fromString("");
	
	/**
	 * Creates the given item with randomized attributes and such.
	 * @param stack
	 * @param player
	 */
	public static void create(ItemStack stack, EntityPlayer player)
	{
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		Rarity rarity = Rarity.getRarity(nbt);
		Random rand = player.getEntityWorld().rand;
		IPlayerInformation playerInfo = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);

		if (playerInfo != null)
		{
			/*
			 * Set rarity
			 * Set level
			 * Generate attributes based on Rarity
			 * 		- Common: 0-1 attributes
			 * 		- Uncommon: 1-2 attributes
			 * 		- Rare: 2-3 attributes
			 * 		- Legendary: 3-4 attributes
			 * 		- Mythic: 4-5 attributes
			 * Generate base damage and base attack speed
			 * Generate name based on attributes + material/type
			 */
			
			if (rarity == Rarity.DEFAULT)
			{
				Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, rand)); // sets a random rarity
				nbt.setInteger("Level", playerInfo.getPlayerLevel()); // set level to current player level
				setRandomAttributes(stack, nbt, Rarity.getRarity(nbt), player);
				setAttributeModifiers(nbt, stack, player);
				// generate name
				NBTHelper.saveStackNBT(stack, nbt);
			}
		}
	}
	
	/**
	 * Sets a certain amount of random attributes to the stack depending on the Rarity.
	 * @param stack
	 * @param nbt
	 * @param rarity
	 * @param player
	 */
	private static void setRandomAttributes(ItemStack stack, NBTTagCompound nbt, Rarity rarity, EntityPlayer player)
	{
		int amount = 0;
		// sets the amount of attributes should be generated depending on rarity.
		if (rarity == Rarity.COMMON) amount = (int) (Math.random() * 2);
		else if (rarity == Rarity.UNCOMMON) amount = (int) (Math.random() * 2 + 1);
		else if (rarity == Rarity.RARE) amount = (int) (Math.random() * 2 + 2);
		else if (rarity == Rarity.LEGENDARY) amount = (int) (Math.random() * 2 + 3);
		else if (rarity == Rarity.MYTHIC) amount = (int) (Math.random() * 2 + 4);

		for (int i = 0; i < amount; i++)
		{
			WeaponAttributes attribute = WeaponAttributes.getRandomAttribute(player.getEntityWorld().rand); // generate random rarity.
			
			if (attribute.hasAttribute(nbt))
				i--; // subtract 1 if we already have an attribute to "re-roll"
			else
				attribute.addAttribute(nbt); // add attribute - this method will handle setting up all the attribute unique modifiers, such as custom rarity, values, etc...
		}
	}
	
	/**
	 * Creates a new Attribute Modifier tag list and adds it to the NBTTagCompound. Overrides default vanilla implementation.
	 * @param nbt
	 * @param sword
	 */
	private static void setAttributeModifiers(NBTTagCompound nbt, ItemStack stack, EntityPlayer player)
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
			
			double baseDamage = damageModifier.getAmount() + player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(); // add one to base damage for player strength
			double baseSpeed = speedModifier.getAmount();
			double damage = getWeightedDamage(Rarity.getRarity(nbt), baseDamage);
			double speed = getWeightedAttackSpeed(Rarity.getRarity(nbt), baseSpeed);

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
			/*Multimap<String, AttributeModifier> map = ((ItemArmor) item).getAttributeModifiers(((ItemArmor) item).armorType, stack);
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
			nbt.setTag("AttributeModifiers", list);*/
		}
	}
	
	private static double getWeightedDamage(Rarity rarity, double base)
	{
		double damage = base;
		int range = 0;
		
		if (rarity == Rarity.COMMON)
		{
			range = 2;
			damage = Math.random() * range + (base - 2);
		}
		else if (rarity == Rarity.UNCOMMON)
		{
			range = 2;
			damage = Math.random() * range + (base - 1);
		}
		else if (rarity == Rarity.RARE)
		{
			range = 3;
			damage = Math.random() * range + (base - 1);
		}
		else if (rarity == Rarity.LEGENDARY)
		{
			range = 3;
			damage = Math.random() * range + (base);
		}
		else if (rarity == Rarity.MYTHIC)
		{
			range = 4;
			damage = Math.random() * range + (base + 1);
		}
		
		return damage;
	}
	
	private static double getWeightedAttackSpeed(Rarity rarity, double base)
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
		else if (rarity == Rarity.MYTHIC)
		{
			range = 0.3;
			speed = Math.random() * range + (base + 0.2);
		}
		
		return speed;
	}
	
	/**
	 * Helper method for writing new attribute modifiers.
	 * @param attribute
	 * @param modifier
	 * @param slot
	 * @return
	 */
	private static NBTTagCompound writeAttributeModifierToNBT(IAttribute attribute, AttributeModifier modifier, EntityEquipmentSlot slot) 
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

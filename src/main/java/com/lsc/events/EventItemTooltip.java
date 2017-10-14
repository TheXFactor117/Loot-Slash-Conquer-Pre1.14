package com.lsc.events;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.lsc.capabilities.playerinfo.CapabilityPlayerInformation;
import com.lsc.capabilities.playerinfo.PlayerInformation;
import com.lsc.entities.projectiles.Rune;
import com.lsc.items.base.ItemLEBauble;
import com.lsc.items.base.ItemLEMagical;
import com.lsc.loot.ArmorAttribute;
import com.lsc.loot.JewelryAttribute;
import com.lsc.loot.Rarity;
import com.lsc.loot.WeaponAttribute;
import com.lsc.util.NBTHelper;
import com.lsc.util.PlayerStatHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventItemTooltip 
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemTooltip(ItemTooltipEvent event)
	{
		ArrayList<String> tooltip = (ArrayList<String>) event.getToolTip();
		ItemStack stack = event.getItemStack();
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		
		if (event.getEntityPlayer() != null)
		{
			PlayerInformation info = (PlayerInformation) event.getEntityPlayer().getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (info != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemLEMagical || stack.getItem() instanceof ItemLEBauble))
			{
				Rarity rarity = Rarity.getRarity(nbt);
				
				if (rarity != Rarity.DEFAULT)
				{
					if (stack.getItem() instanceof ItemSword) drawMelee(tooltip, stack, nbt, event.getEntityPlayer(), info);
					else if (stack.getItem() instanceof ItemArmor) drawArmor(tooltip, stack, nbt, event.getEntityPlayer(), info);
					else if (stack.getItem() instanceof ItemLEMagical) drawMagical(tooltip, stack, nbt, event.getEntityPlayer(), info);
					else if (stack.getItem() instanceof ItemLEBauble) drawBauble(tooltip, stack, nbt, event.getEntityPlayer(), info);
				}
			}
		}
	}
	
	private void drawMelee(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
	{
		/*
		 * NAME
		 * Level
		 * 
		 * Damage
		 * Attack Speed
		 * 
		 * Durability
		 * 
		 * Attributes
		 */
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(1, TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add(1, "Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Damage and Attack Speed
		NBTTagList taglist = nbt.getTagList("AttributeModifiers", 10);
		NBTTagCompound speedNbt = taglist.getCompoundTagAt(1);
		DecimalFormat format = new DecimalFormat("#.##");
		
		double attackSpeed = speedNbt.getDouble("Amount") + 4 + (PlayerStatHelper.ATTACK_SPEED_MULTIPLIER * (double) (info.getTotalAgility()));

		tooltip.add(TextFormatting.BLUE + " +" + nbt.getInteger("MinDamage") + "-" + nbt.getInteger("MaxDamage") + " Damage");
		tooltip.add(TextFormatting.BLUE + " +" + format.format(attackSpeed) + " Attack Speed");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");

		for (WeaponAttribute attribute : WeaponAttribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
	}
	
	private void drawArmor(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
	{
		/*
		 * NAME
		 * Level
		 * 
		 * Armor
		 * Toughness
		 * 
		 * Durability
		 * 
		 * Attributes
		 */
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(1, TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add(1, "Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Armor and Toughness
		NBTTagList taglist = nbt.getTagList("AttributeModifiers", 10);
		NBTTagCompound armorNbt = taglist.getCompoundTagAt(0);
		NBTTagCompound toughnessNbt = taglist.getCompoundTagAt(1);
		DecimalFormat format = new DecimalFormat("#.##");
		
		tooltip.add(TextFormatting.BLUE + " +" + format.format(armorNbt.getDouble("Amount")) + " Armor");
		tooltip.add(TextFormatting.BLUE + " +" + format.format(toughnessNbt.getDouble("Amount")) + " Armor Toughness");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		for (ArmorAttribute attribute : ArmorAttribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
	}
	
	private void drawMagical(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
	{
		/*
		 * NAME
		 * Level
		 * 
		 * Rune
		 * 
		 * Damage
		 * Attack Speed
		 * 
		 * Durability
		 * 
		 * Attributes
		 */
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(1, TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add(1, "Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Rune
		tooltip.add(Rune.getRune(nbt).getColor() + Rune.getRune(nbt).getName());
		tooltip.add("");
		
		// Damage and Attack Speed
		DecimalFormat format = new DecimalFormat("#.##");
		double attackSpeed = nbt.getDouble("AttackSpeed") + (PlayerStatHelper.ATTACK_SPEED_MULTIPLIER * (info.getTotalAgility()));

		tooltip.add(TextFormatting.BLUE + "+" + nbt.getInteger("MinDamage") + "-" + nbt.getInteger("MaxDamage") + " Damage");
		tooltip.add(TextFormatting.BLUE + "+" + format.format(attackSpeed) + " Attack Speed");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		for (WeaponAttribute attribute : WeaponAttribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
	}
	
	private void drawBauble(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
	{
		/*
		 * NAME
		 * Level
		 * 
		 * Attributes
		 */
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(1, TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add(1, "Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Attributes
		DecimalFormat format = new DecimalFormat("#.##");
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		for (JewelryAttribute attribute : JewelryAttribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
		
		tooltip.add("");
	}
}

package com.lsc.client.events;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.lsc.capabilities.cap.CapabilityPlayerInformation;
import com.lsc.capabilities.implementation.PlayerInformation;
import com.lsc.config.Configs;
import com.lsc.entities.projectiles.Rune;
import com.lsc.items.base.ItemBauble;
import com.lsc.items.base.ItemMagical;
import com.lsc.loot.Attribute;
import com.lsc.loot.Rarity;
import com.lsc.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class EventItemTooltip 
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onItemTooltip(ItemTooltipEvent event)
	{
		ArrayList<String> tooltip = (ArrayList<String>) event.getToolTip();
		ItemStack stack = event.getItemStack();
		NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
		
		if (event.getEntityPlayer() != null)
		{
			PlayerInformation info = (PlayerInformation) event.getEntityPlayer().getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (info != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemBow || 
					stack.getItem() instanceof ItemMagical || stack.getItem() instanceof ItemBauble))
			{
				Rarity rarity = Rarity.getRarity(nbt);
				
				if (rarity != Rarity.DEFAULT)
				{
					if (stack.getItem() instanceof ItemSword) drawMelee(tooltip, stack, nbt, event.getEntityPlayer(), info);
					else if (stack.getItem() instanceof ItemArmor) drawArmor(tooltip, stack, nbt, event.getEntityPlayer(), info);
					else if (stack.getItem() instanceof ItemBow) drawRanged(tooltip, stack, nbt, event.getEntityPlayer(), info);
					else if (stack.getItem() instanceof ItemMagical) drawMagical(tooltip, stack, nbt, event.getEntityPlayer(), info);
					else if (stack.getItem() instanceof ItemBauble) drawBauble(tooltip, stack, nbt, event.getEntityPlayer(), info);
				}
				else
				{
					event.getToolTip().add("");
					event.getToolTip().add(TextFormatting.RED + "Close inventory to have stats rolled.");
				}
			}
		}
	}
	
	private static void drawMelee(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
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
		
		if (nbt.hasKey("IsSpecial") && nbt.getBoolean("IsSpecial"))
		{
			tooltip.add(1, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.ITALIC + "Special");
			tooltip.add("");
		}
		else
		{
			tooltip.add(1, "");
		}
		
		
		tooltip.add(Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName());
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Damage and Attack Speed
		NBTTagList taglist = nbt.getTagList("AttributeModifiers", 10);
		NBTTagCompound speedNbt = taglist.getCompoundTagAt(1);
		DecimalFormat format = new DecimalFormat("#.##");
		
		double attackSpeed = speedNbt.getDouble("Amount") + 4 + (Configs.playerCategory.attackSpeedMultiplier * (double) (info.getTotalAgility()));

		tooltip.add(TextFormatting.BLUE + " +" + nbt.getInteger("MinDamage") + "-" + nbt.getInteger("MaxDamage") + " Damage");
		tooltip.add(TextFormatting.BLUE + " +" + format.format(attackSpeed) + " Attack Speed");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");

		for (Attribute attribute : Attribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
	}
	
	private static void drawArmor(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
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
		
		tooltip.add(1, "");
		tooltip.add(Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName());
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
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
		
		for (Attribute attribute : Attribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
	}
	
	private static void drawRanged(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
	{
		tooltip.add(1, "");
		tooltip.add(Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName());
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Damage and Attack Speed
		DecimalFormat format = new DecimalFormat("#.##");
		double attackSpeed = nbt.getDouble("AttackSpeed") + (Configs.playerCategory.attackSpeedMultiplier * (info.getTotalAgility()));

		tooltip.add(TextFormatting.BLUE + "+" + nbt.getInteger("MinDamage") + "-" + nbt.getInteger("MaxDamage") + " Damage");
		tooltip.add(TextFormatting.BLUE + "+" + format.format(attackSpeed) + " Attack Speed");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		for (Attribute attribute : Attribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
	}
	
	private static void drawMagical(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
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
		
		tooltip.add(1, "");
		tooltip.add(Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName());
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Rune
		tooltip.add(Rune.getRune(nbt).getColor() + Rune.getRune(nbt).getName());
		tooltip.add("");
		
		// Damage and Attack Speed
		DecimalFormat format = new DecimalFormat("#.##");
		double attackSpeed = nbt.getDouble("AttackSpeed") + (Configs.playerCategory.attackSpeedMultiplier * (info.getTotalAgility()));

		tooltip.add(TextFormatting.BLUE + "+" + nbt.getInteger("MinDamage") + "-" + nbt.getInteger("MaxDamage") + " Damage");
		tooltip.add(TextFormatting.BLUE + "+" + format.format(attackSpeed) + " Attack Speed");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		for (Attribute attribute : Attribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
	}
	
	private static void drawBauble(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, PlayerInformation info)
	{
		/*
		 * NAME
		 * Level
		 * 
		 * Attributes
		 */
		
		tooltip.add(1, "");
		tooltip.add(Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName());
		
		// Level
		if (info.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Attributes
		DecimalFormat format = new DecimalFormat("#.##");
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		for (Attribute attribute : Attribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}
		
		tooltip.add("");
	}
}

package com.thexfactor117.lsc.client.events;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.entities.projectiles.Rune;
import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.items.base.ItemMagical;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.AttributeBase;
import com.thexfactor117.lsc.util.ItemUtil;
import com.thexfactor117.lsc.util.PlayerUtil;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
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
			LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(event.getEntityPlayer());
			
			if (cap != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemBow || 
					stack.getItem() instanceof ItemMagical || stack.getItem() instanceof ItemBauble))
			{
				Rarity rarity = Rarity.getRarity(nbt);
				
				if (rarity != Rarity.DEFAULT)
				{
					if (stack.getItem() instanceof ItemSword) drawMelee(tooltip, stack, nbt, event.getEntityPlayer(), cap);
					else if (stack.getItem() instanceof ItemArmor) drawArmor(tooltip, stack, nbt, event.getEntityPlayer(), cap);
					else if (stack.getItem() instanceof ItemBow) drawRanged(tooltip, stack, nbt, event.getEntityPlayer(), cap);
					else if (stack.getItem() instanceof ItemMagical) drawMagical(tooltip, stack, nbt, event.getEntityPlayer(), cap);
					else if (stack.getItem() instanceof ItemBauble) drawBauble(tooltip, stack, nbt, event.getEntityPlayer(), cap);
				}
				else
				{
					event.getToolTip().add("");
					event.getToolTip().add(TextFormatting.RED + "Close inventory to have stats rolled.");
				}
			}
		}
	}
	
	private static void drawMelee(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
	{		
		if (nbt.hasKey("IsSpecial") && nbt.getBoolean("IsSpecial"))
		{
			tooltip.add(1, TextFormatting.LIGHT_PURPLE + "" + TextFormatting.ITALIC + "Special");
			tooltip.add("");
		}
		
		tooltip.add(1, Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName());
		
		// Level
		if (cap.getPlayerLevel() < ItemUtil.getItemLevel(stack)) tooltip.add(TextFormatting.RED + "Level: " + ItemUtil.getItemLevel(stack));
		else tooltip.add("Level: " + ItemUtil.getItemLevel(stack));
		
		tooltip.add("");
		
		// Primary Attributes - damage/attack speed
		tooltip.add(TextFormatting.ITALIC + "Primary Attributes");
		
		int damage = ItemUtil.getItemDamage(stack);
		double attackSpeed = ItemUtil.getItemAttackSpeed(stack) + 4;
		
		tooltip.add(TextFormatting.BLUE + " * " + damage + " Damage");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.FORMAT.format(attackSpeed) + " Attack Speed");
		
		// Secondary Attributes - attributes
		if (!ItemUtil.getSecondaryAttributes(stack).isEmpty())
		{
			tooltip.add("");
			tooltip.add(TextFormatting.ITALIC + "Secondary Attributes");
			
			for (AttributeBase attribute : ItemUtil.getSecondaryAttributes(stack))
			{
				tooltip.add(attribute.getTooltipDisplay(nbt));
			}
		}
		
		// Bonus Attributes - bonus attributes
		if (!ItemUtil.getBonusAttributes(stack).isEmpty())
		{
			tooltip.add("");
			tooltip.add(TextFormatting.ITALIC + "Bonus Attributes");
			
			for (AttributeBase attribute : ItemUtil.getBonusAttributes(stack))
			{
				tooltip.add(attribute.getTooltipDisplay(nbt));
			}
		}
	}
	
	private static void drawArmor(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
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
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Armor and Toughness
		DecimalFormat format = new DecimalFormat("#.##");
		
		tooltip.add(TextFormatting.BLUE + " +" + format.format(nbt.getDouble("ArmorPoints")) + " Armor");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		for (AttributeBase attribute : ItemUtil.getSecondaryAttributes(stack))
		{
			tooltip.add(attribute.getTooltipDisplay(nbt));
		}
		
		/*for (Attribute attribute : Attribute.values())
		{
			if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
				tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
			else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
				tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
		}*/
	}
	
	private static void drawRanged(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
	{
		tooltip.add(1, "");
		tooltip.add(Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName());
		
		// Level
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Damage and Attack Speed
		DecimalFormat format = new DecimalFormat("#.##");
		double attackSpeed = nbt.getDouble("AttackSpeed") + (Configs.playerCategory.attackSpeedMultiplier * (cap.getTotalAgility()));

		tooltip.add(TextFormatting.BLUE + "+" + nbt.getInteger("MinDamage") + "-" + nbt.getInteger("MaxDamage") + " Damage");
		tooltip.add(TextFormatting.BLUE + "+" + format.format(attackSpeed) + " Attack Speed");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
	}
	
	private static void drawMagical(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
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
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Rune
		tooltip.add(Rune.getRune(nbt).getColor() + Rune.getRune(nbt).getName());
		tooltip.add("");
		
		// Damage and Attack Speed
		DecimalFormat format = new DecimalFormat("#.##");
		double attackSpeed = nbt.getDouble("AttackSpeed") + (Configs.playerCategory.attackSpeedMultiplier * (cap.getTotalAgility()));

		tooltip.add(TextFormatting.BLUE + "+" + nbt.getInteger("MinDamage") + "-" + nbt.getInteger("MaxDamage") + " Damage");
		tooltip.add(TextFormatting.BLUE + "+" + format.format(attackSpeed) + " Attack Speed");
		tooltip.add("");
		
		// Durability
		tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
		tooltip.add("");
		
		// Attributes
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
	}
	
	private static void drawBauble(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
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
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		
		tooltip.add("");
		
		// Attributes
		DecimalFormat format = new DecimalFormat("#.##");
		tooltip.add(TextFormatting.ITALIC + "Attributes");
		
		
		tooltip.add("");
	}
}

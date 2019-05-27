package com.thexfactor117.lsc.client.events;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.entities.projectiles.Rune;
import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.items.base.weapons.ItemMagical;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.attributes.Attribute;
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

			if (cap != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemMagical || stack.getItem() instanceof ItemBauble))
			{
				if (Rarity.getRarity(nbt) != Rarity.DEFAULT)
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

		tooltip.add(1, Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName() + " " + ItemUtil.getItemOriginalName(stack));

		// Level
		if (cap.getPlayerLevel() < ItemUtil.getItemLevel(stack)) tooltip.add(TextFormatting.RED + "Level: " + ItemUtil.getItemLevel(stack));
		else tooltip.add("Level: " + ItemUtil.getItemLevel(stack));

		tooltip.add("");
		tooltip.add("DPS: " + ItemUtil.FORMAT.format(ItemUtil.getItemDamagePerSecond(stack, cap)));
		tooltip.add("");

		// Primary Attributes - damage/attack speed
		tooltip.add(TextFormatting.ITALIC + "Primary Attributes");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.getItemDamage(stack) + " Damage" + TextFormatting.GRAY + " [" + ItemUtil.getItemMinDamage(stack) + " - " + ItemUtil.getItemMaxDamage(stack) + "]");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.FORMAT.format((ItemUtil.getItemAttackSpeed(stack) + 4)) + " Attack Speed");

		// Secondary Attributes - attributes
		if (!ItemUtil.getSecondaryAttributes(stack).isEmpty())
		{
			tooltip.add("");
			tooltip.add(TextFormatting.ITALIC + "Secondary Attributes");

			for (Attribute attribute : ItemUtil.getSecondaryAttributes(stack))
			{
				tooltip.add(attribute.getTooltipDisplay(nbt));
			}
		}

		// Other Info
		shiftInfo(tooltip, stack);
	}

	private static void drawArmor(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
	{
		tooltip.add(1, Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName() + " " + ItemUtil.getItemOriginalName(stack));

		// Level
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		tooltip.add("");

		// Primary Attributes - armor
		tooltip.add(TextFormatting.ITALIC + "Primary Attributes");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.FORMAT.format(ItemUtil.getItemArmor(stack)) + " Armor");

		// Secondary Attributes
		if (!ItemUtil.getSecondaryAttributes(stack).isEmpty())
		{
			tooltip.add("");
			tooltip.add(TextFormatting.ITALIC + "Secondary Attributes");

			for (Attribute attribute : ItemUtil.getSecondaryAttributes(stack))
			{
				tooltip.add(attribute.getTooltipDisplay(nbt));
			}
		}

		// Other Info
		shiftInfo(tooltip, stack);
	}

	private static void drawRanged(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
	{
		tooltip.add(1, Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName() + " " + ItemUtil.getItemOriginalName(stack));

		// Level
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		tooltip.add("");

		// Primary Attributes - damage/attack speed
		tooltip.add(TextFormatting.ITALIC + "Primary Attributes");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.getItemDamage(stack) + " Damage" + TextFormatting.GRAY + " [" + ItemUtil.getItemMinDamage(stack) + " - " + ItemUtil.getItemMaxDamage(stack) + "]");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.FORMAT.format((ItemUtil.getItemAttackSpeed(stack) + 4)) + " Attack Speed");

		// Secondary Attributes
		if (!ItemUtil.getSecondaryAttributes(stack).isEmpty())
		{
			tooltip.add("");
			tooltip.add(TextFormatting.ITALIC + "Secondary Attributes");

			for (Attribute attribute : ItemUtil.getSecondaryAttributes(stack))
			{
				tooltip.add(attribute.getTooltipDisplay(nbt));
			}
		}

		// Other Info
		shiftInfo(tooltip, stack);
	}

	private static void drawMagical(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
	{
		tooltip.add(1, Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName() + " " + ItemUtil.getItemOriginalName(stack));

		// Level
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));
		tooltip.add("");

		// Rune
		tooltip.add(Rune.getRune(nbt).getColor() + Rune.getRune(nbt).getName());
		tooltip.add("");

		// Primary Attributes - damage/attack speed
		tooltip.add(TextFormatting.ITALIC + "Primary Attributes");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.getItemDamage(stack) + " Damage" + TextFormatting.GRAY + " [" + ItemUtil.getItemMinDamage(stack) + " - " + ItemUtil.getItemMaxDamage(stack) + "]");
		tooltip.add(TextFormatting.BLUE + " * " + ItemUtil.FORMAT.format((ItemUtil.getItemAttackSpeed(stack) + 4)) + " Attack Speed");

		// Secondary Attributes
		if (!ItemUtil.getSecondaryAttributes(stack).isEmpty())
		{
			tooltip.add("");
			tooltip.add(TextFormatting.ITALIC + "Secondary Attributes");

			for (Attribute attribute : ItemUtil.getSecondaryAttributes(stack))
			{
				tooltip.add(attribute.getTooltipDisplay(nbt));
			}
		}

		// Other Info
		shiftInfo(tooltip, stack);
	}

	private static void drawBauble(ArrayList<String> tooltip, ItemStack stack, NBTTagCompound nbt, EntityPlayer player, LSCPlayerCapability cap)
	{
		tooltip.add(1, Rarity.getRarity(nbt).getColor() + Rarity.getRarity(nbt).getName() + " " + ItemUtil.getItemOriginalName(stack));

		// Level
		if (cap.getPlayerLevel() < nbt.getInteger("Level")) tooltip.add(TextFormatting.RED + "Level: " + nbt.getInteger("Level"));
		else tooltip.add("Level: " + nbt.getInteger("Level"));

		// Secondary Attributes
		if (!ItemUtil.getSecondaryAttributes(stack).isEmpty())
		{
			tooltip.add("");
			tooltip.add(TextFormatting.ITALIC + "Secondary Attributes");

			for (Attribute attribute : ItemUtil.getSecondaryAttributes(stack))
			{
				tooltip.add(attribute.getTooltipDisplay(nbt));
			}
		}

		// Other Info
		shiftInfo(tooltip, stack);
	}

	private static void shiftInfo(ArrayList<String> tooltip, ItemStack stack)
	{
		tooltip.add("");
		tooltip.add(TextFormatting.ITALIC + "Shift for additional info");

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			tooltip.add(TextFormatting.DARK_GRAY + "Required Level: ");
			tooltip.add(TextFormatting.DARK_GRAY + "Sockets: ");
			tooltip.add(TextFormatting.DARK_GRAY + "Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
			tooltip.add("");
			tooltip.add(TextFormatting.DARK_GRAY + "Attribute's text color represents the attribute's rarity.");
		}
	}
}

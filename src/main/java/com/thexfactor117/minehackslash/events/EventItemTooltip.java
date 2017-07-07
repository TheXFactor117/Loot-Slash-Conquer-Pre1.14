package com.thexfactor117.minehackslash.events;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.thexfactor117.minehackslash.items.generation.Rarity;
import com.thexfactor117.minehackslash.items.generation.WeaponAttributes;
import com.thexfactor117.minehackslash.util.NBTHelper;

import net.minecraft.item.ItemAxe;
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
		
		if (stack != null && stack.getItem() instanceof ItemSword)
		{
			Rarity rarity = Rarity.getRarity(nbt);
			
			if (rarity != Rarity.DEFAULT)
			{
				tooltip.add("");
				
				if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemAxe)
				{
					NBTTagList taglist = nbt.getTagList("AttributeModifiers", 10);
					NBTTagCompound damageNbt = taglist.getCompoundTagAt(0);
					NBTTagCompound speedNbt = taglist.getCompoundTagAt(1);
					DecimalFormat format = new DecimalFormat("#.##");
					
					// damage and attack speed
					tooltip.add(TextFormatting.BLUE + "+" + format.format(damageNbt.getDouble("Amount")) + " Damage");
					tooltip.add(TextFormatting.BLUE + "+" + format.format(speedNbt.getDouble("Amount") + 4) + " Attack Speed");
					
					tooltip.add("");
					
					tooltip.add(rarity.getColor() + rarity.getName());
					
					for (WeaponAttributes attribute : WeaponAttributes.values())
					{
						if (attribute.hasAttribute(nbt))
							tooltip.add(" " + attribute.getName());
					}
				}
			}
		}
	}
}

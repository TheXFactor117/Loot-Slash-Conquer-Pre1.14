package com.thexfactor117.losteclipse.events.misc;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.IPlayerInformation;
import com.thexfactor117.losteclipse.stats.PlayerStatHelper;
import com.thexfactor117.losteclipse.stats.weapons.ArmorAttribute;
import com.thexfactor117.losteclipse.stats.weapons.Rarity;
import com.thexfactor117.losteclipse.stats.weapons.WeaponAttribute;
import com.thexfactor117.losteclipse.util.NBTHelper;

import net.minecraft.entity.SharedMonsterAttributes;
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
			IPlayerInformation info = event.getEntityPlayer().getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (info != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor))
			{
				Rarity rarity = Rarity.getRarity(nbt);
				
				if (rarity != Rarity.DEFAULT)
				{
					tooltip.add("");
					
					NBTTagList taglist = nbt.getTagList("AttributeModifiers", 10);
					NBTTagCompound damageNbt = taglist.getCompoundTagAt(0);
					NBTTagCompound speedNbt = taglist.getCompoundTagAt(1);
					DecimalFormat format = new DecimalFormat("#.##");
					
					tooltip.add(1, "Level: " + nbt.getInteger("Level"));
					
					if (stack.getItem() instanceof ItemSword)
					{	
						double playerDamage = event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
						double attackSpeed = speedNbt.getDouble("Amount") + 4 + (PlayerStatHelper.ATTACK_SPEED_MULTIPLIER * (double) (info.getAgilityStat() + info.getBonusAgilityStat()));
						
						// damage and attack speed
						tooltip.add(TextFormatting.BLUE + " +" + (nbt.getInteger("MinDamage") + (int) playerDamage) + "-" + (nbt.getInteger("MaxDamage") + (int) playerDamage) + " Damage");
						tooltip.add(TextFormatting.BLUE + " +" + format.format(attackSpeed) + " Attack Speed");
					}
					else if (stack.getItem() instanceof ItemArmor)
					{
						// armor and armor toughness
						tooltip.add(TextFormatting.BLUE + "+" + format.format(damageNbt.getDouble("Amount")) + " Armor");
						tooltip.add(TextFormatting.BLUE + "+" + format.format(speedNbt.getDouble("Amount")) + " Armor Toughness");
					}
					
					tooltip.add("");
					
					tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + " / " + stack.getMaxDamage());
					
					tooltip.add("");
					tooltip.add(TextFormatting.ITALIC + "Attributes");
					
					if (stack.getItem() instanceof ItemSword)
					{
						// attributes
						for (WeaponAttribute attribute : WeaponAttribute.values())
						{
							if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
								tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
							else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
								tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
						}
					}
					else if (stack.getItem() instanceof ItemArmor)
					{
						// attributes
						for (ArmorAttribute attribute : ArmorAttribute.values())
						{
							if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) < 1)
								tooltip.add(TextFormatting.BLUE + " +" + String.format("%.0f%%", attribute.getAmount(nbt) * 100) + " " + attribute.getName());
							else if (attribute.hasAttribute(nbt) && attribute.getAmount(nbt) >= 1)
								tooltip.add(TextFormatting.BLUE + " +" + format.format(attribute.getAmount(nbt)) + " " + attribute.getName());
						}
					}
				}
			}
		}
	}
}

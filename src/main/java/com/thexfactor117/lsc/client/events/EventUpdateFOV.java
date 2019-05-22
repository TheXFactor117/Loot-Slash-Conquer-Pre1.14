package com.thexfactor117.lsc.client.events;

import java.util.UUID;

import com.thexfactor117.lsc.util.PlayerUtil;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class EventUpdateFOV
{
	@SubscribeEvent
	public static void updateFOV(FOVUpdateEvent event)
	{
		AttributeModifier modifier = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(UUID.fromString(PlayerUtil.MOVEMENT_SPEED));
		
		if (modifier != null && event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(modifier) && modifier.getAmount() > 0)
		{	
			double attributeValue = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
			double amount = modifier.getAmount();
			
			float newFov = (float) (1 * ((attributeValue - amount) / event.getEntity().capabilities.getWalkSpeed() + 1) / 2);
			
			event.setNewfov(newFov);
		}
	}
}

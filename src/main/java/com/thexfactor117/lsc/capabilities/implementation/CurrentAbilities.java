package com.thexfactor117.lsc.capabilities.implementation;

import javax.annotation.Nullable;

import com.thexfactor117.lsc.abilities.AbilityHelper;
import com.thexfactor117.lsc.capabilities.api.ICurrentAbilities;

import net.minecraft.entity.Entity;

/**
 * 
 * @author TheXFactor117
 *
 */
public class CurrentAbilities implements ICurrentAbilities
{
	private int slot1Ability;
	private int slot2Ability;
	private int slot3Ability;
	private int ultimateAbility;
	
	@SuppressWarnings("unused")
	private final Entity entity;
	
	public CurrentAbilities(@Nullable Entity entity)
	{
		this.entity = entity;
	}
	
	@Override
	public int getAbilityFromSlot(int slot) 
	{
		if (slot == 1) return slot1Ability;
		else if (slot == 2) return slot2Ability;
		else if (slot == 3) return slot3Ability;
		else if (slot == 4) return ultimateAbility;
		
		return 0;
	}

	@Override
	public void setAbilityInSlot(int slot, int abilityID) 
	{
		if (slot == 1) 
		{
			slot1Ability = abilityID;
			AbilityHelper.abilities.set(0, AbilityHelper.getAbilityFromID(abilityID));
		}
		else if (slot == 2)
		{
			slot2Ability = abilityID;
			AbilityHelper.abilities.set(1, AbilityHelper.getAbilityFromID(abilityID));
		}
		else if (slot == 3) 
		{
			slot3Ability = abilityID;
			AbilityHelper.abilities.set(2, AbilityHelper.getAbilityFromID(abilityID));
		}
		else if (slot == 4) 
		{
			ultimateAbility = abilityID;
			AbilityHelper.abilities.set(3, AbilityHelper.getAbilityFromID(abilityID));
		}
	}
}

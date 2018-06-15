package com.lsc.capabilities.api;

/**
 * 
 * @author TheXFactor117
 *
 */
public interface ICurrentAbilities 
{
	int getAbilityFromSlot(int slot);
	void setAbilityInSlot(int slot, int abilityID);
}

package com.thexfactor117.lootslashconquer.capabilities.abilities;

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

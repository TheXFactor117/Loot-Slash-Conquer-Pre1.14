package com.thexfactor117.lsc.abilities;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author TheXFactor117
 *
 */
public class AbilityHelper 
{
	public static List<Ability> abilities = Lists.newArrayList();
	
	public static final List<Ability> ALL_ABILITIES = Lists.newArrayList();
	public static final List<Ability> WARRIOR_ABILITIES = Lists.newArrayList();
	public static final List<Ability> MAGE_ABILITIES = Lists.newArrayList();
	public static final List<Ability> HUNTER_ABILITIES = Lists.newArrayList();
	
	/** Returns the Ability of the ID passed in. */
	public static Ability getAbilityFromID(int id)
	{
		for (Ability ability : ALL_ABILITIES)
		{
			if (ability.getID() == id) return ability;
		}
		
		return null;
	}
}

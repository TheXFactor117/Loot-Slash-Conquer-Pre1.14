package com.thexfactor117.losteclipse.abilities;

import com.thexfactor117.losteclipse.abilities.warrior.AbilityPowerfulLeap;

import net.minecraft.entity.player.EntityPlayer;

/**
 * 
 * @author TheXFactor117
 *
 */
public abstract class AbilityBase 
{
	public static final AbilityBase POWERFUL_LEAP = new AbilityPowerfulLeap();
	
	private String name; // name of Ability
	private boolean isSlotted; // whether or not the Ability is slotted
	private boolean isActive; // whether or not the Ability is currently activated
	private int cooldown; // how much time left till the Ability can be used again - in seconds
	private int maxCooldown; // the max time it takes for the Ability to "cooldown" - in seconds
	
	public AbilityBase(String name, int maxCooldown)
	{
		this.name = name;
		this.maxCooldown = maxCooldown;
		
		this.isSlotted = false;
		this.isActive = false;
		this.cooldown = 0;
	}
	
	/** Called at the activation of an ability. Used to setup certain things for the specific ability. */
	public void startAbility() {}
	
	/** Called every tick the ability is active. */
	public void useAbility(EntityPlayer player) {}
	
	/** Called when the ability stops executing. Starts cooldown timer and resets stuff. */
	public void stopAbility() {}
}

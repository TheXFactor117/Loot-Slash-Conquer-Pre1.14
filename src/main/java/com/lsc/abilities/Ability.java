package com.lsc.abilities;

/**
 * 
 * @author TheXFactor117
 *
 */
public abstract class Ability
{
	protected String name;
	protected int id;
	protected int playerClass;
	protected int maxCooldown;
	protected int cooldown;
	
	protected boolean isActive;
	
	public Ability(String name, int id, int playerClass, int maxCooldown)
	{
		this.name = name;
		this.id = id;
		this.playerClass = playerClass;
		this.maxCooldown = maxCooldown;
		this.cooldown = 0;
		
		this.isActive = false;
		
		if (playerClass == 0) AbilityHelper.WARRIOR_ABILITIES.add(this);
		else if (playerClass == 1) AbilityHelper.MAGE_ABILITIES.add(this);
		else AbilityHelper.HUNTER_ABILITIES.add(this);
	}
	
	public boolean canStart()
	{
		return false;
	}
	
	public void start() 
	{
		isActive = true;
	}
	
	public void onTick() {}
	
	public void stop() 
	{
		isActive = false;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	public String getName()
	{
		return name;
	}
	
	public int getID()
	{
		return id;
	}
	
	public int getAbilityClass()
	{
		return playerClass;
	}
	
	public int maxCooldown()
	{
		return maxCooldown;
	}
	
	public int getCooldown()
	{
		return cooldown;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
}

package com.thexfactor117.lsc.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

/**
 *
 * @author TheXFactor117
 *
 */
public class LSCDamageSource extends DamageSource
{
	public static final DamageSource FROST = new LSCDamageSource("frost").setFrostDamage();
	public static final DamageSource LIGHTNING = new LSCDamageSource("lightning").setLightningDamage();
	public static final DamageSource POISON = new LSCDamageSource("poison").setPoisonDamage();
	public static final DamageSource CHAINED = new LSCDamageSource("chained").setChainedDamage();
	
	private Entity damageSourceEntity;
	
	private boolean frostDamage;
	private boolean lightningDamage;
	private boolean poisonDamage;
	private boolean chainedDamage;
	
	public LSCDamageSource(String damageType)
	{
		super(damageType);
	}
	
	public LSCDamageSource(String damageType, Entity damageSourceEntity)
	{
		super(damageType);
		this.damageSourceEntity = damageSourceEntity;
	}
	
	@Override
	public Entity getTrueSource()
	{
		return damageSourceEntity;
	}
	
	public static DamageSource causeFireDamage(EntityLivingBase entity)
	{
		return new LSCDamageSource(ON_FIRE.damageType, entity).setFireDamage();
	}
	
	public static DamageSource causeFrostDamage(EntityLivingBase entity)
	{
		return new LSCDamageSource(FROST.damageType, entity).setFrostDamage();
	}
	
	public static DamageSource causeLightningDamage(EntityLivingBase entity)
	{
		return new LSCDamageSource(LIGHTNING.damageType, entity).setLightningDamage();
	}
	
	public static DamageSource causePoisonDamage(EntityLivingBase entity)
	{
		return new LSCDamageSource(POISON.damageType, entity).setPoisonDamage();
	}
	
	public static DamageSource causeChainedDamage(EntityLivingBase entity)
	{
		return new LSCDamageSource(CHAINED.damageType, entity).setChainedDamage();
	}
	
	public LSCDamageSource setFrostDamage()
	{
		this.frostDamage = true;
		return this;
	}
	
	public boolean isFrostDamage()
	{
		return this.frostDamage;
	}
	
	public LSCDamageSource setLightningDamage()
	{
		this.lightningDamage = true;
		return this;
	}
	
	public boolean isLightningDamage()
	{
		return this.lightningDamage;
	}
	
	public LSCDamageSource setPoisonDamage()
	{
		this.poisonDamage = true;
		return this;
	}
	
	public boolean isPoisonDamage()
	{
		return this.poisonDamage;
	}
	
	public LSCDamageSource setChainedDamage()
	{
		this.chainedDamage = true;
		return this;
	}
	
	public boolean isChainedDamage()
	{
		return this.chainedDamage;
	}
}

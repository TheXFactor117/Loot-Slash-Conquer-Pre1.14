package com.lsc.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

/**
 *
 * @author TheXFactor117
 *
 */
public class ElementalDamageSource extends DamageSource
{
	public static final DamageSource FROST = new DamageSource("frost");
	public static final DamageSource LIGHTNING = new DamageSource("lightning");
	public static final DamageSource POISON = new DamageSource("poison");
	
	private Entity damageSourceEntity;
	
	public ElementalDamageSource(String damageType, Entity damageSourceEntity)
	{
		super(damageType);
		this.damageSourceEntity = damageSourceEntity;
	}
	
	@Override
	public Entity getTrueSource()
	{
		return damageSourceEntity;
	}
	
	public static DamageSource causeElementalDamage(EntityLivingBase entity, DamageSource source)
	{
		return new ElementalDamageSource(source.damageType, entity);
	}
}

package com.thexfactor117.losteclipse.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityMagic extends EntityThrowable
{
	private int minDamage;
	private int maxDamage;
	
	public EntityMagic(World world)
	{
		super(world);
	}
	
	public EntityMagic(World world, double x, double y, double z, float velocity, float inaccuracy, int minDamage, int maxDamage)
	{
		super(world, x,y, z);
		this.setThrowableHeading(x, y, z, velocity, inaccuracy);
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.getEntityWorld().isRemote)
		{
			if (!this.inGround && !this.isDead)
			{
				this.getEntityWorld().spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY, this.posZ, 0F, 0F, 0F, new int[0]);
			}
			
			if (this.inGround)
			{
				this.setDead();
			}
		}
	}
	
	@Override
	public void onImpact(RayTraceResult result)
	{
		if (!this.getEntityWorld().isRemote)
		{
			if (result.entityHit != null && result.entityHit instanceof EntityLivingBase)
			{
				float damage = (float) ((Math.random() * (maxDamage - minDamage)) + minDamage);
				result.entityHit.attackEntityFrom(DamageSource.MAGIC, damage);
			}
		}
	}
	
	@Override
	protected float getGravityVelocity()
	{
		return 0;
	}
}

package com.thexfactor117.lsc.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityFireball extends EntityProjectileBase
{
	private int seconds;
	
	public EntityFireball(World world) 
	{
		super(world);
	}
	
	/** Only use this constructor if a player shoots the projectile with the correct item. */
	public EntityFireball(World world, double x, double y, double z, float velocity, float inaccuracy, EntityPlayer player, ItemStack stack, int seconds)
	{
		super(world, x, y, z, velocity, inaccuracy, player, stack);
		this.seconds = seconds;
	}
	
	@Override
	public void onImpact(RayTraceResult result)
	{
		super.onImpact(result);
		
		if (!this.getEntityWorld().isRemote)
		{
			if (result.entityHit != null && result.entityHit instanceof EntityLivingBase && result.entityHit != player)
			{
				result.entityHit.setFire(seconds);
			}
			
			this.setDead();
		}
	}
}

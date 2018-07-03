package com.lsc.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityIcebolt extends EntityProjectileBase
{
	private int seconds;
	
	public EntityIcebolt(World world) 
	{
		super(world);
	}
	
	/** Only use this constructor if a player shoots the projectile with the correct item. */
	public EntityIcebolt(World world, double x, double y, double z, float velocity, float inaccuracy, EntityPlayer player, ItemStack stack, int seconds)
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
				((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * seconds, 1));
			}
			
			this.setDead();
		}
	}
}

package com.lsc.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityAIGaze extends EntityAIBase
{
	private EntityLiving entity;
	public int gazeCooldown; // time left till gaze can activate
	public final int avgGazeCooldown = 20 * 20; // average cooldown between gaze attempts
	private int gazeCount; // running count of how much time we have been gazing
	private final int maxGazeCount = 20 * 3; // amount of time the gaze lasts
	
	public EntityAIGaze(EntityLiving entity)
	{
		this.entity = entity;
		this.setMutexBits(8);
		this.gazeCooldown = (int) (Math.random() * this.avgGazeCooldown) + 10;
	}

	@Override
	public boolean shouldExecute() 
	{
		return gazeCooldown == 0 && this.entity.getAttackTarget() != null && !this.entity.getAttackTarget().isDead;
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		return shouldExecute() && this.gazeCount < this.maxGazeCount;
	}
	
	@Override
	public void startExecuting()
	{
		// start gazing animation
	}
	
	@Override
	public void updateTask()
	{
		gazeCount++;
		
		EntityLivingBase enemy = this.entity.getAttackTarget();
		
		if (gazeCount < this.maxGazeCount)
		{
			if (this.entity.getDistance(enemy) < 16)
			{
				this.entity.faceEntity(enemy, 360, 360);
			}
		}
		else
		{
			Vec3d vec3d = enemy.getLook(1.0F).normalize();
            Vec3d vec3d1 = new Vec3d(this.entity.posX - enemy.posX, this.entity.getEntityBoundingBox().minY + (double) this.entity.getEyeHeight() - (enemy.posY + (double) enemy.getEyeHeight()), this.entity.posZ - enemy.posZ);
            double d0 = vec3d1.lengthVector();
            vec3d1 = vec3d1.normalize();
            double d1 = vec3d.dotProduct(vec3d1);
            boolean isLooking = d1 > 1.0D - 0.5D / d0 ? enemy.canEntityBeSeen(this.entity) : false;
			            
			if (isLooking)
			{
				int chance = (int) (Math.random() * 2);
				
				if (chance == 0)
				{
					double damage = this.entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / 4;
					enemy.attackEntityFrom(DamageSource.causeMobDamage(this.entity), (float) damage);
					enemy.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 5, 10));
					enemy.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 3, 1));
					enemy.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 20 * 3, 1));
				}
				
				this.gazeCooldown = (int) (Math.random() * this.avgGazeCooldown) + 10;
			}
		}
	}
	
	@Override
	public void resetTask()
	{
		this.gazeCount = 0;
	}
	
	@Override
	public boolean isInterruptible()
	{
		return false;
	}
}

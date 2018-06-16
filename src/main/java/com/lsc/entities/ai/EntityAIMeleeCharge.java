package com.lsc.entities.ai;

import com.lsc.LootSlashConquer;
import com.lsc.entities.bosses.EntityCorruptedKnight;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/**
 *
 * @author TheXFactor117
 *
 */
public class EntityAIMeleeCharge extends EntityAIBase
{
	private EntityLiving entity;
	private int chargeDistance; // distance the entity will charge
	private double chargeSpeed; // speed at which the entity will charge
	public int cooldown; // cooldown counter
	private final int maxCooldown = 20 * 15; // amount of time needed to pass for the AI to start again
	private int progress;
	private final int maxProgress = 20 * 5;
	private BlockPos startingPos;
	private double chargeMotionX;
	private double chargeMotionZ;
	
	public EntityAIMeleeCharge(EntityLiving entity, int distance, double speed)
	{
		this.setMutexBits(7); // incompatible with everything vanilla
		this.entity = entity;
		this.chargeDistance = distance;
		this.chargeSpeed = speed;
		this.chargeMotionX = 0;
		this.chargeMotionZ = 0;
	}
	
	@Override
	public boolean shouldExecute()
	{
		boolean canExecuteInStage = false;
		
		if (this.entity instanceof EntityCorruptedKnight)
		{
			if (((EntityCorruptedKnight) this.entity).getStage() == 3) canExecuteInStage = true;
		}
		
		return canExecuteInStage && cooldown == 0 && this.entity.getAttackTarget() != null && !this.entity.getAttackTarget().isDead;
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		return shouldExecute();
	}
	
	@Override
	public void startExecuting()
	{
		LootSlashConquer.LOGGER.info("Executing charge...");
		
		this.startingPos = this.entity.getPosition();
		
		double d0 = this.entity.getAttackTarget().posX - this.entity.posX;
		double d1 = this.entity.getAttackTarget().posZ - this.entity.posZ;
		double f = Math.sqrt(d0 * d0 + d1 * d1);
		
		if (f >= 1.0E-4D)
		{	
			this.chargeMotionX += d0 / f * 0.5 * 0.800000011920929D + this.entity.motionX * 0.20000000298023224;
			this.chargeMotionZ += d1 / f * 0.5 * 0.800000011920929D + this.entity.motionZ * 0.20000000298023224;
		}
		
		//AttributeModifier tempSpeedBoost = new AttributeModifier(TEMP_SPEED_BOOST, "tempSpeedBoost", chargeSpeed, 0);
		//this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(tempSpeedBoost);
	}
	
	@Override
	public void updateTask()
	{
		this.progress++;
		
		// check to make sure the charge can continue
		if (this.startingPos.getDistance(this.entity.getPosition().getX(), this.entity.getPosition().getY(), this.entity.getPosition().getZ()) >= 25 || this.progress >= this.maxProgress)
		{
			LootSlashConquer.LOGGER.info("Charge finished...");
			// reset cooldown when finished
			this.cooldown = this.maxCooldown;
		}
		else
		{
			LootSlashConquer.LOGGER.info("Charging...");
			this.entity.motionX += this.chargeMotionX;
			this.entity.motionZ += this.chargeMotionZ;
			
			EntityPlayer closestPlayer = this.entity.world.getClosestPlayerToEntity(this.entity, 2);
			
			if (closestPlayer != null && closestPlayer.getEntityBoundingBox().intersects(this.entity.getEntityBoundingBox()))
			{
				closestPlayer.knockBack(this.entity, (float) 30 * 0.5F, (double) MathHelper.sin(this.entity.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(this.entity.rotationYaw * 0.017453292F)));
				closestPlayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 3, 10, false, false));
				closestPlayer.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 6, 5, false, false));
				closestPlayer.attackEntityFrom(DamageSource.causeMobDamage(this.entity), (float) (2));
			}
		}
	}
	
	@Override
	public void resetTask()
	{
		//this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(TEMP_SPEED_BOOST);
		this.startingPos = null;
		this.progress = 0;
		this.chargeMotionX = 0;
		this.chargeMotionZ = 0;
	}
	
	@Override
	public boolean isInterruptible()
	{
		return false;
	}
}

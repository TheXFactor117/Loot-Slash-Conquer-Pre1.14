package com.thexfactor117.lsc.entities.ai;

import com.thexfactor117.lsc.entities.bosses.EntityCorruptedKnight;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;

/**
 *
 * @author TheXFactor117
 *
 * AI for the Corrupted Knight's Stage 1 Slam.
 *
 */
public class EntityAICKSlam extends EntityAIBase
{
	private EntityLiving entity;
	public int slamCooldown;
	public final int maxSlamCooldown = 20 * 15;
	private int inProgressCount;
	private int maxProgressCount = 20 * 3;
	
	public EntityAICKSlam(EntityLiving entity)
	{
		this.entity = entity;
		this.setMutexBits(7); // needs to be incompatible with Vanilla AI.
		this.slamCooldown = maxSlamCooldown;
	}
	
	@Override
	public boolean shouldExecute()
	{	
		boolean canExecuteInStage = false;
		
		if (this.entity instanceof EntityCorruptedKnight)
		{
			if (((EntityCorruptedKnight) this.entity).getStage() == 1) canExecuteInStage = true;
		}
		
		// return true if the target still exists.
		return canExecuteInStage && slamCooldown == 0 && this.entity.getAttackTarget() != null && !this.entity.getAttackTarget().isDead;
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		return shouldExecute();
	}
	
	@Override
	public void startExecuting()
	{
		
	}
	
	@Override
	public void updateTask()
	{
		this.inProgressCount++;
		
		if (this.inProgressCount >= this.maxProgressCount)
		{
			EntityPlayer player = (EntityPlayer) this.entity.getAttackTarget();
			
			if (player.getDistance(entity) <= 10)
			{
				player.knockBack(player, (float) 3 * 0.5F, (double) MathHelper.sin(player.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 5, 10, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 10, 5, false, false));
			}
			
			this.slamCooldown = maxSlamCooldown;
		}
	}
	
	@Override
	public void resetTask()
	{
		this.inProgressCount = 0;
	}
	
	@Override
	public boolean isInterruptible()
	{
		return false;
	}
}

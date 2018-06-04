package com.lsc.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityAIMummyGaze extends EntityAIBase
{
	private EntityLiving mummy;
	private int ticksGazing = 0; // keeps track of how many ticks the entity has been gazing
	private boolean gazeSuccessful = false;
	
	public EntityAIMummyGaze(EntityLiving mummy)
	{
		this.mummy = mummy;
		this.setMutexBits(8);
	}

	@Override
	public boolean shouldExecute() 
	{
		EntityLivingBase enemy = mummy.getAttackTarget();
		
		if (enemy != null)
		{
			if (mummy.getDistance(enemy) < 20 && Math.random() < 0.05);
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		EntityLivingBase enemy = this.mummy.getAttackTarget();

        if (enemy == null)
        {
            return false;
        }
        else if (!enemy.isEntityAlive())
        {
            return false;
        }
        else if (ticksGazing > 20 * 3)
        {
        	return false;
        }
        else
        {
            return !(enemy instanceof EntityPlayer) || !((EntityPlayer) enemy).isSpectator() && !((EntityPlayer) enemy).isCreative();
        }
	}
	
	@Override
	public void startExecuting()
	{
		// start gazing animation
	}
	
	@Override
	public void updateTask()
	{
		++ticksGazing;
		
		if (!gazeSuccessful)
		{
			EntityLivingBase enemy = this.mummy.getAttackTarget();
			
			if (enemy != null)
			{
				if (enemy.canEntityBeSeen(this.mummy))
				{
					double damage = this.mummy.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
					enemy.attackEntityFrom(DamageSource.causeMobDamage(this.mummy), (float) damage);
					enemy.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 5, 10));
					enemy.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 3, 1));
					enemy.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 20 * 5, 1));
					gazeSuccessful = true;
				}
			}
		}
	}
	
	@Override
	public void resetTask()
	{
		ticksGazing = 0;
		gazeSuccessful = false;
	}
}

package com.lsc.entities.ai;

import com.lsc.LootSlashConquer;
import com.lsc.entities.monsters.EntityBanshee;

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
public class EntityAIBansheeScream extends EntityAIBase
{
	private EntityLiving banshee;
	
	public EntityAIBansheeScream(EntityLiving banshee)
	{
		this.banshee = banshee;
	}
	
	@Override
	public boolean shouldExecute() 
	{
		EntityLivingBase enemy = this.banshee.getAttackTarget();

        if (enemy == null)
        {
            return false;
        }
        else if (!enemy.isEntityAlive())
        {
            return false;
        }
        else
        {
        	if (Math.random() < 0.01 && this.banshee instanceof EntityBanshee && ((EntityBanshee) this.banshee).canScream())
        	{
        		return !(enemy instanceof EntityPlayer) || !((EntityPlayer) enemy).isSpectator() && !((EntityPlayer) enemy).isCreative();
        	}
        	else
        	{
        		return false;
        	}
        }
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		return false;
	}
	
	@Override
	public void startExecuting()
	{
		// play banshee scream sound
		
		LootSlashConquer.LOGGER.info("Screaming...");
		
		if (this.banshee.getAttackTarget() != null && this.banshee.getAttackTarget() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) this.banshee.getAttackTarget();
			double damage = this.banshee.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / 4.0;
			
			if (player.attackEntityFrom(DamageSource.causeMobDamage(this.banshee), (float) damage))
			{
				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 2, 3));
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 5, 1));
				
				((EntityBanshee) this.banshee).setCanScream(false);
				((EntityBanshee) this.banshee).resetTicksSinceScream();
			}
		}
	}
}

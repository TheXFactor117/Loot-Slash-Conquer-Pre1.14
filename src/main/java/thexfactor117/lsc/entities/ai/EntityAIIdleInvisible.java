package thexfactor117.lsc.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityAIIdleInvisible extends EntityAIBase
{
	private EntityLiving entity;
	private double lookX;
	private double lookZ;
	// counter to determine when to stop idling
	private int idleTime = 0;
	
	public EntityAIIdleInvisible(EntityLiving entity)
	{
		this.entity = entity;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute() 
	{
		return Math.random() < 0.02;
	}
	
	@Override
	public boolean shouldContinueExecuting()
	{
		if (idleTime >= 0)
		{
			if (!this.entity.isPotionActive(MobEffects.INVISIBILITY))
			{
				this.entity.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20 * 5, 1, false, false));
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void startExecuting()
    {
        double d0 = (Math.PI * 2D) * Math.random();
        this.lookX = Math.cos(d0);
        this.lookZ = Math.sin(d0);
        this.idleTime = 20 + ((int) Math.random() * 20);
        
        this.entity.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20 * 5, 1, false, false));
    }
	
	@Override
	public void updateTask()
    {
        --this.idleTime;
        this.entity.getLookHelper().setLookPosition(this.entity.posX + this.lookX, this.entity.posY + (double)this.entity.getEyeHeight(), this.entity.posZ + this.lookZ, (float)this.entity.getHorizontalFaceSpeed(), (float)this.entity.getVerticalFaceSpeed());
    }
}

package com.thexfactor117.lsc.entities.monsters;

import java.util.Iterator;
import java.util.List;

import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.entities.EntityMonster;
import com.thexfactor117.lsc.entities.ai.EntityAINearestAttackableTargetInvisible;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityBanshee extends EntityMonster implements IMob
{	
	private boolean canScream;
	private int screamCooldown;
	private int avgScreamCooldown = 20 * 20;
	
	public EntityBanshee(World world)
	{
		super(world);
		this.setSize(1.0F, 2.0F);
		rarity = 2;
		this.canScream = false;
		this.screamCooldown = (int) (Math.random() * avgScreamCooldown) + 15;
	}
	
	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTargetInvisible<EntityPlayer>(this, EntityPlayer.class, false));
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Configs.monsterStatsCategory.bansheeMaxHealth);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Configs.monsterStatsCategory.bansheeDamage);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(Configs.monsterStatsCategory.bansheeArmor);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(Configs.monsterStatsCategory.bansheeMovementSpeed);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(Configs.monsterStatsCategory.bansheeFollowRange);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Configs.monsterStatsCategory.bansheeKnockbackResistance);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
    {
		// give the player a 20% chance of hitting an invisible banshee.
		int chance = (int) (Math.random() * 5);
		boolean flag = !this.isPotionActive(MobEffects.INVISIBILITY) ? true : chance == 0;
				
		return flag && super.attackEntityFrom(source, amount);
    }
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (!this.world.isRemote)
		{
			if (this.getAttackTarget() != null && !this.getAttackTarget().isDead)
			{
				if (!this.canScream)
				{
					this.screamCooldown--;
					
					if (this.screamCooldown <= 0)
					{
						this.canScream = true;
						this.screamCooldown = (int) (Math.random() * avgScreamCooldown) + 15;
					}
				}
				else
				{
					int radius = 5;
					double damage = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / 5;
					
					List<EntityPlayer> entityList = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX - radius, this.posY - radius, this.posZ - radius, this.posX + radius, this.posY + radius, this.posZ + radius));
					Iterator<EntityPlayer> iterator = entityList.iterator();
					
					while (iterator.hasNext())
					{
						EntityPlayer player = iterator.next();
	                	int chance = (int) (Math.random() * 4);
	                	
	                	if (chance == 0)
	                	{
	                		if (player.attackEntityFrom(DamageSource.causeMobDamage(this), (float) damage))
		                	{
		                		this.playSound(SoundEvents.ENTITY_SPIDER_HURT, 1.0F, 1.0F);
		                		player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 2, 3));
		                		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 5, 1));
		                	}
	                	}
					}
					
					this.canScream = false;
				}
			}
		}
	}
	
	@Override
	public boolean getCanSpawnHere()
	{
		boolean isBelow60 = this.getPosition().getY() < 60 ? true : false;
		
		return super.getCanSpawnHere() && isBelow60;
	}
}

package com.thexfactor117.losteclipse.entities.projectiles;

import com.thexfactor117.losteclipse.events.EventLivingHurtAttack;
import com.thexfactor117.losteclipse.util.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityProjectileBase extends EntityThrowable
{
	protected EntityPlayer player; // shooter
	protected ItemStack stack; // item that shot projectile
	
	public EntityProjectileBase(World world)
	{
		super(world);
	}
	
	/** Only use this constructor if a player shoots the projectile with the correct item. */
	public EntityProjectileBase(World world, double x, double y, double z, float velocity, float inaccuracy, EntityPlayer player, ItemStack stack)
	{
		super(world, x, y, z);
		this.setThrowableHeading(x, y, z, velocity, inaccuracy);
		this.player = player;
		this.stack = stack;
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
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				double playerDamage = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
				double damage = (Math.random() * (nbt.getInteger("MaxDamage") - nbt.getInteger("MinDamage"))) + (nbt.getInteger("MinDamage") + (int) playerDamage);
				
				// apply damage
				result.entityHit.attackEntityFrom(DamageSource.MAGIC, (float) damage);
				result.entityHit.hurtResistantTime = 0; // set hurt resistant time to zero because other calculations might be added.
				
				// apply attributes
				EventLivingHurtAttack.useWeaponAttributes((float) damage, player, (EntityLivingBase) result.entityHit, stack, NBTHelper.loadStackNBT(stack));
			}
		}
	}
	
	@Override
	protected float getGravityVelocity()
	{
		return 0;
	}
}

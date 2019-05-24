package com.thexfactor117.lsc.entities.projectiles;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.util.DamageUtil;
import com.thexfactor117.lsc.util.PlayerUtil;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.EntityLivingBase;
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
public abstract class EntityProjectileBase extends EntityThrowable
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
		this.shoot(x, y, z, velocity, inaccuracy);
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
		}
	}
	
	@Override
	public void onImpact(RayTraceResult result)
	{
		if (!this.getEntityWorld().isRemote && player != null)
		{
			LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
			
			if (result.entityHit != null && result.entityHit instanceof EntityLivingBase && result.entityHit != player && cap != null)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				double damage = (Math.random() * (nbt.getInteger("MaxDamage") - nbt.getInteger("MinDamage"))) + (nbt.getInteger("MinDamage"));
				damage = DamageUtil.applyDamageModifiers(cap, damage, DamageUtil.DamageType.MAGICAL);
				damage = DamageUtil.applyCriticalModifier(cap, damage);
				
				// apply damage
				result.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) damage);
				result.entityHit.hurtResistantTime = 0; // set hurt resistant time to zero because other calculations might be added.

				// apply attributes
			}
		}
	}
	
	@Override
	protected float getGravityVelocity()
	{
		return 0F;
	}
	
	public EntityPlayer getShooter()
	{
		return player;
	}
}

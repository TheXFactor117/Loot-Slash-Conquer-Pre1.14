package com.thexfactor117.lsc.player;

import java.util.Iterator;
import java.util.List;

import com.thexfactor117.lsc.capabilities.cap.CapabilityPlayerStats;
import com.thexfactor117.lsc.capabilities.implementation.Stats;
import com.thexfactor117.lsc.loot.Attribute;
import com.thexfactor117.lsc.util.LSCDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class WeaponUtils 
{
	/** Called to use the current stack's attributes. Called from LivingAttackEvent and projectiles. */
	public static void useWeaponAttributes(float damage, EntityLivingBase attacker, EntityLivingBase enemy, ItemStack stack, NBTTagCompound nbt)
	{
		if (Attribute.DURABLE.hasAttribute(nbt) && Math.random() < Attribute.DURABLE.getAmount(nbt)) stack.setItemDamage(stack.getItemDamage() + 1);
		if (Attribute.FIRE.hasAttribute(nbt)) 
		{
			enemy.attackEntityFrom(DamageSource.ON_FIRE, (float) Attribute.FIRE.getAmount(nbt));
			enemy.hurtResistantTime = 0;
		}
		if (Attribute.FROST.hasAttribute(nbt))
		{
			enemy.attackEntityFrom(LSCDamageSource.causeFrostDamage(attacker), (float) Attribute.FROST.getAmount(nbt));
			enemy.hurtResistantTime = 0;
			enemy.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 3, 5));
		}
		if (Attribute.LIGHTNING.hasAttribute(nbt))
		{
			enemy.attackEntityFrom(LSCDamageSource.causeLightningDamage(attacker), (float) Attribute.LIGHTNING.getAmount(nbt));
			enemy.hurtResistantTime = 0;
			
			// remove half the lightning damage dealt from mana.
			if (enemy instanceof EntityPlayer)
			{
				Stats statsCap = (Stats) enemy.getCapability(CapabilityPlayerStats.STATS, null);
				
				if (statsCap != null)
				{
					statsCap.decreaseMana((int) (Attribute.LIGHTNING.getAmount(nbt) / 2));
				}
			}
		}
		if (Attribute.POISON.hasAttribute(nbt)) 
		{
			enemy.attackEntityFrom(LSCDamageSource.causePoisonDamage(attacker), (float) Attribute.POISON.getAmount(nbt));
			enemy.hurtResistantTime = 0;
		}
		if (Attribute.LIFE_STEAL.hasAttribute(nbt)) 
		{
			attacker.setHealth((float) (attacker.getHealth() + (damage * Attribute.LIFE_STEAL.getAmount(nbt))));
		}
		if (Attribute.MANA_STEAL.hasAttribute(nbt))
		{
			Stats statsCap = (Stats) enemy.getCapability(CapabilityPlayerStats.STATS, null);
			
			if (statsCap != null)
			{
				// adds mana to the player each attack.
				statsCap.increaseMana((int) (Attribute.MANA_STEAL.getAmount(nbt) * damage));
			}
		}
		if (Attribute.CHAINED.hasAttribute(nbt))
		{
			double radius = Attribute.CHAINED.getAmount(nbt);
			World world = enemy.getEntityWorld();
			List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(attacker.posX - radius, attacker.posY - radius, attacker.posZ - radius, attacker.posX + radius, attacker.posY + radius, attacker.posZ + radius));
			Iterator<EntityLivingBase> iterator = entityList.iterator();
			
			while (iterator.hasNext())
			{
                Entity entity = (Entity) iterator.next();
				
                // IF PLAYER IS THE ATTACKER
				if (entity instanceof EntityLivingBase && attacker instanceof EntityPlayer && !(entity instanceof EntityPlayer) && !(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime))
				{
					entity.attackEntityFrom(LSCDamageSource.causeChainedDamage(attacker), (float) (damage * 0.25));
					entity.hurtResistantTime = 0;
				}
				// IF A MOB IS THE ATTACKER
				else if (entity instanceof EntityPlayer && attacker instanceof EntityMob)
				{
					entity.attackEntityFrom(LSCDamageSource.causeChainedDamage(attacker), (float) (damage * 0.25));
				}
			}
		}
		if (Attribute.VOID.hasAttribute(nbt) && Math.random() < Attribute.VOID.getAmount(nbt)) enemy.setHealth(0.00001F);
	}
}

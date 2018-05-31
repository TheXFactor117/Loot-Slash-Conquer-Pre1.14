package com.lsc.player;

import java.util.Iterator;
import java.util.List;

import com.lsc.capabilities.playerstats.CapabilityPlayerStats;
import com.lsc.capabilities.playerstats.Stats;
import com.lsc.init.ModDamageSources;
import com.lsc.loot.WeaponAttribute;

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
public class WeaponHelper 
{
	/** Called to use the current stack's attributes. Called from LivingAttackEvent and projectiles. */
	public static void useWeaponAttributes(float damage, EntityLivingBase attacker, EntityLivingBase enemy, ItemStack stack, NBTTagCompound nbt)
	{
		if (WeaponAttribute.DURABLE.hasAttribute(nbt) && Math.random() < WeaponAttribute.DURABLE.getAmount(nbt)) stack.setItemDamage(stack.getItemDamage() + 1);
		if (WeaponAttribute.FIRE.hasAttribute(nbt)) 
		{
			enemy.attackEntityFrom(DamageSource.ON_FIRE, (float) WeaponAttribute.FIRE.getAmount(nbt));
			enemy.hurtResistantTime = 0;
		}
		if (WeaponAttribute.FROST.hasAttribute(nbt))
		{
			enemy.attackEntityFrom(ModDamageSources.FROST, (float) WeaponAttribute.FROST.getAmount(nbt));
			enemy.hurtResistantTime = 0;
			enemy.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 3, 5));
		}
		if (WeaponAttribute.LIGHTNING.hasAttribute(nbt))
		{
			enemy.attackEntityFrom(ModDamageSources.LIGHTNING, (float) WeaponAttribute.LIGHTNING.getAmount(nbt));
			enemy.hurtResistantTime = 0;
			
			// remove half the lightning damage dealt from mana.
			if (enemy instanceof EntityPlayer)
			{
				Stats statsCap = (Stats) enemy.getCapability(CapabilityPlayerStats.STATS, null);
				
				if (statsCap != null)
				{
					statsCap.decreaseMana((int) (WeaponAttribute.LIGHTNING.getAmount(nbt) / 2));
				}
			}
		}
		if (WeaponAttribute.POISON.hasAttribute(nbt)) 
		{
			enemy.attackEntityFrom(ModDamageSources.POISON, (float) WeaponAttribute.POISON.getAmount(nbt));
			enemy.hurtResistantTime = 0;
		}
		if (WeaponAttribute.LIFE_STEAL.hasAttribute(nbt)) attacker.setHealth((float) (attacker.getHealth() + (damage * WeaponAttribute.LIFE_STEAL.getAmount(nbt))));
		if (WeaponAttribute.MANA_STEAL.hasAttribute(nbt))
		{
			Stats statsCap = (Stats) enemy.getCapability(CapabilityPlayerStats.STATS, null);
			
			if (statsCap != null)
			{
				// adds mana to the player each attack.
				statsCap.increaseMana((int) (WeaponAttribute.MANA_STEAL.getAmount(nbt) * damage));
			}
		}
		if (WeaponAttribute.CHAINED.hasAttribute(nbt))
		{
			double radius = WeaponAttribute.CHAINED.getAmount(nbt);
			World world = enemy.getEntityWorld();
			List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(attacker.posX - radius, attacker.posY - radius, attacker.posZ - radius, attacker.posX + radius, attacker.posY + radius, attacker.posZ + radius));
			Iterator<EntityLivingBase> iterator = entityList.iterator();
			
			while (iterator.hasNext())
			{
                Entity entity = (Entity) iterator.next();
				
                // IF PLAYER IS THE ATTACKER
				if (entity instanceof EntityLivingBase && attacker instanceof EntityPlayer && !(entity instanceof EntityPlayer) && !(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime))
				{
					entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), (float) (damage * 0.25));
					entity.hurtResistantTime = 0;
				}
				// IF A MOB IS THE ATTACKER
				else if (entity instanceof EntityPlayer && attacker instanceof EntityMob)
				{
					entity.attackEntityFrom(DamageSource.causeMobDamage(attacker), (float) (damage * 0.25));
				}
			}
		}
		if (WeaponAttribute.VOID.hasAttribute(nbt) && Math.random() < WeaponAttribute.VOID.getAmount(nbt)) enemy.setHealth(0.00001F);
	}
}

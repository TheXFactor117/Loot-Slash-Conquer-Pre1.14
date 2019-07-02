package com.thexfactor117.lsc.loot.attributes.weapons;

import java.util.*;

import org.lwjgl.input.Keyboard;

import org.apache.logging.log4j.Logger;

import com.thexfactor117.lsc.loot.attributes.AttributeWeapon;
import com.thexfactor117.lsc.util.AttributeUtil;
import com.thexfactor117.lsc.util.misc.LSCDamageSource;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.thexfactor117.lsc.LootSlashConquer;

import static net.minecraft.util.math.MathHelper.*;


/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeChained extends AttributeWeapon
{
	public AttributeChained()
	{
		super("chained", "attributes.weapon.chained", 0.2, true, true);
	}
	
	@Override
	public void onHit(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy)
	{

		Random rand = new Random();
		double chained = this.getAttributeValue(NBTHelper.loadStackNBT(stack));
		double radius = 8 * chained;
		double random = rand.nextDouble();
		double closest = radius;

		World world = enemy.getEntityWorld();
		List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(enemy.posX - radius, enemy.posY - radius, enemy.posZ - radius, enemy.posX + radius, enemy.posY + radius, enemy.posZ + radius));
		Iterator<EntityLivingBase> iterator = entityList.iterator();

		Entity entity = enemy;

		while (random <= chained)
		{
			for(int i = 0; i < entityList.size(); i++)
			{
				double distance = Math.sqrt((entityList.get(i).posX - entity.posX) + (entityList.get(i).posY - entity.posY) + (entityList.get(i).posZ - entity.posZ));
				double origin = Math.sqrt((entityList.get(i).posX - attacker.posX) + (entityList.get(i).posY - attacker.posY) + (entityList.get(i).posZ - attacker.posZ));

				if (distance < closest && origin < radius)//If it is the closest...
				{
					if (entityList.get(i) instanceof EntityMob) //if it is a mob...
					{
						closest = distance;
						entity = entityList.get(i);
					}
				}
			}

			// IF PLAYER IS THE ATTACKER
			if (attacker instanceof EntityPlayer && entity instanceof EntityMob)
			{
				LootSlashConquer.LOGGER.info(entity);
				entity.hurtResistantTime = 0;
				entity.attackEntityFrom(LSCDamageSource.causeChainedDamage(attacker), (float) (damage * (this.getAttributeValue(NBTHelper.loadStackNBT(stack)))));
			}
			// IF A MOB IS THE ATTACKER
			else if (entity instanceof EntityPlayer && attacker instanceof EntityMob)
			{
				entity.hurtResistantTime = 0;
				entity.attackEntityFrom(LSCDamageSource.causeChainedDamage(attacker), (float) (damage * (this.getAttributeValue(NBTHelper.loadStackNBT(stack)))));
			}

			random = rand.nextDouble();
			entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius));

		}
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		Double random = rand.nextDouble();
		super.addAttribute(stack, nbt, rand);
		AttributeUtil.addPercentageAttribute(this, stack, nbt, rand, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		double value =(double) floor((this.getAttributeValue(nbt)) * 100.0);
		String tooltip = " * " + value + "% chance to chain " + value + "% of damage to enemies within 8 blocks.";
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			return this.getAttributeRarity(nbt).getColor() + tooltip;
		}
		
		return ATTRIBUTE_COLOR + tooltip;
	}
}

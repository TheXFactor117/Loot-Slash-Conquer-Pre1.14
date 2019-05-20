package com.thexfactor117.lsc.loot.attributes.weapons.bonus;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.thexfactor117.lsc.loot.attributes.AttributeBaseWeapon;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
public class AttributeChained extends AttributeBaseWeapon
{
	public AttributeChained()
	{
		super("chained", "attributes.weapon.chained", 0.2, true, true, true);
	}
	
	@Override
	public void onHit(ItemStack stack, float damage, EntityLivingBase attacker, EntityLivingBase enemy)
	{
		double radius = 8;
		World world = enemy.getEntityWorld();
		List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(attacker.posX - radius, attacker.posY - radius, attacker.posZ - radius, attacker.posX + radius, attacker.posY + radius, attacker.posZ + radius));
		Iterator<EntityLivingBase> iterator = entityList.iterator();
		
		while (iterator.hasNext())
		{
            Entity entity = (Entity) iterator.next();
			
            // IF PLAYER IS THE ATTACKER
			if (entity instanceof EntityLivingBase && attacker instanceof EntityPlayer && !(entity instanceof EntityPlayer) && !(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime))
			{
				entity.hurtResistantTime = 0;
				entity.attackEntityFrom(LSCDamageSource.causeChainedDamage(attacker), (float) (damage * this.getAttributeValue(NBTHelper.loadStackNBT(stack))));
			}
			// IF A MOB IS THE ATTACKER
			else if (entity instanceof EntityPlayer && attacker instanceof EntityMob)
			{
				entity.hurtResistantTime = 0;
				entity.attackEntityFrom(LSCDamageSource.causeChainedDamage(attacker), (float) (damage * this.getAttributeValue(NBTHelper.loadStackNBT(stack))));
			}
		}
	}
	
	@Override
	public void addAttribute(ItemStack stack, NBTTagCompound nbt, Random rand)
	{
		super.addAttribute(stack, nbt, rand);
		this.addPercentageAttribute(nbt, rand, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTooltipDisplay(NBTTagCompound nbt)
	{
		int value = (int) (this.getAttributeValue(nbt) * 100);
		
		return TextFormatting.RED + " * +" + value + "% of damage dealt to all enemies within 8 blocks.";
	}
}

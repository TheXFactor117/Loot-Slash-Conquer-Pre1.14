package com.thexfactor117.lsc.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 *
 * @author Rensik
 *
 */
public abstract class EntityRarity extends Entity
{
    public static int rarity;

    public EntityRarity(World world)
    {
        super(world);
    }

}

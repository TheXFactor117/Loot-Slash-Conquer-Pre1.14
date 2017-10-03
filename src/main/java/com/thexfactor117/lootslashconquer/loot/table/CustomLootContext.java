package com.thexfactor117.lootslashconquer.loot.table;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableManager;

/**
 * 
 * @author TheXFactor117
 *
 */
public class CustomLootContext extends LootContext
{
	private final BlockPos chestPos;

    public CustomLootContext(float luck, WorldServer world, LootTableManager manager, @Nullable Entity lootedEntity, @Nullable EntityPlayer player, @Nullable DamageSource damageSource, BlockPos chestPos)
    {
        super(luck, world, manager, lootedEntity, player, damageSource);
        this.chestPos = chestPos;
    }
    
    public BlockPos getChestPos()
    {
    	return chestPos;
    }

       public static class Builder
       {
            private final WorldServer world;
            private float luck;
            private Entity lootedEntity;
            private EntityPlayer player;
            private DamageSource damageSource;
            private BlockPos chestPos;

            public Builder(WorldServer worldIn)
            {
                this.world = worldIn;
            }

            public CustomLootContext.Builder withLuck(float luckIn)
            {
                this.luck = luckIn;
                return this;
            }

            public CustomLootContext.Builder withLootedEntity(Entity entityIn)
            {
                this.lootedEntity = entityIn;
                return this;
            }

            public CustomLootContext.Builder withPlayer(EntityPlayer playerIn)
            {
                this.player = playerIn;
                return this;
            }

            public CustomLootContext.Builder withDamageSource(DamageSource dmgSource)
            {
                this.damageSource = dmgSource;
                return this;
            }
            
            public CustomLootContext.Builder withChestPos(BlockPos pos)
            {
            	this.chestPos = pos;
            	return this;
            }

            public CustomLootContext build()
            {
                return new CustomLootContext(this.luck, this.world, this.world.getLootTableManager(), this.lootedEntity, this.player, this.damageSource, this.chestPos);
            }
       }
}

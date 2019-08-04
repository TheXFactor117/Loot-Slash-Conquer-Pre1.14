package com.thexfactor117.lsc.events;

import java.util.UUID;

import com.thexfactor117.lsc.LootSlashConquer;
import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.thexfactor117.lsc.capabilities.implementation.EnemyInfo;
import com.thexfactor117.lsc.config.Configs;
import com.thexfactor117.lsc.entities.EnemyTier;
import com.thexfactor117.lsc.network.client.PacketUpdateEnemyInfo;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
@Mod.EventBusSubscriber
public class EventPlayerStartTracking 
{
	private static final UUID ATTACK_DAMAGE = UUID.fromString("708b7d5f-9e4d-4bb5-9bdc-437ebcd0fb52");
	private static final UUID MAX_HEALTH = UUID.fromString("136ed593-8c70-4ba8-98e9-42c93e64fff0");
	
	@SubscribeEvent
	public static void onPlayerStartTracking(PlayerEvent.StartTracking event)
	{	
		if (!event.getEntityPlayer().world.isRemote)
		{				
			//if (event.getTarget() instanceof EntityMob || event.getTarget() instanceof EntityAnimal || event.getTarget() instanceof EntityGhast || event.getTarget() instanceof EntityVillager)
			//{
				Entity entity = (Entity) event.getTarget();
				
				World world = entity.getEntityWorld();
				EnemyInfo info = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
				IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
					
				if (info != null && chunkLevelHolder != null)
				{
					if (info.getEnemyLevel() == 0 || info.getEnemyTier() == 0)
					{
						IChunkLevel chunkLevelCap = chunkLevelHolder.getChunkLevel(new ChunkPos(entity.getPosition()));
						int chunkLevel = chunkLevelCap.getChunkLevel();
						int level = info.getRandomEnemyLevel(chunkLevel, chunkLevel + Configs.monsterLevelTierCategory.levelSpawnRange);
							
						info.setEnemyTier(EnemyTier.getRandomEnemyTier(world.rand).ordinal());
						info.setEnemyLevel(level);

						LootSlashConquer.LOGGER.info(entity);
						setAttributeModifiers(entity, level, info.getEnemyTier());
					}
						
					// send information to clients
					LootSlashConquer.network.sendTo(new PacketUpdateEnemyInfo(info, entity.getEntityId()), (EntityPlayerMP) event.getEntityPlayer());
				}
			//}
		}
	}

	
	private static void setAttributeModifiers(Entity entity, int level, int tier)
	{
	    if (entity instanceof EntityMob) {

            if (((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(ATTACK_DAMAGE) != null) {
                double baseDamage = ((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
                double damageMultiplier = (Math.pow(Configs.monsterLevelTierCategory.damageBaseFactor, level) * (baseDamage + (Math.pow(tier, Configs.monsterLevelTierCategory.damageTierPower))));
                AttributeModifier attackDamage = new AttributeModifier(ATTACK_DAMAGE, "attackDamage", damageMultiplier, 0);

                if (!((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(attackDamage))
                    ((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attackDamage);
            }
            if (((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(MAX_HEALTH) != null) {
                double baseHealth = ((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
                double healthMultiplier = (Math.pow(Configs.monsterLevelTierCategory.healthBaseFactor, level) * (baseHealth + (Math.pow(tier, Configs.monsterLevelTierCategory.healthTierPower))) - (baseHealth * 2));
                AttributeModifier maxHealth = new AttributeModifier(MAX_HEALTH, "maxHealth", healthMultiplier, 0);

                if (!((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(maxHealth)) {
                    ((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(maxHealth);
                }
                ((EntityMob) entity).setHealth(((EntityMob) entity).getMaxHealth());
            }
        }
        if (entity instanceof EntityGhast)
        {
            if (((EntityGhast) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(MAX_HEALTH) != null) {
                double baseHealth = ((EntityGhast) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
                double healthMultiplier = (Math.pow(Configs.monsterLevelTierCategory.healthBaseFactor, level) * (baseHealth + (Math.pow(tier, Configs.monsterLevelTierCategory.healthTierPower))) - (baseHealth * 2));
                AttributeModifier maxHealth = new AttributeModifier(MAX_HEALTH, "maxHealth", healthMultiplier, 0);

                if (!((EntityGhast) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(maxHealth)) {
                    ((EntityGhast) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(maxHealth);
                }
                ((EntityGhast) entity).setHealth(((EntityGhast) entity).getMaxHealth());
            }
        }
        if (entity instanceof EntityAnimal)
        {
            if (((EntityAnimal) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(MAX_HEALTH) != null) {
                double baseHealth = ((EntityAnimal) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
                double healthMultiplier = (Math.pow(Configs.monsterLevelTierCategory.healthBaseFactor, level) * (baseHealth + (Math.pow(tier, Configs.monsterLevelTierCategory.healthTierPower))) - (baseHealth * 2));
                AttributeModifier maxHealth = new AttributeModifier(MAX_HEALTH, "maxHealth", healthMultiplier, 0);

                if (!((EntityAnimal) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(maxHealth)) {
                    ((EntityAnimal) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(maxHealth);
                }
                ((EntityAnimal) entity).setHealth(((EntityAnimal) entity).getMaxHealth());
            }
        }
	}

}

package com.lsc.events;

import java.util.UUID;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.capabilities.enemylevel.CapabilityEnemyLevel;
import com.lsc.capabilities.enemylevel.IEnemyLevel;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EventEntityJoinWorld 
{
	private static final UUID ATTACK_DAMAGE = UUID.fromString("708b7d5f-9e4d-4bb5-9bdc-437ebcd0fb52");
	private static final UUID MAX_HEALTH = UUID.fromString("136ed593-8c70-4ba8-98e9-42c93e64fff0");
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityLivingBase && !(event.getEntity() instanceof EntityPlayer))
		{
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			World world = entity.getEntityWorld();
			IEnemyLevel enemyLevelCap = entity.getCapability(CapabilityEnemyLevel.ENEMY_LEVEL, null);
			IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
			
			if (enemyLevelCap != null && chunkLevelHolder != null)
			{
				if (enemyLevelCap.getEnemyLevel() == 0)
				{
					IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(new ChunkPos(entity.getPosition()));
					int level = chunkLevel.getChunkLevel();
					
					enemyLevelCap.setEnemyLevel(level);
					entity.setCustomNameTag("Level: " + enemyLevelCap.getEnemyLevel());
					
					LootSlashConquer.LOGGER.info("Side?");
					
					if (level > 1) setAttributeModifiers(entity, level);
				}
			}
		}
	}
	
	public static void setAttributeModifiers(EntityLivingBase entity, int level)
	{
		AttributeModifier attackDamage = new AttributeModifier(ATTACK_DAMAGE, "attackDamage", level * 0.1, 1);
		AttributeModifier maxHealth = new AttributeModifier(MAX_HEALTH, "maxHealth", level * 0.2, 1);
		
		if (!entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(attackDamage))
			entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attackDamage);
		
		if (!entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(maxHealth))
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(maxHealth);
			entity.setHealth(entity.getMaxHealth());
		}
	}
}

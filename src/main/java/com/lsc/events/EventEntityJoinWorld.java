package com.lsc.events;

import java.util.UUID;

import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.capabilities.enemyinfo.CapabilityEnemyInfo;
import com.lsc.capabilities.enemyinfo.EnemyInfo;
import com.lsc.entities.EnemyTier;
import com.lsc.entities.EntityMonster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.EnumDifficulty;
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
		if (event.getWorld().getDifficulty() == EnumDifficulty.PEACEFUL && event.getEntity() instanceof EntityMonster)
		{
			event.setCanceled(true);
		}
		
		if (event.getEntity() instanceof EntityLivingBase && !(event.getEntity() instanceof EntityPlayer))
		{	
			EntityLivingBase entity = (EntityLivingBase) event.getEntity();
			World world = entity.getEntityWorld();
			EnemyInfo info = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
			IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
			
			if (info != null && chunkLevelHolder != null)
			{
				if (info.getEnemyLevel() == 0)
				{
					IChunkLevel chunkLevelCap = chunkLevelHolder.getChunkLevel(new ChunkPos(entity.getPosition()));
					int chunkLevel = chunkLevelCap.getChunkLevel();
					int level = info.getRandomEnemyLevel(chunkLevel, chunkLevel + 3);
					
					info.setEnemyTier(EnemyTier.getRandomEnemyTier(world.rand).ordinal());
					info.setEnemyLevel(level);
					
					setAttributeModifiers(entity, level, info.getEnemyTier());
				}
			}
		}
	}
	
	public static void setAttributeModifiers(EntityLivingBase entity, int level, int tier)
	{
		AttributeModifier attackDamage = new AttributeModifier(ATTACK_DAMAGE, "attackDamage", (level * (tier * 2)), 1);
		AttributeModifier maxHealth = new AttributeModifier(MAX_HEALTH, "maxHealth", (level * (tier * 2)) * 0.2, 1);
		
		if (!entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(attackDamage))
			entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attackDamage);
		
		if (!entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(maxHealth))
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(maxHealth);
			entity.setHealth(entity.getMaxHealth());
		}
	}
}

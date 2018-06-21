package com.lsc.events;

import java.util.UUID;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.api.IChunkLevel;
import com.lsc.capabilities.api.IChunkLevelHolder;
import com.lsc.capabilities.cap.CapabilityChunkLevel;
import com.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.lsc.capabilities.implementation.EnemyInfo;
import com.lsc.entities.EnemyTier;
import com.lsc.network.PacketUpdateEnemyInfo;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
			if (event.getTarget() instanceof EntityLivingBase)
			{	
				EntityLivingBase entity = (EntityLivingBase) event.getTarget();
				World world = entity.getEntityWorld();
				EnemyInfo info = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
				IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
				
				if (info != null && chunkLevelHolder != null)
				{
					if (info.getEnemyLevel() == 0 || info.getEnemyTier() == 0)
					{
						IChunkLevel chunkLevelCap = chunkLevelHolder.getChunkLevel(new ChunkPos(entity.getPosition()));
						int chunkLevel = chunkLevelCap.getChunkLevel();
						int level = info.getRandomEnemyLevel(chunkLevel, chunkLevel + 3);
						
						info.setEnemyTier(EnemyTier.getRandomEnemyTier(world.rand).ordinal());
						info.setEnemyLevel(level);
						
						setAttributeModifiers(entity, level, info.getEnemyTier());
					}
					
					// send information to clients
					LootSlashConquer.network.sendTo(new PacketUpdateEnemyInfo(info, entity.getEntityId()), (EntityPlayerMP) event.getEntityPlayer());
				}
			}
		}
	}
	
	private static void setAttributeModifiers(EntityLivingBase entity, int level, int tier)
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

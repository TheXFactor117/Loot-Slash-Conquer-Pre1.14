package thexfactor117.lsc.events;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thexfactor117.lsc.LootSlashConquer;
import thexfactor117.lsc.capabilities.api.IChunkLevel;
import thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import thexfactor117.lsc.capabilities.cap.CapabilityEnemyInfo;
import thexfactor117.lsc.capabilities.implementation.EnemyInfo;
import thexfactor117.lsc.config.Configs;
import thexfactor117.lsc.entities.EnemyTier;
import thexfactor117.lsc.network.PacketUpdateEnemyInfo;

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
				
				if (entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				{
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
							
							setAttributeModifiers(entity, level, info.getEnemyTier());
						}
						
						// send information to clients
						LootSlashConquer.network.sendTo(new PacketUpdateEnemyInfo(info, entity.getEntityId()), (EntityPlayerMP) event.getEntityPlayer());
					}
				}
			}
		}
	}
	
	private static void setAttributeModifiers(EntityLivingBase entity, int level, int tier)
	{
		double baseDamage = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
		double baseHealth = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
		double damageMultiplier = (Math.pow(Configs.monsterLevelTierCategory.damageBaseFactor, level) * (baseDamage + (Math.pow(tier, Configs.monsterLevelTierCategory.damageTierPower))) - (baseDamage * 2));
		double healthMultiplier = (Math.pow(Configs.monsterLevelTierCategory.healthBaseFactor, level) * (baseHealth + (Math.pow(tier, Configs.monsterLevelTierCategory.healthTierPower))) - (baseHealth * 2));
		
		AttributeModifier attackDamage = new AttributeModifier(ATTACK_DAMAGE, "attackDamage", damageMultiplier, 0);
		AttributeModifier maxHealth = new AttributeModifier(MAX_HEALTH, "maxHealth", healthMultiplier, 0);
		
		if (!entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(attackDamage))
			entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attackDamage);
		
		if (!entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(maxHealth))
		{
			entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(maxHealth);
			entity.setHealth(entity.getMaxHealth());
		}
	}
}

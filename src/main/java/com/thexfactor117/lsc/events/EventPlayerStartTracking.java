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

import ibxm.Player;
import net.minecraft.entity.*;
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
public class EventPlayerStartTracking {
	private static final UUID ATTACK_DAMAGE = UUID.fromString("708b7d5f-9e4d-4bb5-9bdc-437ebcd0fb52");
	private static final UUID MAX_HEALTH = UUID.fromString("136ed593-8c70-4ba8-98e9-42c93e64fff0");

	@SubscribeEvent
	public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
		if (!event.getEntityPlayer().world.isRemote) {

			if (event.getTarget() instanceof EntityLivingBase && event.getTarget() instanceof EntityPlayerMP)
			{

			Entity entity = event.getTarget();

			World world = entity.getEntityWorld();
			EnemyInfo info = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
			IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);

			if (info != null && chunkLevelHolder != null) {
				if (info.getEnemyLevel() == 0 || info.getEnemyTier() == 0) {
					IChunkLevel chunkLevelCap = chunkLevelHolder.getChunkLevel(new ChunkPos(entity.getPosition()));
					int chunkLevel = chunkLevelCap.getChunkLevel();
					int level = info.getRandomEnemyLevel(chunkLevel, chunkLevel + Configs.monsterLevelTierCategory.levelSpawnRange);

					info.setEnemyTier(EnemyTier.getRandomEnemyTier(world.rand).ordinal());
					info.setEnemyLevel(level);

					//LootSlashConquer.LOGGER.info(entity);
					setAttributeModifiers((EntityLivingBase) entity, level, info.getEnemyTier());
				}

				// send information to clients
				LootSlashConquer.network.sendTo(new PacketUpdateEnemyInfo(info, entity.getEntityId()), (EntityPlayerMP) event.getEntityPlayer());
			}
			}
		}
	}


	private static void setAttributeModifiers(EntityLivingBase entity, int level, int tier) {
		if (entity instanceof EntityLivingBase) {

			if (!(entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)) {
				double baseDamage = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
				double damageMultiplier = Configs.monsterLevelTierCategory.damageBaseFactor * baseDamage * level * tier;

				AttributeModifier attackDamage = new AttributeModifier(ATTACK_DAMAGE, "attackDamage", damageMultiplier, 0);

				if (!(entity).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(attackDamage))
					(entity).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attackDamage);
			}
			if (entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) != null) {

				double baseHealth = (entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
				double healthMultiplier = Configs.monsterLevelTierCategory.healthBaseFactor * baseHealth * level * tier;

				AttributeModifier maxHealth = new AttributeModifier(MAX_HEALTH, "maxHealth", healthMultiplier, 0);

				if (!(entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(maxHealth)) {
					(entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(maxHealth);
				}

				(entity).setHealth((entity).getMaxHealth());
			}
			if (entity.getEntityAttribute(SharedMonsterAttributes.ARMOR) != null) {

				double baseArmor = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).getBaseValue();
				double armorMultiplier = Configs.monsterLevelTierCategory.healthBaseFactor * baseArmor * tier;

				entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(armorMultiplier);
			}

			if (entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS) != null) {

				double baseToughness = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getBaseValue();
				double toughnessMultiplier = baseToughness * tier * Math.random() + 1;

				entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(toughnessMultiplier);
			}
		}
	}
}

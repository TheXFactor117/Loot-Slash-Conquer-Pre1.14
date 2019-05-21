package com.thexfactor117.lsc.config;

import com.thexfactor117.lsc.util.misc.Reference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;

/**
 *
 * @author TheXFactor117
 *
 */
@Config(modid = Reference.MODID, name = Reference.NAME, category = "Main")
public class Configs
{	
	// sub-categories
	@Name("World Generation")
	public static WorldGenCategory worldgenCategory = new WorldGenCategory();
	@Name("Main Monster Information")
	public static MainMonsterCategory mainMonsterCategory = new MainMonsterCategory();
	@Name("Monster Stats")
	public static MonsterStatsCategory monsterStatsCategory = new MonsterStatsCategory();
	@Name("Monster Leveling and Tiers")
	public static MonsterLevelTierCategory monsterLevelTierCategory = new MonsterLevelTierCategory();
	@Name("Player Information")
	public static PlayerCategory playerCategory = new PlayerCategory();
	@Name("Weapon Information")
	public static WeaponCategory weaponCategory = new WeaponCategory();
	@Name("Rendering Information")
	public static RenderingCategory renderingCategory = new RenderingCategory();
	
	// main configuration options
	
	@Comment({
		"Sets the amount of ticks per stat update.",
		"Only change this if you are experiencing bad lag. This may not help but potentially could.",
		"10 ticks will run twice per second. If you are experiencing lag, try 20 ticks."
	})
	@RangeInt(min = 5)
	public static int ticksPerStatUpdate = 10;
	
	@Comment("If true, LSC items will generate from Vanilla loot sources, including entities and chests.")
	public static boolean enableVanillaLoot = true;
	
	@Comment("If true, LSC will damage equipment you use if it is higher than your player level.")
	public static boolean damageHighLeveledEquipment = true;
	
	/*
	 * CATEGORIES
	 */
	
	public static class WorldGenCategory
	{
		@Comment("If false, ALL LSC world generation will be cancelled.")
		public boolean enableLSCWorldGen = true;
		
		@Comment("If false, ALL Boss Structures world generation will be cancelled.")
		public boolean enableBossStructures = true;
		
		@Comment("If false, ALL Tower Dungeons world generation will be cancelled.")
		public boolean enableTowerGeneration = true;
		
		@Comment("Sets the max level an area can be. Avoid using high numbers because it can cause issues. Test carefully.")
		public int maxAreaLevel = 100;
		
		@Comment({
			"Sets the amount of chunks needed to increase the Area Level.",
			"For example, by default, Area Level will increase every 10 chunks",
			"away from spawn."
		})
		@RangeInt(min = 1)
		public int chunksPerAreaLevel = 10;
		
		@Comment("Sets the starting Area Level in the Overworld.")
		@RangeInt(min = 1)
		public int overworldStartingAreaLevel = 1;
		
		@Comment("Sets the starting Area Level in the Nether.")
		@RangeInt(min = 1)
		public int netherStartingAreaLevel = 15;
		
		@Comment("Sets the starting Area Level in the End")
		@RangeInt(min = 1)
		public int endStartingAreaLevel = 35;
		
		@Comment("Sets the starting Area Level for modded dimensions. All modded dimensions will use this value.")
		@RangeInt(min = 1)
		public int defaultDimensionAreaLevel = 15;
		
		@Comment({
			"Set this to true if you want the Overworld to have a static Area Level (won't change).",
			"This will use the starting Area Level for the corresponding dimension as the static level."
		})
		public boolean overworldStaticAreaLevel = false;
		
		@Comment({
			"Set this to true if you want the Nether to have a static Area Level (won't change).",
			"This will use the starting Area Level for the corresponding dimension as the static level."
		})
		public boolean netherStaticAreaLevel = false;
		
		@Comment({
			"Set this to true if you want the End to have a static Area Level (won't change).",
			"This will use the starting Area Level for the corresponding dimension as the static level."
		})
		public boolean endStaticAreaLevel = false;
		
		@Comment({
			"Set this to true if you want modded dimensions to have a static Area Level (won't change).",
			"This will use the starting Area Level for the corresponding dimension as the static level."
		})
		public boolean moddedDimensionsStaticAreaLevel = false;
		
		// towers
		@Comment({
			"Controls the frequency of Tower Dungeons.",
			"This number gets multiplied by a random number from 0-1. Casted to an int, if it equals 0, spawning will proceed."
		})
		public int towerSpawnRate = 300;
		
		@Comment({
			"Sets the minimum height of Tower Dungeons.",
			"BE CAREFUL WITH THE NUMBERS YOU PUT IN. I'm not going to check to see if it can spawn within the chunk limits (0-255)",
			"so if something crashes/breaks because this is too high, that's on you.",
			"These will spawn around Y-65 for reference."
		})
		public int towerMinHeight = 5;
		
		@Comment({
			"Sets the randomized additional (0-value) height of Tower Dungeons.",
			"BE CAREFUL WITH THE NUMBERS YOU PUT IN. I'm not going to check to see if it can spawn within the chunk limits (0-255)",
			"so if something crashes/breaks because this is too high, that's on you.",
			"These will spawn around Y-65 for reference."
		})
		public int towerAdditionalHeight = 5;
		
		@Comment({
			"Sets the minimum depth of Tower Dungeons.",
			"BE CAREFUL WITH THE NUMBERS YOU PUT IN. I'm not going to check to see if it can spawn within the chunk limits (0-255)",
			"so if something crashes/breaks because this is too high, that's on you.",
			"These will spawn around Y-65 for reference."
		})
		public int towerMinDepth = 3;
		
		@Comment({
			"Sets the randomized additional (0-value) depth of Tower Dungeons.",
			"BE CAREFUL WITH THE NUMBERS YOU PUT IN. I'm not going to check to see if it can spawn within the chunk limits (0-255)",
			"so if something crashes/breaks because this is too high, that's on you.",
			"These will spawn around Y-65 for reference."
		})
		public int towerAdditionalDepth = 3;
		
		@Comment({
			"Gives chests a chance of spawning on normal floors.",
			"This number is multiplied by a random number between 0-1. If it equals 0, it will spawn.",
			"Common numbers: 0 and 1 = 100%, 2 = 50%, 3 = 33%, 4 = 25%, etc..."
		})
		public int towerNormalChestChance = 4;
		
		@Comment({
			"Gives spawners a chance of spawning on normal floors.",
			"This number is multiplied by a random number between 0-1. If it equals 0, it will spawn.",
			"Common numbers: 0 and 1 = 100%, 2 = 50%, 3 = 33%, 4 = 25%, etc..."
		})
		public int towerNormalSpawnerChance = 0;
		
		@Comment({
			"Gives chests a chance of spawning in the Treasure Room only.",
			"This number is multiplied by a random number between 0-1. If it equals 0, it will spawn.",
			"Common numbers: 0 and 1 = 100%, 2 = 50%, 3 = 33%, 4 = 25%, etc..."
		})
		public int towerTreasureChestChance = 2;
		
		// boss structures
		@Comment({
			"Controls the frequency of Corrupted Towers.",
			"This number gets multiplied by a random number from 0-1. Casted to an int, if it equals 0, spawning will proceed.",
			"NOTE: this structure only spawns in Plains/Desert biomes. Higher numbers will exponentially increase rarity."
		})
		public int corruptedTowerSpawnRate = 300;
		
		@Comment("Sets the max amount of Corrupted Towers which spawn in the world.")
		public int maxCorruptedTowers = 3;
		
		@Comment({
			"Gives chests a chance of spawning.",
			"This number is multiplied by a random number between 0-1. If it equals 0, it will spawn.",
			"Common numbers: 0 and 1 = 100%, 2 = 50%, 3 = 33%, 4 = 25%, etc..."
		})
		public int corruptedTowerChestChance = 2;
		
		@Comment({
			"Gives spawners a chance of spawning.",
			"This number is multiplied by a random number between 0-1. If it equals 0, it will spawn.",
			"Common numbers: 0 and 1 = 100%, 2 = 50%, 3 = 33%, 4 = 25%, etc..."
		})
		public int corruptedTowerSpawnerChance = 0;
	}
	
	public static class MainMonsterCategory
	{
		@Comment({
			"Sets the Barbarian spawn weight.",
			"Vanilla monsters have weights around 90-100, with rare ones being down to 5-10"
		})
		public int barbarianSpawnWeight = 100;
		
		@Comment({
			"Sets the Ghost spawn weight.",
			"Vanilla monsters have weights around 90-100, with rare ones being down to 5-10"
		})
		public int ghostSpawnWeight = 80;
		
		@Comment({
			"Sets the Mummy spawn weight.",
			"Vanilla monsters have weights around 90-100, with rare ones being down to 5-10"
		})
		public int mummySpawnWeight = 90;
		
		@Comment({
			"Sets the Banshee spawn weight.",
			"Vanilla monsters have weights around 90-100, with rare ones being down to 5-10"
		})
		public int bansheeSpawnWeight = 50;
		
		@Comment({
			"Sets the Barbarian spawn weight.",
			"Vanilla monsters have weights around 90-100, with rare ones being down to 5-10"
		})
		public int golemSpawnWeight = 10;
	}
	
	public static class MonsterStatsCategory
	{
		/*
		 * MONSTERS
		 */
		
		// Barbarian
		@Comment("Set the max health of the Barbarian.")
		@RangeDouble(min = 1)
		public double barbarianMaxHealth = 30;
		
		@Comment("Set the damage of the Barbarian.")
		@RangeDouble(min = 1)
		public double barbarianDamage = 5;
		
		@Comment("Set the armor rating of the Barbarian.")
		@RangeDouble(min = 0)
		public double barbarianArmor = 4;
		
		@Comment("Set the movement speed of the Barbarian.")
		@RangeDouble(min = 0.001)
		public double barbarianMovementSpeed = 0.3;
		
		@Comment("Set the follow range of the Barbarian.")
		@RangeDouble(min = 1, max = 64)
		public double barbarianFollowRange = 50;
		
		@Comment("Set the knockback resistance of the Barbarian.")
		@RangeDouble(min = 0, max = 1)
		public double barbarianKnockbackResistance = 0;
		
		// Ghost
		@Comment("Set the max health of the Ghost.")
		@RangeDouble(min = 1)
		public double ghostMaxHealth = 20;
		
		@Comment("Set the damage of the Ghost.")
		@RangeDouble(min = 1)
		public double ghostDamage = 8;
		
		@Comment("Set the armor rating of the Ghost.")
		@RangeDouble(min = 0)
		public double ghostArmor = 0;
		
		@Comment("Set the movement speed of the Ghost.")
		@RangeDouble(min = 0.001)
		public double ghostMovementSpeed = 0.22;
		
		@Comment("Set the follow range of the Ghost.")
		@RangeDouble(min = 1, max = 64)
		public double ghostFollowRange = 30;
		
		@Comment("Set the knockback resistance of the Ghost.")
		@RangeDouble(min = 0, max = 1)
		public double ghostKnockbackResistance = 0;
		
		// Mummy
		@Comment("Set the max health of the Mummy.")
		@RangeDouble(min = 1)
		public double mummyMaxHealth = 25;
		
		@Comment("Set the damage of the Mummy.")
		@RangeDouble(min = 1)
		public double mummyDamage = 6;
		
		@Comment("Set the armor rating of the Mummy.")
		@RangeDouble(min = 0)
		public double mummyArmor = 0;
		
		@Comment("Set the movement speed of the Mummy.")
		@RangeDouble(min = 0.001)
		public double mummyMovementSpeed = 0.25;
		
		@Comment("Set the follow range of the Mummy.")
		@RangeDouble(min = 1, max = 64)
		public double mummyFollowRange = 40;
		
		@Comment("Set the knockback resistance of the Mummy.")
		@RangeDouble(min = 0, max = 1)
		public double mummyKnockbackResistance = 0;
		
		// Bandit
		@Comment("Set the max health of the Bandit.")
		@RangeDouble(min = 1)
		public double banditMaxHealth = 40;
		
		@Comment("Set the damage of the Bandit.")
		@RangeDouble(min = 1)
		public double banditDamage = 8;
		
		@Comment("Set the armor rating of the Bandit.")
		@RangeDouble(min = 0)
		public double banditArmor = 4;
		
		@Comment("Set the movement speed of the Bandit.")
		@RangeDouble(min = 0.001)
		public double banditMovementSpeed = 0.3;
		
		@Comment("Set the follow range of the Bandit.")
		@RangeDouble(min = 1, max = 64)
		public double banditFollowRange = 50;
		
		@Comment("Set the knockback resistance of the Bandit.")
		@RangeDouble(min = 0, max = 1)
		public double banditKnockbackResistance = 0;
		
		// Banshee
		@Comment("Set the max health of the Banshee.")
		@RangeDouble(min = 1)
		public double bansheeMaxHealth = 30;
		
		@Comment("Set the damage of the Banshee.")
		@RangeDouble(min = 1)
		public double bansheeDamage = 12;
		
		@Comment("Set the armor rating of the Banshee.")
		@RangeDouble(min = 0)
		public double bansheeArmor = 0;
		
		@Comment("Set the movement speed of the Banshee.")
		@RangeDouble(min = 0.001)
		public double bansheeMovementSpeed = 0.25;
		
		@Comment("Set the follow range of the Banshee.")
		@RangeDouble(min = 1, max = 64)
		public double bansheeFollowRange = 40;
		
		@Comment("Set the knockback resistance of the Banshee.")
		@RangeDouble(min = 0, max = 1)
		public double bansheeKnockbackResistance = 0;
		
		// Golem
		@Comment("Set the max health of the Golem.")
		@RangeDouble(min = 1)
		public double golemMaxHealth = 75;
		
		@Comment("Set the damage of the Golem.")
		@RangeDouble(min = 1)
		public double golemDamage = 20;
		
		@Comment("Set the armor rating of the Golem.")
		@RangeDouble(min = 0)
		public double golemArmor = 10;
		
		@Comment("Set the movement speed of the Golem.")
		@RangeDouble(min = 0.001)
		public double golemMovementSpeed = 0.2;
		
		@Comment("Set the follow range of the Golem.")
		@RangeDouble(min = 1, max = 64)
		public double golemFollowRange = 16;
		
		@Comment("Set the knockback resistance of the Golem.")
		@RangeDouble(min = 0, max = 1)
		public double golemKnockbackResistance = 0.5;
		
		// Spectral Knight
		@Comment("Set the max health of the Spectral Knight.")
		@RangeDouble(min = 1)
		public double spectralKnightMaxHealth = 20;
		
		@Comment("Set the damage of the Spectral Knight.")
		@RangeDouble(min = 1)
		public double spectralKnightDamage = 30;
		
		@Comment("Set the armor rating of the Spectral Knight.")
		@RangeDouble(min = 0)
		public double spectralKnightArmor = 2;
		
		@Comment("Set the movement speed of the Spectral Knight.")
		@RangeDouble(min = 0.001)
		public double spectralKnightMovementSpeed = 0.35;
		
		@Comment("Set the follow range of the Spectral Knight.")
		@RangeDouble(min = 1, max = 64)
		public double spectralKnightFollowRange = 24;
		
		@Comment("Set the knockback resistance of the Spectral Knight.")
		@RangeDouble(min = 0, max = 1)
		public double spectralKnightKnockbackResistance = 0;
		
		/*
		 * BOSSES
		 */
		
		// Corrupted Knight
		@Comment("Set the max health of the Corrupted Knight Boss.")
		@RangeDouble(min = 1)
		public double corruptedKnightHealth = 300;
		
		@Comment("Set the damage of the Corrupted Knight Boss.")
		@RangeDouble(min = 1)
		public double corruptedKnightDamage = 40;
		
		@Comment("Set the armor rating of the Corrupted Knight Boss.")
		@RangeDouble(min = 0)
		public double corruptedKnightArmor = 10;
		
		@Comment("Set the movement speed of the Corrupted Knight Boss.")
		@RangeDouble(min = 0.001)
		public double corruptedKnightMovementSpeed = 0.3;
		
		@Comment("Set the follow range of the Corrupted Knight Boss.")
		@RangeDouble(min = 1, max = 64)
		public double corruptedKnightFollowRange = 32;
		
		@Comment("Set the knockback resistance of the Corrupted Knight Boss.")
		@RangeDouble(min = 0, max = 1)
		public double corruptedKnightKnockbackResistance = 0.1;
	}
	
	public static class MonsterLevelTierCategory
	{
		// tier spawn chances
		@Comment({
			"The chance at which Normal monsters will spawn.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double normalChance = 62;
		
		@Comment({
			"The chance at which Hardened monsters will spawn.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double hardenedChance = 20;
		
		@Comment({
			"The chance at which Superior monsters will spawn.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double superiorChance = 10;
		
		@Comment({
			"The chance at which Elite monsters will spawn.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double eliteChance = 6;
		
		@Comment({
			"The chance at which Legendary monsters will spawn.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double legendaryChance = 2;
		
		// monster scaling
		@Comment({
			"Only change this if you know how the algorithm works. Small changes can screw things up.",
			"Sets the base factor for the damage scaling algorithm.",
			"If you change this, make it small changes. 1.1 is a SIGNFICANT change, for example."
		})
		public double damageBaseFactor = 1.06;
		
		@Comment({
			"Only change this if you know how the algorithm works. Small changes can screw things up.",
			"Sets the tier power for the damage scaling algorithm.",
			"This influences how much effect the Tier has on the scaling. A bigger power = bigger difference."
		})
		public double damageTierPower = 1.3;
		
		@Comment({
			"Only change this if you know how the algorithm works. Small changes can screw things up.",
			"Sets the base factor for the health scaling algorithm.",
			"If you change this, make it small changes. 1.14 is a SIGNFICANT change, for example."
		})
		public double healthBaseFactor = 1.13;
		
		@Comment({
			"Only change this if you know how the algorithm works. Small changes can screw things up.",
			"Sets the tier power for the health scaling algorithm.",
			"This influences how much effect the Tier has on the scaling. A bigger power = bigger difference."
		})
		public double healthTierPower = 2.25;
		
		// monster experience
		@Comment({
			"Only change this if you know what you are doing.",
			"This number gets raised to the level+1 power."
		})
		public double experienceBaseFactor = 1.15;
		
		@Comment({
			"Only change this if you know what you are doing.",
			"This number influences the experience dropped difference between the different tiers. Bigger number = bigger difference."
		})
		public double experienceTierPower = 2.25;
		
		@Comment({
			"Only change this if you know what you are doing.",
			"This number influences the experience dropped difference between the different rarities. Bigger number = bigger difference."
		})
		public double experienceRarityPower = 1.75;
		
		@Comment({
			"Only change this if you know what you are doing.",
			"Divides the outcome of tier^tierPower. Recommended for people who understand the algorithm ENTIRELY."
		})
		public double experienceTierDivisor = 3;
		
		@Comment({
			"Only change this if you know what you are doing.",
			"Divides the outcome of rarity^rarityPower. Recommended for people who understand the algorithm ENTIRELY."
		})
		public double experienceRarityDivisor = 2.5;
		
		@Comment({
			"Only change this if you know what you are doing.",
			"Divides the ENTIRE experience amount by this number. Recommended for people who understand the algorithm ENTIRELY."
		})
		public double experienceDivisor = 1.5;
		
		@Comment({
			"Base amount of experience for ALL monsters.",
			"Placeholder number for now - could change in the future, or become permanent."
		})
		public double baseExperience = 10;
		
		// misc
		@Comment({
			"Defines the level range a monster can spawn at.",
			"If the Area Level is 4, the monster level will be a random",
			"number between 4-7 if the default value is used."
		})
		@RangeInt(min = 0)
		public int levelSpawnRange = 3;
		
		
	}
	
	public static class PlayerCategory
	{
		// experience
		@Comment({
			"Only change this if you know how the algorithm works. Small changes can screw things up.",
			"Sets the power of the level up algorithm for player experience."
		})
		@RangeDouble(min = 0)
		public double levelUpExpPower = 3.52;
		
		@Comment("If true, the player will gain Bonus skill points every 5 and 10 levels, in addition to every level.")
		public boolean useTieredSkillPointDistribution = true;
		
		@Comment("The amount of skill points the player will get every level.")
		public int skillPointsPerLevel = 1;
		
		@Comment("If enabled, the amount of skill points the player will get every 5 levels. Doesn't stack with skillPointsPerLevel.")
		public int skillPointsPer5Levels = 3;
		
		@Comment("If enabled, the amount of skill points the player will get every 10 levels. Doesn't stack with skillPointsPerLevel")
		public int skillPointsPer10Levels = 5;
		
		@Comment("If true, level up particles will spawn around the player.")
		public boolean spawnLevelUpParticles = true;
		
		@Comment({
			"Only change this if you know how the algorithm works. Small changes can screw things up.",
			"Sets the additive to be added to the level up algorithm for player experience."
		})
		@RangeInt(min = 0)
		public int levelUpAdditive = 250;
		
		// player characteristics
		@Comment("Defines the starting Max Mana for players.")
		@RangeInt(min = 1)
		public int maxMana = 100;
		
		@Comment("Defines the starting MP5 for players.")
		@RangeInt(min = 1)
		public int manaPer5 = 10;
		
		@Comment("Definies the starting HP5 for players.")
		@RangeInt(min = 1)
		public int healthPer5 = 1;
		
		// player stats
		@Comment("This amount gets added to the player's movement speed for every Agility point.")
		public double movementSpeedMultiplier = 0.001;
		
		@Comment("This amount gets added to the player's attack speed for every Dexterity point.")
		public double attackSpeedMultiplier = 0.03;
		
		@Comment("This amount gets added to the player's critical chance for every Dexterity point.")
		public double critChanceMultiplier = 0.05;
		
		@Comment("This amount gets added to the player's critical damage for every Dexterity point.")
		public double critDamageMultiplier = 0.05;
		
		@Comment("This amount gets added to the player's max health for every Fortitude point.")
		public double maxHealthMultiplier = 2;
		
		@Comment("This amount gets added to the player's max mana for every Wisdom point.")
		public double maxManaMultiplier = 2;
	}
	
	public static class WeaponCategory
	{
		@Comment("A list of active weapon attributes. Remove a value to remove the attribute from generating on items.")
		public String[] weaponAttributes = new String[] { "fire_damage", "frost_damage", "lightning_damage", "poison_damage", "attack_speed", "bonus_experience",
				"critical_damage", "critical_chance", "life_steal", "mana_steal", "minimum_damage", "maximum_damage", "stun", "slow", "blind", "nausea" };
		
		@Comment("A list of active armor attributes. Remove a value to remove the attribute from generating on items.")
		public String[] armorAttributes = new String[] { "strength", "agility" };
		
		// rarity chances
		@Comment({
			"The chance at which the Common rarity gets applied to weapons/armor.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double commonChance = 62;
		
		@Comment({
			"The chance at which the Uncommon rarity gets applied to weapons/armor.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double uncommonChance = 20;
		
		@Comment({
			"The chance at which the Rare rarity gets applied to weapons/armor.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double rareChance = 10;
		
		@Comment({
			"The chance at which the Epic rarity gets applied to weapons/armor.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double epicChance = 6;
		
		@Comment({
			"The chance at which the Legendary rarity gets applied to weapons/armor.",
			"The number is effectively a weight, so the number may exceed 100."
		})
		public double legendaryChance = 2;
		
		// damage algorithm
		// range
		@Comment("Only change this if you know what you're doing.")
		public double rangeMinRandFactor = 0.5;
		
		@Comment("Only change this if you know what you're doing.")
		public double rangeMaxRandFactor = 0.7;
		
		@Comment({
			"Only change this if you know what you're doing.",
			"Influences the affect the Level has on the range. For bigger damage ranges, increase this value."
		})
		@RangeDouble(min = 0.1)
		public double rangeMultiplier = 0.3;
		
		// damage
		@Comment("Only change this if you know what you're doing.")
		public double damageMinRandFactor = 0.7;
		
		@Comment("Only change this if you know what you're doing.")
		public double damageMaxRandFactor = 0.9;
		
		@Comment({
			"Only change this if you know what you're doing.",
			"Changing this number slightly will have significant effects.",
			"This number gets raised to the level power, then multiplied by the rest of the algorithm."
		})
		public double damageBaseFactor = 1.109;
		
		@Comment({
			"Only change this if you know what you're doing.",
			"This influences the default damage depending on the rarity.",
			"For example, if it's an Iron Sword, it would be this value multiplied by 6 (the default Iron Sword damage)."
		})
		public double commonFactor = 0.85;
		
		@Comment({
			"Only change this if you know what you're doing.",
			"This influences the default damage depending on the rarity.",
			"For example, if it's an Iron Sword, it would be this value multiplied by 6 (the default Iron Sword damage)."
		})
		public double uncommonFactor = 1;
		
		@Comment({
			"Only change this if you know what you're doing.",
			"This influences the default damage depending on the rarity.",
			"For example, if it's an Iron Sword, it would be this value multiplied by 6 (the default Iron Sword damage)."
		})
		public double rareFactor = 1.2;
		
		@Comment({
			"Only change this if you know what you're doing.",
			"This influences the default damage depending on the rarity.",
			"For example, if it's an Iron Sword, it would be this value multiplied by 6 (the default Iron Sword damage)."
		})
		public double epicFactor = 1.45;
		
		@Comment({
			"Only change this if you know what you're doing.",
			"This influences the default damage depending on the rarity.",
			"For example, if it's an Iron Sword, it would be this value multiplied by 6 (the default Iron Sword damage)."
		})
		public double legendaryFactor = 1.8;
	}
	
	public static class RenderingCategory
	{
		@Comment("If true, this will render the Level and Tier above a monster's head.")
		public boolean renderNameplate = true;
		
		@Comment("If true, this will render the Area Level in the top left corner of the HUD.")
		public boolean renderAreaLevel = true;
		
		@Comment({
			"If true, this will completely render the player invisible, even if wearing armor.",
			"If false, rendering will be like Vanilla concerning invisibility."
		})
		public boolean renderInvisibilePlayer = true;
		
		@Comment("If true, this will render LSC's custom health bar.")
		public boolean renderCustomHealthbar = true;
		
		@Comment("If true, this will render LSC's custom mana bar.")
		public boolean renderCustomManabar = true;
	}
}

package com.lsc.entities.bosses;

import java.util.UUID;

import com.lsc.LootSlashConquer;
import com.lsc.entities.EntityMonster;
import com.lsc.entities.ai.EntityAICKSlam;
import com.lsc.entities.ai.EntityAIMeleeCharge;
import com.lsc.entities.monsters.EntitySpectralKnight;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 *
 * @author TheXFactor117
 *
 */
public class EntityCorruptedKnight extends EntityMonster
{
	private int stage; // current stage the boss is in
	private boolean canSpawnMobs;
	private int spawnerCount; // current counter tracking when we should spawn extra mobs
	private int maxSpawnerCount;
	private final int averageSpawnerCount = 20 * 10; // average amount of time (in ticks) that extra mobs will spawn
	
	protected EntityAICKSlam aiCKSlam;
	protected EntityAIMeleeCharge aiMeleeCharge;

	public EntityCorruptedKnight(World world)
	{
		super(world);
		this.setSize(1.0F, 3.5F);
		this.stage = 1;
		this.maxSpawnerCount = averageSpawnerCount;
		this.canSpawnMobs = false;
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		aiCKSlam = new EntityAICKSlam(this);
		aiMeleeCharge = new EntityAIMeleeCharge(this, 15, 0.5);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, aiCKSlam);
		this.tasks.addTask(1, aiMeleeCharge);
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(40);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.1);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.world.isRemote)
		{				
			this.updateCustomTasks();
			
			// update stage based on health
			if (this.stage == 1 && this.getHealth() < (this.getMaxHealth() * (double) (2.0 / 3.0)))
			{
				LootSlashConquer.LOGGER.info("Increasing stage...");
				this.stage++;
				EntityPlayer player = this.world.getClosestPlayerToEntity(this, 32);
				if (player != null) player.sendMessage(new TextComponentString("The monster seems to be transforming..."));

				// add bonus modifiers/stats
				AttributeModifier attackDamage = new AttributeModifier(UUID.fromString("e3340e2a-2595-4161-8076-64580a874894"), "bonusDamage", 10, 1);
				AttributeModifier speedBoost = new AttributeModifier(UUID.fromString("2eb670fc-6662-4307-96d8-18e8d08b88eb"), "speedBoost", 0.03, 0);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attackDamage);
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(speedBoost);
			}
			else if (this.stage == 2 && this.getHealth() < (this.getMaxHealth() * (double) (1.0 / 3.0)))
			{
				LootSlashConquer.LOGGER.info("Increasing stage...");
				this.stage++;
				EntityPlayer player = this.world.getClosestPlayerToEntity(this, 32);
				if (player != null) player.sendMessage(new TextComponentString("The monster seems to be transforming...again..."));

				// add bonus modifiers/stats
				AttributeModifier attackDamage = new AttributeModifier(UUID.fromString("456ccc6a-97b7-4347-8362-4c3794f1cf14"), "bonusDamage", 20, 1);
				AttributeModifier knockbackResistance = new AttributeModifier(UUID.fromString("c2ca2654-7975-477b-b823-48a944693d5b"), "knockbackResist", 0.9, 0);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attackDamage);
				this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(knockbackResistance);
			}

			// spawn extra mobs if checks pass
			if (this.stage == 2)
			{
				//LootSlashConquer.LOGGER.info("Stage 2 is active...");
				
				if (!this.canSpawnMobs)
				{
					this.spawnerCount++;

					if (this.spawnerCount >= this.maxSpawnerCount)
					{
						this.canSpawnMobs = true;
						this.spawnerCount = 0;
					}
				}
				else
				{
					this.maxSpawnerCount = (int) (Math.random() * averageSpawnerCount) + 15;

					int amountToSpawn = (int) (Math.random() * 3) + 1;
					
					// spawn extra mobs
					for (int i = 0; i < amountToSpawn; i++)
					{
						int x = this.getPosition().getX() + ((int) (Math.random() * 10) - 5);
						int y = this.getPosition().getY();
						int z = this.getPosition().getZ() + ((int) (Math.random() * 10) - 5);
						BlockPos monsterPos = new BlockPos(x, y, z);
						
						// check to see if the monster can spawn in a good area
						if (canSpawnMobAtPos(monsterPos))
						{
							EntitySpectralKnight monster = new EntitySpectralKnight(this.world);
							monster.setPosition(x, y, z);
							this.world.spawnEntity(monster);
						}
						else // if check fails, retry spawn.
						{
							i--;
						}
					}
					
					this.canSpawnMobs = false;
				}
			}
		}
	}
	
	/**
	 * Entity is attacked by someone else.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
    {		
		LootSlashConquer.LOGGER.info("CORRUPTED KNIGHT: attacked for " + amount);
		LootSlashConquer.LOGGER.info("CORRUPTED KNIGHT: now has " + this.getHealth() + " health");
		
		return super.attackEntityFrom(source, amount);
    }
	
	/**
	 * Entity attacks someone else.
	 */
	@Override
	public boolean attackEntityAsMob(Entity entity)
    {
		LootSlashConquer.LOGGER.info("CORRUPTED KNIGHT: attacking...");
		
		return super.attackEntityAsMob(entity);
    }
	
	/**
	 * Updates custom AI cooldowns, etc...
	 */
	private void updateCustomTasks()
	{
		if (this.aiCKSlam.slamCooldown > 0)
		{
			this.aiCKSlam.slamCooldown--;
		}
		
		if (this.aiMeleeCharge.cooldown > 0)
		{
			this.aiMeleeCharge.cooldown--;
		}
	}
	
	/**
	 * Checks to see if the boss can spawn additional mobs.
	 * This checks to make sure there is air where they are supposed to spawn so they
	 * don't suffocate in wall or something like that.
	 * @param pos
	 * @return
	 */
	private boolean canSpawnMobAtPos(BlockPos pos)
	{	
		return this.world.getBlockState(pos) == Blocks.AIR.getDefaultState() && this.world.getBlockState(pos.up(1)) == Blocks.AIR.getDefaultState();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		
		nbt.setInteger("BossStage", this.stage);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		
		if (nbt.hasKey("BossStage"))
		{
			this.stage = nbt.getInteger("BossStage");
		}
	}
	
	@Override
	public boolean canDespawn()
	{
		return false;
		
	}
	
	public int getStage()
	{
		return this.stage;
	}
}

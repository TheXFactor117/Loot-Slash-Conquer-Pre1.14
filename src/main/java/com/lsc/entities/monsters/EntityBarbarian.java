package com.lsc.entities.monsters;

import java.util.List;

import com.lsc.LootSlashConquer;
import com.lsc.capabilities.enemyinfo.CapabilityEnemyInfo;
import com.lsc.capabilities.enemyinfo.EnemyInfo;
import com.lsc.entities.EntityMonster;
import com.lsc.loot.NameGenerator;
import com.lsc.loot.generation.ItemGenerator;
import com.lsc.loot.table.CustomLootContext;
import com.lsc.loot.table.CustomLootTable;
import com.lsc.player.WeaponHelper;
import com.lsc.util.NBTHelper;
import com.lsc.util.Reference;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class EntityBarbarian extends EntityMonster
{
	public EntityBarbarian(World world)
	{
		super(world);
		this.setSize(1.0F, 2.0F);
	}
	
	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false));
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0);
	}

	@Override
	public boolean attackEntityAsMob(Entity enemy)
    {
		ItemStack stack = this.getHeldItemMainhand();
		
		if (stack != null)
		{
			// damage enemy based on weapon's damage
			NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
			double damage = Math.random() * (nbt.getInteger("MaxDamage") - nbt.getInteger("MinDamage")) + nbt.getInteger("MinDamage");
			
			boolean hasAttacked = enemy.attackEntityFrom(DamageSource.causeMobDamage(this), (float) damage);
			
			if (hasAttacked && enemy instanceof EntityLivingBase)
			{
				// apply attributes from weapon
				WeaponHelper.useWeaponAttributes((float) damage, this, (EntityLivingBase) enemy, stack, nbt);
				
				// apply knockback if attack was successful
				int knockback = EnchantmentHelper.getKnockbackModifier(this);
				
				if (knockback > 0)
	            {
	                ((EntityLivingBase) enemy).knockBack(this, (float) knockback * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
	                this.motionX *= 0.6D;
	                this.motionZ *= 0.6D;
	            }
			}
			
			return hasAttacked;
		}
		else
		{
			return false;
		}
    }
	
	/**
	 * Called to give the Barbarian a random melee weapon.
	 */
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.getRandomWeapon());
	}
	
	/**
	 * Returns a randomly generated ItemStack based on the tier of the monster.
	 * @return
	 */
	private ItemStack getRandomWeapon()
	{
		EnemyInfo enemyInfo = (EnemyInfo) this.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
		
		if (enemyInfo != null)
		{
			CustomLootContext context = new CustomLootContext.Builder(this.getServer().getWorld(this.dimension)).withChestPos(this.getPosition()).build();
			ItemStack stack = null;
			
			// set loot table dependent on tier
			if (enemyInfo.getEnemyTier() == 1) // normal
			{			
				CustomLootTable commonTable = (CustomLootTable) this.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "base/common/common_physical_weapons"));
				List<ItemStack> items = commonTable.generateLootForPools(this.rand, context);
				stack = items.get(0);
			}
			else if (enemyInfo.getEnemyTier() == 2) // hardened
			{
				CustomLootTable uncommonTable = (CustomLootTable) this.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "base/uncommon/uncommon_physical_weapons"));
				List<ItemStack> items = uncommonTable.generateLootForPools(this.rand, context);
				stack = items.get(0);
			}
			else if (enemyInfo.getEnemyTier() == 3) // superior
			{
				CustomLootTable rareTable = (CustomLootTable) this.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "base/rare/rare_physical_weapons"));
				List<ItemStack> items = rareTable.generateLootForPools(this.rand, context);
				stack = items.get(0);
			}
			else if (enemyInfo.getEnemyTier() == 4) // elite
			{
				CustomLootTable epicTable = (CustomLootTable) this.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "base/epic/epic_physical_weapons"));
				List<ItemStack> items = epicTable.generateLootForPools(this.rand, context);
				stack = items.get(0);
			}
			else if (enemyInfo.getEnemyTier() == 5) // legendary
			{
				CustomLootTable legendaryTable = (CustomLootTable) this.world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "base/legendary/legendary_physical_weapons"));
				List<ItemStack> items = legendaryTable.generateLootForPools(this.rand, context);
				stack = items.get(0);
			}
			
			if (stack != null)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				ItemGenerator.create(stack, nbt, world, nbt.getInteger("TagLevel"));
				stack.setTagCompound(nbt);
				NameGenerator.generateName(stack, nbt);
				return stack;
			}
		}
		
		LootSlashConquer.LOGGER.info("Error setting Barbarian's random weapon...leaving null for now...");
		return null;
	}
	
	@Override
	public void onDeath(DamageSource cause)
    {
		super.onDeath(cause);
		
		if (this.getHeldItemMainhand() != null)
		{
			this.entityDropItem(this.getHeldItemMainhand(), 0);
		}
    }
}

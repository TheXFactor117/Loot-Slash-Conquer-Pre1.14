package com.thexfactor117.losteclipse.items.magical;

import com.thexfactor117.losteclipse.LostEclipse;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.CapabilityPlayerStats;
import com.thexfactor117.losteclipse.capabilities.api.IPlayerInformation;
import com.thexfactor117.losteclipse.capabilities.api.IStats;
import com.thexfactor117.losteclipse.entities.projectiles.EntityFireball;
import com.thexfactor117.losteclipse.entities.projectiles.EntityIcebolt;
import com.thexfactor117.losteclipse.entities.projectiles.EntityLightning;
import com.thexfactor117.losteclipse.entities.projectiles.Rune;
import com.thexfactor117.losteclipse.events.EventPlayerTick;
import com.thexfactor117.losteclipse.network.PacketUpdateStats;
import com.thexfactor117.losteclipse.stats.PlayerStatHelper;
import com.thexfactor117.losteclipse.util.NBTHelper;
import com.thexfactor117.losteclipse.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemLEMagical extends Item
{
	private boolean isBonusActive = false; // controls whether or not player stats should update. See onUpdate
	
	private double baseDamage;
	private double baseAttackSpeed;
	private int manaPerUse;
	
	public ItemLEMagical(String name, double baseDamage, double attackSpeed, int manaPerUse, int durability)
	{
		super();
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setMaxDamage(durability);
		this.baseDamage = baseDamage;
		this.baseAttackSpeed = attackSpeed;
		this.manaPerUse = manaPerUse;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			IPlayerInformation info = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			
			if (!world.isRemote && info != null)
			{
				if (selected && !isBonusActive)
				{
					EventPlayerTick.updateStats(player, info);
					isBonusActive = true;
				}
				
				if (!selected && isBonusActive)
				{
					EventPlayerTick.updateStats(player, info);
					isBonusActive = false;
				}
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		IStats statsCap = player.getCapability(CapabilityPlayerStats.STATS, null);
		
		if (statsCap != null)
		{
			// DEBUG
			statsCap.setMana(50);
			
			if (statsCap.getMana() - this.manaPerUse >= 0)
			{	
				player.setActiveHand(hand);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.inventory.getCurrentItem());
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.inventory.getCurrentItem());
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int count)
	{	
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			IStats statsCap = player.getCapability(CapabilityPlayerStats.STATS, null);	
			IPlayerInformation info = player.getCapability(CapabilityPlayerInformation.PLAYER_INFORMATION, null);
			NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
			
			if (info != null)
			{
				// check to see if we have held it long enough
				double attackSpeed = nbt.getDouble("AttackSpeed") + (PlayerStatHelper.ATTACK_SPEED_MULTIPLIER * (info.getAgilityStat() + info.getBonusAgilityStat()));
				
				if (count > (this.getMaxItemUseDuration(stack) - ((1 / attackSpeed) * 20))) 
				{
					LostEclipse.LOGGER.info("Count: " + count + "\t" + ((1 / attackSpeed) * 20));
					return;
				}
				
				// fire projectile because check passed
				if (statsCap != null)
				{
					world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					
					if (!world.isRemote)
					{
						// spawn entity and set position to specified direction
						Vec3d look = player.getLookVec();

						fireProjectile(world, player, stack, nbt, look);
						
						// update mana and send to client
						statsCap.setMana(statsCap.getMana() - this.manaPerUse);
						LostEclipse.network.sendTo(new PacketUpdateStats(statsCap), (EntityPlayerMP) player);
						
						// damage item
						stack.damageItem(1, player);
					}
				}
			}
		}
	}
	
	private void fireProjectile(World world, EntityPlayer player, ItemStack stack, NBTTagCompound nbt, Vec3d look)
	{
		if (Rune.getRune(nbt) == Rune.FIREBALL) 
		{
			EntityFireball fireball = new EntityFireball(world, look.x, look.y, look.z, 1F, 0F, player, stack, 2);
			fireball.setPosition(player.posX + look.x, player.posY + look.y + 1.5, player.posZ + look.z);
			world.spawnEntity(fireball);
		}
		else if (Rune.getRune(nbt) == Rune.ICEBOLT)
		{
			EntityIcebolt icebolt = new EntityIcebolt(world, look.x, look.y, look.z, 1F, 0F, player, stack, 2);
			icebolt.setPosition(player.posX + look.x, player.posY + look.y + 1.5, player.posZ + look.z);
			world.spawnEntity(icebolt);
		}
		else if (Rune.getRune(nbt) == Rune.LIGHTNING)
		{
			EntityLightning lightning = new EntityLightning(world, look.x, look.y, look.z, 1F, 0F, player, stack, 2);
			lightning.setPosition(player.posX + look.x, player.posY + look.y + 1.5, player.posZ + look.z);
			world.spawnEntity(lightning);
		}
		else if (Rune.getRune(nbt) == Rune.FIRESTORM)
		{
			for (int i = 0; i < 4; i++)
			{
				EntityFireball fireball = new EntityFireball(world, look.x, look.y, look.z, 1F, 15F, player, stack, 2);
				fireball.setPosition(player.posX + look.x, player.posY + look.y + 1.5, player.posZ + look.z);
				world.spawnEntity(fireball);
			}
		}
		else if (Rune.getRune(nbt) == Rune.BLIZZARD)
		{
			for (int i = 0; i < 4; i++)
			{
				EntityIcebolt icebolt = new EntityIcebolt(world, look.x, look.y, look.z, 1F, 0.3F, player, stack, 2);
				icebolt.setPosition(player.posX + look.x, player.posY + look.y + 1.5, player.posZ + look.z);
				world.spawnEntity(icebolt);
			}
		}
		else if (Rune.getRune(nbt) == Rune.DISCHARGE)
		{
			for (int i = 0; i < 4; i++)
			{
				EntityLightning lightning = new EntityLightning(world, look.x, look.y, look.z, 1F, 0F, player, stack, 2);
				lightning.setPosition(player.posX + look.x, player.posY + look.y + 1.5, player.posZ + look.z);
				world.spawnEntity(lightning);
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 300;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BLOCK;
	}
	
	public double getBaseDamage()
	{
		return baseDamage;
	}
	
	public double getBaseAttackSpeed()
	{
		return baseAttackSpeed;
	}
	
	public int getManaPerUse()
	{
		return manaPerUse;
	}
}

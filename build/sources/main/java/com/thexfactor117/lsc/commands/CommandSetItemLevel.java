package com.thexfactor117.lsc.commands;

import java.util.List;

import com.google.common.collect.Lists;
import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.items.base.weapons.ItemMagical;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 *
 * @author TheXFactor117
 *
 */
public class CommandSetItemLevel extends CommandBase
{
	private final List<String> aliases;
	
	private int itemLevel;
	
	public CommandSetItemLevel()
	{
		aliases = Lists.newArrayList();
		aliases.add("setItemLevel");
		aliases.add("setLevel");
	}
	
	@Override
	public String getName()
	{
		return "setItemLevel";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "setItemLevel <level>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		
		if (!world.isRemote)
		{
			if (args.length > 0)
			{
				itemLevel = Integer.parseInt(args[0]);
				
				if (sender.getCommandSenderEntity() != null && sender.getCommandSenderEntity() instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
					ItemStack weapon = player.getHeldItem(player.getActiveHand());
					
					if (weapon.getItem() instanceof ItemSword || weapon.getItem() instanceof ItemArmor || weapon.getItem() instanceof ItemBow || weapon.getItem() instanceof ItemMagical || weapon.getItem() instanceof ItemBauble)
					{
						NBTTagCompound nbt = NBTHelper.loadStackNBT(weapon);
						nbt.setInteger("Level", itemLevel);
					}
					else
					{
						sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error: you are holding an invalid item."));
						return;
					}
				}
				
				sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Set level to " + itemLevel + " successfully!"));
			}
			else
			{
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: " + getUsage(sender)));
				return;
			}
		}
	}
	
	@Override
	public List<String> getAliases()
	{
		return aliases;
	}

	// TODO: setup command permissions properly
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}
}

package com.thexfactor117.lsc.commands;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 *
 * @author TheXFactor117
 *
 */
public class CommandModifyItem extends CommandBase
{
	private final String[] keys = new String[] { "rarity", "level", "damage", "attackspeed", "armor", "attribute" };
	private final List<String> aliases;
	private String key;
	
	public CommandModifyItem()
	{
		aliases = Lists.newArrayList();
		aliases.add("modifyItem");
	}
	
	@Override
	public String getName()
	{
		return "modifyItem";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "Usage: /modifyItem <key>...";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (!sender.getEntityWorld().isRemote)
		{
			if (args.length > 0)
			{
				key = args[0].toLowerCase();
				
				if (key.equals("rarity"))
				{
					executeRarity(sender, args);
				}
				else if (key.equals("level"))
				{
				
				}
				else if (key.equals("damage"))
				{
					
				}
				else if (key.equals("attackspeed"))
				{
					
				}
				else if (key.equals("armor"))
				{
					
				}
				else if (key.equals("attribute"))
				{
					
				}
				else
				{
					sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error: you entered an invalid key...\nKeys: rarity, level, damage, attackspeed, armor, attribute"));
				}
			}
			else
			{
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error: Invalid arguments\n" + getUsage(sender)));
			}
		}
	}
	
	private void executeRarity(ICommandSender sender, String[] args)
	{
		if (args.length > 1)
		{
			//String rarity = args[1];
		}
		else
		{
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error: Invalid arguments\nExpecting an applicable rarity..."));
		}
	}
	
	@Override
	public List<String> getAliases()
	{
		return aliases;
	}
	
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

package com.thexfactor117.lsc.commands;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 *
 * @author TheXFactor117
 *
 */
public class CommandAddAttribute extends CommandBase
{
	private final List<String> aliases;
	
	private String attributeName;
	
	public CommandAddAttribute()
	{
		aliases = Lists.newArrayList();
		aliases.add("addAttribute");
	}

	@Override
	public String getName()
	{
		return "addAttribute";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "addAttribute <attribute> <amount>";
	}

	/*@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		
		if (!world.isRemote)
		{
			if (args.length > 0)
			{
				attributeName = args[0];
				Attribute attribute = Attribute.getAttributeFromName(attributeName);
				
				if (attribute != null)
				{
					if (sender.getCommandSenderEntity() != null && sender.getCommandSenderEntity() instanceof EntityPlayer)
					{
						EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
						ItemStack weapon = player.getHeldItem(player.getActiveHand());
						
						if (weapon.getItem() instanceof ItemSword || weapon.getItem() instanceof ItemArmor || weapon.getItem() instanceof ItemMagical || weapon.getItem() instanceof ItemBauble)
						{
							NBTTagCompound nbt = NBTHelper.loadStackNBT(weapon);
							
							if (args.length == 1)
							{
								attribute.addAttribute(nbt, world.rand);
							}
							else if (args.length == 2)
							{
								attribute.addAttribute(nbt, world.rand, Double.parseDouble(args[1]));
							}
						}
						else
						{
							sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error: you are holding an invalid item."));
							return;
						}
					}
				}
				else
				{
					sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error: the attribute " + attributeName + " does not exist."));
					return;
				}
				
				sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Added the " + attribute.getLocalizedName() + " attribute to the item successfully!"));
			}
			else
			{
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Invalid arguments - " + getUsage(sender)));
				return;
			}
		}
	}*/
	
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
		if (args.length == 1)
		{
			List<String> list = Lists.newArrayList();
			
			
			return getListOfStringsMatchingLastWord(args, list);
		}
		
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		// TODO Auto-generated method stub
		
	}
}

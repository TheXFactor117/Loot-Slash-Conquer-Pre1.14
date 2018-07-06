package com.lsc.blocks;

import java.util.List;

import com.lsc.events.EventContainerOpen;
import com.lsc.init.ModTabs;
import com.lsc.items.base.ItemBauble;
import com.lsc.items.base.ItemMagical;
import com.lsc.loot.Rarity;
import com.lsc.loot.table.CustomLootContext;
import com.lsc.loot.table.CustomLootTable;
import com.lsc.util.NBTHelper;
import com.lsc.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 *
 * @author TheXFactor117
 *
 */
public class BlockJar extends Block
{
	public Rarity rarity;
	
	public BlockJar(String name, Rarity rarity)
	{
		super(Material.CLAY);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModTabs.tabLE);
		this.setSoundType(SoundType.GLASS);
		this.setHardness(0F);
		this.rarity = rarity;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess blockAccess, BlockPos pos, IBlockState state, int fortune)
    {
		super.getDrops(drops, blockAccess, pos, state, fortune);
		
		drops.clear();
		
		World world = (World) blockAccess;
		List<ItemStack> stacks = null;
		CustomLootTable table;
		CustomLootContext context;
		
		switch (rarity)
		{
			case COMMON:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "jars/common_jar"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case UNCOMMON:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "jars/uncommon_jar"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case RARE:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "jars/rare_jar"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case EPIC:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "jars/epic_jar"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case LEGENDARY:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "jars/legendary_jar"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			default:
				break;
		}
		
		if (stacks != null)
		{
			for (ItemStack stack : stacks)
			{
				if (stack != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemBow 
						|| stack.getItem() instanceof ItemMagical || stack.getItem() instanceof ItemBauble))
				{
					NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
					stack.setTagCompound(nbt);

					if (nbt != null)
					{
						if (nbt.getInteger("Level") == 0)
						{
							if (nbt.hasKey("TagLevel"))
							{
								EventContainerOpen.generate(stack, nbt, world, nbt.getInteger("TagLevel"));
								drops.add(stack);
							}
						}
					}
				}
			}
		}
    }
}

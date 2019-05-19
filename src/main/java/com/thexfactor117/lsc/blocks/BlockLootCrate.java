package com.thexfactor117.lsc.blocks;

import java.util.List;

import com.thexfactor117.lsc.events.EventContainerOpen;
import com.thexfactor117.lsc.init.ModTabs;
import com.thexfactor117.lsc.items.base.ItemBauble;
import com.thexfactor117.lsc.items.base.ItemMagical;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.table.CustomLootContext;
import com.thexfactor117.lsc.loot.table.CustomLootTable;
import com.thexfactor117.lsc.util.misc.NBTHelper;
import com.thexfactor117.lsc.util.misc.Reference;

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
public class BlockLootCrate extends Block
{
	public Rarity rarity;
	
	public BlockLootCrate(String name, Rarity rarity)
	{
		super(Material.WOOD);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(ModTabs.lscTab);
		this.setSoundType(SoundType.WOOD);
		this.setHardness(1F);
		this.rarity = rarity;
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
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "blocks/common_crate"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case UNCOMMON:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "blocks/uncommon_crate"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case RARE:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "blocks/rare_crate"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case EPIC:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "blocks/epic_crate"));
				context = new CustomLootContext.Builder((WorldServer) world).withChestPos(pos).build();
				stacks = table.generateLootForPools(world.rand, context);
				break;
			case LEGENDARY:
				table = (CustomLootTable) world.getLootTableManager().getLootTableFromLocation(new ResourceLocation(Reference.MODID, "blocks/legendary_crate"));
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

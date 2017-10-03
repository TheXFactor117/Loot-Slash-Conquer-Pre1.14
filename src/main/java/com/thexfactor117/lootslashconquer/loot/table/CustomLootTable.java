package com.thexfactor117.lootslashconquer.loot.table;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.thexfactor117.lootslashconquer.LootSlashConquer;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;

public class CustomLootTable extends LootTable
{
	public CustomLootTable(LootPool[] poolsIn) 
	{
		super(poolsIn);
	}

	@Override
    public void fillInventory(IInventory inventory, Random rand, LootContext context)
    {
    	TileEntityChest chest = (TileEntityChest) inventory;
        CustomLootContext.Builder context$builder = new CustomLootContext.Builder((WorldServer) chest.getWorld());
        context$builder.withChestPos(chest.getPos());
        CustomLootContext customContext = context$builder.build();
    	
        List<ItemStack> list = this.generateLootForPools(rand, customContext);
        List<Integer> list1 = this.getEmptySlotsRandomized(inventory, rand);
        this.shuffleItems(list, list1.size(), rand);

        for (ItemStack itemstack : list)
        {
            if (list1.isEmpty())
            {
            	LootSlashConquer.LOGGER.warn("Tried to over-fill a container");
                return;
            }
            
            if (itemstack.isEmpty())
            {
                inventory.setInventorySlotContents(((Integer)list1.remove(list1.size() - 1)).intValue(), ItemStack.EMPTY);
            }
            else
            {
                inventory.setInventorySlotContents(((Integer)list1.remove(list1.size() - 1)).intValue(), itemstack);
            }
        }
    }

    /**
     * shuffles items by changing their order and splitting stacks
     */
    private void shuffleItems(List<ItemStack> stacks, int p_186463_2_, Random rand)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();
        Iterator<ItemStack> iterator = stacks.iterator();

        while (iterator.hasNext())
        {
            ItemStack itemstack = iterator.next();

            if (itemstack.isEmpty())
            {
                iterator.remove();
            }
            else if (itemstack.getCount() > 1)
            {
                list.add(itemstack);
                iterator.remove();
            }
        }

        p_186463_2_ = p_186463_2_ - stacks.size();

        while (p_186463_2_ > 0 && !list.isEmpty())
        {
            ItemStack itemstack2 = list.remove(MathHelper.getInt(rand, 0, list.size() - 1));
            int i = MathHelper.getInt(rand, 1, itemstack2.getCount() / 2);
            ItemStack itemstack1 = itemstack2.splitStack(i);

            if (itemstack2.getCount() > 1 && rand.nextBoolean())
            {
                list.add(itemstack2);
            }
            else
            {
                stacks.add(itemstack2);
            }

            if (itemstack1.getCount() > 1 && rand.nextBoolean())
            {
                list.add(itemstack1);
            }
            else
            {
                stacks.add(itemstack1);
            }
        }

        stacks.addAll(list);
        Collections.shuffle(stacks, rand);
    }

    private List<Integer> getEmptySlotsRandomized(IInventory inventory, Random rand)
    {
        List<Integer> list = Lists.<Integer>newArrayList();

        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            if (inventory.getStackInSlot(i).isEmpty())
            {
                list.add(Integer.valueOf(i));
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }
}

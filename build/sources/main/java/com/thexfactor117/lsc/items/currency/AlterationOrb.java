package com.thexfactor117.lsc.items.currency;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.items.base.ItemBase;
import com.thexfactor117.lsc.loot.attributes.Attribute;
import com.thexfactor117.lsc.util.PlayerUtil;
import com.thexfactor117.lsc.util.misc.NBTHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.thexfactor117.lsc.loot.generation.ItemGeneration.create;

public class AlterationOrb extends ItemBase {


    public AlterationOrb(String name, CreativeTabs tab) {

        super(name, tab);
        super.setMaxStackSize(64);

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        LSCPlayerCapability cap = PlayerUtil.getLSCPlayer(player);
        ItemStack currentStack = player.getHeldItem(hand);
        ItemStack currentOffHandStack =  player.getHeldItemOffhand();
        boolean itemChanged = false;

        int playerLevel = cap.getPlayerLevel();

        NBTTagCompound nbt = NBTHelper.loadStackNBT(currentOffHandStack);

        nbt.setInteger("Level", playerLevel);

        for (Attribute attribute : Attribute.ALL_ATTRIBUTES)
        {
            if (attribute.hasAttribute(nbt))
            {
                attribute.removeAttribute(nbt);
                itemChanged = true;
            }
        }

        create(currentOffHandStack, playerLevel);

        if (!world.isRemote && !player.isCreative() && player.getHeldItemOffhand() != ItemStack.EMPTY && itemChanged == true)
        {
            currentStack.setCount(currentStack.getCount()-1);
            player.setHeldItem(hand, currentStack);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, currentStack);
    }
}

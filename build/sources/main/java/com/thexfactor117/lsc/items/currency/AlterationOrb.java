package com.thexfactor117.lsc.items.currency;

import com.thexfactor117.lsc.capabilities.implementation.LSCPlayerCapability;
import com.thexfactor117.lsc.items.base.ItemBase;
import com.thexfactor117.lsc.loot.Rarity;
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
import net.minecraftforge.common.util.Constants;

import static com.thexfactor117.lsc.loot.generation.ItemGeneration.create;
import static com.thexfactor117.lsc.util.ItemGenerationUtil.rand;

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

        Double random = Math.random();

        switch(Rarity.getRarity(nbt)){
            case COMMON:
            case UNCOMMON:
            case RARE:
                Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, rand));
                while (Rarity.getRarity(nbt) == Rarity.COMMON) Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, rand));
                break;
            case EPIC:
                Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, rand));
                while (Rarity.getRarity(nbt) == Rarity.COMMON || Rarity.getRarity(nbt) == Rarity.UNCOMMON) Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, rand));
                break;
            case LEGENDARY: break;
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

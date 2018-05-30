package com.lsc.items.base;

import javax.annotation.Nullable;

import com.lsc.init.ModTabs;
import com.lsc.util.Reference;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemRanged extends ItemBow
{
	private double baseDamage;
	private double baseDrawSpeed;
	
	public ItemRanged(String name, double damage, double drawSpeed, int durability)
	{
		super();
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setUnlocalizedName(name);
        this.setMaxDamage(durability);
        this.setCreativeTab(ModTabs.tabLE);
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return !(entityIn.getActiveItemStack().getItem() instanceof ItemRanged)  ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
	}
	
	// private ItemStack findAmmo(EntityPlayer player)
	// implement later if custom arrows are used.
	
	// public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	// implement later if custom arrows are used. probably will handle attributes and such by events
	
	// public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	// may need to change stuff here later.
	
	public double getBaseDamage()
	{
		return baseDamage;
	}
	
	public double getBaseDrawSpeed()
	{
		return baseDrawSpeed;
	}
}

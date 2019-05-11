package com.thexfactor117.lsc.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.thexfactor117.lsc.items.base.ISpecial;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.table.CustomLootContext;
import com.thexfactor117.lsc.util.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

/**
 * 
 * @author TheXFactor117
 *
 */
public class CreateSpecial extends LootFunction
{
	protected CreateSpecial(LootCondition[] conditionsIn) 
	{
		super(conditionsIn);
	}
	
	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
		if (context instanceof CustomLootContext)
		{
			CustomLootContext customContext = (CustomLootContext) context;
			BlockPos pos;
			NBTTagCompound nbt;
			World world = context.getWorld();
			
			if (customContext.getChestPos() != null) pos = customContext.getChestPos();
			else pos = context.getLootedEntity().getPosition();
			
			if (!stack.hasTagCompound()) nbt = new NBTTagCompound();
			else nbt = stack.getTagCompound();
			
			if (stack.getItem() instanceof ISpecial)
			{
				ISpecial special = (ISpecial) stack.getItem();
				special.createSpecial(stack, nbt, world, new ChunkPos(pos));
			}

			nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
			stack.setTagCompound(nbt);
			stack.setStackDisplayName(Rarity.getRarity(nbt).getColor() + stack.getDisplayName());
			return stack;
		}
		
		return stack;
    }

    public static class Serializer extends LootFunction.Serializer<CreateSpecial>
        {
            public Serializer()
            {
                super(new ResourceLocation(Reference.MODID, "create_special"), CreateSpecial.class);
            }

            public void serialize(JsonObject object, CreateSpecial functionClazz, JsonSerializationContext serializationContext)
            {
            	
            }

            public CreateSpecial deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
            {
                return new CreateSpecial(conditionsIn);
            }
        }
}

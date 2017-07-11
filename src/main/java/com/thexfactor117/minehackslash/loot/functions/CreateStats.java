package com.thexfactor117.minehackslash.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.thexfactor117.minehackslash.loot.CustomLootContext;
import com.thexfactor117.minehackslash.stats.weapons.ItemGenerator;
import com.thexfactor117.minehackslash.stats.weapons.Rarity;
import com.thexfactor117.minehackslash.util.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

public class CreateStats extends LootFunction
{
	protected CreateStats(LootCondition[] conditionsIn) 
	{
		super(conditionsIn);
	}
	
	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
		if (context instanceof CustomLootContext)
		{
			CustomLootContext customContext = (CustomLootContext) context;
			BlockPos pos = customContext.getChestPos();
			NBTTagCompound nbt;
			
			if (!stack.hasTagCompound())
				nbt = new NBTTagCompound();
			else
				nbt = stack.getTagCompound();
			
			ItemGenerator.create(stack, nbt, pos);
			stack.setTagCompound(nbt);
			stack.setStackDisplayName(Rarity.getRarity(nbt).getColor() + stack.getDisplayName());
			return stack;
		}
		
		return stack;
    }

    public static class Serializer extends LootFunction.Serializer<CreateStats>
        {
            public Serializer()
            {
                super(new ResourceLocation(Reference.MODID, "create_stats"), CreateStats.class);
            }

            public void serialize(JsonObject object, CreateStats functionClazz, JsonSerializationContext serializationContext)
            {
            	
            }

            public CreateStats deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
            {
                return new CreateStats(conditionsIn);
            }
        }
}

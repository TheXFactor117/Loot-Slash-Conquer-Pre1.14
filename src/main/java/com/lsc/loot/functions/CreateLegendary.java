package com.lsc.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lsc.items.base.ItemLEBauble;
import com.lsc.items.base.ItemLEMagical;
import com.lsc.loot.ItemGenerator;
import com.lsc.loot.NameGenerator;
import com.lsc.loot.Rarity;
import com.lsc.loot.table.CustomLootContext;
import com.lsc.util.Reference;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
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
public class CreateLegendary extends LootFunction
{
	protected CreateLegendary(LootCondition[] conditionsIn) 
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
			
			if (customContext.getChestPos() != null)
				pos = customContext.getChestPos();
			else
				pos = context.getLootedEntity().getPosition();
			
			if (!stack.hasTagCompound())
				nbt = new NBTTagCompound();
			else
				nbt = stack.getTagCompound();
			
			Rarity.setRarity(nbt, Rarity.getWeightedRarity(nbt, rand, Rarity.LEGENDARY));
			
			if (stack.getItem() instanceof ItemLEMagical)
			{
				ItemGenerator.createMagical(stack, nbt, world, new ChunkPos(pos));
			}
			else if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor)
			{
				ItemGenerator.create(stack, nbt, world, new ChunkPos(pos));
			}
			else if (stack.getItem() instanceof ItemLEBauble)
			{
				ItemGenerator.createJewelry(stack, nbt, world, new ChunkPos(pos));
			}
			
			stack.setTagCompound(nbt);
			NameGenerator.generateName(stack, stack.getTagCompound());
			return stack;
		}
		
		return stack;
    }

    public static class Serializer extends LootFunction.Serializer<CreateLegendary>
        {
            public Serializer()
            {
                super(new ResourceLocation(Reference.MODID, "create_legendary"), CreateLegendary.class);
            }

            public void serialize(JsonObject object, CreateLegendary functionClazz, JsonSerializationContext serializationContext)
            {
            	
            }

            public CreateLegendary deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
            {
                return new CreateLegendary(conditionsIn);
            }
        }
}

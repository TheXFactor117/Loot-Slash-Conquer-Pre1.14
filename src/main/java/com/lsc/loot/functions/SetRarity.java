package com.lsc.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lsc.loot.Rarity;
import com.lsc.util.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

/**
 * 
 * @author TheXFactor117
 *
 */
public class SetRarity extends LootFunction
{
	private int rarity;
	
	protected SetRarity(LootCondition[] conditions, int rarity) 
	{
		super(conditions);
		this.rarity = rarity;
	}
	
	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context)
	{
		NBTTagCompound nbt;
		
		if (!stack.hasTagCompound()) nbt = new NBTTagCompound();
		else nbt = stack.getTagCompound();
		
		Rarity rarity = Rarity.DEFAULT;
		
		if (this.rarity == 1) rarity = Rarity.COMMON;
		else if (this.rarity == 2) rarity = Rarity.UNCOMMON;
		else if (this.rarity == 3) rarity = Rarity.RARE;
		else if (this.rarity == 4) rarity = Rarity.EPIC;
		else if (this.rarity == 5) rarity = Rarity.LEGENDARY;
		
		Rarity.setRarity(nbt, Rarity.getWeightedRarity(nbt, rand, rarity));
		
		stack.setTagCompound(nbt);
		return stack;
	}
	
	public static class Serializer extends LootFunction.Serializer<SetRarity>
	{
		public Serializer()
		{
			super(new ResourceLocation(Reference.MODID, "set_rarity"), SetRarity.class);
		}
		
		public void serialize(JsonObject object, SetRarity functionClazz, JsonSerializationContext serializationContext)
		{
			object.add("rarity", serializationContext.serialize(functionClazz.rarity));
		}
		
		public SetRarity deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditions)
		{
			return new SetRarity(conditions, (int) JsonUtils.deserializeClass(object, "rarity", deserializationContext, int.class));
		}
	}
}

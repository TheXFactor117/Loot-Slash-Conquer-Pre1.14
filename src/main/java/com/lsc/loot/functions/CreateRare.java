package com.lsc.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lsc.capabilities.chunk.CapabilityChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevel;
import com.lsc.capabilities.chunk.IChunkLevelHolder;
import com.lsc.capabilities.enemylevel.CapabilityEnemyLevel;
import com.lsc.capabilities.enemylevel.IEnemyLevel;
import com.lsc.loot.ItemGenerator;
import com.lsc.loot.NameGenerator;
import com.lsc.loot.Rarity;
import com.lsc.loot.table.CustomLootContext;
import com.lsc.util.Reference;

import net.minecraft.entity.EntityLivingBase;
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
public class CreateRare extends LootFunction
{
	protected CreateRare(LootCondition[] conditionsIn) 
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
			
			Rarity.setRarity(nbt, Rarity.getWeightedRarity(nbt, rand, Rarity.RARE));
			
			ChunkPos chunkPos = new ChunkPos(pos);
			IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
			IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(chunkPos);
			
			ItemGenerator.create(stack, nbt, world, chunkLevel.getChunkLevel());
			
			stack.setTagCompound(nbt);
			NameGenerator.generateName(stack, stack.getTagCompound());
			return stack;
		}
		else
		{
			NBTTagCompound nbt;
			World world = context.getWorld();
			
			if (!stack.hasTagCompound()) nbt = new NBTTagCompound();
			else nbt = stack.getTagCompound();
			
			Rarity.setRarity(nbt, Rarity.getWeightedRarity(nbt, rand, Rarity.RARE));
			
			EntityLivingBase entity = (EntityLivingBase) context.getKiller();
			IEnemyLevel enemyLevel = entity.getCapability(CapabilityEnemyLevel.ENEMY_LEVEL, null);
			
			ItemGenerator.create(stack, nbt, world, enemyLevel.getEnemyLevel());
			
			stack.setTagCompound(nbt);
			NameGenerator.generateName(stack, stack.getTagCompound());
			return stack;
		}
    }

    public static class Serializer extends LootFunction.Serializer<CreateRare>
        {
            public Serializer()
            {
                super(new ResourceLocation(Reference.MODID, "create_rare"), CreateRare.class);
            }

            public void serialize(JsonObject object, CreateRare functionClazz, JsonSerializationContext serializationContext)
            {
            	
            }

            public CreateRare deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
            {
                return new CreateRare(conditionsIn);
            }
        }
}

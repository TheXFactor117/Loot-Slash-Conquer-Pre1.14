package com.lsc.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lsc.capabilities.api.IChunkLevel;
import com.lsc.capabilities.api.IChunkLevelHolder;
import com.lsc.capabilities.cap.CapabilityChunkLevel;
import com.lsc.capabilities.cap.CapabilityEnemyInfo;
import com.lsc.capabilities.implementation.EnemyInfo;
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
public class TagLoot extends LootFunction
{
	protected TagLoot(LootCondition[] conditions)
	{
		super(conditions);
	}
	
	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context)
	{
		if (context instanceof CustomLootContext)
		{
			CustomLootContext customContext = (CustomLootContext) context;
			NBTTagCompound nbt;
			World world = context.getWorld();
			
			if (!stack.hasTagCompound()) nbt = new NBTTagCompound();
			else nbt = stack.getTagCompound();
			
			// coming from chest
			if (customContext.getChestPos() != null) 
			{
				BlockPos pos = customContext.getChestPos();
				ChunkPos chunkPos = new ChunkPos(pos);
				IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
				IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(chunkPos);

				nbt.setInteger("TagLevel", chunkLevel.getChunkLevel());
				
				stack.setTagCompound(nbt);
				return stack;
			}
		}
		// coming from mob drop
		else 
		{
			NBTTagCompound nbt;

			if (!stack.hasTagCompound()) nbt = new NBTTagCompound();
			else nbt = stack.getTagCompound();
			
			EntityLivingBase entity = (EntityLivingBase) context.getLootedEntity();
			EnemyInfo enemyLevel = (EnemyInfo) entity.getCapability(CapabilityEnemyInfo.ENEMY_INFO, null);
			
			nbt.setInteger("TagLevel", enemyLevel.getEnemyLevel());
			
			stack.setTagCompound(nbt);
			return stack;
		}		
		return stack;
	}
	
	public static class Serializer extends LootFunction.Serializer<TagLoot>
	{
		public Serializer()
		{
			super(new ResourceLocation(Reference.MODID, "tag_loot"), TagLoot.class);
		}
		
		public void serialize(JsonObject object, TagLoot functionClazz, JsonSerializationContext serializationContext)
		{
			
		}
		
		public TagLoot deserialize(JsonObject object, JsonDeserializationContext serializationContext, LootCondition[] conditions)
		{
			return new TagLoot(conditions);
		}
	}
}

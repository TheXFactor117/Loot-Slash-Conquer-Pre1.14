package com.thexfactor117.lsc.world;

import com.thexfactor117.lsc.util.Reference;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

/**
 *
 * @author TheXFactor117
 *
 */
public class LSCWorldSavedData extends WorldSavedData
{
	private static final String ID = Reference.MODID + "_StructureData";
	private int corruptedTowers = 0;
	
	public LSCWorldSavedData()
	{
		super(ID);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.corruptedTowers = nbt.getInteger("CorruptedTowers");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("CorruptedTowers", corruptedTowers);		
		
		return nbt;
	}
	
	public void increaseCorruptedTowers()
	{
		this.corruptedTowers++;
		this.markDirty();
	}

	public int getCorruptedTowers()
	{
		return this.corruptedTowers;
	}
	
	public static LSCWorldSavedData get(World world)
	{
		MapStorage storage = world.getMapStorage();
		LSCWorldSavedData data = (LSCWorldSavedData) storage.getOrLoadData(LSCWorldSavedData.class, ID);
		
		if (data == null)
		{
			data = new LSCWorldSavedData();
			storage.setData(ID, data);
		}
		
		return data;
	}
}

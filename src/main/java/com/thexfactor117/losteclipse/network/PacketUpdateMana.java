package com.thexfactor117.losteclipse.network;

import com.thexfactor117.losteclipse.capabilities.CapabilityMana;
import com.thexfactor117.losteclipse.capabilities.api.IMana;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * 
 * @author TheXFactor117
 *
 */
public class PacketUpdateMana implements IMessage
{
	private int mana;
	private int maxMana;
	private int manaPerSecond;
	
	public PacketUpdateMana() {}
	
	public PacketUpdateMana(IMana mana)
	{
		this.mana = mana.getMana();
		this.maxMana = mana.getMaxMana();
		this.manaPerSecond = mana.getManaPerSecond();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		mana = buf.readInt();
		maxMana = buf.readInt();
		manaPerSecond = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(mana);
		buf.writeInt(maxMana);
		buf.writeInt(manaPerSecond);
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateMana, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketUpdateMana message, final MessageContext ctx) 
		{			
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable()
			{
				@Override
				public void run() 
				{
					EntityPlayer player = Minecraft.getMinecraft().player;
					IMana mana = player.getCapability(CapabilityMana.MANA, null);
					
					if (mana != null)
					{
						mana.setMana(message.mana);
						mana.setMaxMana(message.maxMana);
						mana.setManaPerSecond(message.manaPerSecond);
					}
				}
			});
			
			return null;
		}
	}
}

package ak.EnchantChanger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import ak.MultiToolHolders.ItemMultiToolHolder;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;


public class Packet_EnchantChanger implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player)
	{
		if (packet.channel.equals("EC|CS"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EntityPlayer entityPlayer = (EntityPlayer) player;
			if(entityPlayer.inventory.getCurrentItem() == null)
				return;
			if(entityPlayer.inventory.getCurrentItem().getItem() instanceof EcItemCloudSword)
			{
				EcItemCloudSword sword = (EcItemCloudSword) entityPlayer.getCurrentEquippedItem().getItem();
				sword.readSlotNumData(data, entityPlayer.getCurrentEquippedItem());
			}
			else if(EnchantChanger.loadMTH  && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemMultiToolHolder)
			{
				ItemMultiToolHolder mth = (ItemMultiToolHolder) entityPlayer.inventory.getCurrentItem().getItem();
				if(mth.tools.getStackInSlot(mth.SlotNum) != null && mth.tools.getStackInSlot(mth.SlotNum).getItem() instanceof EcItemCloudSword)
				{
					EcItemCloudSword sword = (EcItemCloudSword) mth.tools.getStackInSlot(mth.SlotNum).getItem();
					sword.readSlotNumData(data, mth.tools.getStackInSlot(mth.SlotNum));
				}
			}
		}
		if (packet.channel.equals("EC|Sw"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EntityPlayer entityPlayer = (EntityPlayer) player;
			if(entityPlayer.inventory.getCurrentItem() == null)
				return;
			if(entityPlayer.inventory.getCurrentItem().getItem() instanceof EcItemSword)
			{
				EcItemSword sword = (EcItemSword) entityPlayer.getCurrentEquippedItem().getItem();
				sword.readPacketToggleData(data);
			}
			else if(EnchantChanger.loadMTH  && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemMultiToolHolder)
			{
				ItemMultiToolHolder mth = (ItemMultiToolHolder) entityPlayer.inventory.getCurrentItem().getItem();
				if(mth.tools.getStackInSlot(mth.SlotNum) != null && mth.tools.getStackInSlot(mth.SlotNum).getItem() instanceof EcItemSword)
				{
					EcItemSword sword = (EcItemSword) mth.tools.getStackInSlot(mth.SlotNum).getItem();
					sword.readPacketToggleData(data);
				}
			}
		}
		if (packet.channel.equals("EC|Levi"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EnchantChanger.livingeventhooks.readPacketData(data, (EntityPlayer) player);
		}
	}
	public static Packet getPacketCS(EcItemCloudSword ItemCloudSword, ItemStack stack)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		ItemCloudSword.writeSlotNumData(dos, stack);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "EC|CS";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();

		return packet;
	}
	public static Packet getPacketSword(EcItemSword Sword)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		Sword.writePacketToggleData(dos);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "EC|Sw";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();

		return packet;
	}
	public static Packet getPacketLevi(LivingEventHooks LivingEventhooks, EntityPlayer player)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		LivingEventhooks.writePacketData(dos, player);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "EC|Levi";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();

		return packet;
	}
}
package Nanashi.AdvancedTools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("AT|Tool"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EntityPlayer entityPlayer = (EntityPlayer) player;
			if(entityPlayer.inventory.getCurrentItem() != null)
			{
				if(entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemUGTool)
				{
					ItemUGTool item = (ItemUGTool) entityPlayer.inventory.getCurrentItem().getItem();
					item.readPacketData(data);
				}
//				else if(AdvancedTools.hasMultiToolHolder && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemMultiToolHolder)
//				{
//					ItemMultiToolHolder item = (ItemMultiToolHolder) entityPlayer.inventory.getCurrentItem().getItem();
//					if(item.tools != null && item.tools.getStackInSlot(item.SlotNum) != null && item.tools.getStackInSlot(item.SlotNum).getItem() instanceof ItemUGTool)
//					{
//						ItemUGTool item1 = (ItemUGTool)item.tools.getStackInSlot(item.SlotNum).getItem();
//						item1.readPacketData(data);
//					}
//				}
			}
		}
	}
	public static Packet getPacketTool(ItemUGTool item)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		item.writePacketData(dos);
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AT|Tool";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;

		return packet;
	}
}
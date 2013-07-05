package ak.MultiToolHolders;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import ak.EnchantChanger.EcItemCloudSwordCore;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("MTH|Tool"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EntityPlayer entityPlayer = (EntityPlayer) player;
			if(entityPlayer.inventory.getCurrentItem() != null && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemMultiToolHolder)
			{
				ItemMultiToolHolder item = (ItemMultiToolHolder) entityPlayer.inventory.getCurrentItem().getItem();
				item.readPacketData(data);
			}
		}
	}
	public static Packet getPacketTool(ItemMultiToolHolder item)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		item.writePacketData(dos);
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "MTH|Tool";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;

		return packet;
	}
}
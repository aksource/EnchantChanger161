package CaveStory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

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
		if (packet.channel.equals("Booster"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			CaveStory.livingeventhooks.readPacketData(data);
		}
	}
	public static Packet getPacket(LivingEventHooks event)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		event.writePacketData(dos);
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Booster";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;

		return packet;
	}
}
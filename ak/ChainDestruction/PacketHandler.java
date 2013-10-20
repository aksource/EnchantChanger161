package ak.ChainDestruction;

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
		if(packet.channel.equals("CD|RegKey"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			ChainDestruction.instance.interactblockhook.readPacketData(data);
		}
	}
	public static Packet getPacketRegKeyToggle(InteractBlockHook hook)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		hook.writePacketData(dos);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "CD|RegKey"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();

		return packet;
	}
}
package VRGenerator;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		
	}
	public static Packet getPacketTileGen(CETileEntityGenerator tile)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
 
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		short facing = tile.getFacing();
 
		try
		{
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeShort(facing);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
 
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TileEntity";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;
 
		return packet;
	}
}
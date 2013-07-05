package ak.PrepaidEnchantment;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;


 public class PacketHandler implements IPacketHandler
 {
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player)
	{
		if (packet.channel.equals("PE|Tile"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			int x, y, z, point, Lv;
			try
			{
				x = data.readInt();
				y = data.readInt();
				z = data.readInt();
				point = data.readInt();
				Lv = data.readInt();

				World world = PrepaidEnchantment.proxy.getClientWorld();
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

				if (tileEntity instanceof TileEntityPEnchantmentTable)
				{
					TileEntityPEnchantmentTable tileEntityPE = (TileEntityPEnchantmentTable)tileEntity;
					tileEntityPE.setPoint(point);
					tileEntityPE.setCurrentMaxLevel(Lv);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(packet.channel.equals("PE|Button"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			Container container = ((EntityPlayerMP)player).openContainer;
			if(container != null && container instanceof ContainerPEnchantment)
			{
				((ContainerPEnchantment)container).readPacketData(data);
			}
		}

	}
	public static Packet getPacketTile(TileEntityPEnchantmentTable tileEntity)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		int x = tileEntity.xCoord;
		int y = tileEntity.yCoord;
		int z = tileEntity.zCoord;
		int point = tileEntity.getPoint();
		int Lv = tileEntity.getCurrentMaxLevel();

		try
		{
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(point);
			dos.writeInt(Lv);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "PE|Tile";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;

		return packet;
	}
	public static Packet getPacketButton(ContainerPEnchantment container)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		container.writePacketData(dos);
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "PE|Button";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;

		return packet;
	}
 }
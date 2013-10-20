package compactengine;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.energy.TileEngine;
import buildcraft.transport.PipeTransport;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TileGenericPipe;
import buildcraft.transport.pipes.PipePowerWood;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ItemEnergyChecker extends Item
{
	public ItemEnergyChecker(int par1)
	{
		super(par1);
		setCreativeTab(CreativeTabBuildCraft.tabBuildCraft);
	}

	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			int id = world.getBlockId(x, y, z);
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile == null)
				return false;
			if(tile instanceof TileEngine)
			{
				TileEngine engine = (TileEngine)tile;
				float mjt = engine.maxEnergyExtracted() / (1.28f / engine.getPistonSpeed());
				float heat = engine.getHeat();
				if(heat == 0){
					heat = (int)(engine.energy / engine.getMaxEnergy() * 100);
				}else{
					heat = (int)(heat / 10);
				}

				CompactEngine.addChat(
					I18n.getString("energyChecker.maxPower") + ": %1.2f MJ/t  " +
							I18n.getString("energyChecker.energy") + ": %1.0f / %d MJ  " +
							I18n.getString("energyChecker.heat") + ": %d\u00B0C / %d\u00B0C"
					, (float)mjt, engine.energy, MathHelper.ceiling_float_int(engine.getMaxEnergy()), MathHelper.ceiling_float_int(heat), MathHelper.ceiling_float_int(engine.getMaxEnergy()) / 10);
				return true;
			}
			else if(tile instanceof IPowerReceptor && (
					((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.DOWN) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.UP) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.NORTH) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.SOUTH) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.WEST) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.EAST) != null))
			{
				PowerReceiver receiver;
				if(((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.DOWN) != null)
					receiver = ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.DOWN);
				else if(((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.UP) != null)
					receiver = ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.UP);
				else if(((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.NORTH) != null)
					receiver = ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.NORTH);
				else if(((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.SOUTH) != null)
					receiver = ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.SOUTH);
				else if(((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.WEST) != null)
					receiver = ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.WEST);
				else
					receiver = ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.EAST);
				CompactEngine.addChat(
						I18n.getString("energyChecker.energy") + ": %1.2f / %d MJ  " +
								I18n.getString("energyChecker.workEnergy") + ": %d MJ"
								, receiver.getEnergyStored(), MathHelper.ceiling_float_int(receiver.getMaxEnergyStored()), MathHelper.ceiling_float_int(receiver.getMaxEnergyReceived()));
				return true;
			}
			else if(tile instanceof TileGenericPipe)
			{
				if(((TileGenericPipe)tile).pipe instanceof PipePowerWood)
				{
					CompactEngine.addChat(
							I18n.getString("energyChecker.energy") + ": 10000 / 10000 MJ");
					return true;
				}

				PipeTransport transport = ((TileGenericPipe)tile).pipe.transport;
				if(transport instanceof PipeTransportPower)
				{
					double PowerMax = 0;
					float[] internalPower = ((PipeTransportPower)transport).internalNextPower;
					for(int i = 0; i < 6; i++){if(PowerMax < internalPower[i]) PowerMax = internalPower[i];}
//					internalPower = ((PipeTransportPower)transport).internalPower;
					internalPower = ObfuscationReflectionHelper.getPrivateValue(PipeTransportPower.class, (PipeTransportPower)transport, 13);
					for(int i = 0; i < 6; i++){if(PowerMax < internalPower[i]) PowerMax = internalPower[i];}
					CompactEngine.addChat(
							I18n.getString("energyChecker.pipeEnergy") + ": %1.4f / 10000 MJ", PowerMax);
					return true;
				}
			}
			return false;
		}
		return false;
	}

}

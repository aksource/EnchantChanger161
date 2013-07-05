package ak.PrepaidEnchantment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ak.PrepaidEnchantment.Client.GuiPEnchantment;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public CommonProxy(){}
	public void registerRenderInformation(){}

	public void registerTileEntitySpecialRenderer(){}

	//returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		if(id == PrepaidEnchantment.guiIdPEnchantment)
		{
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			//return new EcContainerMaterializer(world, player.inventory, (EcTileEntityMaterializer) tileEntity);
			return new ContainerPEnchantment(player.inventory, world, x, y, z, (TileEntityPEnchantmentTable)tileEntity);
		}

		else
			return null;
	}

	//returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == PrepaidEnchantment.guiIdPEnchantment)
		{
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			return new GuiPEnchantment(player.inventory, world, x, y, z,  ((TileEntityPEnchantmentTable) tileEntity).func_94135_b() ?  ((TileEntityPEnchantmentTable) tileEntity).func_94133_a() : null, (TileEntityPEnchantmentTable)tileEntity);
		}
		else
			return null;
	}
	public World getClientWorld()
	{
		return null;
	}
}
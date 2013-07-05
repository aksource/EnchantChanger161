package ak.PrepaidEnchantment.Client;

import net.minecraft.world.World;
import ak.PrepaidEnchantment.CommonProxy;
import ak.PrepaidEnchantment.TileEntityPEnchantmentTable;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
//		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
//		TickRegistry.registerTickHandler(new CommonTickHandler(), Side.SERVER);
	}

	@Override
	public void registerTileEntitySpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPEnchantmentTable.class, new RenderPEnchantmentTable());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
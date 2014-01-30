package ak.BigItems.Client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import ak.BigItems.BigItems;
import ak.BigItems.CommonProxy;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
	@SideOnly(Side.CLIENT)
    public static KeyBinding MagicKey = new KeyBinding("Key.EcMagic",Keyboard.KEY_V);
	@Override
	public void registerClientInformation()
	{
		for(int i= 0;i<BigItems.ItemIDs.length;i++)
			MinecraftForgeClient.registerItemRenderer(BigItems.ItemIDs[i], new BigItemRenderer());
	}
	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
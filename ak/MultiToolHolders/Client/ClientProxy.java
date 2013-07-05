package ak.MultiToolHolders.Client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import ak.MultiToolHolders.CommonProxy;
import ak.MultiToolHolders.MultiToolHolders;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding OpenKey = new KeyBinding("Key.openToolHolder",Keyboard.KEY_F);
	public static KeyBinding NextKey = new KeyBinding("Key.nextToolHolder",Keyboard.KEY_T);
	public static KeyBinding PrevKey = new KeyBinding("Key.prevToolHolder",Keyboard.KEY_R);
	public static KeyBinding[] Keys = new KeyBinding[]{OpenKey, NextKey,PrevKey};
	public static boolean[] repeat = new boolean[]{false, false, false};
	@Override
	public void registerClientInformation()
	{

		KeyBindingRegistry.registerKeyBinding(new MTHKeyHandler(Keys, repeat));
		MinecraftForgeClient.registerItemRenderer(MultiToolHolders.ItemIDShift, (IItemRenderer) MultiToolHolders.ItemMultiToolHolder3);
		MinecraftForgeClient.registerItemRenderer(MultiToolHolders.ItemIDShift + 1, (IItemRenderer) MultiToolHolders.ItemMultiToolHolder5);
		MinecraftForgeClient.registerItemRenderer(MultiToolHolders.ItemIDShift + 2, (IItemRenderer) MultiToolHolders.ItemMultiToolHolder9);
		MinecraftForgeClient.registerItemRenderer(MultiToolHolders.ItemIDShift + 3, (IItemRenderer) MultiToolHolders.ItemMultiToolHolder7);
	}

	@Override
	public void registerTileEntitySpecialRenderer()
	{
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPEnchantmentTable.class, new RenderPEnchantmentTable());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
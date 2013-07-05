package Booster;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding boostKey = new KeyBinding("Key.BoosterSwitch",Keyboard.KEY_B);
	public static boolean[] repeat = new boolean[]{false, false, false};
	@Override
	public void registerClientInformation()
	{
		KeyBindingRegistry.registerKeyBinding(new BoosterKeyHandler(new KeyBinding[]{this.boostKey}, new boolean[]{true}));
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
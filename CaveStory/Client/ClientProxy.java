package CaveStory.Client;

import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import CaveStory.BoosterKeyHandler;
import CaveStory.CommonProxy;
import CaveStory.EntityMissile;
import CaveStory.EntityWeaponOrb;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding boostKey = new KeyBinding("Key.BoosterSwitch",Keyboard.KEY_B);
	public static boolean[] repeat = new boolean[]{false, false, false};
	@Override
	public void registerClientInformation()
	{
		KeyBindingRegistry.registerKeyBinding(new BoosterKeyHandler(new KeyBinding[]{this.boostKey}, new boolean[]{true}));
		RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, new RenderMissile());
		RenderingRegistry.registerEntityRenderingHandler(EntityWeaponOrb.class, new RenderXPOrb());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
package ak.ChainDestruction;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class ClientProxy extends CommonProxy
{
	public static KeyBinding registItemKey = new KeyBinding("Key.CDRegistItem",Keyboard.KEY_K);
	public static KeyBinding digUnderKey = new KeyBinding("Key.CDDIgUnder",Keyboard.KEY_U);
	@Override
	public void registerClientInfo(){
		KeyBindingRegistry.registerKeyBinding(new CDKeyHandler(new KeyBinding[]{registItemKey, digUnderKey},new boolean[]{false, false}));
	}
}
package ak.MultiToolHolders.Client;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class MTHKeyHandler extends KeyHandler
{
	public static boolean openKeydown = false;
	public static boolean nextKeydown = false;
	public static boolean prevKeydown = false;
	public MTHKeyHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
		super(keyBindings, repeatings);
	}

	@Override
	public String getLabel() {
		return "KeyHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,	boolean tickEnd, boolean isRepeat) {
		if(kb == ClientProxy.OpenKey)
		{
			openKeydown = true;
		}
		else if(kb == ClientProxy.NextKey)
		{
			nextKeydown = true;
		}
		else if(kb == ClientProxy.PrevKey)
		{
			prevKeydown = true;
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if(kb == ClientProxy.OpenKey)
		{
			openKeydown = false;
		}
		else if(kb == ClientProxy.NextKey)
		{
			nextKeydown = false;
		}
		else if(kb == ClientProxy.PrevKey)
		{
			prevKeydown = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}
}
package ak.EnchantChanger.Client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import ak.EnchantChanger.CommonProxy;
import ak.EnchantChanger.CommonTickHandler;
import ak.EnchantChanger.EcEntityApOrb;
import ak.EnchantChanger.EcEntityExExpBottle;
import ak.EnchantChanger.EcEntityMeteo;
import ak.EnchantChanger.EcTileEntityHugeMateria;
import ak.EnchantChanger.EnchantChanger;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	public static KeyBinding MagicKey = new KeyBinding("Key.EcMagic",
			Keyboard.KEY_V);

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(
				EcEntityExExpBottle.class, new EcRenderItemThrowable(0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EcEntityMeteo.class,
				new EcRenderItemThrowable(EnchantChanger.MeteoSize));
		RenderingRegistry.registerEntityRenderingHandler(EcEntityApOrb.class,
				new EcRenderApOrb());
		TickRegistry.registerTickHandler(new CommonTickHandler(), Side.SERVER);
		// TickRegistry.registerTickHandler(new ClientTickHandler(),
		// Side.CLIENT);
		KeyBindingRegistry.registerKeyBinding(new EcKeyHandler(
				new KeyBinding[] { MagicKey }, new boolean[] { false }));
		IItemRenderer swordRenderer = new EcSwordRenderer();
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.SephirothSwordItemID,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.ZackSwordItemID,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.FirstSwordItemID,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.CloudSwordItemID,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.UltimateWeaponItemID,
				swordRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.ImitateSephSwordID,
				swordRenderer);
		IItemRenderer materiaRenderer = new EcRenderMateria();
		MinecraftForgeClient.registerItemRenderer(EnchantChanger.MateriaID,
				materiaRenderer);
		MinecraftForgeClient.registerItemRenderer(
				EnchantChanger.MasterMateriaID, materiaRenderer);
	}

	@Override
	public void registerTileEntitySpecialRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(
				EcTileEntityHugeMateria.class, new EcRenderHugeMateria());
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
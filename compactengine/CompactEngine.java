package compactengine;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.fluids.Fluid;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftEnergy;
import buildcraft.BuildCraftSilicon;
import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="CompactEngine", name="CompactEngine", version="build 3(for mc1.6.2  bc4.0.1  Forge#804 )", dependencies ="required-after:BuildCraft|Energy")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class CompactEngine
{
	@Instance("CompactEngine")
	public static CompactEngine instance;
	@SidedProxy(clientSide = "compactengine.Client.ClientProxy", serverSide = "compactengine.CommonProxy")
	public static CommonProxy proxy;

	public static BlockCompactEngine engineBlock;
	public static ItemBlock engineItem;
	public static Item energyChecker;
	private static Fluid buildcraftFluidOil;

	public static int blockID_CompactEngine;
	public static int itemID_energyChecker;
	public static boolean isAddCompactEngine512and2048;
	public static int CompactEngineExplosionPowerLevel;
	public static int CompactEngineExplosionTimeLevel;
	public static int CompactEngineExplosionAlertMinute;
	public static boolean neverExplosion;
//	public static int OilFlowingSpeed;
	
	public static ItemStack engine1;
	public static ItemStack engine2;
	public static ItemStack engine3;
	public static ItemStack engine4;
//	public static ItemStack engine5 = new ItemStack(engineBlock, 1, 4);
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		blockID_CompactEngine = config.get(Configuration.CATEGORY_BLOCK, "CompactEngineId", 1529).getInt();
		itemID_energyChecker = config.get(Configuration.CATEGORY_ITEM, "EnergyCheckerId", 19500).getInt();
		isAddCompactEngine512and2048 = config.get(Configuration.CATEGORY_GENERAL, "Add high compact engine", false,"add Engine is x512 (Note explosion)").getBoolean(false);
		CompactEngineExplosionPowerLevel = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionPowerLevel", 1,"min=0, max=3").getInt();
		CompactEngineExplosionTimeLevel = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionTimeLevel", 1,"min=0, max=3").getInt();
		CompactEngineExplosionAlertMinute = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionAlertMinute", 3,"0 is not alert display, min=0.0D, max=30.0D").getInt();
		neverExplosion = config.get(Configuration.CATEGORY_GENERAL, "neverExplosion", false, "Engine No Explosion").getBoolean(false);
//		OilFlowingSpeed = config.get(Configuration.CATEGORY_GENERAL, "OilFlowingSpeed", 20, "Change OilFlowingSpeed. Default:20tick").getInt();
		config.save();
		engineBlock =new BlockCompactEngine(blockID_CompactEngine);	
		engineItem  = new ItemCompactEngine(blockID_CompactEngine - 256);		
		energyChecker = new ItemEnergyChecker(itemID_energyChecker - 256).setUnlocalizedName("compactengine:energyChecker").setTextureName("compactengine:energyChecker");
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
//		Block.blocksList[BuildCraftEnergy.fluidOil.getBlockID()] = null;
//		oilMoving = (new BlockOilFlowing2(BuildCraftEnergy.fluidOil.getBlockID(), Material.water));
//		buildcraftFluidOil = new Fluid("oil");
//		ObfuscationReflectionHelper.setPrivateValue(BuildCraftEnergy.class, BuildCraftEnergy.instance, buildcraftFluidOil, 6);
//		BuildCraftEnergy.fluidOil = FluidRegistry.getFluid("oil");
//		BuildCraftEnergy.fluidOil.setBlockID(BuildCraftEnergy.blockOil);
		engine1 = new ItemStack(engineBlock, 1, 0);
		engine2 = new ItemStack(engineBlock, 1, 1);
		engine3 = new ItemStack(engineBlock, 1, 2);
		engine4 = new ItemStack(engineBlock, 1, 3);
		proxy.registerTileEntitySpecialRenderer();
//		GameRegistry.registerTileEntity(TileCompactEngine.class, "tile.compactengine");
		GameRegistry.registerTileEntity(TileCompactEngine8.class, "tile.compactengine8");
		GameRegistry.registerTileEntity(TileCompactEngine32.class, "tile.compactengine32");
		GameRegistry.registerTileEntity(TileCompactEngine128.class, "tile.compactengine128");
		GameRegistry.registerTileEntity(TileCompactEngine512.class, "tile.compactengine512");

		ItemStack woodEngine = new ItemStack(BuildCraftEnergy.engineBlock, 1, 0);
		ItemStack ironEngine = new ItemStack(BuildCraftEnergy.engineBlock, 1, 2);
		ItemStack ironGear = new ItemStack(BuildCraftCore.ironGearItem);
		ItemStack diaGear = new ItemStack(BuildCraftCore.diamondGearItem);
		ItemStack diaChip = new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 3);
		ItemStack goldORGate = new ItemStack(BuildCraftTransport.pipeGate, 1, 4);
		ItemStack diaORGate = new ItemStack(BuildCraftTransport.pipeGate, 1, 6);

		GameRegistry.addRecipe(engine1, new Object[]{"www", "wgw", "www", 'w', woodEngine, 'g', ironGear});
		GameRegistry.addRecipe(engine2, new Object[]{"geg", "eie", "geg", 'e', engine1, 'g', diaGear, 'i', ironEngine});
		GameRegistry.addRecipe(engine3, new Object[]{"geg", "eie", "geg", 'e', engine2, 'g', diaChip, 'i', ironEngine});

		if(isAddCompactEngine512and2048)
		{
			GameRegistry.addRecipe(engine4, new Object[]{"geg", "eie", "geg", 'e', engine3, 'g', goldORGate, 'i', ironEngine});
//			GameRegistry.addRecipe(engine5, new Object[]{"geg", "eie", "geg", 'e', engine4, 'g', diaORGate, 'i', ironEngine});
		}
		GameRegistry.addRecipe(new ItemStack(energyChecker), new Object[]{"w", "i",
			'w', BuildCraftTransport.pipePowerWood, 'i', Item.ingotIron});
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LanguageRegistry.addName(engine1, "Redstone Engine x8");
		LanguageRegistry.instance().addNameForObject(engine1, "ja_JP", "8倍圧縮 木エンジン");
		LanguageRegistry.addName(engine2, "Redstone Engine x32");
		LanguageRegistry.instance().addNameForObject(engine2, "ja_JP", "32倍圧縮 木エンジン");
		LanguageRegistry.addName(engine3, "Redstone Engine x128");
		LanguageRegistry.instance().addNameForObject(engine3, "ja_JP", "128倍圧縮 木エンジン");
		LanguageRegistry.addName(engine4, "Redstone Engine x512");
		LanguageRegistry.instance().addNameForObject(engine4, "ja_JP", "512倍圧縮 木エンジン");
//		LanguageRegistry.addName(engine5, "Redstone Engine x2048");
//		LanguageRegistry.instance().addNameForObject(engine5, "ja_JP", "2048倍圧縮 木エンジン");
		LanguageRegistry.addName(energyChecker, "Energy Checker");
		LanguageRegistry.instance().addNameForObject(energyChecker, "ja_JP", "エネルギー計測器");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_1", "Redstone Engine x8");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_1", "ja_JP", "8倍圧縮 木エンジン");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_2", "Redstone Engine x32");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_2", "ja_JP", "32倍圧縮 木エンジン");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_3", "Redstone Engine x128");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_3", "ja_JP", "128倍圧縮 木エンジン");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_4", "Redstone Engine x512");
		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_4", "ja_JP", "512倍圧縮 木エンジン");
//		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_5.name", "Redstone Engine x2048");
//		LanguageRegistry.instance().addStringLocalization("tile.CompactEngineWood.level_5.name", "ja_JP", "2048倍圧縮 木エンジン");

		LanguageRegistry.instance().addStringLocalization("energyChecker.maxPower"  , "maxPower"  );
		LanguageRegistry.instance().addStringLocalization("energyChecker.maxPower"  , "ja_JP", "出力"  );
		LanguageRegistry.instance().addStringLocalization("energyChecker.energy"    , "energy"    );
		LanguageRegistry.instance().addStringLocalization("energyChecker.energy"    , "ja_JP", "熱量"    );
		LanguageRegistry.instance().addStringLocalization("energyChecker.workEnergy", "workEnergy");
		LanguageRegistry.instance().addStringLocalization("energyChecker.workEnergy", "ja_JP", "受入限界");
		LanguageRegistry.instance().addStringLocalization("energyChecker.pipeEnergy", "pipeEnergy");
		LanguageRegistry.instance().addStringLocalization("energyChecker.pipeEnergy", "ja_JP", "流量");
		LanguageRegistry.instance().addStringLocalization("energyChecker.heat",       "Heat");
		LanguageRegistry.instance().addStringLocalization("energyChecker.heat",       "ja_JP","温度");
		LanguageRegistry.instance().addStringLocalization("engine.alert", "CompactEngine x %s explosion is limit %d:00 (x:%d y:%d z:%d)");
		LanguageRegistry.instance().addStringLocalization("engine.alert", "ja_JP", "%s倍圧縮木エンジンがあと%d分で爆発します。  座標 x:%d y:%d z:%d");
		LanguageRegistry.instance().addStringLocalization("engine.explode", "Explode! range %s (x:%d y:%d z:%d)");
		LanguageRegistry.instance().addStringLocalization("engine.explode", "ja_JP", "爆発力%sの圧縮木エンジンが爆発しました。  座標 x:%d y:%d z:%d");

	}
	public static void addChat(String message)
	{

		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			Minecraft mc = Minecraft.getMinecraft();
			mc.ingameGUI.getChatGUI().printChatMessage(message);
		}
		else if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			PacketDispatcher.sendPacketToAllPlayers(new Packet3Chat(ChatMessageComponent.createFromText(message)));
		}
	}

	public static void addChat(String format,Object... args)
	{	
		addChat(String.format(format,args));
	}
}
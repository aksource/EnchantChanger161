package ak.HyperDimensionalBag;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="HyperDimensionalBag", name="HyperDimensionalBag", version="1.0b",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class HyperDimensionalBag
{
	@Mod.Instance("HyperDimensionalBag")
	public static HyperDimensionalBag instance;
	@SidedProxy(clientSide = "ak.HyperDimensionalBag.ClientProxy", serverSide = "ak.HyperDimensionalBag.CommonProxy")
	public static CommonProxy proxy;
	
	public static int bagID;
	public static int guiID = 0;
	public static String GuiBagTex ="textures/gui/GuiBag.png";
	public static String TextureDomain = "hyperdimensionalbag:";
	public static String Assets = "hyperdimensionalbag";
	public static boolean loadSB = false;
	public static boolean hardRecipe;
	public static Item HDBag;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		bagID = config.get(Configuration.CATEGORY_ITEM, "BagID", 12000).getInt();
		hardRecipe = config.get(Configuration.CATEGORY_GENERAL, "HardRecipe", false).getBoolean(false);
		config.save();
		HDBag = new ItemHDBag(bagID - 256).setUnlocalizedName(this.TextureDomain + "Bag").setTextureName(this.TextureDomain + "Bag").setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(HDBag, "hyperdimentionalbag");
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new PlayerPickHook());
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		for(int i = 0;i<15;i++)
			GameRegistry.addShapelessRecipe(new ItemStack(HDBag, 1, i), new ItemStack(HDBag, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Item.dyePowder, 1, i));
		if(!hardRecipe)
			GameRegistry.addShapedRecipe(new ItemStack(HDBag, 1, 15), new Object[]{"LDL","DCD","LDL", 'L',Item.leather,'D',Item.diamond,'C',Block.chest});
		else
			GameRegistry.addShapedRecipe(new ItemStack(HDBag, 1, 15), new Object[]{"LDL","DCD","LDL", 'L',Item.leather,'D',Item.diamond,'C',Item.netherStar});
		
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		loadSB = Loader.isModLoaded("mod_StorageBox");
	}
}
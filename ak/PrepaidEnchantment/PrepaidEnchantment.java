package ak.PrepaidEnchantment;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="PrepaidEnchantment", name="PrepaidEnchantment", version="1.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"PE|Tile","PE|Button"}, packetHandler=PacketHandler.class)
public class PrepaidEnchantment
{
	public static int PEnchantID;
	public static Block BlockPEnchant;
	public static int EmeraldCost;
	public static int GoldCost;
	public static int DiamondCost;

	public static String TextureDomain = "prepaidenchantment:";
	public static String GuiPEnchTex ="textures/gui/enchant.png";

	public static boolean incompatible = false;
	@Mod.Instance("PrepaidEnchantment")
	public static PrepaidEnchantment instance;
	@SidedProxy(clientSide = "ak.PrepaidEnchantment.Client.ClientProxy", serverSide = "ak.PrepaidEnchantment.CommonProxy")
	public static CommonProxy proxy;
	public static int guiIdPEnchantment= 0;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		PEnchantID = config.get(Configuration.CATEGORY_BLOCK, "EnchantChanger Id",4000).getInt();

		EmeraldCost = config.get(Configuration.CATEGORY_GENERAL, "EmeraldCost", 2).getInt();
		GoldCost = config.get(Configuration.CATEGORY_GENERAL, "GoldCost", 5).getInt();
		DiamondCost = config.get(Configuration.CATEGORY_GENERAL, "DiamondCost", 10).getInt();

		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		BlockPEnchant = (new BlockPEnchantmentTable(PEnchantID)).setUnlocalizedName("PrepaidEnchantmentTable").setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(BlockPEnchant,"PrepaidEnchantmentTable");
		GameRegistry.registerTileEntity(TileEntityPEnchantmentTable.class, "container.prepaidenchantmenttable");
		//KeyBindingRegistry.registerKeyBinding(new EcKeyHandler(EcKeys,EcKeysRepeat));

		//"this" is an instance of the mod class
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		proxy.registerRenderInformation();
		proxy.registerTileEntitySpecialRenderer();

		GameRegistry.addRecipe(new ItemStack(BlockPEnchant, 1), new Object[]{"XXX","XYX", "XXX", Character.valueOf('X'),Item.ingotGold, Character.valueOf('Y'),Block.enchantmentTable});
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		AddLocalization();
	}
	public void AddLocalization()
	{
		LanguageRegistry.addName(BlockPEnchant, "Prepaid Enchantment Table");
		LanguageRegistry.instance().addNameForObject(BlockPEnchant, "ja_JP","プリペイドエンチャントテーブル");
		LanguageRegistry.instance().addStringLocalization("container.prepaidenchantmenttable", "Prepaid Enchantment Table");
		LanguageRegistry.instance().addStringLocalization("container.prepaidenchantmenttable", "ja_JP", "プリペイドエンチャントテーブル");
		LanguageRegistry.instance().addStringLocalization("container.prepaidenchantmenttable.cost", "Available Point : ");
		LanguageRegistry.instance().addStringLocalization("container.prepaidenchantmenttable.cost", "ja_JP", "利用可能ポイント : ");

	}
}
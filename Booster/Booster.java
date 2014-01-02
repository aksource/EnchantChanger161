package Booster;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="Booster", name="Booster", version="1.6.2v3",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"Booster"}, packetHandler=PacketHandler.class)
public class Booster
{
	public static int BoosterID;
	public static Item Booster08;
	public static Item Booster20;

	public static int BoostPower;

	int CanBoost=BoostPower;

	public static boolean BoosterDefaultSwitch;

	public static boolean Alwaysflying = false;


	public static double movement =1d;
	public static String TextureDomain = "booster:";
	public static String Armor08_1 = "textures/armor/AR08_1.png";
	public static String Armor08_2 = "textures/armor/AR08_2.png";
	public static String Armor20_1 = "textures/armor/AR20_1.png";
	public static String Armor20_2 = "textures/armor/AR20_2.png";
	public static LivingEventHooks livingeventhooks;

	@Mod.Instance("Booster")
	public static Booster instance;
	@SidedProxy(clientSide = "Booster.ClientProxy", serverSide = "Booster.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		BoosterID = config.get(Configuration.CATEGORY_ITEM, "BoosterID", 20000).getInt();
		BoostPower = config.get(Configuration.CATEGORY_GENERAL, "BoostPower", 25).getInt();
		BoosterDefaultSwitch = config.get(Configuration.CATEGORY_GENERAL, "BoostPower", true).getBoolean(true);
		Alwaysflying = config.get(Configuration.CATEGORY_GENERAL, "Alwaysflying", false).getBoolean(false);
		movement = config.get(Configuration.CATEGORY_GENERAL, "movement", 1d).getDouble(1);
		config.save();
		Booster08 = new ItemBooster(BoosterID - 256,EnumArmorMaterial.IRON ,2,1).setUnlocalizedName(TextureDomain + "Booster08").setCreativeTab(CreativeTabs.tabCombat);
		GameRegistry.registerItem(Booster08, "booster08", "Booster");
		Booster20 = new ItemBooster(BoosterID - 256 + 1,EnumArmorMaterial.DIAMOND ,3,1).setUnlocalizedName(TextureDomain + "Booster20").setCreativeTab(CreativeTabs.tabCombat);
		GameRegistry.registerItem(Booster20, "booster20", "Booster");
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.registerClientInformation();

		livingeventhooks = new LivingEventHooks();
		MinecraftForge.EVENT_BUS.register(livingeventhooks);

		GameRegistry.addRecipe(new ItemStack(Booster08),
				new Object[]{ "XRX","XPX","X X",
			Character.valueOf('X'),Item.ingotIron,
			Character.valueOf('R'),Item.redstoneRepeater,
			Character.valueOf('P'),Block.pistonBase});

		GameRegistry.addRecipe(new ItemStack(Booster20),
				new Object[]{ "I I"," B ","IDI",
			Character.valueOf('B'),Booster08,
			Character.valueOf('I'),Item.ingotIron,
			Character.valueOf('D'),Item.diamond});

		if(getClass().getPackage() != null)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Booster08,1), new Object[]{ new ItemStack(Block.dirt, 1)});
			GameRegistry.addShapelessRecipe(new ItemStack(Booster20,1), new Object[]{ new ItemStack(Block.sand, 1)});
			GameRegistry.addShapelessRecipe(new ItemStack(Item.plateChain,1), new Object[]{ new ItemStack(Block.workbench, 1)});
		}
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LanguageRegistry.addName(Booster08, "Booster0.8");
		LanguageRegistry.addName(Booster20, "Booster2.0");
		LanguageRegistry.instance().addStringLocalization("Key.BoosterSwitch", "ja_JP", "ブースタースイッチ");
		LanguageRegistry.instance().addStringLocalization("Key.BoosterSwitch", "en_US", "BoosterSwitch");
	}
}
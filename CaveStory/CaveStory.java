package CaveStory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="CaveStory", name="CaveStory", version="1.5.2v1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false/*, channels={"CaveStory"}, packetHandler=PacketHandler.class*/)
public class CaveStory
{
	public static int CaveStoryID;
//	public static Item Booster08;
//	public static Item Booster20;
	public static Item PolarStar;
	public static Item MachineGun;
	public static Item Bubbler;
	public static Item MissileLauncher;
	public static Item Missile;
	public static Item SuperMissileLauncher;
	public static Item SuperMissile;
	public static Item FireBall;
	public static Item Snake;
	public static Item Nemesis;
	public static Item Spur;

	public static int BoostPower;

	int CanBoost=BoostPower;

	public static boolean BoosterDefaultSwitch;

	public static boolean Alwaysflying = false;


	public static double movement =1d;
	public static String TextureDomain = "cavestory:";
	public static String Armor08_1 = "textures/armor/AR08_1.png";
	public static String Armor08_2 = "textures/armor/AR08_2.png";
	public static String Armor20_1 = "textures/armor/AR20_1.png";
	public static String Armor20_2 = "textures/armor/AR20_2.png";
	public static String MissileTex ="textures/items/Missile-rendering.png";
	public static String SuperMissileTex ="textures/items/SuperMissile-rendering.png";
//	public static LivingEventHooks livingeventhooks;

	@Mod.Instance("CaveStory")
	public static CaveStory instance;
	@SidedProxy(clientSide = "CaveStory.Client.ClientProxy", serverSide = "CaveStory.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		CaveStoryID = config.get(Configuration.CATEGORY_ITEM, "CaveStoryID", 20000).getInt();
		BoostPower = config.get(Configuration.CATEGORY_GENERAL, "BoostPower", 25).getInt();
		BoosterDefaultSwitch = config.get(Configuration.CATEGORY_GENERAL, "BoostPower", true).getBoolean(true);
		Alwaysflying = config.get(Configuration.CATEGORY_GENERAL, "Alwaysflying", false).getBoolean(false);
		movement = config.get(Configuration.CATEGORY_GENERAL, "movement", 1d).getDouble(1);
		config.save();
//		Booster08 = new ItemBooster(CaveStoryID - 256,EnumArmorMaterial.IRON ,2,1).setUnlocalizedName(TextureDomain + "Booster08").setCreativeTab(CreativeTabs.tabCombat);
//		Booster20 = new ItemBooster(CaveStoryID - 256 + 1,EnumArmorMaterial.DIAMOND ,3,1).setUnlocalizedName(TextureDomain + "Booster20").setCreativeTab(CreativeTabs.tabCombat);

	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.registerClientInformation();
		EntityRegistry.registerModEntity(EntityPolarStar.class, "PolarStar", 0, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityMissile.class, "Missile", 1, this, 128, 1, true);
		EntityRegistry.registerModEntity(EntityWeaponOrb.class, "weaponOrb", 2, this, 64, 1, true);

//		livingeventhooks = new LivingEventHooks();
//		MinecraftForge.EVENT_BUS.register(livingeventhooks);

//		GameRegistry.addRecipe(new ItemStack(Booster08),
//                new Object[]{ "XRX","XPX","X X",
//                Character.valueOf('X'),Item.ingotIron,
//                Character.valueOf('R'),Item.redstoneRepeater,
//                Character.valueOf('P'),Block.pistonBase});
//
//		GameRegistry.addRecipe(new ItemStack(Booster20),
//                new Object[]{ "I I"," B ","IDI",
//                Character.valueOf('B'),Booster08,
//                Character.valueOf('I'),Item.ingotIron,
//                Character.valueOf('D'),Item.diamond});

		if(getClass().getPackage() != null)
		{
//			GameRegistry.addShapelessRecipe(new ItemStack(Booster08,1), new Object[]{ new ItemStack(Block.dirt, 1)});
//			GameRegistry.addShapelessRecipe(new ItemStack(Booster20,1), new Object[]{ new ItemStack(Block.sand, 1)});
			GameRegistry.addShapelessRecipe(new ItemStack(Item.plateChain,1), new Object[]{ new ItemStack(Block.workbench, 1)});
		}
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
//		LanguageRegistry.addName(Booster08, "Booster0.8");
//		LanguageRegistry.addName(Booster20, "Booster2.0");
		LanguageRegistry.instance().addStringLocalization("Key.BoosterSwitch", "ja_JP", "ブースタースイッチ");
		LanguageRegistry.instance().addStringLocalization("Key.BoosterSwitch", "en_US", "BoosterSwitch");
	}
}
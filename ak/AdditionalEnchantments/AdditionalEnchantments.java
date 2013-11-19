package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="AdditionalEnchantments", name="AdditionalEnchantments", version="1.1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class AdditionalEnchantments
{
	@Mod.Instance("AdditionalEnchantments")
	public static AdditionalEnchantments instance;
//	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
//	public static CommonProxy proxy;
	
	public static Enchantment vorpal;
	public static boolean addVorpal;
	public static int idVorpal;
	public static Enchantment disjunction;
	public static boolean addDisjunction;
	public static int idDisjunction;
	public static Enchantment waterAspect;
	public static boolean addWaterAspect;
	public static int idWaterAspect;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		addVorpal = config.get(Configuration.CATEGORY_GENERAL, "addVorpal", true, "add Vorpal Enchantment").getBoolean(true);
		idVorpal = config.get(Configuration.CATEGORY_GENERAL, "idVorpal", 22, "Vorpal Enchantment Id").getInt();
		addDisjunction = config.get(Configuration.CATEGORY_GENERAL, "addDisjunction", true, "add Disjunction Enchantment").getBoolean(true);
		idDisjunction = config.get(Configuration.CATEGORY_GENERAL, "idDisjunction", 23, "Disjunction Enchantment Id").getInt();
		addWaterAspect = config.get(Configuration.CATEGORY_GENERAL, "addWaterAspect", true, "add WaterAspect Enchantment").getBoolean(true);;
		idWaterAspect = config.get(Configuration.CATEGORY_GENERAL, "idWaterAspect", 24, "WaterAspect Enchantment Id").getInt();;
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		if(addVorpal)
		{
			vorpal = new EnchantmentVorpal(idVorpal, 2).setName("vorpal");
			addLocalLanguage("enchantment.vorpal", "Vorpal", "首狩り");
			MinecraftForge.EVENT_BUS.register(new VorpalEventHook());
		}
		if(addDisjunction)
		{
			disjunction = new EnchantmentDisjunction(idDisjunction, 5).setName("disjunction");
			addLocalLanguage("enchantment.disjunction", "Disjunction", "エンダーマン特効");
		}
		if(addWaterAspect)
		{
			waterAspect = new EnchantmentWaterAspect(idWaterAspect, 5).setName("wateraspect");
			addLocalLanguage("enchantment.wateraspect", "Water Aspect", "水属性");
		}
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
	public static void addLocalLanguage(String key, String en, String jp)
	{
		LanguageRegistry.instance().addStringLocalization(key, en);
		LanguageRegistry.instance().addStringLocalization(key,"ja_JP", jp);
	}
}
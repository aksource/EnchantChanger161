package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="AdditionalEnchantments", name="AdditionalEnchantments", version="1.2a",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class AdditionalEnchantments
{
	@Mod.Instance("AdditionalEnchantments")
	public static AdditionalEnchantments instance;
	
	public static Enchantment vorpal;
	public static boolean addVorpal;
	public static int idVorpal;
	public static Enchantment disjunction;
	public static boolean addDisjunction;
	public static int idDisjunction;
	public static Enchantment waterAspect;
	public static boolean addWaterAspect;
	public static int idWaterAspect;
	public static Enchantment magicProtection;
	public static boolean addMagicProtection;
	public static int idMagicProtection;
	public static Enchantment voidJump;
	public static boolean addVoidJump;
	public static int idVoidJump;
	
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
		addMagicProtection = config.get(Configuration.CATEGORY_GENERAL, "addMagicProtection", true, "add Magic Protection Enchantment").getBoolean(true);;
		idMagicProtection = config.get(Configuration.CATEGORY_GENERAL, "idMagicProtection", 10, "Magic Protection Enchantment Id").getInt();;
		addVoidJump = config.get(Configuration.CATEGORY_GENERAL, "addVoidJump", true, "add Void Jump Enchantment").getBoolean(true);;
		idVoidJump = config.get(Configuration.CATEGORY_GENERAL, "idVoidJump", 11, "Void Jump Enchantment Id").getInt();;
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		if(addVorpal)
		{
			vorpal = new EnchantmentVorpal(idVorpal, 2).setName("vorpal");
			MinecraftForge.EVENT_BUS.register(new VorpalEventHook());
		}
		if(addDisjunction)
		{
			disjunction = new EnchantmentDisjunction(idDisjunction, 5).setName("disjunction");
		}
		if(addWaterAspect)
		{
			waterAspect = new EnchantmentWaterAspect(idWaterAspect, 5).setName("wateraspect");
		}
		if(addMagicProtection)
		{
			magicProtection = new EnchantmentMagicProtection(idMagicProtection, 5).setName("magicprotection");
		}
		if(addVoidJump)
		{
			voidJump = new EnchantmentVoidJump(idVoidJump, 1).setName("voidjump");
			MinecraftForge.EVENT_BUS.register(new VoidJumpEventHook());
		}
	}
}
package ak.VillagerTweaks;

import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="VillagerTweaks", name="VillagerTweaks", version="1.0",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class VillagerTweaks
{
	@Mod.Instance("VillagerTweaks")
	public static VillagerTweaks instance;
//	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
//	public static CommonProxy proxy;
	public static int changeTradeItem;
	public static int changeProfessionItem;
	public static int setMatingItem;
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		changeTradeItem = config.get(Configuration.CATEGORY_GENERAL, "ChangeTradeItem", Item.ingotGold.itemID, "set Trade Changing Item ID0").getInt();
		changeProfessionItem = config.get(Configuration.CATEGORY_GENERAL, "ChangfeProfesstionItem", Item.appleGold.itemID, "set Profession Changing Item ID").getInt();
//		setMatingItem = config.get(Configuration.CATEGORY_GENERAL, "SetMateItem", Item.diamond.itemID, "set Mating Item ID").getInt();
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new VillagerInteractHook());
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
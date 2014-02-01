package ak.MultiToolHolders;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="MultiToolHolders", name="MultiToolHolders", version="1.2d",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"MTH|Tool"}, packetHandler=PacketHandler.class)
public class MultiToolHolders
{
	public static int ItemIDShift;
	public static  Item ItemMultiToolHolder3;
	public static  Item ItemMultiToolHolder5;
	public static  Item ItemMultiToolHolder7;
	public static  Item ItemMultiToolHolder9;

	public static String GuiToolHolder3 ="textures/gui/ToolHolder3.png";
	public static String GuiToolHolder5 ="textures/gui/ToolHolder5.png";
	public static String GuiToolHolder7 ="textures/gui/ToolHolder7.png";
	public static String GuiToolHolder9 ="textures/gui/ToolHolder9.png";
	public static String TextureDomain = "multitoolholders:";
	public static String Assets = "multitoolholders";

	public static String itemTexture = "textures/items.png";


	@Mod.Instance("MultiToolHolders")
	public static MultiToolHolders instance;
	@SidedProxy(clientSide = "ak.MultiToolHolders.Client.ClientProxy", serverSide = "ak.MultiToolHolders.CommonProxy")
	public static CommonProxy proxy;
	public static int guiIdHolder3 = 0;
	public static int guiIdHolder5 = 1;
	public static int guiIdHolder9 = 2;
	public static int guiIdHolder7 = 3;


	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ItemIDShift = config.get(Configuration.CATEGORY_ITEM, "Item ID Shift Number", 7000).getInt();
		config.save();
		ItemMultiToolHolder3 = (new ItemMultiToolHolder(ItemIDShift - 256, 3)).setUnlocalizedName(this.TextureDomain + "Holder3").setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(ItemMultiToolHolder3, "itemmultitoolholder3");
		ItemMultiToolHolder5 = (new ItemMultiToolHolder(ItemIDShift - 256 + 1, 5)).setUnlocalizedName(this.TextureDomain + "Holder5").setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(ItemMultiToolHolder5, "itemmultitoolholder5");
		ItemMultiToolHolder9 = (new ItemMultiToolHolder(ItemIDShift - 256 + 2, 9)).setUnlocalizedName(this.TextureDomain + "Holder9").setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(ItemMultiToolHolder9, "itemmultitoolholder9");
		ItemMultiToolHolder7 = (new ItemMultiToolHolder(ItemIDShift - 256 + 3, 7)).setUnlocalizedName(this.TextureDomain + "Holder7").setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(ItemMultiToolHolder7, "itemmultitoolholder7");
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		proxy.registerClientInformation();
		proxy.registerTileEntitySpecialRenderer();

		GameRegistry.addRecipe(new ItemStack(ItemMultiToolHolder3), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.ingotIron,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Block.tripWireSource});
		GameRegistry.addRecipe(new ItemStack(ItemMultiToolHolder7), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.ingotGold,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Block.tripWireSource});
		GameRegistry.addRecipe(new ItemStack(ItemMultiToolHolder9), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), Item.diamond,Character.valueOf('B'),Block.chest, Character.valueOf('C'),Block.tripWireSource});
		GameRegistry.addRecipe(new ItemStack(ItemMultiToolHolder5), new Object[]{"AAA","ABA", "CCC", Character.valueOf('A'), new ItemStack(Item.dyePowder,1,4),Character.valueOf('B'),Block.chest, Character.valueOf('C'),Block.tripWireSource});
	}
}
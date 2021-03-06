package ak.DeleteRecipes;

import java.util.Iterator;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.Configuration;
import ak.EnchantChanger.EnchantChanger;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="DeleteRecipes", name="DeleteRecipes", version="1.0",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class DeleteRecipes
{
	@Mod.Instance("modID")
	public static DeleteRecipes instance;
//	@SidedProxy(clientSide = "ClientProxy", serverSide = "CommonProxy")
//	public static CommonProxy proxy;
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{

	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		DeleteRecipe(new ItemStack(EnchantChanger.MateriaID,1,0));
	}
	public static void DeleteRecipe(Item item)
	{
		DeleteRecipe(new ItemStack(item));
	}
	public static void DeleteRecipe(ItemStack par1ItemStack)
	{
		List recipes = CraftingManager.getInstance().getRecipeList();

		for(Iterator i = recipes.listIterator(); i.hasNext();)
		{
			IRecipe recipe = (IRecipe)i.next();
			ItemStack is = recipe.getRecipeOutput();

			if(is != null)
			{
				if(is.itemID == par1ItemStack.itemID)
				{
					i.remove();
				}
			}
		}
	}
}
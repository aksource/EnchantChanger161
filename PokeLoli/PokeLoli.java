package PokeLoli;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="PokeLoli", name="PokeLoli", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class PokeLoli
{
	@Mod.Instance("PokeLoli")
	public static PokeLoli instance;
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new PokeLoliEventHandler());
		Item.monsterPlacer = (new plItemMobegg(127)).setUnlocalizedName("monsterPlacer");
		LanguageRegistry.addName(Item.monsterPlacer, "Mob Egg");
		LanguageRegistry.instance().addNameForObject(Item.monsterPlacer, "ja_JP", "モブエッグ");
		GameRegistry.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, 0), new Object[]{
            Item.egg, Item.redstone});
	}
}
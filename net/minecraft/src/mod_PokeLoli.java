package net.minecraft.src;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.PokeLoli.PokeLoliEventHandler;
import net.minecraft.src.PokeLoli.plItemMobegg;
import net.minecraftforge.common.MinecraftForge;

public class mod_PokeLoli extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-2";
	}

	public void load()
	{
		MinecraftForge.EVENT_BUS.register(new PokeLoliEventHandler());
//		Item.itemsList[127+256] = null;
		Item.monsterPlacer = (new plItemMobegg(127)).setUnlocalizedName("monsterPlacer");
		ModLoader.addName(Item.monsterPlacer, "Mob Egg");
		ModLoader.addName(Item.monsterPlacer, "ja_JP", "モブエッグ");
		ModLoader.addShapelessRecipe(new ItemStack(Item.monsterPlacer, 1, 0), new Object[]{
            Item.egg, Item.redstone});
	}

	public static void addChat(String str)
	{
		ModLoader.getMinecraftInstance().ingameGUI.getChatGUI().printChatMessage(str);

	}
}

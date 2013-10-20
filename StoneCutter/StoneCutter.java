package StoneCutter;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="StoneCutter", name="StoneCutter", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class StoneCutter
{
	@Mod.Instance("StoneCutter")
	public static StoneCutter instance;
    int cutterItemID = 6000;
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
        ItemStoneCutter cutter = (ItemStoneCutter)(new ItemStoneCutter(cutterItemID-256)).setUnlocalizedName("cutter");
        GameRegistry.registerCraftingHandler(cutter);
        
        LanguageRegistry.addName(cutter, "StoneCutter");
        GameRegistry.addShapelessRecipe(new ItemStack(44, 2, 3),
            new ItemStack(Block.cobblestone), new ItemStack(cutter,1,32767));
	}
}
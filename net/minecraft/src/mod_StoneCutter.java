package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.src.StoneCutter.ItemStoneCutter;
import cpw.mods.fml.common.registry.GameRegistry;

public class mod_StoneCutter extends BaseMod {

    public String getVersion() {
        return "1.0.2";
    }
    
    int cutterItemID = 6000;
    //Catは猫です．
    public void load()
    {
        ItemStoneCutter cutter = (ItemStoneCutter)(new ItemStoneCutter(cutterItemID-256)).setUnlocalizedName("cutter");
        GameRegistry.registerCraftingHandler(cutter);
        
        ModLoader.addName(cutter, "StoneCutter");
        ModLoader.addShapelessRecipe(new ItemStack(44, 2, 3),
            new ItemStack(Block.cobblestone), new ItemStack(cutter,1,32767));
    }
}
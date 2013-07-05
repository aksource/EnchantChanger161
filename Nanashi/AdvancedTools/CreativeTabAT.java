package Nanashi.AdvancedTools;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabAT extends CreativeTabs
{
	public CreativeTabAT(String var1)
    {
        super(var1);
    }

    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return AdvancedTools.UGDiamondPickaxe.itemID;
    }
    public String getTranslatedTabLabel()
    {
    	return "Adv.Tools";
    }
}
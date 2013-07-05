package Nanashi.AdvancedTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnhancer extends Item
{
    private int id;

    protected ItemEnhancer(int var1)
    {
        super(var1);
        this.id = var1;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	if(id == AdvancedTools.ItemID_INDEX )
    		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "EnhancerR");
    	else
    		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "EnhancerB");
    }
    public boolean hasEffect(ItemStack var1)
    {
        return true;
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        if(this.id == AdvancedTools.ItemID_INDEX)
        	return EnumRarity.uncommon;
        else
        	return EnumRarity.rare;
    }
}

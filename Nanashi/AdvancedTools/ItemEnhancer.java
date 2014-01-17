package Nanashi.AdvancedTools;

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
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return true;
    }
    @Override
    public EnumRarity getRarity(ItemStack var1)
    {
        if(this.id == AdvancedTools.ItemID_INDEX)
        	return EnumRarity.uncommon;
        else
        	return EnumRarity.rare;
    }
}

package net.minecraft.src.StackableTools;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.src.mod_StackableTools;

public class ItemBowStack extends ItemBow
{
	public ItemBowStack(int par1)
	{
		super(par1);
		this.setMaxStackSize(mod_StackableTools.BowMax);
		this.setHasSubtypes(true);
		this.func_111206_d("bow");
	}

	public boolean isItemTool(ItemStack par1ItemStack)
	{
		return par1ItemStack.stackSize == 1;
	}

	public boolean isDamageable()
	{
		return true;
	}

}

package StackableTools;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public class ItemBowStack extends ItemBow
{
	public ItemBowStack(int par1)
	{
		super(par1);
		this.setMaxStackSize(StackableTools.BowMax);
		this.setHasSubtypes(true);
		this.setTextureName("bow");
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

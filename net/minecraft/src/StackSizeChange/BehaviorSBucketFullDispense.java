package net.minecraft.src.StackSizeChange;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.src.mod_StackSizeChange;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;

public class BehaviorSBucketFullDispense extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem defaultItemDispenseBehavior = new BehaviorDefaultDispenseItem();

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        ItemBucket itembucket = (ItemBucket)par2ItemStack.getItem();
        int i = par1IBlockSource.getXInt();
        int j = par1IBlockSource.getYInt();
        int k = par1IBlockSource.getZInt();
        EnumFacing enumfacing = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());

        if (itembucket.tryPlaceContainedLiquid(par1IBlockSource.getWorld(), i + enumfacing.getFrontOffsetX(), j + enumfacing.getFrontOffsetY(), k + enumfacing.getFrontOffsetZ()))
        {
	        if (--par2ItemStack.stackSize == 0)
	        {
	            par2ItemStack.itemID = mod_StackSizeChange.bucketEmpty.itemID;
	            par2ItemStack.stackSize = 1;
	        }
	        else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).addItem(new ItemStack(mod_StackSizeChange.bucketEmpty)) < 0)
	        {
	            this.defaultItemDispenseBehavior.dispense(par1IBlockSource, new ItemStack(mod_StackSizeChange.bucketEmpty));
	        }
            return par2ItemStack;
        }
        else
        {
            return this.defaultItemDispenseBehavior.dispense(par1IBlockSource, par2ItemStack);
        }
    }

}

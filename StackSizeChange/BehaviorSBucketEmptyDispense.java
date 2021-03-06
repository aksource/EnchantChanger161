package StackSizeChange;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorSBucketEmptyDispense extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem dispenseItem = new BehaviorDefaultDispenseItem();

    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing enumfacing = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        World world = par1IBlockSource.getWorld();
        int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
        int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
        int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();
        Material material = world.getBlockMaterial(i, j, k);
        int l = world.getBlockMetadata(i, j, k);
        Item item;

        if (Material.water.equals(material) && l == 0)
        {
            item = StackSizeChange.bucketWater;
        }
        else
        {
            if (!Material.lava.equals(material) || l != 0)
            {
                return super.dispenseStack(par1IBlockSource, par2ItemStack);
            }

            item = StackSizeChange.bucketLava;
        }

        world.setBlockToAir(i, j, k);

        if (--par2ItemStack.stackSize == 0)
        {
            par2ItemStack.itemID = item.itemID;
            par2ItemStack.stackSize = 1;
        }
        else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).addItem(new ItemStack(item)) < 0)
        {
            this.dispenseItem.dispense(par1IBlockSource, new ItemStack(item));
        }

        return par2ItemStack;
    }
}

package net.minecraft.src.FarmOptimize;

import java.util.Random;

import net.minecraft.block.BlockCocoa;
import net.minecraft.src.mod_FarmOptimize;
import net.minecraft.world.World;

public class foBlockCocoa extends BlockCocoa
{
    public foBlockCocoa(int par1)
    {
        super(par1);
        this.func_111022_d("cocoa");
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlock(par2, par3, par4,0,0,3);
        }
        else if (mod_FarmOptimize.growSpeedCocoa == 0 
			|| par1World.rand.nextInt(mod_FarmOptimize.growSpeedCocoa) == 0)
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4);
            int var7 = func_72219_c(var6);

            if (var7 < 2)
            {
                ++var7;
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 << 2 | getDirection(var6), 3);
            }
        }
    }

}

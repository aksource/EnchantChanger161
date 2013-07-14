package net.minecraft.src.FarmOptimize;

import java.util.Random;

import net.minecraft.block.BlockNetherStalk;
import net.minecraft.src.mod_FarmOptimize;
import net.minecraft.world.World;

public class foBlockNetherStalk extends BlockNetherStalk
{
    public foBlockNetherStalk(int par1)
    {
        super(par1);
        this.func_111022_d("nether_wart");
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);

        if (var6 < 3 && (mod_FarmOptimize.growSpeedNetherWart == 0 
			|| par5Random.nextInt(mod_FarmOptimize.growSpeedNetherWart) == 0))
        {
            ++var6;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6,3);
        }

        super.updateTick(par1World, par2, par3, par4, par5Random);
    }

}

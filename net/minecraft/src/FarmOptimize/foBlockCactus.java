package net.minecraft.src.FarmOptimize;

import java.util.Random;

import net.minecraft.block.BlockCactus;
import net.minecraft.src.mod_FarmOptimize;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class foBlockCactus extends BlockCactus
{
    public foBlockCactus(int par1)
    {
        super(par1);
        this.func_111022_d("cactus");
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.isAirBlock(par2, par3 + 1, par4))
        {
            int var6;

            for (var6 = 1; par1World.getBlockId(par2, par3 - var6, par4) == this.blockID; ++var6)
            {
                ;
            }

            if (var6 < mod_FarmOptimize.CactusLimit)
            {
                int var7 = par1World.getBlockMetadata(par2, par3, par4);

                if (var7 >= mod_FarmOptimize.CactusSpeed)
                {
                    par1World.setBlock(par2, par3 + 1, par4, this.blockID, 0, 3);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, 0,3);
                }
                else
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + 1,3);
                }
            }
        }
    }

	@ForgeSubscribe
	public void useBonemeal(BonemealEvent event)
	{
		if(!event.world.isRemote && event.ID == blockID && canBlockStay(event.world, event.X, event.Y, event.Z))
		{
            int size;
            for (size = event.Y; canBlockStay(event.world, event.X, size, event.Z); --size)
            {
                ;
            }
            int min = size + 1;
			int top = 0;
            for (size = min; (size - min + 1) < mod_FarmOptimize.CactusUsedBonemealLimit; size++)
            {
                if(event.world.isAirBlock(event.X, size + 1, event.Z))
				{
                    event.world.setBlock(event.X, size + 1, event.Z, this.blockID,0,3);
					top++;
				}
            }
			if(top > 0)
			{
				event.setResult(Result.ALLOW);
			}
		}
	}

}

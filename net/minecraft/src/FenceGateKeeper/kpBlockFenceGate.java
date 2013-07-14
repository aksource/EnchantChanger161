package net.minecraft.src.FenceGateKeeper;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.world.IBlockAccess;

public class kpBlockFenceGate extends BlockFenceGate
{
    public kpBlockFenceGate(int par1)
    {
        super(par1);
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return false;
    }

}

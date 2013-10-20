package LilypadBreed;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockLilypadBreed extends BlockLilyPad
{
	public BlockLilypadBreed(int par1)
	{
		super(par1);
	}
	public int getRenderType()
	{
		return 23;
	}
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(!par1World.isRemote 
			&& par5EntityPlayer.getCurrentEquippedItem() != null
			&& par5EntityPlayer.getCurrentEquippedItem().itemID == Item.dyePowder.itemID
			&& par5EntityPlayer.getCurrentEquippedItem().getItemDamage() == 15)
		{
			par5EntityPlayer.getCurrentEquippedItem().stackSize--;
			for(int x = -2 + par2; x < 3 + par2; x++){
			for(int z = -2 + par4; z < 3 + par4; z++){
				if(par1World.getBlockId(x, par3 - 1 , z) == Block.waterMoving.blockID
					|| par1World.getBlockId(x, par3 - 1 , z) == Block.waterStill.blockID
					&& par1World.getBlockId(x, par3, z) == 0)
				{
					if(x == par2 && z == par4
						|| par1World.rand.nextInt(100) < LilypadBreed.LilypadRate)
					{
						par1World.setBlock(x, par3, z, blockID,0,3);
					}
				}
			}}
			return true;
		}
		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
	}
}

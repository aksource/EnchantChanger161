package ak.EnchantChanger;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
public class EcBlockHugeMateria extends BlockContainer
{
	private boolean isBottom;
	public EcBlockHugeMateria(int par1)
	{
		super(par1, Material.rock);
		setHardness(5F);
		setResistance(2000.0F);
//		setBlockName("HugeMateria");
        this.setLightOpacity(0);
//        this.blockIndexInTexture = 49;
        this.setLightValue(1.0f);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("glass");
	}
	public boolean renderAsNormalBlock()
    {
        return false;
    }
//	public String getTextureFile()
//	{
//		return EnchantChanger.EcTerrain;
//	}
	public boolean isOpaqueCube()
    {
        return false;
    }

	public int quantityDropped(Random random)
    {
        return 1;
    }

//	public void addCreativeItems(ArrayList itemList)
//	{
//		itemList.add(new ItemStack(this, 1, 0));
//	}
	@Override
	public TileEntity createNewTileEntity(World var1)
    {
		return new EcTileEntityHugeMateria();
    }
	@Override
	public TileEntity createTileEntity(World world, int metadata)
    {
        return metadata == 0 ?createNewTileEntity(world):null;
    }
	@Override
    public int getRenderType()
	{
		return -1;
	}
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
		if(par1World.isRemote)
		{
			return true;
		}
		else if(par1World.getBlockId(par2, par3-1, par4) == this.blockID && par1World.getBlockMetadata(par2, par3, par4) != 0)
		{
			return this.onBlockActivated(par1World, par2, par3 - 1, par4, par5EntityPlayer, par6, par7, par8, par9);
		}
		else
		{
			if(par1World.getBlockTileEntity(par2, par3, par4) != null)
				par5EntityPlayer.openGui(EnchantChanger.instance,EnchantChanger.guiIdHugeMateria,par1World,par2,par3,par4);
			return true;
		}

    }
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);

		if (var6 != 0)
		{
			if(var6 ==1)
			{
				if (par1World.getBlockId(par2, par3 - 1, par4) != this.blockID || par1World.getBlockId(par2, par3 + 1, par4) != this.blockID)
				{
					par1World.setBlock(par2, par3, par4, 0, 0, 1);
				}
				if (par5 > 0 && par5 != this.blockID)
				{
					this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
					this.onNeighborBlockChange(par1World, par2, par3 + 1, par4, par5);
				}
			}
			else//var6 ==2
			{
				if (par1World.getBlockId(par2, par3 - 1, par4) != this.blockID)
				{
					par1World.setBlock(par2, par3, par4, 0, 0, 1);
				}
//				if (par5 > 0 && par5 != this.blockID)
//				{
//					this.onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
//				}
			}
		}
		else
		{
			boolean var7 = false;

			if (par1World.getBlockId(par2, par3 + 1, par4) != this.blockID)
			{
				par1World.setBlock(par2, par3, par4, 0, 0, 1);
				var7 = true;
			}

//			if (!par1World.isBlockSolidOnSide(par2, par3 - 1, par4, 1))
//			{
//				par1World.setBlockWithNotify(par2, par3, par4, 0);
//				var7 = true;
//
//				if (par1World.getBlockId(par2, par3 + 1, par4) == this.blockID)
//				{
//					par1World.setBlockWithNotify(par2, par3 + 1, par4, 0);
//				}
//			}

			if (var7)
			{
				if (!par1World.isRemote)
				{
					this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
				}
			}
		}
	}
	/**
     * Called whenever the block is removed.
     */
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
       if(par1World.getBlockTileEntity(par2, par3, par4) != null)
       {
    	   EcTileEntityHugeMateria t = (EcTileEntityHugeMateria)par1World.getBlockTileEntity(par2, par3, par4);
          for(int l = 0; l < t.getSizeInventory(); l++)
          {
             ItemStack ist = t.getStackInSlot(l);
             if(ist == null || ist.stackSize <= 0)
             {
                continue;
             }
             EntityItem eit = new EntityItem(par1World, (double)par2+0.5D, (double)par3+0.5D, (double)par4+0.5D, ist);
             par1World.spawnEntityInWorld(eit);
          }
       }
       super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		switch(var1)
		{
		case 0:this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F);return;
		case 1:this.setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F);return;
		case 2:this.setBlockBounds(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F);return;
		}
//		if(par1IBlockAccess.getBlockId(par2, par3 - 1, par4) == this.blockID)
//			if(par1IBlockAccess.getBlockId(par2, par3 + 1, par4) == this.blockID)
//				this.setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F);
//			else if(var1 ==2)
//				this.setBlockBounds(0.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//		else
//			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F);
	}
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return par1  != 0 ? 0: EnchantChanger.ItemHugeMateria.itemID;
	}
	public boolean isBottom()
	{
		return this.isBottom;
	}
	public void setBottom(boolean par1)
	{
		this.isBottom = par1;
	}
}
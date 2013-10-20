package StackSizeChange;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ItemBucketStack extends ItemBucket
{
	private int isFull;

	public ItemBucketStack(int par1, int par2)
	{
		super(par1, par2);
		this.isFull = par2;
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setTextureName(par1 == 69 ?"bucket_empty": par1 == 70 ? "bucket_water":"bucket_lava");
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		float var4 = 1.0F;
		double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)var4;
		double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par3EntityPlayer.yOffset;
		double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)var4;
		boolean var11 = this.isFull == 0;
		MovingObjectPosition var12 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, var11);

		if (var12 == null)
		{
			return par1ItemStack;
		}
		else
		{
			FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, par1ItemStack, par2World, var12);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				return par1ItemStack;
			}

			if (event.getResult() == Event.Result.ALLOW)
			{
				return StackSizeChange.addropItems(par1ItemStack, par3EntityPlayer, event.result);
			}

			if (var12.typeOfHit == EnumMovingObjectType.TILE)
			{
				int var13 = var12.blockX;
				int var14 = var12.blockY;
				int var15 = var12.blockZ;

				if (!par2World.canMineBlock(par3EntityPlayer, var13, var14, var15))
				{
					return par1ItemStack;
				}

				if (this.isFull == 0)
				{
					if (!par3EntityPlayer.canPlayerEdit(var13, var14, var15, var12.sideHit, par1ItemStack))
					{
						return par1ItemStack;
					}

					if (par2World.getBlockMaterial(var13, var14, var15) == Material.water && par2World.getBlockMetadata(var13, var14, var15) == 0)
					{
//						par2World.setBlockWithNotify(var13, var14, var15, 0);
						par2World.setBlockToAir(var13, var14, var15);
						return StackSizeChange.addropItems(par1ItemStack, par3EntityPlayer, new ItemStack(StackSizeChange.bucketWater));
					}

					if (par2World.getBlockMaterial(var13, var14, var15) == Material.lava && par2World.getBlockMetadata(var13, var14, var15) == 0)
					{
//						par2World.setBlockWithNotify(var13, var14, var15, 0);
						par2World.setBlockToAir(var13, var14, var15);
						return StackSizeChange.addropItems(par1ItemStack, par3EntityPlayer, new ItemStack(StackSizeChange.bucketLava));
					}
				}
				else
				{
					if (this.isFull < 0)
					{
						return StackSizeChange.addropItems(par1ItemStack, par3EntityPlayer, new ItemStack(StackSizeChange.bucketEmpty));
					}

					if (var12.sideHit == 0)
					{
						--var14;
					}

					if (var12.sideHit == 1)
					{
						++var14;
					}

					if (var12.sideHit == 2)
					{
						--var15;
					}

					if (var12.sideHit == 3)
					{
						++var15;
					}

					if (var12.sideHit == 4)
					{
						--var13;
					}

					if (var12.sideHit == 5)
					{
						++var13;
					}

					if (!par3EntityPlayer.canPlayerEdit(var13, var14, var15, var12.sideHit, par1ItemStack))
					{
						return par1ItemStack;
					}

					if (this.tryPlaceContainedLiquid(par2World, var5, var7, var9, var13, var14, var15))
					{
						return StackSizeChange.addropItems(par1ItemStack, par3EntityPlayer, new ItemStack(StackSizeChange.bucketEmpty));
					}
				}
			}

			return par1ItemStack;
		}
	}

	public boolean tryPlaceContainedLiquid(World par1World, double par2, double par4, double par6, int par8, int par9, int par10)
	{
		if (this.isFull <= 0)
		{
			return false;
		}
		else if (!par1World.isAirBlock(par8, par9, par10) && par1World.getBlockMaterial(par8, par9, par10).isSolid())
		{
			return false;
		}
		else
		{
			if (par1World.provider.isHellWorld && this.isFull == Block.waterMoving.blockID && StackSizeChange.isNetherSetWater)
			{
				par1World.playSoundEffect(par2 + 0.5D, par4 + 0.5D, par6 + 0.5D, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

				for (int var11 = 0; var11 < 8; ++var11)
				{
					par1World.spawnParticle("largesmoke", (double)par8 + Math.random(), (double)par9 + Math.random(), (double)par10 + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			}
			else
			{
				par1World.setBlock(par8, par9, par10, this.isFull, 0, 3);
			}

			return true;
		}
	}

    public Icon getIconFromDamage(int par1)
    {
		if(!StackSizeChange.BucketReplace && StackSizeChange.addStackableBucket)
		{
			if(isFull == Block.waterMoving.blockID)
			{
				return Item.bucketWater.getIconFromDamage(par1);
			}
			else if(isFull == Block.lavaMoving.blockID)
			{
				return Item.bucketLava.getIconFromDamage(par1);
			}
			else
			{
				return Item.bucketEmpty.getIconFromDamage(par1);
			}
		}
		else
		{
	        return super.getIconFromDamage(par1);
		}
    }

}

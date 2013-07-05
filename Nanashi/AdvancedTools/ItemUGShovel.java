package Nanashi.AdvancedTools;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUGShovel extends ItemUGTool
{
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium};
	private EnumToolMaterial material;
	protected ItemUGShovel(int var1, EnumToolMaterial var2, float var3)
	{
		super(var1, 1, var2, blocksEffectiveAgainst, var3);
		this.material = var2;
	}

	protected ItemUGShovel(int var1, EnumToolMaterial var2)
	{
		super(var1, 1, var2, blocksEffectiveAgainst, 1.0F);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		if(this.getUnlocalizedName().equals("item.UpgradedWoodenShovel"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGWoodshovel");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedStoneShovel"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGStoneshovel");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedIronShovel"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGIronshovel");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedGoldenShovel"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGGoldshovel");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedDiamondShovel"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGDiamondshovel");
		}
		else if(this.getUnlocalizedName().equals("item.InfinityShovel"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "Infinityshovel");
		}
	}
	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block var1)
	{
		for (int var2 = 0; var2 < blocksEffectiveAgainst.length; ++var2)
		{
			if (blocksEffectiveAgainst[var2] == var1)
			{
				return true;
			}
		}

		return false;
	}
//	@Override
//	protected void searchAndDestroyBlock(World world,int var1, int var2, int var3, int var4, int var5, ItemStack var6, EntityPlayer var7)
//	{
//		if (var5 > 0)
//		{
//			if (Block.blocksList[var5] != Block.sand)
//			{
//				super.searchAndDestroyBlock(world, var1, var2, var3, var4, var5, var6, var7);
//			}
//			else
//			{
//				ArrayList var8 = new ArrayList();
//				var8.add(new ChunkPosition(var1, var2, var3));
//				int var9;
//				int var10;
//				int var11;
//				int var12;
//				int var13;
//				int var14;
//
//				if (!this.doChainDestraction(Block.blocksList[var5]))
//				{
//					var9 = var1 - this.destroyRange;
//					var10 = var2;
//					var11 = var3 - this.destroyRange;
//					var12 = var1 + this.destroyRange;
//					var13 = var2;
//					var14 = var3 + this.destroyRange;
//
//					if (var4 == 0 || var4 == 1)
//					{
//						var10 = var2;
//						var13 = var2;
//					}
//
//					if (var4 == 2 || var4 == 3)
//					{
//						var11 = var3;
//						var14 = var3;
//						--var10;
//					}
//
//					if (var4 == 4 || var4 == 5)
//					{
//						var9 = var1;
//						var12 = var1;
//						--var10;
//					}
//				}
//				else
//				{
//					var9 = var1 - this.cDestroyRange;
//					var10 = var2 - this.cDestroyRange;
//					var11 = var3 - this.cDestroyRange;
//					var12 = var1 + this.cDestroyRange;
//					var13 = var2 + this.cDestroyRange;
//					var14 = var3 + this.cDestroyRange;
//				}
//
//				ChunkPosition var15 = new ChunkPosition(var9, var10, var11);
//				ChunkPosition var16 = new ChunkPosition(var12, var13, var14);
//
//				for (int var17 = 0; var17 < 256; ++var17)
//				{
//					ArrayList var18 = new ArrayList();
//
//					for (int var19 = 0; var19 < var8.size(); ++var19)
//					{
//						int var20 = ((ChunkPosition)var8.get(var19)).x;
//						int var21 = ((ChunkPosition)var8.get(var19)).y;
//						int var22 = ((ChunkPosition)var8.get(var19)).z;
//						var18.addAll(this.searchAroundBlock(world,(ChunkPosition)var8.get(var19), var15, var16, var5, var6, var7));
//						ChunkPosition var23 = new ChunkPosition(var20, var21, var22);
//						ChunkPosition var24;
//
//						if (!this.destroyBlock(world,var23, var5, var6, var7))
//						{
//							do
//							{
//								++var21;
//								var24 = new ChunkPosition(var20, var21, var22);
//							}
//							while (this.destroyBlock(world, var24, var5, var6, var7));
//						}
//					}
//
//					if (var18.isEmpty())
//					{
//						break;
//					}
//
//					var8.clear();
//					var8.addAll(var18);
//				}
//			}
//		}
//	}
//	@Override
//	protected ArrayList searchAroundBlock(World world,ChunkPosition var1, ChunkPosition var2, ChunkPosition var3, int var4, ItemStack var5, EntityPlayer var6)
//	{
//		ArrayList var7 = new ArrayList();
//		ChunkPosition[] var8 = new ChunkPosition[9];
//
//		if (var1.z > var2.z)
//		{
//			var8[0] = new ChunkPosition(var1.x, var1.y, var1.z - 1);
//		}
//
//		if (var1.z < var3.z)
//		{
//			var8[1] = new ChunkPosition(var1.x, var1.y, var1.z + 1);
//		}
//
//		if (var1.x > var2.x)
//		{
//			var8[2] = new ChunkPosition(var1.x - 1, var1.y, var1.z);
//		}
//
//		if (var1.x < var3.x)
//		{
//			var8[3] = new ChunkPosition(var1.x + 1, var1.y, var1.z);
//		}
//
//		if (var1.y > var2.y)
//		{
//			var8[4] = new ChunkPosition(var1.x, var1.y - 1, var1.z);
//		}
//
//		for (int var9 = 0; var9 < 9; ++var9)
//		{
//			if (var8[var9] != null && this.destroyBlock(world, var8[var9], var4, var5, var6))
//			{
//				var7.add(var8[var9]);
//			}
//		}
//
//		return var7;
//	}

	public boolean doChainDestraction(Block var1)
	{
		return var1 == Block.blockClay || var1 == Block.gravel;
	}
}

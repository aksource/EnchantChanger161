package Nanashi.AdvancedTools;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUGAxe extends ItemUGTool
{
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.pumpkin, Block.pumpkinLantern};
	private EnumToolMaterial material;
	protected ItemUGAxe(int var1, EnumToolMaterial var2, float var3)
	{
		super(var1, 3, var2, blocksEffectiveAgainst, var3);
		this.material = var2;
	}

	protected ItemUGAxe(int var1, EnumToolMaterial var2)
	{
		super(var1, 3, var2, blocksEffectiveAgainst, 1.0F);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		if(this.getUnlocalizedName().equals("item.UpgradedWoodenAxe"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGWoodaxe");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedStoneAxe"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGStoneaxe");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedIronAxe"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGIronaxe");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedGoldenAxe"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGGoldaxe");
		}
		else if(this.getUnlocalizedName().equals("item.UpgradedDiamondAxe"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGDiamondaxe");
		}
		else if(this.getUnlocalizedName().equals("item.InfinityAxe"))
		{
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "Infinityaxe");
		}
	}

	public boolean canHarvestBlock(Block var1)
	{
		return var1.blockMaterial == Material.wood;
	}
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block != null && (par2Block.blockMaterial == Material.wood || par2Block.blockMaterial == Material.plants || par2Block.blockMaterial == Material.vine) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
    }
	public boolean doChainDestraction(Block var1)
	{
		return var1 == Block.wood;
	}
	@Override
	protected ArrayList searchAroundBlock(World world, ChunkPosition var1, ChunkPosition var2, ChunkPosition var3, int var4, ItemStack var5, EntityPlayer var6)
	{
		ArrayList var7 = new ArrayList();
		ChunkPosition[] var8 = new ChunkPosition[17];

		if (var1.y < var3.y)
		{
			var8[0] = new ChunkPosition(var1.x, var1.y + 1, var1.z);
		}

		if (var1.z > var2.z)
		{
			var8[1] = new ChunkPosition(var1.x, var1.y, var1.z - 1);
		}

		if (var1.z < var3.z)
		{
			var8[2] = new ChunkPosition(var1.x, var1.y, var1.z + 1);
		}

		if (var1.x > var2.x)
		{
			var8[3] = new ChunkPosition(var1.x - 1, var1.y, var1.z);
		}

		if (var1.x < var3.x)
		{
			var8[4] = new ChunkPosition(var1.x + 1, var1.y, var1.z);
		}

		if (Block.blocksList[var4] == Block.wood)
		{
			if (var1.z > var2.z && var1.x > var2.x)
			{
				var8[5] = new ChunkPosition(var1.x - 1, var1.y, var1.z - 1);
			}

			if (var1.z < var3.z && var1.x > var2.x)
			{
				var8[6] = new ChunkPosition(var1.x - 1, var1.y, var1.z + 1);
			}

			if (var1.z > var2.z && var1.x < var3.x)
			{
				var8[7] = new ChunkPosition(var1.x + 1, var1.y, var1.z - 1);
			}

			if (var1.z < var3.z && var1.x < var3.x)
			{
				var8[8] = new ChunkPosition(var1.x + 1, var1.y, var1.z + 1);
			}

			if (var1.y < var3.y)
			{
				if (var1.z > var2.z)
				{
					var8[13] = new ChunkPosition(var1.x, var1.y + 1, var1.z - 1);
				}

				if (var1.z < var3.z)
				{
					var8[14] = new ChunkPosition(var1.x, var1.y + 1, var1.z + 1);
				}

				if (var1.x > var2.x)
				{
					var8[15] = new ChunkPosition(var1.x - 1, var1.y + 1, var1.z);
				}

				if (var1.x < var3.x)
				{
					var8[16] = new ChunkPosition(var1.x + 1, var1.y + 1, var1.z);
				}

				if (var1.z > var2.z && var1.x > var2.x)
				{
					var8[9] = new ChunkPosition(var1.x - 1, var1.y + 1, var1.z - 1);
				}

				if (var1.z < var3.z && var1.x > var2.x)
				{
					var8[10] = new ChunkPosition(var1.x - 1, var1.y + 1, var1.z + 1);
				}

				if (var1.z > var2.z && var1.x < var3.x)
				{
					var8[11] = new ChunkPosition(var1.x + 1, var1.y + 1, var1.z - 1);
				}

				if (var1.z < var3.z && var1.x < var3.x)
				{
					var8[12] = new ChunkPosition(var1.x + 1, var1.y + 1, var1.z + 1);
				}
			}
		}
		else if (var1.y > var2.y)
		{
			var8[5] = new ChunkPosition(var1.x, var1.y - 1, var1.z);
		}

		for (int var9 = 0; var9 < 17; ++var9)
		{
			if (var8[var9] != null && this.destroyBlock(world,var8[var9], var4, var5, var6))
			{
				var7.add(var8[var9]);
			}
		}

		return var7;
	}
}

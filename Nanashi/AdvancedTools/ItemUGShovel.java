package Nanashi.AdvancedTools;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
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
		if(this.getUnlocalizedName().equals("item.UpgradedWoodenShovel")){
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGWoodshovel");
		}else if(this.getUnlocalizedName().equals("item.UpgradedStoneShovel")){
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGStoneshovel");
		}else if(this.getUnlocalizedName().equals("item.UpgradedIronShovel")){
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGIronshovel");
		}else if(this.getUnlocalizedName().equals("item.UpgradedGoldenShovel")){
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGGoldshovel");
		}else if(this.getUnlocalizedName().equals("item.UpgradedDiamondShovel")){
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGDiamondshovel");
		}else if(this.getUnlocalizedName().equals("item.InfinityShovel")){
	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "Infinityshovel");
		}
	}

	public boolean canHarvestBlock(Block var1)
	{
		for (int var2 = 0; var2 < blocksEffectiveAgainst.length; ++var2){
			if (blocksEffectiveAgainst[var2] == var1){
				return true;
			}
		}
		return false;
	}

	public boolean doChainDestraction(Block var1)
	{
		return checkArrays(var1, AdvancedTools.addBlockForShovel) && this.canHarvestBlock(var1);
	}
}

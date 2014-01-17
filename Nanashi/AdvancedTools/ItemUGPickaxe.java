package Nanashi.AdvancedTools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class ItemUGPickaxe extends ItemUGTool
{
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered, Block.railActivator};
    private EnumToolMaterial material;
	protected ItemUGPickaxe(int var1, EnumToolMaterial var2, float var3)
	{
		super(var1, 2, var2, blocksEffectiveAgainst, var3);
		this.material = var2;
	}

	protected ItemUGPickaxe(int var1, EnumToolMaterial var2)
	{
		super(var1, 2, var2, blocksEffectiveAgainst, 1.0F);
	}

	@Override
	public boolean canHarvestBlock(Block var1)
	{
		if (var1 == Block.obsidian){
			return this.toolMaterial.getHarvestLevel() == 3;
		}else if (var1 != Block.blockDiamond && var1 != Block.oreDiamond){
			if (var1 != Block.blockGold && var1 != Block.oreGold){
				if (var1 != Block.blockIron && var1 != Block.oreIron){
					if (var1 != Block.blockLapis && var1 != Block.oreLapis){
						if (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing){
							if (var1.blockMaterial == Material.rock){
								return true;
							}else{
								for (int var2 = 0; var2 < blocksEffectiveAgainst.length; ++var2){
									if (blocksEffectiveAgainst[var2] == var1){
										return true;
									}
								}
								return var1.blockMaterial == Material.iron;
							}
						}else{
							return this.toolMaterial.getHarvestLevel() >= 2;
						}
					}else{
						return this.toolMaterial.getHarvestLevel() >= 1;
					}
				}else{
					return this.toolMaterial.getHarvestLevel() >= 1;
				}
			}else{
				return this.toolMaterial.getHarvestLevel() >= 2;
			}
		}else{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
	}
	@Override
	public float getStrVsBlock(ItemStack var1, Block var2)
	{
		return var2 != null && (var2.blockMaterial == Material.iron || var2.blockMaterial == Material.rock) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(var1, var2);
	}
	@Override
	public boolean doChainDestraction(Block var1)
	{
		return checkArrays(var1, AdvancedTools.addBlockForPickaxe) && this.canHarvestBlock(var1);
//		return var1 == Block.oreDiamond ? this.toolMaterial.getHarvestLevel() >= 2 : (var1 == Block.oreGold ? this.toolMaterial.getHarvestLevel() >= 2 : (var1 == Block.oreIron ? this.toolMaterial.getHarvestLevel() >= 1 : (var1 == Block.oreLapis ? this.toolMaterial.getHarvestLevel() >= 1 : (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing ? var1 == Block.oreCoal || var1 == Block.oreNetherQuartz : this.toolMaterial.getHarvestLevel() >= 2))));
	}

}

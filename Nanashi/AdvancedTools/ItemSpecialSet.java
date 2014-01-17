package Nanashi.AdvancedTools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class ItemSpecialSet extends Item
{
    EnumRarity r;

    protected ItemSpecialSet(int var1, EnumRarity var2)
    {
        super(var1);
        this.r = var2;
    }
    @Override
    public boolean hasEffect(ItemStack var1)
    {
        return true;
    }
	@Override
    public EnumRarity getRarity(ItemStack var1)
    {
        return this.r;
    }
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World var3, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            --par5;
        }

        if (par7 == 1)
        {
            ++par5;
        }

        if (par7 == 2)
        {
            --par6;
        }

        if (par7 == 3)
        {
            ++par6;
        }

        if (par7 == 4)
        {
            --par4;
        }

        if (par7 == 5)
        {
            ++par4;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            int var8 = var3.getBlockId(par4, par5, par6);
            int var9 = var3.getBlockId(par4 + 1, par5, par6);

            if (var8 == 0 && var9 == 0)
            {
                var3.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                var3.setBlock(par4, par5, par6, Block.chest.blockID);
                TileEntityChest var10 = (TileEntityChest)var3.getBlockTileEntity(par4, par5, par6);

                if (var10 != null)
                {
                    var10.setInventorySlotContents(0, new ItemStack(AdvancedTools.UGWoodPickaxe));
                    var10.setInventorySlotContents(1, new ItemStack(AdvancedTools.UGStonePickaxe));
                    var10.setInventorySlotContents(2, new ItemStack(AdvancedTools.UGIronPickaxe));
                    var10.setInventorySlotContents(3, new ItemStack(AdvancedTools.UGDiamondPickaxe));
                    var10.setInventorySlotContents(4, new ItemStack(AdvancedTools.UGGoldPickaxe));
                    var10.setInventorySlotContents(5, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(6, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(7, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(8, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(9, new ItemStack(AdvancedTools.UGWoodShovel));
                    var10.setInventorySlotContents(10, new ItemStack(AdvancedTools.UGStoneShovel));
                    var10.setInventorySlotContents(11, new ItemStack(AdvancedTools.UGIronShovel));
                    var10.setInventorySlotContents(12, new ItemStack(AdvancedTools.UGDiamondShovel));
                    var10.setInventorySlotContents(13, new ItemStack(AdvancedTools.UGGoldShovel));
                    var10.setInventorySlotContents(14, new ItemStack(AdvancedTools.LuckLuck));
                    var10.setInventorySlotContents(15, new ItemStack(AdvancedTools.SmashBat));
                    var10.setInventorySlotContents(16, new ItemStack(AdvancedTools.CrossBow));
                    var10.setInventorySlotContents(17, new ItemStack(Item.arrow, 64));
                    var10.setInventorySlotContents(18, new ItemStack(AdvancedTools.UGWoodAxe));
                    var10.setInventorySlotContents(19, new ItemStack(AdvancedTools.UGStoneAxe));
                    var10.setInventorySlotContents(20, new ItemStack(AdvancedTools.UGIronAxe));
                    var10.setInventorySlotContents(21, new ItemStack(AdvancedTools.UGDiamondAxe));
                    var10.setInventorySlotContents(22, new ItemStack(AdvancedTools.UGGoldAxe));
                    var10.setInventorySlotContents(23, new ItemStack(AdvancedTools.ThrowingKnife, 16));
                    var10.setInventorySlotContents(24, new ItemStack(AdvancedTools.ThrowingKnife, 16));
                    var10.setInventorySlotContents(25, new ItemStack(AdvancedTools.PoisonKnife, 16));
                    var10.setInventorySlotContents(26, new ItemStack(AdvancedTools.PoisonKnife, 16));
                }

                var3.setBlock(par4 + 1, par5, par6, Block.chest.blockID);
                var10 = (TileEntityChest)var3.getBlockTileEntity(par4 + 1, par5, par6);

                if (var10 != null)
                {
                    var10.setInventorySlotContents(0, new ItemStack(AdvancedTools.BlazeBlade));
                    var10.setInventorySlotContents(1, new ItemStack(AdvancedTools.IceHold));
                    var10.setInventorySlotContents(2, new ItemStack(AdvancedTools.AsmoSlasher));
                    var10.setInventorySlotContents(3, new ItemStack(AdvancedTools.PlanetGuardian));
                    var10.setInventorySlotContents(4, new ItemStack(AdvancedTools.StormBringer));
                    var10.setInventorySlotContents(5, new ItemStack(AdvancedTools.HolySaber));
                    var10.setInventorySlotContents(6, new ItemStack(AdvancedTools.DevilSword));
                    var10.setInventorySlotContents(7, new ItemStack(AdvancedTools.RedEnhancer, 64));
                    var10.setInventorySlotContents(8, new ItemStack(AdvancedTools.BlueEnhancer, 64));
                    var10.setInventorySlotContents(9, new ItemStack(AdvancedTools.InfiniteSword));
                    var10.setInventorySlotContents(10, new ItemStack(AdvancedTools.InfinitePickaxe));
                    var10.setInventorySlotContents(11, new ItemStack(AdvancedTools.InfiniteShovel));
                    var10.setInventorySlotContents(12, new ItemStack(AdvancedTools.InfiniteAxe));
                    var10.setInventorySlotContents(13, new ItemStack(AdvancedTools.InfiniteHoe));
                    var10.setInventorySlotContents(14, new ItemStack(Item.stick, 64));
                    var10.setInventorySlotContents(15, new ItemStack(Item.ingotIron, 64));
                    var10.setInventorySlotContents(16, new ItemStack(Item.ingotGold, 64));
                    var10.setInventorySlotContents(17, new ItemStack(Item.diamond, 64));
                    var10.setInventorySlotContents(18, new ItemStack(Block.wood, 64));
                    var10.setInventorySlotContents(19, new ItemStack(Item.blazeRod, 64));
                    var10.setInventorySlotContents(20, new ItemStack(Block.blockSnow, 64));
                    var10.setInventorySlotContents(21, new ItemStack(Item.feather, 64));
                    var10.setInventorySlotContents(22, new ItemStack(Block.glowStone, 64));
                    var10.setInventorySlotContents(23, new ItemStack(Item.seeds, 64));
                    var10.setInventorySlotContents(24, new ItemStack(Item.bucketWater));
                    var10.setInventorySlotContents(25, new ItemStack(Item.eyeOfEnder, 64));
                    var10.setInventorySlotContents(26, new ItemStack(Item.spiderEye, 64));
                    var10.setInventorySlotContents(27, new ItemStack(Item.redstone, 64));
                }
            }

            --par1ItemStack.stackSize;
            return true;
        }
    }
}

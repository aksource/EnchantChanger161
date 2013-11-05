package Nanashi.AdvancedTools;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpecialSet extends Item
{
    EnumRarity r;

    protected ItemSpecialSet(int var1, EnumRarity var2)
    {
        super(var1);
        this.r = var2;
    }

    public boolean hasEffect(ItemStack var1)
    {
        return true;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {

    }

    public EnumRarity getRarity(ItemStack var1)
    {
        return this.r;
    }

    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7)
    {
        if (var7 == 0)
        {
            --var5;
        }

        if (var7 == 1)
        {
            ++var5;
        }

        if (var7 == 2)
        {
            --var6;
        }

        if (var7 == 3)
        {
            ++var6;
        }

        if (var7 == 4)
        {
            --var4;
        }

        if (var7 == 5)
        {
            ++var4;
        }

        if (!var2.canPlayerEdit(var4, var5, var6, var7, var1))
        {
            return false;
        }
        else
        {
            int var8 = var3.getBlockId(var4, var5, var6);
            int var9 = var3.getBlockId(var4 + 1, var5, var6);

            if (var8 == 0 && var9 == 0)
            {
                var3.playSoundEffect((double)var4 + 0.5D, (double)var5 + 0.5D, (double)var6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                var3.setBlock(var4, var5, var6, Block.chest.blockID);
                TileEntityChest var10 = (TileEntityChest)var3.getBlockTileEntity(var4, var5, var6);

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

                var3.setBlock(var4 + 1, var5, var6, Block.chest.blockID);
                var10 = (TileEntityChest)var3.getBlockTileEntity(var4 + 1, var5, var6);

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

            --var1.stackSize;
            return true;
        }
    }
}

package compactengine;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.Position;
import buildcraft.energy.ItemEngine;
import buildcraft.energy.TileEngine;

public class ItemCompactEngine extends ItemEngine
{
    public ItemCompactEngine(int i)
    {
        super(i);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
		int meta = itemstack.getItemDamage()+1;
        return (meta > 5) ? null: "tile.CompactEngineWood.level_" + meta;
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        return (I18n.getString(this.getUnlocalizedName(par1ItemStack)));
    }

    @Override
    public int getBlockID()
    {
        return super.getBlockID();
    }
    @Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
    	int blockID = ((ItemBlock)this).getBlockID();
		if (!world.setBlock(x, y, z, blockID, metadata, 3))
		{
			return false;
		}
		if(!(world.getBlockTileEntity(x, y, z) instanceof TileEngine))
		{
			return false;
		}
		TileEngine tileengine = (TileEngine) world.getBlockTileEntity(x, y, z);
		tileengine.orientation = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[side]);
		ForgeDirection o = ForgeDirection.values()[ForgeDirection.OPPOSITES[side]];
		Position pos = new Position(tileengine.xCoord, tileengine.yCoord, tileengine.zCoord, o);
		pos.moveForwards(1);
		TileEntity tile = world.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);
		if (!tileengine.isPoweredTile(tile, o))
		{
			tileengine.switchOrientation(false);
		}

		if (world.getBlockId(x, y, z) == blockID)
		{
			Block.blocksList[blockID].onBlockPlacedBy(world, x, y, z, player, stack);
			Block.blocksList[blockID].onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}
}

package compactengine;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import buildcraft.energy.BlockEngine;
import buildcraft.energy.TileEngine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCompactEngine extends BlockEngine
{
	private static Icon woodTexture;

    public BlockCompactEngine(int i)
    {
        super(i);
        this.setResistance(10.0f);
        setUnlocalizedName("CompactEngine:CompactEngineWood");
    }
	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
//    	return new TileCompactEngine(metadata);
    	if(metadata == 0)
    		return new TileCompactEngine8();
    	else if(metadata == 1)
    		return new TileCompactEngine32();
    	else if(metadata == 2)
    		return new TileCompactEngine128();
    	else
    		return new TileCompactEngine512();
    }
    @Override
	public void getSubBlocks(int blockid, CreativeTabs par2CreativeTabs, List arraylist)
	{
        arraylist.add(new ItemStack(this, 1, 0));
        arraylist.add(new ItemStack(this, 1, 1));
        arraylist.add(new ItemStack(this, 1, 2));
		if(CompactEngine.isAddCompactEngine512and2048)
		{
	        arraylist.add(new ItemStack(this, 1, 3));
//	        arraylist.add(new ItemStack(this, 1, 4));
		}
//        arraylist.add(new ItemStack(this, 1, 5));
	}

    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
        TileEngine tileengine = (TileEngine)world.getBlockTileEntity(x, y, z);

		if(entityplayer.getCurrentEquippedItem() != null )
		{
			int itemID = entityplayer.getCurrentEquippedItem().getItem().itemID;

			if (entityplayer.capabilities.isCreativeMode && itemID == Item.blazeRod.itemID)
			{
				tileengine.energy += tileengine.getMaxEnergy() / 8;
				return true;
			}
		}

		return super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

    }

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int par5) {
//		TileEngine tile = (TileEngine) world.getBlockTileEntity(x, y, z);
//		tile.orientation = ForgeDirection.UP;
//		tile.switchOrientation();
	}

    public String getBlockName()
    {
        return "tile.CompactEngineWood";
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		woodTexture = par1IconRegister.registerIcon("buildcraft:engineWoodBottom");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		return woodTexture;
	}

}

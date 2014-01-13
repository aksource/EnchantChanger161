package ak.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class EcBlockMaterialize extends BlockContainer
{
	private Icon top;
	private Icon side;
	private Icon bottom;
	public EcBlockMaterialize(int par1)
	{
		super(par1, Material.rock);
		setHardness(5F);
		setResistance(2000.0F);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		this.setLightOpacity(0);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.top = par1IconRegister.registerIcon(EnchantChanger.EcTextureDomain + "EnchantChanger-top");
		this.side = par1IconRegister.registerIcon(EnchantChanger.EcTextureDomain + "EnchantChanger-side");
		this.bottom = par1IconRegister.registerIcon(EnchantChanger.EcTextureDomain + "EnchantChanger-bottom");
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return par1 == 0 ? this.bottom : (par1 == 1 ? this.top : this.side);
	}
	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this, 1, 0));
	}
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		par5EntityPlayer.openGui(EnchantChanger.instance, 0, par1World, par2, par3, par4);
		return true;
	}
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.removeBlockTileEntity(par2, par3, par4);
	}
	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new EcTileEntityMaterializer();
	}

}
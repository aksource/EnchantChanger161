package ak.EnchantChanger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EcItemEnchantmentTable extends Item
{

	public EcItemEnchantmentTable(int par1) {
		super(par1);
		maxStackSize = 1;
		setMaxDamage(0);
        this.setTextureName(EnchantChanger.EcTextureDomain + "PortableEnchantmentTable");
	}
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
	{
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.openGui(EnchantChanger.instance, EnchantChanger.guiIdPortableEnchantmentTable,par2World,0,0,0);

		return par1ItemStack;
	}

}
package ak.EnchantChanger;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import ak.EnchantChanger.Client.ClientProxy;

public class EcItemMaterializer extends Item
{

	public EcItemMaterializer(int par1) {
		super(par1);
		maxStackSize = 1;
		setMaxDamage(0);
		this.setTextureName(EnchantChanger.EcTextureDomain + "PortableEnchantChanger");
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.openGui(EnchantChanger.instance, EnchantChanger.guiIdMaterializer,par2World,0,0,0);

		return par1ItemStack;
	}
}
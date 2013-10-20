package Booster;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemBooster extends ItemArmor {
	private Icon icon08;
	private Icon icon20;
	public ItemBooster(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		this.setTextureName(par1 + 256 == Booster.BoosterID ? Booster.TextureDomain + "Booster08":Booster.TextureDomain + "Booster20");
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister par1IconRegister)
//	{
//		this.itemIcon = par1IconRegister.registerIcon(Booster.TextureDomain + "Booster08");
//		this.icon08 = par1IconRegister.registerIcon(Booster.TextureDomain + "Booster08");
//		this.icon20 = par1IconRegister.registerIcon(Booster.TextureDomain + "Booster20");
//	}
//	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
//	{
//		return stack.itemID == Booster.BoosterID ? this.icon08:this.icon20;
//	}
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
		String st;
		if(stack.itemID == Booster.BoosterID)
			st = (layer == 1)? Booster.TextureDomain + Booster.Armor08_1:Booster.TextureDomain + Booster.Armor08_2;
		else
			st = (layer == 1)? Booster.TextureDomain + Booster.Armor20_1:Booster.TextureDomain + Booster.Armor20_2;
    	return st;
	}
}

package StackableTools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemSwordStack extends ItemSword
{
	public static final String[] toolMaterialNames = {"Wood", "Stone", "Iron", "Diamond", "Gold"};
	public float efficiencyOnWeb = 4.0F;
	public ItemSwordStack(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, par2EnumToolMaterial);
		this.setMaxStackSize(StackableTools.SwordMax);
		this.setHasSubtypes(true);
		this.efficiencyOnWeb = par2EnumToolMaterial.getEfficiencyOnProperMaterial()
							 * par2EnumToolMaterial.getEfficiencyOnProperMaterial() / 2 + 10.0F;
		setUnlocalizedName("sword" + toolMaterialNames[par2EnumToolMaterial.ordinal()]);
		this.setTextureName(ItemSwordStack.toolMaterialNames[par2EnumToolMaterial.ordinal()].toLowerCase() + "_sword");
	}
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block.blockID == Block.web.blockID ? this.efficiencyOnWeb : 1.5F;
	}
	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving)
	{
		if(par2EntityLiving.getMaxHealth()>1)
		{
			par1ItemStack.damageItem(1, par3EntityLiving);
		}
		return true;
	}
	@Override
	public boolean isItemTool(ItemStack par1ItemStack)
	{
		return par1ItemStack.stackSize == 1;
	}
	@Override
	public boolean isDamageable()
	{
		return true;
	}
	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
	{
		if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) > 0.0D)
		{	
			if((double)Block.blocksList[par3].blockID == Block.web.blockID)
			{
				par1ItemStack.damageItem(StackableTools.WebDamage, par7EntityLivingBase);
			}
			else
			{
				par1ItemStack.damageItem(2, par7EntityLivingBase);
			}
		}
		return true;
	}

}

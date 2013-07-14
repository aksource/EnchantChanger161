package net.minecraft.src.StackableTools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.src.mod_StackableTools;
import net.minecraft.world.World;

public class ItemSpadeStack extends ItemSpade
{
	public ItemSpadeStack(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, par2EnumToolMaterial);
		this.setMaxStackSize(mod_StackableTools.ShovelMax);
		this.setHasSubtypes(true);
		setUnlocalizedName("shovel" + ItemSwordStack.toolMaterialNames[par2EnumToolMaterial.ordinal()]);
		this.func_111206_d(ItemSwordStack.toolMaterialNames[par2EnumToolMaterial.ordinal()].toLowerCase() + "_shovel");
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		if(par2EntityLiving.func_110138_aP()>1)
		{
			par1ItemStack.damageItem(2, par3EntityLiving);
		}
		return true;
	}

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) > 0.0D)
		{
			par1ItemStack.damageItem(1, par7EntityLiving);
		}
		return true;
	}

	public boolean isItemTool(ItemStack par1ItemStack)
	{
		return par1ItemStack.stackSize == 1;
	}

	public boolean isDamageable()
	{
		return true;
	}

}

package ak.EnchantChanger;

import java.util.ArrayList;

import ak.EnchantChanger.Client.*;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.*;
import net.minecraft.world.World;
import net.minecraftforge.client.*;

public class EcItemZackSword extends EcItemSword
{
	public EcItemZackSword(int par1)
	{
		super(par1, EnumToolMaterial.IRON);
        this.func_111206_d(EnchantChanger.EcTextureDomain + "ZackSword");
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking() && CommonTickHandler.LimitBreakCoolDownCount[0] == 0&&(par3EntityPlayer.func_110143_aJ() < 3 || par3EntityPlayer.capabilities.isCreativeMode))
		{
			CommonTickHandler.LimitBreakFlag[0]=true;
			CommonTickHandler.LimitBreakCount[0]=20*15;
			CommonTickHandler.LimitBreakCoolDownCount[0]=20*60*3;
			par3EntityPlayer.addPotionEffect(new PotionEffect(3,CommonTickHandler.LimitBreakCount[0],3));
			par3EntityPlayer.addChatMessage("LIMIT BREAK!!");
			return par1ItemStack;
		}
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
}

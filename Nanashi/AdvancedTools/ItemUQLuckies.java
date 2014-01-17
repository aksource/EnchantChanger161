package Nanashi.AdvancedTools;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemUQLuckies extends ItemUniqueArms
{
	private int dmg;
	protected ItemUQLuckies(int var1, EnumToolMaterial var2)
	{
		super(var1, var2);
	}

	protected ItemUQLuckies(int var1, EnumToolMaterial var2, int var3)
	{
		super(var1, var2, var3);
		dmg = var3;
	}
	@Override
	public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
	{
		super.onCreated(var1, var2, var3);
		var1.addEnchantment(Enchantment.looting, 7);
	}
	@Override
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (!var1.hasTagCompound() || var1.getEnchantmentTagList() == null)
		{
			var1.addEnchantment(Enchantment.looting, 7);
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity var1)
	{
		int var2 = this.dmg;
		if (var1 instanceof EntityLiving)
		{
			EntityLiving var3 = (EntityLiving)var1;
			int var4 = MathHelper.floor_float(var3.getHealth());

			if (var4 <= var2 && var4 > 0 && var3.hurtTime <= 0)
			{
				int var5 = (int)(2.0F * (float)var3.experienceValue);
				var3.experienceValue = var5;
			}
		}
		return false;
	}
}

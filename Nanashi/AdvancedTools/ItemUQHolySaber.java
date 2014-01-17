package Nanashi.AdvancedTools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ItemUQHolySaber extends ItemUniqueArms
{
	private int dmg;
	protected ItemUQHolySaber(int var1, EnumToolMaterial var2)
	{
		super(var1, var2);
	}

	protected ItemUQHolySaber(int var1, EnumToolMaterial var2, int var3)
	{
		super(var1, var2, var3);
		dmg = var3;
	}
	@Override
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (var3 instanceof EntityPlayer)
		{
			EntityPlayer var6 = (EntityPlayer)var3;

			if (var6.getHealth() < var6.getMaxHealth() && var6.getCurrentEquippedItem() != null && var6.getCurrentEquippedItem().itemID == this.itemID /*&& !var6.activePotionsMap.containsKey(Integer.valueOf(Potion.regeneration.id))*/)
			{
				var6.addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, 0));
			}
		}
	}
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity var1)
	{
		byte var2 = 0;

		if (var1 instanceof EntityLiving)
		{
			EntityLiving var3 = (EntityLiving)var1;

			if (var3.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
			{
				var2 = 7;
			}
			else if (var1 instanceof EntityEnderman)
			{
				var2 = 10;
			}
		}
		ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, (Object)(dmg + var2), 0);
		return false;
	}
}

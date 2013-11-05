package Nanashi.AdvancedTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "Luckluck");
	}

	public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
	{
		super.onCreated(var1, var2, var3);
		var1.addEnchantment(Enchantment.looting, 7);
	}

	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (!var1.hasTagCompound() || var1.getEnchantmentTagList() == null)
		{
			var1.addEnchantment(Enchantment.looting, 7);
		}
	}


	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity var1)
	{
		int var2 = this.weaponStrength;
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
		this.dmg = var2;
		return false;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.dmg, 0));
		return this.weaponStrength < 0 ? super.getItemAttributeModifiers() : multimap;
	}
//	public int getDamageVsEntity(Entity var1)
//	{
//		int var2 = super.getDamageVsEntity(var1);
//
//		if (var1 instanceof EntityLiving)
//		{
//			EntityLiving var3 = (EntityLiving)var1;
//			int var4 = var3.getHealth();
//
//			if (var4 <= var2 && var4 > 0 && var3.hurtTime <= 0)
//			{
//				int var5 = (int)(2.0F * (float)var3.experienceValue);
//				var3.experienceValue = var5;
//			}
//		}
//
//		return var2;
//	}
}

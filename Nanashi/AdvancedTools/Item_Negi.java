package Nanashi.AdvancedTools;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Item_Negi extends ItemFood
{
	public Item_Negi(int var1, int var2, float var3, boolean var4)
	{
		super(var1, var2, var3, var4);
		this.setMaxDamage(87);
		this.setMaxStackSize(1);
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "NEGI");
	}
	public ItemStack onEaten(ItemStack var1, World var2, EntityPlayer var3)
	{
		float var4 = 0.5F * (float)var1.getItemDamage() / (float)this.getMaxDamage();

		if (var4 > 0.0F)
		{
			this.setPotionEffect(Potion.hunger.id, 30, 0, var4);
		}

		if (0.65F - var4 >= var2.rand.nextFloat())
		{
			var3.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 0));
		}

		return super.onEaten(var1, var2, var3);
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3)
	{
		var1.damageItem(1, var3);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
	{
		var1.damageItem(2, var6);
		return true;
	}

	public float func_82803_g()
	{
		return 3;
	}
	@Override
	public Multimap func_111205_h()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)3, 0));
		return multimap;
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack var1, List var2)
	{
		var2.add("You can eat this.");
	}
}

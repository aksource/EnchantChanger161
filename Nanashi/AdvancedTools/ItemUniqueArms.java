package Nanashi.AdvancedTools;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUniqueArms extends ItemSword
{
	protected int weaponStrength = -1;

	protected ItemUniqueArms(int var1, EnumToolMaterial var2)
	{
		super(var1, var2);
	}

	protected ItemUniqueArms(int var1, EnumToolMaterial var2, int var3)
	{
		super(var1, var2);
		this.weaponStrength = var3;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		if(this.getUnlocalizedName().equals("item.SmashBat"))
			this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "SmashBat");
		else if(this.getUnlocalizedName().equals("item.InfiniteSword"))
			this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "Infinitysword");
		else if(this.getUnlocalizedName().equals("item.GenocideBlade"))
			this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "GenocideBlade");
	}
	/**
	 * Called when item is crafted/smelted. Used only by maps so far.
	 */
	public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
	{
		super.onCreated(var1, var2, var3);

		if (var1.getItem() == AdvancedTools.SmashBat)
		{
			var1.addEnchantment(Enchantment.knockback, 10);
		}
	}

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (!var1.hasTagCompound())
		{
			if (var1.getItem() == AdvancedTools.SmashBat)
			{
				var1.addEnchantment(Enchantment.knockback, 10);
			}
		}
	}
	public float func_82803_g()
	{
		return this.weaponStrength;
	}
	@Override

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.weaponStrength, 0));
		return this.weaponStrength < 0 ? super.getItemAttributeModifiers() : multimap;
	}
	/**
	 * Returns the damage against a given entity.
	 */
//	public int getDamageVsEntity(Entity var1)
//	{
//		return this.weaponStrength < 0 ? super.getDamageVsEntity(var1) : this.weaponStrength;
//	}
}

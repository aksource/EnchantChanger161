package Nanashi.AdvancedTools;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemUQHolySaber extends ItemUniqueArms
{
	private byte dmg;
	protected ItemUQHolySaber(int var1, EnumToolMaterial var2)
	{
		super(var1, var2);
	}

	protected ItemUQHolySaber(int var1, EnumToolMaterial var2, int var3)
	{
		super(var1, var2, var3);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "HolySaber");
//		this.iconIndex = par1IconRegister.registerIcon("AdvancedTools:HolySaber");
	}
	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
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

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3)
	{
		var1.damageItem(1, var3);
		return true;
	}
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
		this.dmg = var2;
		return false;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap getItemAttributeModifiers()
	{

		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.weaponStrength + this.dmg, 0));
		return this.weaponStrength < 0 ? super.getItemAttributeModifiers() : multimap;
	}

//	public int getDamageVsEntity(Entity var1)
//	{
//		byte var2 = 0;
//
//		if (var1 instanceof EntityLiving)
//		{
//			EntityLiving var3 = (EntityLiving)var1;
//
//			if (var3.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
//			{
//				var2 = 7;
//			}
//			else if (var1 instanceof EntityEnderman)
//			{
//				var2 = 10;
//			}
//		}
//
//		return super.getDamageVsEntity(var1) + var2;
//	}
}

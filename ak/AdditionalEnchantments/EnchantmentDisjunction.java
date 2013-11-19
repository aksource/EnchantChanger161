package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;

public class EnchantmentDisjunction extends Enchantment
{
	public EnchantmentDisjunction(int id, int weight)
	{
		super(id,weight, EnumEnchantmentType.weapon);
	}
	public int getMaxLevel()
	{
		return 5;
	}
	public int getMinEnchantability(int par1)
	{
		return 5 + (par1 - 1) * 8;
	}
	public int getMaxEnchantability(int par1)
	{
		return this.getMinEnchantability(par1) + 20;
	}
	public float calcModifierLiving(int lv, EntityLivingBase living)
	{
		return (living instanceof EntityEnderman)?2.5F:0.0F;
	}
	public boolean canApplyTogether(Enchantment enchantment)
	{
		return !(enchantment instanceof EnchantmentDamage) || this != enchantment;
	}
}
package ak.EnchantChanger;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EcEnchantmentFloat extends Enchantment
{
	public EcEnchantmentFloat(int var1, int var2)
    {
        super(var1, var2, EnumEnchantmentType.weapon);
        this.setName("Floating");
    }
	/**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int var1)
    {
        return super.getMinEnchantability(var1);
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int var1)
    {
        return super.getMinEnchantability(var1);
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }
    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment par1Enchantment)
    {
        return this != par1Enchantment;
    }
}
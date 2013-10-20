package StackableTools;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorStack extends ItemArmor
{
	private static final String[] partsNames = {"helmet", "chestplate", "leggings", "boots"};
	private static final String[] materialNames = {"Cloth", "Chain", "Iron", "Diamond", "Gold"};
	private static final String[] materialfileNames = {"leather_", "chainmail_", "iron_", "diamond_", "gold_"};
	public ItemArmorStack(int id, EnumArmorMaterial enumArmorMaterial, int material, int parts)
	{
		super(id, enumArmorMaterial, material, parts);
		this.setHasSubtypes(true);
		this.setMaxStackSize(StackableTools.ArmorMax);
		setUnlocalizedName(partsNames[parts] + materialNames[material]);
		this.setTextureName(materialfileNames[material] + partsNames[parts]);
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

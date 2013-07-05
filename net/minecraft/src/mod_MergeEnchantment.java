package net.minecraft.src;

import net.minecraft.item.crafting.CraftingManager;

public class mod_MergeEnchantment extends BaseMod {
	public static CraftingManager cm;
	@MLProp
	public static String extraItemIDs = "";
	@MLProp
	public static String extraBaseIDs = "";

	@MLProp
	public static String priority = "after:*";

	@Override
	public String getVersion()
	{
		return "1.2a";
	}

	@Override
	public String getPriorities()
	{
		return priority;
	}

	@Override
	public void load()
	{
		cm = CraftingManager.getInstance();
		cm.getRecipeList().add(new AddEnchantmentRecipes());
		cm.getRecipeList().add(new MergeEnchantmentRecipes());
	}
}

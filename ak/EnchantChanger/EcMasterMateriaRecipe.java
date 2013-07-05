package ak.EnchantChanger;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class EcMasterMateriaRecipe implements IRecipe
{
	private ItemStack output = null;
	@Override
	public boolean matches(InventoryCrafting var1, World var2)
	{
		ItemStack[] materia = new ItemStack[]{null,null,null,null,null,null};
		boolean flag = false;
		ItemStack craftitem;
		for(int i=0; i< var1.getSizeInventory();i++)
		{
			craftitem = var1.getStackInSlot(i);
			if(craftitem == null)
				continue;
			if(craftitem.getItem() instanceof EcItemMateria)
			{
				if(materia[0] == null)
					materia[0] = craftitem;
				else if(materia[1] == null && !ItemStack.areItemStacksEqual(materia[0], materia[1]))
					materia[1] = craftitem;
				else if(materia[2] == null && !ItemStack.areItemStacksEqual(materia[0], materia[2]) && !ItemStack.areItemStacksEqual(materia[1], materia[2]))
					materia[2] = craftitem;
				else if(materia[3] == null && !ItemStack.areItemStacksEqual(materia[0], materia[3]) && !ItemStack.areItemStacksEqual(materia[1], materia[3]) && !ItemStack.areItemStacksEqual(materia[2], materia[3]))
					materia[3] = craftitem;
				else if(materia[4] == null && !ItemStack.areItemStacksEqual(materia[0], materia[4]) && !ItemStack.areItemStacksEqual(materia[1], materia[4]) && !ItemStack.areItemStacksEqual(materia[2], materia[4]) && !ItemStack.areItemStacksEqual(materia[3], materia[4]))
					materia[4] = craftitem;
				else if(materia[5] == null && !ItemStack.areItemStacksEqual(materia[0], materia[5]) && !ItemStack.areItemStacksEqual(materia[1], materia[5]) && !ItemStack.areItemStacksEqual(materia[2], materia[5]) && !ItemStack.areItemStacksEqual(materia[3], materia[5]) && !ItemStack.areItemStacksEqual(materia[4], materia[5]))
					materia[5] = craftitem;
				else
					return false;
			}
			else
				return false;
		}
		if(materia[5] != null && materia[0].isItemEnchanted() && materia[1].isItemEnchanted()&& materia[2].isItemEnchanted()&& materia[3].isItemEnchanted()&& materia[4].isItemEnchanted()&& materia[5].isItemEnchanted())
		{
			if(this.chekEnchmateria(materia, 6, 16, 21))
			{
				this.output = new ItemStack(EnchantChanger.MasterMateria,1,3);
				flag = true;
			}
			else if(this.chekEnchmateria(materia, 5, 0, 4) || this.chekEnchmateria(materia, 5, 7, 7))
			{
				this.output = new ItemStack(EnchantChanger.MasterMateria,1,1);
				flag = true;
			}
		}
		else if(materia[3] != null && materia[0].isItemEnchanted() && materia[1].isItemEnchanted()&& materia[2].isItemEnchanted()&& materia[3].isItemEnchanted())
		{
			if(this.chekEnchmateria(materia, 4, 32, 35))
			{
				this.output = new ItemStack(EnchantChanger.MasterMateria,1,4);
				flag = true;
			}
			else if(this.chekEnchmateria(materia, 4, 48, 51))
			{
				this.output = new ItemStack(EnchantChanger.MasterMateria,1,5);
				flag = true;
			}
		}
		else if(materia[2] == null && materia[1] != null && materia[0].isItemEnchanted() && materia[1].isItemEnchanted())
		{
			if(this.chekEnchmateria(materia, 2, 5, 6))
			{
				this.output = new ItemStack(EnchantChanger.MasterMateria,1,2);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1)
	{
		return this.output.copy();
	}

	@Override
	public int getRecipeSize()
	{
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return this.output;
	}
	public boolean chekEnchmateria(ItemStack[] items, int num, int init, int end)
	{
		boolean ret = false;
		for(int i = 0;i<num;i++)
		{
			if(checkEnch(items[i], init, end))
			{
				ret = true;
				break;
			}
		}
		return ret;
	}
	public boolean checkEnch(ItemStack materia, int init, int end)
	{
		boolean ret=false;
		for(int i=init;i<=end;i++)
		{
			if(EnchantmentHelper.getEnchantmentLevel(i, materia) > 0)
			{
				ret = true;
				break;
			}
		}
		return ret;
	}
}
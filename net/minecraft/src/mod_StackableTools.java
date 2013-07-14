package net.minecraft.src;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.stItemFieldAccessHelper;
import net.minecraft.src.StackableTools.ItemArmorStack;
import net.minecraft.src.StackableTools.ItemAxeStack;
import net.minecraft.src.StackableTools.ItemBowStack;
import net.minecraft.src.StackableTools.ItemPickaxeStack;
import net.minecraft.src.StackableTools.ItemSpadeStack;
import net.minecraft.src.StackableTools.ItemSwordStack;

public class mod_StackableTools extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}
	@MLProp(info="Axe Max Stack Size", min = 1, max = 64)
	public static int AxeMax = 64;
	@MLProp(info="Pickaxe Max Stack Size", min = 1, max = 64)
	public static int PickaxeMax = 64;
	@MLProp(info="Shovel Max Stack Size", min = 1, max = 64)
	public static int ShovelMax = 64;
	@MLProp(info="Sword Max Stack Size", min = 1, max = 64)
	public static int SwordMax = 64;
	@MLProp(info="Hoe Max Stack Size", min = 1, max = 64)
	public static int HoeMax = 64;
	@MLProp(info="Flint Max Stack Size", min = 1, max = 64)
	public static int FrintMax = 64;
	@MLProp(info="Bow Max Stack Size", min = 1, max = 64)
	public static int BowMax = 64;
	@MLProp(info="Shears Max Stack Size", min = 1, max = 64)
	public static int ShearsMax = 64;
	@MLProp(info="FishingRod Max Stack Size", min = 1, max = 64)
	public static int FishingRodMax = 64;
	@MLProp(info="Armor Max Stack Size", min = 1, max = 64)
	public static int ArmorMax = 64;
	@MLProp(info="Damage Destroyed Web with Sword", min = 0, max = 2)
	public static int WebDamage = 1;

	public void load()
	{
		if(AxeMax != 1)
		{
			Item.axeWood 	= new ItemAxeStack(15, EnumToolMaterial.WOOD);
			Item.axeStone 	= new ItemAxeStack(19, EnumToolMaterial.STONE);
			Item.axeIron 	= new ItemAxeStack( 2, EnumToolMaterial.IRON);
			Item.axeDiamond = new ItemAxeStack(23, EnumToolMaterial.EMERALD);
			Item.axeGold 	= new ItemAxeStack(30, EnumToolMaterial.GOLD);
		}
		
		if(PickaxeMax != 1)
		{
			Item.pickaxeWood 	= new ItemPickaxeStack(14, EnumToolMaterial.WOOD);
			Item.pickaxeStone 	= new ItemPickaxeStack(18, EnumToolMaterial.STONE);
			Item.pickaxeIron 	= new ItemPickaxeStack( 1, EnumToolMaterial.IRON);
			Item.pickaxeDiamond = new ItemPickaxeStack(22, EnumToolMaterial.EMERALD);
			Item.pickaxeGold 	= new ItemPickaxeStack(29, EnumToolMaterial.GOLD);
		}
		
		if(ShovelMax != 1)
		{
			Item.shovelWood 	= new ItemSpadeStack(13, EnumToolMaterial.WOOD);
			Item.shovelStone 	= new ItemSpadeStack(17, EnumToolMaterial.STONE);
			Item.shovelIron 	= new ItemSpadeStack( 0, EnumToolMaterial.IRON);
			Item.shovelDiamond 	= new ItemSpadeStack(21, EnumToolMaterial.EMERALD);
			Item.shovelGold 	= new ItemSpadeStack(28, EnumToolMaterial.GOLD);
		}
		
		if(BowMax != 1)
		{
			Item.bow = (ItemBow)(new ItemBowStack(5)).setUnlocalizedName("bow");
		}
		
		if(SwordMax != 1 || WebDamage != 2)
		{
			Item.swordWood 		= new ItemSwordStack(12, EnumToolMaterial.WOOD);
			Item.swordStone 	= new ItemSwordStack(16, EnumToolMaterial.STONE);
			Item.swordIron 		= new ItemSwordStack(11, EnumToolMaterial.IRON);
			Item.swordDiamond 	= new ItemSwordStack(20, EnumToolMaterial.EMERALD);
			Item.swordGold 		= new ItemSwordStack(27, EnumToolMaterial.GOLD);
		}
		
		//Diamond装備を直接置き換えないと空き装備スロットのテクスチャがバグる
		if(ArmorMax != 1)
		{
			Item.helmetLeather	= (ItemArmor)new ItemArmorStack(42, EnumArmorMaterial.CLOTH, 0, 0);
			Item.plateLeather	= (ItemArmor)new ItemArmorStack(43, EnumArmorMaterial.CLOTH, 0, 1);
			Item.legsLeather	= (ItemArmor)new ItemArmorStack(44, EnumArmorMaterial.CLOTH, 0, 2);
			Item.bootsLeather	= (ItemArmor)new ItemArmorStack(45, EnumArmorMaterial.CLOTH, 0, 3);
			Item.helmetChain	= (ItemArmor)new ItemArmorStack(46, EnumArmorMaterial.CHAIN, 1, 0);
			Item.plateChain		= (ItemArmor)new ItemArmorStack(47, EnumArmorMaterial.CHAIN, 1, 1);
			Item.legsChain		= (ItemArmor)new ItemArmorStack(48, EnumArmorMaterial.CHAIN, 1, 2);
			Item.bootsChain		= (ItemArmor)new ItemArmorStack(49, EnumArmorMaterial.CHAIN, 1, 3);
			Item.helmetIron		= (ItemArmor)new ItemArmorStack(50, EnumArmorMaterial.IRON, 2, 0);
			Item.plateIron		= (ItemArmor)new ItemArmorStack(51, EnumArmorMaterial.IRON, 2, 1);
			Item.legsIron		= (ItemArmor)new ItemArmorStack(52, EnumArmorMaterial.IRON, 2, 2);
			Item.bootsIron		= (ItemArmor)new ItemArmorStack(53, EnumArmorMaterial.IRON, 2, 3);
			Item.helmetDiamond	= (ItemArmor)new ItemArmorStack(54, EnumArmorMaterial.DIAMOND, 3, 0);
			Item.plateDiamond	= (ItemArmor)new ItemArmorStack(55, EnumArmorMaterial.DIAMOND, 3, 1);
			Item.legsDiamond	= (ItemArmor)new ItemArmorStack(56, EnumArmorMaterial.DIAMOND, 3, 2);
			Item.bootsDiamond	= (ItemArmor)new ItemArmorStack(57, EnumArmorMaterial.DIAMOND, 3, 3);
			Item.helmetGold		= (ItemArmor)new ItemArmorStack(58, EnumArmorMaterial.GOLD, 4, 0);
			Item.plateGold		= (ItemArmor)new ItemArmorStack(59, EnumArmorMaterial.GOLD, 4, 1);
			Item.legsGold		= (ItemArmor)new ItemArmorStack(60, EnumArmorMaterial.GOLD, 4, 2);
			Item.bootsGold		= (ItemArmor)new ItemArmorStack(61, EnumArmorMaterial.GOLD, 4, 3);
		}
		
		//Itemクラスのパッケージ内にフィールド変更用クラスを作れば問題ない．書き換えが面倒だっただけ．
		//よくよく考えたらパッケージを編成し直すから意味が無いのかもしれないが，動作したので取り敢えずこのまま．
		if(FrintMax != 1)
		{
			//Item.flintAndSteel.setHasSubtypes(true).setMaxStackSize(FrintMax);
			stItemFieldAccessHelper.changeField(Item.flintAndSteel, FrintMax);
		}
		
		if(FishingRodMax != 1)
		{
			//Item.fishingRod.setHasSubtypes(true).setMaxStackSize(FishingRodMax);
			stItemFieldAccessHelper.changeField(Item.fishingRod, FishingRodMax);
		}
		
		if(ShearsMax != 1)
		{
			//Item.shears.setHasSubtypes(true).setMaxStackSize(ShearsMax);
			stItemFieldAccessHelper.changeField(Item.shears, ShearsMax);
		}
		
		if(HoeMax != 1)
		{
			//Item.hoeWood.setHasSubtypes(true).setMaxStackSize(HoeMax);
			stItemFieldAccessHelper.changeField(Item.hoeWood, HoeMax);
			//Item.hoeStone.setHasSubtypes(true).setMaxStackSize(HoeMax);
			stItemFieldAccessHelper.changeField(Item.hoeStone, HoeMax);
			//Item.hoeIron.setHasSubtypes(true).setMaxStackSize(HoeMax);
			stItemFieldAccessHelper.changeField(Item.hoeIron, HoeMax);
			//Item.hoeDiamond.setHasSubtypes(true).setMaxStackSize(HoeMax);
			stItemFieldAccessHelper.changeField(Item.hoeDiamond, HoeMax);
			//Item.hoeGold.setHasSubtypes(true).setMaxStackSize(HoeMax);
			stItemFieldAccessHelper.changeField(Item.hoeGold, HoeMax);
		}
	}

}

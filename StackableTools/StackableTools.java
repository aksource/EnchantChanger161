package StackableTools;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="StackableTools", name="StackableTools", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class StackableTools
{
	@Mod.Instance("StackableTools")
	public static StackableTools instance;
	
	public static int AxeMax;
	public static int PickaxeMax;
	public static int ShovelMax;
	public static int SwordMax;
	public static int HoeMax;
	public static int FrintMax;
	public static int BowMax;
	public static int ShearsMax;
	public static int FishingRodMax;
	public static int ArmorMax;
	public static int WebDamage;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		AxeMax = config.get(Configuration.CATEGORY_GENERAL, "AxeMax", 64, "Axe Max Stack Size, min = 1, max = 64").getInt();
		AxeMax = (AxeMax<1)?1:(AxeMax>64)?64:AxeMax;
		PickaxeMax = config.get(Configuration.CATEGORY_GENERAL, "PickaxeMax", 64, "Pickaxe Max Stack Size, min = 1, max = 64").getInt();
		PickaxeMax = (PickaxeMax<1)?1:(PickaxeMax>64)?64:PickaxeMax;
		ShovelMax = config.get(Configuration.CATEGORY_GENERAL, "ShovelMax", 64, "Shovel Max Stack Size, min = 1, max = 64").getInt();
		ShovelMax = (ShovelMax<1)?1:(ShovelMax>64)?64:ShovelMax;
		SwordMax = config.get(Configuration.CATEGORY_GENERAL, "SwordMax", 64, "Sword Max Stack Size, min = 1, max = 64").getInt();
		SwordMax = (SwordMax<1)?1:(SwordMax>64)?64:SwordMax;
		HoeMax = config.get(Configuration.CATEGORY_GENERAL, "HoeMax", 64, "Hoe Max Stack Size, min = 1, max = 64").getInt();
		HoeMax = (HoeMax<1)?1:(HoeMax>64)?64:HoeMax;
		FrintMax = config.get(Configuration.CATEGORY_GENERAL, "FrintMax", 64, "Flint Max Stack Size, min = 1, max = 64").getInt();
		FrintMax = (FrintMax<1)?1:(FrintMax>64)?64:FrintMax;
		BowMax = config.get(Configuration.CATEGORY_GENERAL, "BowMax", 64, "Bow Max Stack Size, min = 1, max = 64").getInt();
		BowMax = (BowMax<1)?1:(BowMax>64)?64:BowMax;
		ShearsMax = config.get(Configuration.CATEGORY_GENERAL, "ShearsMax", 64, "Shears Max Stack Size, min = 1, max = 64").getInt();
		ShearsMax = (ShearsMax<1)?1:(ShearsMax>64)?64:ShearsMax;
		FishingRodMax = config.get(Configuration.CATEGORY_GENERAL, "FishingRodMax", 64, "FishingRod Max Stack Size, min = 1, max = 64").getInt();
		FishingRodMax = (FishingRodMax<1)?1:(FishingRodMax>64)?64:FishingRodMax;
		ArmorMax = config.get(Configuration.CATEGORY_GENERAL, "ArmorMax", 64, "Armor Max Stack Size, min = 1, max = 64").getInt();
		ArmorMax = (ArmorMax<1)?1:(ArmorMax>64)?64:ArmorMax;
		WebDamage = config.get(Configuration.CATEGORY_GENERAL, "WebDamage", 1, "Damage Destroyed Web with Sword, min = 0, max = 2").getInt();
		WebDamage = (WebDamage<0)?0:(WebDamage>2)?2:WebDamage;
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
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
			Item.flintAndSteel.setMaxStackSize(FrintMax);
			changeHasSubType(Item.flintAndSteel);
		}
		
		if(FishingRodMax != 1)
		{
			Item.fishingRod.setMaxStackSize(FishingRodMax);
			changeHasSubType(Item.fishingRod);
		}
		
		if(ShearsMax != 1)
		{
			Item.shears.setMaxStackSize(ShearsMax);
			changeHasSubType(Item.shears);
		}
		
		if(HoeMax != 1)
		{
			Item.hoeWood.setMaxStackSize(HoeMax);
			changeHasSubType(Item.hoeWood);
			Item.hoeStone.setMaxStackSize(HoeMax);
			changeHasSubType(Item.hoeStone);
			Item.hoeIron.setMaxStackSize(HoeMax);
			changeHasSubType(Item.hoeIron);
			Item.hoeDiamond.setMaxStackSize(HoeMax);
			changeHasSubType(Item.hoeDiamond);
			Item.hoeGold.setMaxStackSize(HoeMax);
			changeHasSubType(Item.hoeGold);
		}
	}
	private void changeHasSubType(Item item)
	{
		ObfuscationReflectionHelper.setPrivateValue(Item.class, item, true, 178);
	}
}
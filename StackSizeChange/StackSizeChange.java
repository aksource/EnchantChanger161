package StackSizeChange;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="StackSizeChange", name="StackSizeChange", version="1.6srg-2",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class StackSizeChange
{
	@Mod.Instance("StackSizeChange")
	public static StackSizeChange instance;
	
	public static int SignMax;
	public static int DoorMax;
	public static int BedMax;
	public static int CakeMax;
	public static int BoatMax;
	public static int CartMax;
	public static int EggMax;
	public static int EnderMax;
	public static int SnowMax;
	public static int RecordMax;
	public static int SaddleMax;
	public static int BucketMax;
	public static int PotionMax;
	public static int SoupMax;
	public static int MilkMax;
	public static int BucketWaterMax;
	public static int BucketLavaMax;
	public static int EnchantBookMax;
	public static boolean BucketReplace;
	public static boolean addStackableBucket;
	public static int StackableBucketID;
	public static int SoupAmount;
	public static boolean isNetherSetWater;
	public static boolean isStackCustom;
	
	public static Item bucketEmpty;
	public static Item bucketWater;
	public static Item bucketLava;
	public static Item bucketMilk;

	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		SignMax = config.get(Configuration.CATEGORY_GENERAL, "SignMax", 64, "Sign Max Stack Size, min = 1, max = 64").getInt();
		SignMax = (SignMax<1)?1:(SignMax>64)?64:SignMax;
		DoorMax = config.get(Configuration.CATEGORY_GENERAL, "DoorMax", 64, "Door Max Stack Size, min = 1, max = 64").getInt();
		DoorMax = (DoorMax<1)?1:(DoorMax>64)?64:DoorMax;
		BedMax = config.get(Configuration.CATEGORY_GENERAL, "BedMax", 64, "Bed Max Stack Size, min = 1, max = 64").getInt();
		BedMax = (BedMax<1)?1:(BedMax>64)?64:BedMax;
		CakeMax = config.get(Configuration.CATEGORY_GENERAL, "CakeMax", 64, "Cake Max Stack Size, min = 1, max = 64").getInt();
		CakeMax = (CakeMax<1)?1:(CakeMax>64)?64:CakeMax;
		BoatMax = config.get(Configuration.CATEGORY_GENERAL, "BoatMax", 64, "Boat Max Stack Size, min = 1, max = 64").getInt();
		BoatMax = (BoatMax<1)?1:(BoatMax>64)?64:BoatMax;
		CartMax = config.get(Configuration.CATEGORY_GENERAL, "CartMax", 64, "Minecart Max Stack Size, min = 1, max = 64").getInt();
		CartMax = (CartMax<1)?1:(CartMax>64)?64:CartMax;
		EggMax = config.get(Configuration.CATEGORY_GENERAL, "EggMax", 64, "Egg Max Stack Size, min = 1, max = 64").getInt();
		EggMax = (EggMax<1)?1:(EggMax>64)?64:EggMax;
		EnderMax = config.get(Configuration.CATEGORY_GENERAL, "EnderMax", 64, "EnderParl Max Stack Size, min = 1, max = 64").getInt();
		EnderMax = (EnderMax<1)?1:(EnderMax>64)?64:EnderMax;
		SnowMax = config.get(Configuration.CATEGORY_GENERAL, "SnowMax", 64, "Snowball Max Stack Size, min = 1, max = 64").getInt();
		SnowMax = (SnowMax<1)?1:(SnowMax>64)?64:SnowMax;
		RecordMax = config.get(Configuration.CATEGORY_GENERAL, "RecordMax", 64, "Record Max Stack Size, min = 1, max = 64").getInt();
		RecordMax = (RecordMax<1)?1:(RecordMax>64)?64:RecordMax;
		SaddleMax = config.get(Configuration.CATEGORY_GENERAL, "SaddleMax", 64, "Saddle Max Stack Size, min = 1, max = 64").getInt();
		SaddleMax = (SaddleMax<1)?1:(SaddleMax>64)?64:SaddleMax;
		BucketMax = config.get(Configuration.CATEGORY_GENERAL, "BucketMax", 64, "Bucket Max Stack Size, min = 1, max = 64").getInt();
		BucketMax = (BucketMax<1)?1:(BucketMax>64)?64:BucketMax;
		PotionMax = config.get(Configuration.CATEGORY_GENERAL, "PotionMax", 64, "Potion Max Stack Size, min = 1, max = 64").getInt();
		PotionMax = (PotionMax<0)?1:(PotionMax>64)?64:PotionMax;
		SoupMax = config.get(Configuration.CATEGORY_GENERAL, "SoupMax", 64, "Soup Max Stack Size, min = 1, max = 64").getInt();
		SoupMax = (SoupMax<1)?1:(SoupMax>64)?64:SoupMax;
		MilkMax = config.get(Configuration.CATEGORY_GENERAL, "MilkMax", 64, "BucketMilk Max Stack Size, min = 1, max = 64").getInt();
		MilkMax = (MilkMax<1)?1:(MilkMax>64)?64:MilkMax;
		BucketWaterMax = config.get(Configuration.CATEGORY_GENERAL, "BucketWaterMax", 64, "BucketWater Max Stack Size, min = 1, max = 64").getInt();
		BucketWaterMax = (BucketWaterMax<1)?1:(BucketWaterMax>64)?64:BucketWaterMax;
		BucketLavaMax = config.get(Configuration.CATEGORY_GENERAL, "BucketLavaMax", 64, "BucketLava Max Stack Size, min = 1, max = 64").getInt();
		BucketLavaMax = (BucketLavaMax<1)?1:(BucketLavaMax>64)?64:BucketLavaMax;
		EnchantBookMax = config.get(Configuration.CATEGORY_GENERAL, "EnchantBookMax", 64, "EnchantableBook Max Stack Size, min = 1, max = 64").getInt();
		EnchantBookMax = (EnchantBookMax<1)?1:(EnchantBookMax>64)?64:EnchantBookMax;
		BucketReplace = config.get(Configuration.CATEGORY_GENERAL, "BucketReplace", true).getBoolean(true);
		addStackableBucket = config.get(Configuration.CATEGORY_GENERAL, "addStackableBucket", false).getBoolean(false);
		StackableBucketID = config.get(Configuration.CATEGORY_GENERAL, "StackableBucketID", 17250, "only use addStackableBacket=true. on use +0..+3 IDs, min = 4096, max = 31996").getInt();
		StackableBucketID = (StackableBucketID<4096)?4096:(StackableBucketID>31996)?31996:StackableBucketID;
		SoupAmount = config.get(Configuration.CATEGORY_GENERAL, "SoupAmount", 6, "1.2.5 = 8, 1.3.x = 6, min=1, max=20").getInt();
		SoupAmount = (SoupAmount<1)?1:(SoupAmount>20)?20:SoupAmount;
		isNetherSetWater = config.get(Configuration.CATEGORY_GENERAL, "isNetherSetWater", false,"true: I would like to put the water into a StackableBucket with Nether.").getBoolean(false);
		isStackCustom = config.get(Configuration.CATEGORY_GENERAL, "isStackCustom", true, "true: Improve the operation of the stack").getBoolean(true);
		
		config.save();
		Item.doorWood.setMaxStackSize(DoorMax);
		Item.minecartEmpty.setMaxStackSize(CartMax);
		Item.saddle.setMaxStackSize(SaddleMax);
		Item.doorIron.setMaxStackSize(DoorMax);
		Item.snowball.setMaxStackSize(SnowMax);
		Item.boat.setMaxStackSize(BoatMax);
		Item.minecartCrate.setMaxStackSize(CartMax);
		Item.minecartPowered.setMaxStackSize(CartMax);
		Item.egg.setMaxStackSize(EggMax);
		Item.cake.setMaxStackSize(CakeMax);
		Item.bed.setMaxStackSize(BedMax);
		Item.enderPearl.setMaxStackSize(EnderMax);
		Item.potion.setMaxStackSize(PotionMax);
		Item.record13.setMaxStackSize(RecordMax);
		Item.recordCat.setMaxStackSize(RecordMax);
		Item.recordBlocks.setMaxStackSize(RecordMax);
		Item.recordChirp.setMaxStackSize(RecordMax);
		Item.recordFar.setMaxStackSize(RecordMax);
		Item.recordMall.setMaxStackSize(RecordMax);
		Item.recordMellohi.setMaxStackSize(RecordMax);
		Item.recordStal.setMaxStackSize(RecordMax);
		Item.recordStrad.setMaxStackSize(RecordMax);
		Item.recordWard.setMaxStackSize(RecordMax);
		Item.record11.setMaxStackSize(RecordMax);
		Item.recordWait.setMaxStackSize(RecordMax);
		Item.enchantedBook.setMaxStackSize(EnchantBookMax);
		Item.minecartTnt.setMaxStackSize(CartMax);
		Item.minecartHopper.setMaxStackSize(CartMax);

		Item.sign.setMaxStackSize(SignMax);
		Item.bowlSoup = (new ItemSoupStack(26, SoupAmount)).setUnlocalizedName("mushroomStew").setMaxStackSize(SoupMax);
		MinecraftForge.EVENT_BUS.register((ItemSoupStack)Item.bowlSoup);

		if(BucketReplace)
		{
			bucketEmpty = (new ItemBucketStack(69, 0)).setUnlocalizedName("bucket").setMaxStackSize(BucketMax);
			bucketWater = (new ItemBucketStack(70, Block.waterMoving.blockID)).setUnlocalizedName("bucketWater").setContainerItem(bucketEmpty).setMaxStackSize(BucketWaterMax);
			bucketLava = (new ItemBucketStack(71, Block.lavaMoving.blockID)).setUnlocalizedName("bucketLava").setContainerItem(bucketEmpty).setMaxStackSize(BucketLavaMax);
			bucketMilk = (new ItemBucketMilkStack(79)).setUnlocalizedName("milk").setContainerItem(bucketEmpty).setMaxStackSize(MilkMax);
			
			Item.bucketEmpty = bucketEmpty;
			Item.bucketWater = bucketWater;
			Item.bucketLava = bucketLava;
			Item.bucketMilk = bucketMilk;
		}
		if(!BucketReplace && addStackableBucket)
		{
			bucketEmpty = (new ItemBucketStack(0 + StackableBucketID-256, 0)).setUnlocalizedName("stackbucket").setMaxStackSize(BucketMax);
			bucketWater = (new ItemBucketStack(1 + StackableBucketID-256, Block.waterMoving.blockID)).setUnlocalizedName("stackbucketWater").setContainerItem(bucketEmpty).setMaxStackSize(BucketWaterMax);
			bucketLava = (new ItemBucketStack(2 + StackableBucketID-256, Block.lavaMoving.blockID)).setUnlocalizedName("stackbucketLava").setContainerItem(bucketEmpty).setMaxStackSize(BucketLavaMax);
			bucketMilk = (new ItemBucketMilkStack(3 + StackableBucketID-256)).setUnlocalizedName("stackmilk").setContainerItem(bucketEmpty).setMaxStackSize(MilkMax);
			addName(bucketEmpty, "Stackable Bucket",       "スタックバケツ");
			addName(bucketWater, "Stackable Bucket Water", "スタック水入りバケツ");
			addName(bucketLava,  "Stackable Bucket Lava",  "スタック溶岩入りバケツ");
			addName(bucketMilk,  "Stackable Bucket Milk",  "スタック牛乳");
			GameRegistry.addRecipe(new ItemStack(bucketEmpty, 2), new Object[]{ "I I","III"," I ", 'I',Item.ingotIron });
			GameRegistry.addShapelessRecipe(new ItemStack(bucketEmpty, 1), new Object[]{ Item.bucketEmpty });
			GameRegistry.addShapelessRecipe(new ItemStack(Item.bucketEmpty, 1), new Object[]{ bucketEmpty });
			GameRegistry.addRecipe(new ItemStack(Item.cake, 1), new Object[]
				{ "MMM","SES","WWW", 'M',bucketMilk, 'S',Item.sugar, 'E',Item.egg, 'W',Item.wheat });
		}
		if(BucketReplace || addStackableBucket)
		{
			BehaviorSBucketFullDispense var2 = new BehaviorSBucketFullDispense();
			BlockDispenser.dispenseBehaviorRegistry.putObject(bucketLava, var2);
			BlockDispenser.dispenseBehaviorRegistry.putObject(bucketWater, var2);
			BlockDispenser.dispenseBehaviorRegistry.putObject(bucketEmpty, new BehaviorSBucketEmptyDispense());
			MinecraftForge.EVENT_BUS.register((ItemBucketMilkStack)bucketMilk);
		}
		if(isStackCustom)
		{
			Item.potion = (ItemPotion)(new ItemPotionStack(117)).setUnlocalizedName("potion").setMaxStackSize(PotionMax);
			Item.glassBottle = (new ItemGlassBottleStack(118)).setUnlocalizedName("glassBottle");
		}
	}
	public static void addName(Item item, String enName, String jaName)
	{
		LanguageRegistry.addName(item, enName);
		LanguageRegistry.instance().addNameForObject(item, "ja_JP", jaName);
	}

	public static void addName(Block block, String enName, String jaName)
	{
		LanguageRegistry.addName(block, enName);
		LanguageRegistry.instance().addNameForObject(block, "ja_JP", jaName);
	}

	public static ItemStack addropItems(ItemStack par1ItemStack, EntityPlayer par3EntityPlayer, ItemStack addItemStack)
	{
		return addropItems(par1ItemStack, par3EntityPlayer, addItemStack, true);
	}

	public static ItemStack addropItems(ItemStack par1ItemStack, EntityPlayer par3EntityPlayer, ItemStack addItemStack, boolean dec)
	{
		return addropItems(par1ItemStack, par3EntityPlayer, addItemStack, dec, false);
	}

	public static ItemStack addropItems(ItemStack par1ItemStack, EntityPlayer par3EntityPlayer, ItemStack addItemStack, boolean dec, boolean creativeDec)
	{
		if (par3EntityPlayer.capabilities.isCreativeMode && !creativeDec || par1ItemStack == null)
		{
			return par1ItemStack;
		}

		if(dec){--par1ItemStack.stackSize;}
		
		if (par1ItemStack.stackSize <= 0)
		{
			if(!isStackCustom) return addItemStack;
			
			boolean isStackable = false;
			int slot =0;
			for (int i = 0; i < par3EntityPlayer.inventory.mainInventory.length; ++i)
			{
				if (par3EntityPlayer.inventory.mainInventory[i] != null 
				&& par3EntityPlayer.inventory.mainInventory[i].itemID == addItemStack.itemID
				&& par3EntityPlayer.inventory.mainInventory[i].stackSize < addItemStack.getMaxStackSize())
				{
					isStackable = true;
					slot = i;
					break;
				}
			}

			if(isStackable)
			{
				par3EntityPlayer.inventory.addItemStackToInventory(addItemStack);
				ItemStack result = par3EntityPlayer.inventory.mainInventory[slot];
				par3EntityPlayer.inventory.mainInventory[slot] = null;
				return result;
			}
			return addItemStack;
		}

		if (!par3EntityPlayer.inventory.addItemStackToInventory(addItemStack))
		{
			par3EntityPlayer.dropPlayerItem(addItemStack);
		}

		return par1ItemStack;

	}
}

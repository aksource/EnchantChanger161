package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.src.StackSizeChange.BehaviorSBucketEmptyDispense;
import net.minecraft.src.StackSizeChange.BehaviorSBucketFullDispense;
import net.minecraft.src.StackSizeChange.ItemBucketMilkStack;
import net.minecraft.src.StackSizeChange.ItemBucketStack;
import net.minecraft.src.StackSizeChange.ItemGlassBottleStack;
import net.minecraft.src.StackSizeChange.ItemPotionStack;
import net.minecraft.src.StackSizeChange.ItemSoupStack;
import net.minecraftforge.common.MinecraftForge;

public class mod_StackSizeChange extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}

	@MLProp(info="Sign Max Stack Size", min = 1, max = 64)
	public static int SignMax = 64;
	@MLProp(info="Door Max Stack Size", min = 1, max = 64)
	public static int DoorMax = 64;
	@MLProp(info="Bed Max Stack Size", min = 1, max = 64)
	public static int BedMax = 64;
	@MLProp(info="Cake Max Stack Size", min = 1, max = 64)
	public static int CakeMax = 64;
	@MLProp(info="Boat Max Stack Size", min = 1, max = 64)
	public static int BoatMax = 64;
	@MLProp(info="Minecart Max Stack Size", min = 1, max = 64)
	public static int CartMax = 64;
	@MLProp(info="Egg Max Stack Size", min = 1, max = 64)
	public static int EggMax = 64;
	@MLProp(info="EnderParl Max Stack Size", min = 1, max = 64)
	public static int EnderMax = 64;
	@MLProp(info="Snowball Max Stack Size", min = 1, max = 64)
	public static int SnowMax = 64;
	@MLProp(info="Record Max Stack Size", min = 1, max = 64)
	public static int RecordMax = 64;
	@MLProp(info="Saddle Max Stack Size", min = 1, max = 64)
	public static int SaddleMax = 64;
	@MLProp(info="Bucket Max Stack Size", min = 1, max = 64)
	public static int BucketMax = 64;
	@MLProp(info="Potion Max Stack Size", min = 1, max = 64)
	public static int PotionMax = 64;
	@MLProp(info="Soup Max Stack Size", min = 1, max = 64)
	public static int SoupMax = 64;
	@MLProp(info="BucketMilk Max Stack Size", min = 1, max = 64)
	public static int MilkMax = 64;
	@MLProp(info="BucketWater Max Stack Size", min = 1, max = 64)
	public static int BucketWaterMax = 64;
	@MLProp(info="BucketLava Max Stack Size", min = 1, max = 64)
	public static int BucketLavaMax = 64;
	@MLProp(info="EnchantableBook Max Stack Size", min = 1, max = 64)
	public static int EnchantBookMax = 64;
	@MLProp()
	public static boolean BucketReplace = true;
	@MLProp()
	public static boolean addStackableBucket = false;
	@MLProp(info="only use addStackableBacket=true. on use +0..+3 IDs", min = 4096, max = 31996)
	public static int StackableBucketID = 17250;
	@MLProp(info="1.2.5 = 8, 1.3.x = 6", min=1, max=20)
	public static int SoupAmount = 6;
	@MLProp(info="true: I would like to put the water into a StackableBucket with Nether.")
	public static boolean isNetherSetWater = false;
	@MLProp(info="true: Improve the operation of the stack")
	public static boolean isStackCustom = true;
	
	public static Item bucketEmpty;
	public static Item bucketWater;
	public static Item bucketLava;
	public static Item bucketMilk;


	public void load()
	{
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
			ModLoader.addRecipe(new ItemStack(bucketEmpty, 2), new Object[]{ "I I","III"," I ", 'I',Item.ingotIron });
			ModLoader.addShapelessRecipe(new ItemStack(bucketEmpty, 1), new Object[]{ Item.bucketEmpty });
			ModLoader.addShapelessRecipe(new ItemStack(Item.bucketEmpty, 1), new Object[]{ bucketEmpty });
			ModLoader.addRecipe(new ItemStack(Item.cake, 1), new Object[]
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
		ModLoader.addName(item, enName);
		ModLoader.addName(item, "ja_JP", jaName);
	}

	public static void addName(Block block, String enName, String jaName)
	{
		ModLoader.addName(block, enName);
		ModLoader.addName(block, "ja_JP", jaName);
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

/*	牛置き換え時に使用、現在は未使用
	public static boolean getItems(EntityPlayer par1EntityPlayer, Item container, Item resultItem)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

		if (var2 != null && var2.stackSize > 0 && var2.itemID == container.itemID)
		{
			if (--var2.stackSize < 1)
			{
				boolean isStackable = false;
				for (int i = 0; i < par1EntityPlayer.inventory.mainInventory.length; ++i)
				{
					if (par1EntityPlayer.inventory.mainInventory[i] != null 
					&& par1EntityPlayer.inventory.mainInventory[i].itemID == resultItem.itemID
					&& par1EntityPlayer.inventory.mainInventory[i].stackSize < resultItem.getItemStackLimit())
					{
						isStackable = true;
					}
				}

				if(isStackable)
				{
					par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(resultItem));
					par1EntityPlayer.inventory.mainInventory[
						par1EntityPlayer.inventory.currentItem] = null;
					return true;
				}
				par1EntityPlayer.inventory.mainInventory[
					par1EntityPlayer.inventory.currentItem] = new ItemStack(resultItem);
				return true;
			}
			
			if (!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(resultItem)))
			{
				par1EntityPlayer.dropPlayerItem(new ItemStack(resultItem));
			}
			return true;
		}
		return false;
	}
*/

}

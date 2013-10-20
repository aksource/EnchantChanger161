package ak.PrepaidEnchantment;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerPEnchantment extends Container
{
	/** SlotEnchantmentTable object with ItemStack to be enchanted */
	public IInventory tableInventory = new SlotPEnchantmentTable(this, "PEnchant", true, 2);

	/** current world (for bookshelf counting) */
	private World worldPointer;
	private int posX;
	private int posY;
	private int posZ;
	private Random rand = new Random();

	/** used as seed for EnchantmentNameParts (see GuiEnchantment) */
	public long nameSeed;

	/** 3-member array storing the enchantment levels of each slot */
	public int[] enchantLevels = new int[3];
	private TileEntityPEnchantmentTable tileEntity;
	private int Bonus = 0;
	private int Button;

	public ContainerPEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, TileEntityPEnchantmentTable te)
	{
		this.worldPointer = par2World;
		this.tileEntity = te;
		this.posX = par3;
		this.posY = par4;
		this.posZ = par5;
		this.addSlotToContainer(new SlotPEnchantment(this, this.tableInventory, 0, 25, 47));
		this.addSlotToContainer(new SlotPEnchantment(this, this.tableInventory, 1, 25, 15));
		int l;

		for (l = 0; l < 3; ++l)
		{
			for (int i1 = 0; i1 < 9; ++i1)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, l, 8 + l * 18, 142));
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	 public void detectAndSendChanges()
	{
		 super.detectAndSendChanges();

		 for (int i = 0; i < this.crafters.size(); ++i)
		 {
			 ICrafting icrafting = (ICrafting)this.crafters.get(i);
			 icrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
			 icrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
			 icrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
		 }
	}

	 @SideOnly(Side.CLIENT)
	 public void updateProgressBar(int par1, int par2)
	 {
		 if (par1 >= 0 && par1 <= 2)
		 {
			 this.enchantLevels[par1] = par2;
		 }
		 else
		 {
			 super.updateProgressBar(par1, par2);
		 }
	 }

	 /**
	  * Callback for when the crafting matrix is changed.
	  */
	 public void onCraftMatrixChanged(IInventory par1IInventory)
	 {
		 if (par1IInventory == this.tableInventory)
		 {
			 ItemStack itemstack = par1IInventory.getStackInSlot(0);
			 ItemStack money = par1IInventory.getStackInSlot(1);
			 int i;
			 if(money != null && this.isMoney(money))
			 {
				 int point = (money.itemID == Item.emerald.itemID)? PrepaidEnchantment.EmeraldCost:(money.itemID == Item.ingotGold.itemID)? PrepaidEnchantment.GoldCost: (money.itemID == Item.diamond.itemID)? PrepaidEnchantment.DiamondCost:0;
				 point *= money.stackSize;
				 this.tileEntity.addCurrentMaxLevel(point);
				 par1IInventory.setInventorySlotContents(1, null);
			 }

			 if (itemstack != null && itemstack.isItemEnchantable())
			 {
				 this.nameSeed = this.rand.nextLong();

				 if (!this.worldPointer.isRemote)
				 {
					 i = 0;
					 int j;
					 this.Bonus = 0;
					 for (j = -1; j <= 1; ++j)
					 {
						 for (int k = -1; k <= 1; ++k)
						 {
							 if ((j != 0 || k != 0) && this.worldPointer.isAirBlock(this.posX + k, this.posY, this.posZ + j) && this.worldPointer.isAirBlock(this.posX + k, this.posY + 1, this.posZ + j))
							 {
								 this.Bonus += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY,     posZ + j * 2);
								 this.Bonus += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY + 1, posZ + j * 2);

								 if (k != 0 && j != 0)
								 {
									 this.Bonus += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY,     posZ + j    );
									 this.Bonus += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY + 1, posZ + j    );
									 this.Bonus += ForgeHooks.getEnchantPower(worldPointer, posX + k,     posY,     posZ + j * 2);
									 this.Bonus += ForgeHooks.getEnchantPower(worldPointer, posX + k,     posY + 1, posZ + j * 2);
								 }
							 }
						 }
					 }

					 for (j = 0; j < 3; ++j)
					 {
						 this.enchantLevels[j] = this.calcItemStackEnchantability(this.rand, j, this.tileEntity.getCurrentMaxLevel(), itemstack);
					 }

					 this.detectAndSendChanges();
				 }
			 }
			 else
			 {
				 for (i = 0; i < 3; ++i)
				 {
					 this.enchantLevels[i] = 0;
				 }
			 }
		 }
	 }
	 public boolean isMoney(ItemStack item)
	 {
		 return (item.itemID == Item.emerald.itemID) || (item.itemID == Item.ingotGold.itemID) || (item.itemID == Item.diamond.itemID);
	 }
	 public static int calcItemStackEnchantability(Random par0Random, int par1, int par2, ItemStack par3ItemStack)
	 {
		 Item item = par3ItemStack.getItem();
		 int k = item.getItemEnchantability();

		 if (k <= 0)
		 {
			 return 0;
		 }
		 else
		 {
//			 if (par2 > 15)
//			 {
//				 par2 = 15;
//			 }

			 int l = par0Random.nextInt(8) + 1 + (par2/2 >> 1) + par0Random.nextInt(par2/2 + 1);
			 return par1 == 0 ? Math.max(l / 3, 1) : (par1 == 1 ? l * 2 / 3 + 1 : Math.max(l, par2));
		 }
	 }
	 /**
	  * enchants the item on the table using the specified slot; also deducts XP from player
	  */
	 public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
	 {
		 ItemStack itemstack = this.tableInventory.getStackInSlot(0);

		 if (this.enchantLevels[par2] > 0 && itemstack != null && (this.tileEntity.getPoint() >= this.enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode))
		 {
			 if (!this.worldPointer.isRemote)
			 {
				 List list = this.buildEnchantmentList(this.rand, itemstack, this.enchantLevels[par2] + this.Bonus);
				 boolean flag = itemstack.itemID == Item.book.itemID;

				 if (list != null)
				 {
//					 par1EntityPlayer.addExperienceLevel(-this.enchantLevels[par2]);
					 this.tileEntity.removePoint(this.enchantLevels[par2]);

					 if (flag)
					 {
						 itemstack.itemID = Item.enchantedBook.itemID;
					 }

					 int j = flag ? this.rand.nextInt(list.size()) : -1;

					 for (int k = 0; k < list.size(); ++k)
					 {
						 EnchantmentData enchantmentdata = (EnchantmentData)list.get(k);

						 if (!flag || k == j)
						 {
							 if (flag)
							 {
								 Item.enchantedBook.addEnchantment(itemstack, enchantmentdata);
							 }
							 else
							 {
								 itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
							 }
						 }
					 }

					 this.onCraftMatrixChanged(this.tableInventory);
				 }
			 }

			 return true;
		 }
		 else
		 {
			 return false;
		 }
	 }
	 public static List buildEnchantmentList(Random par0Random, ItemStack par1ItemStack, int par2)
	 {
		 Item item = par1ItemStack.getItem();
		 int j = item.getItemEnchantability();

		 if (j <= 0)
		 {
			 return null;
		 }
		 else
		 {
			 j /= 2;
			 j = 1 + par0Random.nextInt((j >> 1) + 1) + par0Random.nextInt((j >> 1) + 1);
			 int k = j + par2;
			 float f = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0F) * 0.15F;
			 int l = (int)((float)k * (1.0F + f) + 0.5F);

			 if (l < 1)
			 {
				 l = 1;
			 }

			 ArrayList arraylist = null;
			 Map map = mapEnchantmentData(l, par1ItemStack);

			 if (map != null && !map.isEmpty())
			 {
				 EnchantmentData enchantmentdata = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());

				 if (enchantmentdata != null)
				 {
					 arraylist = new ArrayList();
					 arraylist.add(enchantmentdata);

					 for (int i1 = l; par0Random.nextInt(50) <= i1; i1 >>= 1)
					 {
						 Iterator iterator = map.keySet().iterator();

						 while (iterator.hasNext())
						 {
							 Integer integer = (Integer)iterator.next();
							 boolean flag = true;
							 Iterator iterator1 = arraylist.iterator();

							 while (true)
							 {
								 if (iterator1.hasNext())
								 {
									 EnchantmentData enchantmentdata1 = (EnchantmentData)iterator1.next();

									 if (enchantmentdata1.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[integer.intValue()]))
									 {
										 continue;
									 }

									 flag = false;
								 }

								 if (!flag)
								 {
									 iterator.remove();
								 }

								 break;
							 }
						 }

						 if (!map.isEmpty())
						 {
							 EnchantmentData enchantmentdata2 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());
							 arraylist.add(enchantmentdata2);
						 }
					 }
				 }
			 }

			 return arraylist;
		 }
	 }
	 public static Map mapEnchantmentData(int par0, ItemStack par1ItemStack)
	 {
		 Item item = par1ItemStack.getItem();
		 HashMap hashmap = null;
		 boolean flag = par1ItemStack.itemID == Item.book.itemID;
		 Enchantment[] aenchantment = Enchantment.enchantmentsList;
		 int j = aenchantment.length;

		 for (int k = 0; k < j; ++k)
		 {
			 Enchantment enchantment = aenchantment[k];

			 if (enchantment != null && (enchantment.canApplyAtEnchantingTable(par1ItemStack) || flag))
			 {
				 for (int l = enchantment.getMinLevel(); l <= enchantment.getMaxLevel(); ++l)
				 {
					 if (par0 >= enchantment.getMinEnchantability(l) && par0 <= enchantment.getMaxEnchantability(l))
					 {
						 if (hashmap == null)
						 {
							 hashmap = new HashMap();
						 }

						 hashmap.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, l));
					 }
				 }
			 }
		 }

		 return hashmap;
	 }
	 /**
	  * Callback for when the crafting gui is closed.
	  */
	  public void onContainerClosed(EntityPlayer par1EntityPlayer)
	 {
		  super.onContainerClosed(par1EntityPlayer);

		  if (!this.worldPointer.isRemote)
		  {
			  ItemStack itemstack = this.tableInventory.getStackInSlotOnClosing(0);

			  if (itemstack != null)
			  {
				  par1EntityPlayer.dropPlayerItem(itemstack);
			  }
		  }
	 }

	  public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	  {
		  return this.worldPointer.getBlockId(this.posX, this.posY, this.posZ) != Block.enchantmentTable.blockID ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
	  }

	  /**
	   * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	   */
	  public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	  {
		  ItemStack itemstack = null;
		  Slot slot = (Slot)this.inventorySlots.get(par2);

		  if (slot != null && slot.getHasStack())
		  {
			  ItemStack itemstack1 = slot.getStack();
			  itemstack = itemstack1.copy();

			  if (par2 == 0)
			  {
				  if (!this.mergeItemStack(itemstack1, 1, 37, true))
				  {
					  return null;
				  }
			  }
			  else
			  {
				  if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1))
				  {
					  return null;
				  }

				  if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1)
				  {
					  ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
					  itemstack1.stackSize = 0;
				  }
				  else if (itemstack1.stackSize >= 1)
				  {
					  ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.itemID, 1, itemstack1.getItemDamage()));
					  --itemstack1.stackSize;
				  }
			  }

			  if (itemstack1.stackSize == 0)
			  {
				  slot.putStack((ItemStack)null);
			  }
			  else
			  {
				  slot.onSlotChanged();
			  }

			  if (itemstack1.stackSize == itemstack.stackSize)
			  {
				  return null;
			  }

			  slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		  }

		  return itemstack;
	  }
	  public void onButtonPushed(int buttonId)
	  {
		  this.Button = (byte)buttonId;
		  PacketDispatcher.sendPacketToServer(PacketHandler.getPacketButton(this));
		  switch(this.Button)
		  {
		  case 1:this.tileEntity.addCurrentMaxLevel(5);break;
		  case 2:this.tileEntity.addCurrentMaxLevel(-5);break;
		  case 3:this.tileEntity.addCurrentMaxLevel(10);break;
		  case 4:this.tileEntity.addCurrentMaxLevel(-10);break;
		  }
	  }
	  public void readPacketData(ByteArrayDataInput data)
	  {
		  try
		  {
			  // byte型の読み込み
			  this.Button = data.readByte();
		  }
		  catch (Exception e)
		  {
			  e.printStackTrace();
		  }
	  }

	  // パケットの書き込み(パケットの生成自体はPacketHandlerで行う)
	  public void writePacketData(DataOutputStream dos)
	  {
		  try
		  {
			  // byte型(1byte = 8bit = -127～128の整数値)を書き込む
			  dos.writeByte(this.Button);
		  }
		  catch (Exception e)
		  {
			  e.printStackTrace();
		  }
	  }
}

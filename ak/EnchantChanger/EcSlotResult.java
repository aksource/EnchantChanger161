package ak.EnchantChanger;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
class EcSlotResult extends InventoryBasic
{
    /** The brewing stand this slot belongs to. */
    final EcContainerMaterializer container;
    private ItemStack[] ResultContents;

    EcSlotResult(EcContainerMaterializer par1ContainerMaterializer, String par2Str, int par3)
    {
        super(par2Str, true, par3);
        this.container = par1ContainerMaterializer;
        this.ResultContents = new ItemStack[EcContainerMaterializer.ResultSlotNum];
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 1;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    /**
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
        this.container.onCraftMatrixChanged(this);
    }
	*/


    /**
     * Reads a tile entity from NBT.
     */
    /**
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.ResultContents = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.ResultContents.length)
            {
                this.ResultContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    */
    /**
     * Writes a tile entity to NBT.
     */
    /**
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.ResultContents.length; ++var3)
        {
            if (this.ResultContents[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.ResultContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }
    */
}
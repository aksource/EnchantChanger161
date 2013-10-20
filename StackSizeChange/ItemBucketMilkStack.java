package StackSizeChange;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class ItemBucketMilkStack extends ItemBucketMilk
{
	public ItemBucketMilkStack(int par1)
	{
		super(par1);
		this.setTextureName("bucket_milk");
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par2World.isRemote)
		{
			par3EntityPlayer.clearActivePotions();
		}

		return StackSizeChange.addropItems(par1ItemStack, par3EntityPlayer, new ItemStack(StackSizeChange.bucketEmpty));
	}

    public Icon getIconFromDamage(int par1)
    {
		if(!StackSizeChange.BucketReplace && StackSizeChange.addStackableBucket)
		{
			return Item.bucketMilk.getIconFromDamage(par1);
		}
		else
		{
	        return super.getIconFromDamage(par1);
		}
    }

	@ForgeSubscribe
	public void onEntityInteractEvent(EntityInteractEvent event)
	{
		if(!(event.target instanceof EntityCow))
		{
			return;
		}
		
		ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();
		if(itemstack != null && itemstack.itemID == StackSizeChange.bucketEmpty.itemID)
		{
			ItemStack result = StackSizeChange.addropItems(itemstack, event.entityPlayer, new ItemStack(StackSizeChange.bucketMilk), true, true);
			event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, result);
			event.setResult(Result.ALLOW);
			return;
		}
		return;
	}

}

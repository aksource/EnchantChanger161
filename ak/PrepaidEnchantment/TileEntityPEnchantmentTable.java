package ak.PrepaidEnchantment;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntityEnchantmentTable;

public class TileEntityPEnchantmentTable extends TileEntityEnchantmentTable
{
	/** Used by the render to make the book 'bounce' */
	public int tickCount;

	/** Value used for determining how the page flip should look. */
	public float pageFlip;

	/** The last tick's pageFlip value. */
	public float pageFlipPrev;
	public float field_70373_d;
	public float field_70374_e;

	/** The amount that the book is open. */
	public float bookSpread;

	/** The amount that the book is open. */
	public float bookSpreadPrev;
	public float bookRotation2;
	public float bookRotationPrev;
	public float bookRotation;
	private static Random rand = new Random();
	private String field_94136_s;
	private int point;
	private int CurrentMaxLevel = 10;
	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setInteger("prepaidPoint", this.point);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		this.point = par1NBTTagCompound.getInteger("prepaidPoint");
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	public void updateEntity()
	{
		super.updateEntity();
	}
	@Override
	public Packet getDescriptionPacket()
	{
		return PacketHandler.getPacketTile(this);
	}
	public void setPoint(int par1)
	{
		this.point = par1;
	}
	public void removePoint(int par1)
	{
		this.point -= par1;
	}
	public int getPoint()
	{
		return this.point;
	}
	public void setCurrentMaxLevel(int par1)
	{
		this.CurrentMaxLevel = par1;
	}
	public int getCurrentMaxLevel()
	{
		return this.CurrentMaxLevel;
	}
	public void addCurrentMaxLevel(int par1)
	{
		this.CurrentMaxLevel += par1;
	}
}

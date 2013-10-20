package ak.EnchantChanger;

import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import com.google.common.collect.Multimap;
import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class EcItemCloudSword extends EcItemSword
{
	private int SlotNum = 5;
	private EcCloudSwordData SwordData;
	private Random rand = new Random();
	public EcItemCloudSword(int par1)
    {
        super(par1, EnumToolMaterial.EMERALD);
        this.setTextureName(EnchantChanger.EcTextureDomain + "CloudSword");
    }

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(this.SlotNum == 5)
			return false;
		else if(this.SwordData.getStackInSlot(SlotNum) != null)
		{
			this.attackTargetEntityWithTheItem(entity, player, this.SwordData.getStackInSlot(SlotNum));
			return true;
		}
		else
			return false;
	}
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par3EntityPlayer.isSneaking())
		{
			this.doCastOffSwords(par2World, par3EntityPlayer);
			return this.makeCloudSwordCore(par1ItemStack);
		}
		else
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			if(par2World.isRemote)
			{
				if(this.SlotNum == 5)
					this.SlotNum = 0;
				else
					this.SlotNum++;
    			PacketDispatcher.sendPacketToServer(Packet_EnchantChanger.getPacketCS(this));
    			if(this.SlotNum != 5)
        			par3EntityPlayer.addChatMessage(this.SwordData.getStackInSlot(SlotNum).getDisplayName());
			}
			return par1ItemStack;
		}
    }
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    	if (!par2World.isRemote && par3Entity instanceof EntityPlayer)
		{
			this.SwordData = this.getSwordData(par1ItemStack, par2World);
			this.SwordData.onUpdate(par2World, (EntityPlayer) par3Entity);
		}
    }
	private EcCloudSwordData getSwordData(ItemStack var1, World var2)
	{
		String var3 = "swords";
		EcCloudSwordData var4 = (EcCloudSwordData)var2.loadItemData(EcCloudSwordData.class, var3);

		if (var4 == null)
		{
			var4 = new EcCloudSwordData(var3);
			var4.markDirty();
			var2.setItemData(var3, var4);
		}

		return var4;
	}
	public ItemStack makeCloudSwordCore(ItemStack stack)
	{
    	int EnchNum;
    	int EnchLv;
    	ItemStack ChangeSwordCore = new ItemStack(EnchantChanger.ItemCloudSwordCore, 1);
		NBTTagList enchOnItem = stack.getEnchantmentTagList();
		if(enchOnItem !=null)
		{
			for (int i = 0; i < enchOnItem.tagCount(); ++i)
			{
				if(((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl") > 0)
				{
					EnchNum = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("id");
					EnchLv = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl");
					ChangeSwordCore.addEnchantment(Enchantment.enchantmentsList[EnchNum], EnchLv);
				}
			}
		}
		return ChangeSwordCore;
	}
	public void doCastOffSwords(World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			for(int i=0;i<5;i++)
			{
				int j;
				for(j=0;j<9;j++)
				{
					if(player.inventory.getStackInSlot(j) == null)
					{
						player.inventory.setInventorySlotContents(j, SwordData.getStackInSlot(i));
						break;
					}
				}
				if(j == 9)
					player.dropPlayerItem(SwordData.getStackInSlot(i));
				SwordData.setInventorySlotContents(i, null);
			}
		}
	}
	public void attackTargetEntityWithTheItem(Entity par1Entity, EntityPlayer player,ItemStack stack)
	{
		if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, par1Entity)))
		{
			return;
		}
		if (stack != null && stack.getItem().onLeftClickEntity(stack, player, par1Entity))
		{
			return;
		}
		if (par1Entity.canAttackWithItem())
		{
			if (!par1Entity.hitByEntity(player))
			{
//				int var2 = stack.getDamageVsEntity(par1Entity);
                float var2 = (float)this.getItemStrength(stack);
				if (player.isPotionActive(Potion.damageBoost))
				{
					var2 += 3 << player.getActivePotionEffect(Potion.damageBoost).getAmplifier();
				}

				if (player.isPotionActive(Potion.weakness))
				{
					var2 -= 2 << player.getActivePotionEffect(Potion.weakness).getAmplifier();
				}

				int var3 = 0;
				int var4 = 0;

				if (par1Entity instanceof EntityLivingBase)
				{
					var4 = this.getEnchantmentModifierLiving(stack, player, (EntityLivingBase)par1Entity);
					var3 += EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
				}

				if (player.isSprinting())
				{
					++var3;
				}

				if (var2 > 0 || var4 > 0)
				{
					boolean var5 = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && par1Entity instanceof EntityLivingBase;

					if (var5 && var2 > 0)
					{
						var2 *= 1.5F;
					}

					var2 += var4;
					boolean var6 = false;
					int var7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);

					if (par1Entity instanceof EntityLivingBase && var7 > 0 && !par1Entity.isBurning())
					{
						var6 = true;
						par1Entity.setFire(1);
					}

					boolean var8 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(player), var2);

					if (var8)
					{
						if (var3 > 0)
						{
							par1Entity.addVelocity((double)(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F), 0.1D, (double)(MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * (float)var3 * 0.5F));
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}

						if (var5)
						{
							player.onCriticalHit(par1Entity);
						}

						if (var4 > 0)
						{
							player.onEnchantmentCritical(par1Entity);
						}

						if (var2 >= 18)
						{
							player.triggerAchievement(AchievementList.overkill);
						}

						player.setLastAttacker(par1Entity);

						if (par1Entity instanceof EntityLivingBase)
						{
							EnchantmentThorns.func_92096_a(player, (EntityLivingBase)par1Entity, this.rand);
						}
					}

					ItemStack var9 = stack;

					if (var9 != null && par1Entity instanceof EntityLivingBase)
					{
						var9.hitEntity((EntityLivingBase)par1Entity, player);

						if (var9.stackSize <= 0)
						{
							this.destroyTheItem(player, stack);
							this.doCastOffSwords(player.worldObj, player);
							player.inventory.setInventorySlotContents(player.inventory.currentItem, this.makeCloudSwordCore(player.getCurrentEquippedItem()));
						}
					}

					if (par1Entity instanceof EntityLivingBase)
					{


						player.addStat(StatList.damageDealtStat, Math.round(var2 * 10.0F));

						if (var7 > 0 && var8)
						{
							par1Entity.setFire(var7 * 4);
						}
						else if (var6)
						{
							par1Entity.extinguish();
						}
					}

					player.addExhaustion(0.3F);
				}
			}
		}
	}
	public double getItemStrength(ItemStack item)
	{
		Multimap multimap = item.getAttributeModifiers();
		double d0;
		double d1 = 0;
		if (!multimap.isEmpty())
		{
			Iterator iterator = multimap.entries().iterator();

			while (iterator.hasNext())
			{
				Entry entry = (Entry)iterator.next();
				AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
				d0 = attributemodifier.getAmount();

				if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2)
				{
					d1 = attributemodifier.getAmount();
				}
				else
				{
					d1 = attributemodifier.getAmount() * 100.0D;
				}
			}
		}
		return d1;
	}
	public void destroyTheItem(EntityPlayer player, ItemStack orig)
	{
		this.SwordData.setInventorySlotContents(this.SlotNum, (ItemStack)null);
		MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, orig));
	}
	public int getEnchantmentModifierLiving(ItemStack stack, EntityLivingBase attacker, EntityLivingBase enemy)
	{
		int calc = 0;
		if (stack != null)
		{
			NBTTagList nbttaglist = stack.getEnchantmentTagList();

			if (nbttaglist != null)
			{
				for (int i = 0; i < nbttaglist.tagCount(); ++i)
				{
					short short1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
					short short2 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

					if (Enchantment.enchantmentsList[short1] != null)
					{
						calc += Enchantment.enchantmentsList[short1].calcModifierLiving(short2, enemy);
					}
				}
			}
		}
		return calc > 0 ? 1 + rand.nextInt(calc) : 0;
	}
	@SideOnly(Side.CLIENT)

	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		List slotItem;
		if(this.SlotNum != 5 && this.SwordData.getStackInSlot(SlotNum) != null)
		{
			slotItem = this.SwordData.getStackInSlot(SlotNum).getTooltip(par2EntityPlayer, par4);
			par3List.addAll(slotItem);
		}
	}
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
    	par2EntityLiving.hurtResistantTime = 0;
    	return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
    }

    // パケットの読み込み(パケットの受け取りはPacketHandlerで行う)
 	public void readPacketData(ByteArrayDataInput data)
 	{
 		try
 		{
 			this.SlotNum = data.readInt();
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
 			dos.writeInt(SlotNum);
 		}
 		catch (Exception e)
 		{
 			e.printStackTrace();
 		}
 	}
}
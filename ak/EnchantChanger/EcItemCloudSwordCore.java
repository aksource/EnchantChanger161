package ak.EnchantChanger;

import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



public class EcItemCloudSwordCore extends EcItemSword
{
	public static boolean ActiveMode=false;
	public static Entity Attackentity = null;
	public ItemStack[] swords = new ItemStack[5];
	private EcCloudSwordData SwordData;
	private int mode = 0;
	private Minecraft mc;
	private Icon open;
	private Icon close;
	private Random rand = new Random();

	public EcItemCloudSwordCore(int par1)
	{
		super(par1, EnumToolMaterial.EMERALD);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(EnchantChanger.EcTextureDomain + "FirstSword-close");;
		this.open = par1IconRegister.registerIcon(EnchantChanger.EcTextureDomain + "FirstSword-open");
		this.close = par1IconRegister.registerIcon(EnchantChanger.EcTextureDomain + "FirstSword-close");
	}
//	public int getDamageVsEntity(Entity par1Entity)
//	{
//		return (ActiveMode)?7:6;
//	}

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (ActiveMode)?7:6, 0));
        return multimap;
    }
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking())
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			if (side == Side.CLIENT) {
				ActiveMode=!ActiveMode;
				this.itemIcon = (this.ActiveMode)? this.open:this.close;
				PacketDispatcher.sendPacketToServer(Packet_EnchantChanger.getPacketCSC(this));
			}
			return par1ItemStack;
		}
		else
		{
			if(!this.ActiveMode && canUnion2(par3EntityPlayer))
			{
				UnionSword2(par3EntityPlayer);
				EcCloudSwordData data = this.getSwordData(par1ItemStack, par2World);
				makeSwordData(data, swords);
				return this.makeCloudSword(par1ItemStack);
			}
			else
			{
				par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
				return par1ItemStack;
			}
		}
	}
	public ItemStack makeCloudSword(ItemStack stack)
	{
		int EnchNum;
		int EnchLv;
		ItemStack ChangeSword = new ItemStack(EnchantChanger.ItemCloudSword, 1);
		NBTTagList enchOnItem = stack.getEnchantmentTagList();
		if(enchOnItem !=null)
		{
			for (int i = 0; i < enchOnItem.tagCount(); ++i)
			{
				if(((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl") > 0)
				{
					EnchNum = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("id");
					EnchLv = ((NBTTagCompound)enchOnItem.tagAt(i)).getShort("lvl");
					ChangeSword.addEnchantment(Enchantment.enchantmentsList[EnchNum], EnchLv);
				}
			}
		}
		return ChangeSword;
	}
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		Attackentity = entity;
		if(ActiveMode)
			this.attackTargetEntityWithInventoryItem(entity, player);
		return false;
	}

	public Item setNoRepair()
	{
		canRepair = false;
		return this;
	}
	public boolean canUnion2(EntityPlayer player)
	{
		int Index = 0;
		int CurrentSlot = player.inventory.currentItem;
		ItemStack sword;
		for(int i = 0; i<9;i++)
		{
			if(i == CurrentSlot)
				continue;
			sword = player.inventory.getStackInSlot(i);
			if(sword != null && sword.getItem() instanceof ItemSword)
			{
				Index++;
			}
		}
		return Index >= 5;
	}
	//    public static int SwordNuminInventoryslot(EntityPlayer par1)
	//    {
	//    	int Sword=0;
	//    	for (int i = 0;i<9;i++)
	//    	{
	//    		ItemStack SlotItem=par1.inventory.getStackInSlot(i);
	//    		if(SlotItem!=null)
	//    			if(SlotItem.getItem()==Item.swordDiamond||SlotItem.getItem()==Item.swordGold||SlotItem.getItem()==Item.swordIron)
	//    				Sword++;
	//    	}
	//    	return Sword;
	//    }
	//    public int SwordNuminInventoryslot(EntityPlayerSP par1)
	//    {
	//    	int Sword=0;
	//    	for (int i = 0;i<9;i++)
	//    	{
	//    		ItemStack SlotItem=par1.inventory.getStackInSlot(i);
	//    		if(SlotItem!=null)
	//    			if(SlotItem.getItem()==Item.swordDiamond||SlotItem.getItem()==Item.swordGold||SlotItem.getItem()==Item.swordIron)
	//    				Sword++;
	//    	}
	//    	return Sword;
	//    }
	//    public boolean checkmode(int mode)
	//	{
	//		return mode != 0;
	//	}
	public void attackTargetEntityWithInventoryItem(Entity par1Entity, EntityPlayer player)
	{
		ItemStack sword;
		int CurrentSlot = player.inventory.currentItem;
		for(int i=0;i<9;i++)
		{
			if(i == CurrentSlot)
				continue;
			sword = player.inventory.getStackInSlot(i);
			if(sword != null && sword.getItem() instanceof ItemSword && !(sword.getItem() instanceof EcItemSword))
			{
				this.attackTargetEntityWithTheItem(par1Entity, player, sword, i);
			}
		}
	}
	public void attackTargetEntityWithTheItem(Entity par1Entity, EntityPlayer player,ItemStack stack, int Slot)
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
					boolean var5 = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && par1Entity instanceof EntityLiving;

					if (var5 && var2 >0)
					{
						var2 *= 1.5F;
					}

					var2 += var4;
					boolean var6 = false;
					int var7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);

					if (par1Entity instanceof EntityLiving && var7 > 0 && !par1Entity.isBurning())
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

						if (par1Entity instanceof EntityLiving)
						{
							EnchantmentThorns.func_92096_a(player, (EntityLiving)par1Entity, this.rand);
						}
					}

					ItemStack var9 = stack;

					if (var9 != null && par1Entity instanceof EntityLiving)
					{
						var9.hitEntity((EntityLiving)par1Entity, player);
						par1Entity.hurtResistantTime = 0;
						if (var9.stackSize <= 0)
						{
							MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, stack));
							player.inventory.setInventorySlotContents(Slot, null);
						}
					}

					if (par1Entity instanceof EntityLiving)
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
	public void UnionSword2(EntityPlayer player)
	{
		int Index = 0;
		int CurrentSlot = player.inventory.currentItem;
		ItemStack sword;
		for(int i = 0; i<9;i++)
		{
			if(i == CurrentSlot)
				continue;
			sword = player.inventory.getStackInSlot(i);
			if(sword != null && sword.getItem() instanceof ItemSword && !(sword.getItem() instanceof EcItemSword) && Index < 5)
			{
				this.swords[Index] = sword;
				this.swords[Index].setTagCompound(sword.getTagCompound());
				player.inventory.setInventorySlotContents(i, null);
				Index++;
			}
		}
	}
	public void makeSwordData(EcCloudSwordData data, ItemStack[] items)
	{
		for(int i=0;i<5;i++)
		{
			data.setInventorySlotContents(i, items[i]);
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
	// パケットの読み込み(パケットの受け取りはPacketHandlerで行う)
	public void readPacketData(ByteArrayDataInput data)
	{
		try
		{
			this.ActiveMode = data.readBoolean();
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
			dos.writeBoolean(ActiveMode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
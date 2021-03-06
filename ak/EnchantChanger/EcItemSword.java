package ak.EnchantChanger;

import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import ak.EnchantChanger.Client.EcKeyHandler;

import com.google.common.collect.Multimap;
import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;

public class EcItemSword extends ItemSword {

	private boolean toggle = false;

	public EcItemSword(int par1, EnumToolMaterial toolMaterial) {
		super(par1, toolMaterial);
		// this.setMaxDamage(-1);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		if (par3Entity instanceof EntityPlayer && par5) {
			if (par2World.isRemote) {
				this.toggle = EcKeyHandler.MagicKeyDown
						&& EcKeyHandler.MagicKeyUp;
				PacketDispatcher.sendPacketToServer(Packet_EnchantChanger
						.getPacketSword(this));
			} else if (par1ItemStack.getItemDamage() > 0
					&& par2World.getTotalWorldTime() % 100L == 0L) {
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
				;
			}
			if (toggle) {
				EcKeyHandler.MagicKeyUp = false;
				doMagic(par1ItemStack, par2World, (EntityPlayer) par3Entity);
			}
		}
	}
//For Debug
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(ItemStack item, EntityPlayer player, List par3List, boolean par4)
//	{
//		par3List.add(String.valueOf(item.getItemDamage()));
//	}

	public static void doMagic(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (EnchantmentHelper.getEnchantmentLevel(
				EnchantChanger.EnchantmentMeteoId, par1ItemStack) > 0) {
			EcItemMateria.Meteo(par2World, par3EntityPlayer);
		}
		if (EnchantmentHelper.getEnchantmentLevel(
				EnchantChanger.EndhantmentHolyId, par1ItemStack) > 0) {
			EcItemMateria.Holy(par2World, par3EntityPlayer);
		}
		if (EnchantmentHelper.getEnchantmentLevel(
				EnchantChanger.EnchantmentTelepoId, par1ItemStack) > 0) {
			EcItemMateria.Teleport(par1ItemStack, par2World, par3EntityPlayer);
		}
		if (EnchantmentHelper.getEnchantmentLevel(
				EnchantChanger.EnchantmentThunderId, par1ItemStack) > 0) {
			EcItemMateria.Thunder(par2World, par3EntityPlayer);
		}
	}

	public static boolean hasFloat(ItemStack itemstack) {
		return EnchantmentHelper.getEnchantmentLevel(
				EnchantChanger.EnchantmentFloatId, itemstack) > 0;
	}

	public Item setNoRepair() {
		canRepair = false;
		return this;
	}

	// 内蔵武器切り替え用攻撃メソッドの移植
	public void attackTargetEntityWithTheItem(Entity par1Entity,
			EntityPlayer player, ItemStack stack, boolean cancelHurt) {
		if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player,
				par1Entity))) {
			return;
		}
		if (stack != null
				&& stack.getItem().onLeftClickEntity(stack, player, par1Entity)) {
			return;
		}
		if (par1Entity.canAttackWithItem()) {
			if (!par1Entity.hitByEntity(player)) {
				float var2 = (float) this.getItemStrength(stack);
				if (player.isPotionActive(Potion.damageBoost)) {
					var2 += 3 << player.getActivePotionEffect(
							Potion.damageBoost).getAmplifier();
				}

				if (player.isPotionActive(Potion.weakness)) {
					var2 -= 2 << player.getActivePotionEffect(Potion.weakness)
							.getAmplifier();
				}

				int var3 = 0;
				int var4 = 0;

				if (par1Entity instanceof EntityLivingBase) {
					var4 = this.getEnchantmentModifierLiving(stack, player,
							(EntityLivingBase) par1Entity);
					var3 += EnchantmentHelper.getEnchantmentLevel(
							Enchantment.knockback.effectId, stack);
				}

				if (player.isSprinting()) {
					++var3;
				}

				if (var2 > 0 || var4 > 0) {
					boolean var5 = player.fallDistance > 0.0F
							&& !player.onGround && !player.isOnLadder()
							&& !player.isInWater()
							&& !player.isPotionActive(Potion.blindness)
							&& player.ridingEntity == null
							&& par1Entity instanceof EntityLivingBase;

					if (var5 && var2 > 0) {
						var2 *= 1.5F;
					}

					var2 += var4;
					boolean var6 = false;
					int var7 = EnchantmentHelper.getEnchantmentLevel(
							Enchantment.fireAspect.effectId, stack);

					if (par1Entity instanceof EntityLivingBase && var7 > 0
							&& !par1Entity.isBurning()) {
						var6 = true;
						par1Entity.setFire(1);
					}

					boolean var8 = par1Entity.attackEntityFrom(
							DamageSource.causePlayerDamage(player), var2);

					if (var8) {
						if (var3 > 0) {
							par1Entity.addVelocity(
									(double) (-MathHelper
											.sin(player.rotationYaw
													* (float) Math.PI / 180.0F)
											* (float) var3 * 0.5F),
									0.1D,
									(double) (MathHelper.cos(player.rotationYaw
											* (float) Math.PI / 180.0F)
											* (float) var3 * 0.5F));
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}

						if (var5) {
							player.onCriticalHit(par1Entity);
						}

						if (var4 > 0) {
							player.onEnchantmentCritical(par1Entity);
						}

						if (var2 >= 18) {
							player.triggerAchievement(AchievementList.overkill);
						}

						player.setLastAttacker(par1Entity);

						if (par1Entity instanceof EntityLivingBase) {
							EnchantmentThorns.func_92096_a(player,
									(EntityLivingBase) par1Entity,
									player.worldObj.rand);
						}
					}

					ItemStack var9 = stack;

					if (var9 != null && par1Entity instanceof EntityLivingBase) {
						var9.hitEntity((EntityLivingBase) par1Entity, player);
						if (cancelHurt)
							par1Entity.hurtResistantTime = 0;
						if (var9.stackSize <= 0) {
							this.destroyTheItem(player, stack);
						}
					}

					if (par1Entity instanceof EntityLivingBase) {
						player.addStat(StatList.damageDealtStat,
								Math.round(var2 * 10.0F));

						if (var7 > 0 && var8) {
							par1Entity.setFire(var7 * 4);
						} else if (var6) {
							par1Entity.extinguish();
						}
					}

					player.addExhaustion(0.3F);
				}
			}
		}
	}

	public double getItemStrength(ItemStack item) {
		Multimap multimap = item.getAttributeModifiers();
		double d0;
		double d1 = 0;
		if (!multimap.isEmpty()) {
			Iterator iterator = multimap.entries().iterator();

			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				AttributeModifier attributemodifier = (AttributeModifier) entry
						.getValue();
				d0 = attributemodifier.getAmount();

				if (attributemodifier.getOperation() != 1
						&& attributemodifier.getOperation() != 2) {
					d1 = attributemodifier.getAmount();
				} else {
					d1 = attributemodifier.getAmount() * 100.0D;
				}
			}
		}
		return d1;
	}

	public int getEnchantmentModifierLiving(ItemStack stack,
			EntityLivingBase attacker, EntityLivingBase enemy) {
		int calc = 0;
		if (stack != null) {
			NBTTagList nbttaglist = stack.getEnchantmentTagList();

			if (nbttaglist != null) {
				for (int i = 0; i < nbttaglist.tagCount(); ++i) {
					short short1 = ((NBTTagCompound) nbttaglist.tagAt(i))
							.getShort("id");
					short short2 = ((NBTTagCompound) nbttaglist.tagAt(i))
							.getShort("lvl");

					if (Enchantment.enchantmentsList[short1] != null) {
						calc += Enchantment.enchantmentsList[short1]
								.calcModifierLiving(short2, enemy);
					}
				}
			}
		}
		return calc > 0 ? 1 + attacker.worldObj.rand.nextInt(calc) : 0;
	}

	public void destroyTheItem(EntityPlayer player, ItemStack orig) {
	}

	// 魔法キーのトグル判定用パケット読み込み
	public void readPacketToggleData(ByteArrayDataInput data) {
		try {
			this.toggle = data.readBoolean();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 魔法キーのトグル判定用パケット書き込み
	public void writePacketToggleData(DataOutputStream dos) {
		try {
			dos.writeBoolean(toggle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
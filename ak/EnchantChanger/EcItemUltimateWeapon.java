package ak.EnchantChanger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
public class EcItemUltimateWeapon extends EcItemSword
{
	private int ultimateWeaponDamage;

	public EcItemUltimateWeapon(int par1)
	{
		super(par1, EnumToolMaterial.EMERALD);
		this.ultimateWeaponDamage =  0;
        this.func_111206_d(EnchantChanger.EcTextureDomain + "UltimateWeapon");
	}
	public float func_82803_g()
	{
		return this.ultimateWeaponDamage;
	}
	public Multimap func_111205_h()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.ultimateWeaponDamage, 0));
		return multimap;
	}
//	@Override
//	public int getDamageVsEntity(Entity par1Entity)
//	{
//		return this.ultimateWeaponDamage;
//	}
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		float playerhealth = player.func_110143_aJ();
		float playermaxhealth =  player.func_110138_aP();
		float healthratio = playerhealth / playermaxhealth;
		int mobmaxhealth = 0;
		if(entity instanceof EntityLivingBase)
		{
			mobmaxhealth = MathHelper.floor_float(((EntityLivingBase) entity).func_110138_aP()/3)+1;
			if(player instanceof EntityPlayer && healthratio >= 1 && mobmaxhealth > this.ultimateWeaponDamage+WeaponDamagefromHP(player))
			{
				this.ultimateWeaponDamage = mobmaxhealth;
			}
			else
			{
				this.ultimateWeaponDamage = WeaponDamagefromHP(player);
			}
		}
		else if(entity instanceof EntityDragonPart)
		{
			this.ultimateWeaponDamage = 100;
		}
		else
		{
			this.ultimateWeaponDamage = 10;
		}
		return false;
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	public int WeaponDamagefromHP(EntityPlayer player)
	{
		int nowHP = MathHelper.ceiling_float_int(player.func_110143_aJ());
		float damageratio;
		switch(nowHP)
		{
		case 20:
		case 19:
		case 18:
		case 17:
			damageratio = 1;break;
		case 16:
		case 15:
		case 14:
		case 13:
		case 12:
		case 11:
			damageratio = 0.7f;break;
		case 10:
		case 9:
		case 8:
		case 7:
		case 6:
		case 5:
			damageratio = 0.5f;break;
		case 4:
		case 3:
		case 2:
		case 1:
			damageratio = 0.3f;break;
		default :damageratio = 0;
		}
		int EXPLv = player.experienceLevel;
		return MathHelper.floor_float((10 + EXPLv/5)*damageratio);

	}
}

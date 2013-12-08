package ak.EnchantChanger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
public class EcItemUltimateWeapon extends EcItemSword
{
	private double ultimateWeaponDamage = 0;
	public EcItemUltimateWeapon(int par1)
	{
		super(par1, EnumToolMaterial.EMERALD);
        this.setTextureName(EnchantChanger.EcTextureDomain + "UltimateWeapon");
	}
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.ultimateWeaponDamage, 0));
		return multimap;
	}
	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if(player.worldObj.isRemote)return false;
		if(entity instanceof EntityLivingBase){
			float mobmaxhealth =((EntityLivingBase) entity).getMaxHealth() / 3 + 1;
			float weaponDmgFromHP = WeaponDamagefromHP(player);
			ultimateWeaponDamage = (mobmaxhealth > weaponDmgFromHP)?mobmaxhealth:weaponDmgFromHP;
		}else if(entity instanceof EntityDragonPart){
			ultimateWeaponDamage = 100;
		}else{
			ultimateWeaponDamage = 10;
		}
		changeItemDamageStrength(player, itemstack);
		return false;
	}
	private void changeItemDamageStrength(EntityLivingBase entity, ItemStack item){
		BaseAttributeMap attributeMap = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, entity, 2);
		attributeMap.applyAttributeModifiers(item.getAttributeModifiers());
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	public float WeaponDamagefromHP(EntityPlayer player)
	{
		float nowHP = player.getHealth();
		float maxHP = player.getMaxHealth();
		float hpRatio = nowHP / maxHP;
		float damageratio;
		if(hpRatio >= 0.8){
			damageratio = 1;
		}else if(hpRatio >= 0.5){
			damageratio = 0.7F;
		}else if(hpRatio >= 0.2){
			damageratio = 0.5F;
		}else{
			damageratio = 0.3F;
		}
		int EXPLv = player.experienceLevel;
		return MathHelper.floor_float((10 + EXPLv/5)*damageratio);
	}
}

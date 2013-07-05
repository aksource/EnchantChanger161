package Nanashi.AdvancedTools;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Entity_FireZombie extends EntityZombie
{
	private static final ItemStack defaultHeldItem = new ItemStack(Item.axeStone, 1);

	public Entity_FireZombie(World var1)
	{
		super(var1);
		//		this.texture = AdvancedTools.mobTexture + "fzombie.png";
		this.isImmuneToFire = true;
	}
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
		this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
		this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
//		this.func_110140_aT().func_111150_b(field_110186_bp).func_111128_a(this.rand.nextDouble() * 0.10000000149011612D);
	}
	protected void entityInit()
	{
		super.entityInit();
	}
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.isWet())
		{
			this.attackEntityFrom(DamageSource.drown, 2);
		}
		else
		{
			this.setFire(1);
		}
	}

	public boolean attackEntityAsMob(Entity var1)
	{
		var1.setFire(5);
		return super.attackEntityAsMob(var1);
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
	protected void attackEntity(Entity var1, float var2)
	{
		if (this.attackTime <= 0 && var2 < 2.0F && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY)
		{
			var1.setFire(5);
			this.attackTime = 20;
			this.attackEntityAsMob(var1);
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource var1)
	{
		super.onDeath(var1);

		if (var1.getEntity() instanceof EntityPlayer && this.rand.nextFloat() <= 0.05F)
		{
			ItemStack var2 = new ItemStack(Item.axeStone, 1);

			if (this.rand.nextFloat() <= 0.5F)
			{
				var2.addEnchantment(Enchantment.efficiency, 1 + this.rand.nextInt(4));
			}

			if (this.rand.nextFloat() <= 0.5F)
			{
				var2.addEnchantment(Enchantment.unbreaking, 1);
			}

			this.entityDropItem(var2, 1.0F);
		}
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		if (this.rand.nextFloat() <= 0.2F + 0.1F * (float)var2)
		{
			this.dropItem(AdvancedTools.RedEnhancer.itemID, 1);
		}
	}

	//	public int getMaxHealth()
	//	{
	//		return 30;
	//	}
	//	public int getAttackStrength(Entity par1Entity)
	//	{
	//		return 6;
	//	}
	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	public ItemStack getHeldItem()
	{
		return defaultHeldItem;
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
}

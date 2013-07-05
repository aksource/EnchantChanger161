package Nanashi.AdvancedTools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Entity_ZombieWarrior extends EntityZombie
{
	private final ItemStack defaultHeldItem;

	public Entity_ZombieWarrior(World var1)
	{
		super(var1);
//		this.moveSpeed = 0.32F;
		this.experienceValue = 10;
//		this.texture = AdvancedTools.mobTexture + "zombiew.png";
		this.defaultHeldItem = new ItemStack(AdvancedTools.DevilSword, 1);
	}
    protected void func_110147_ax()
    {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.32D);
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0D);
    }
//	public String getTexture()
//	{
//		return this.texture;
//	}
	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource var1)
	{
		super.onDeath(var1);

		if (var1.getEntity() instanceof EntityPlayer)
		{
			boolean var2 = false;
			ItemStack var3 = ((EntityPlayer)var1.getEntity()).getCurrentEquippedItem();

			if (var3 != null)
			{
				var2 = var3.itemID == AdvancedTools.LuckLuck.itemID && this.rand.nextFloat() < 0.5F;
			}

			if (var2 || this.rand.nextFloat() < 0.05F)
			{
				this.dropItem(AdvancedTools.DevilSword.itemID, 1);
			}
		}
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		if (this.rand.nextFloat() <= 0.1F + 0.1F * (float)var2)
		{
			this.dropItem(AdvancedTools.BlueEnhancer.itemID, 1);
		}
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
	protected void attackEntity(Entity var1, float var2)
	{
		if (this.attackTime <= 0 && var2 < 2.0F && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY)
		{
			this.attackTime = 20;
			this.attackEntityAsMob(var1);
		}
	}

	/**
	 * knocks back this entity
	 */
	public void knockBack(Entity var1, int var2, double var3, double var5)
	{
		this.motionY += 0.4000000059604645D;

		if (this.motionY > 0.4000000059604645D)
		{
			this.motionY = 0.4000000059604645D;
		}
	}

//	public int getMaxHealth()
//	{
//		return 40;
//	}
//	public int getAttackStrength(Entity par1Entity)
//	{
//		return 10;
//	}
	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	public ItemStack getHeldItem()
	{
		return this.func_110143_aJ() > 0 ? this.defaultHeldItem : null;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	public boolean getCanSpawnHere()
	{
		return this.posY < 50.0D && super.getCanSpawnHere();
	}
}

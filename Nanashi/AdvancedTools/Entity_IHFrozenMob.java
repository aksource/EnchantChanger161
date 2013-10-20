package Nanashi.AdvancedTools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Entity_IHFrozenMob extends Entity
{
	EntityLivingBase frozen;
	EntityPlayer player;
	int FrozenRest;
//	int maxHealth;

	public Entity_IHFrozenMob(World var1)
	{
		super(var1);
	}

	public Entity_IHFrozenMob(World var1, EntityLivingBase var2, EntityPlayer var3)
	{
		super(var1);

		if (var2 == null)
		{
			this.setDead();
			this.frozen = null;
		}
		else
		{
			this.frozen = var2;
			this.player = var3;
			this.setSize(this.frozen.width, this.frozen.height);
			this.frozen.ridingEntity = null;
			this.setPosition(this.frozen.posX, this.frozen.posY, this.frozen.posZ);
			this.rotationYaw = this.frozen.rotationYaw;

			if (this.frozen.isBurning())
			{
				this.setDead();
				this.frozen.setFire(0);
				this.frozen = null;
			}
			else
			{
				this.frozen.setFire(0);
//				List var3 = var1.getEntitiesWithinAABB(Entity_IHFrozenMob.class, this.boundingBox.expand(1.0D, 1.0D, 1.0D));
//
//				for (int var4 = 0; var4 < var3.size(); ++var4)
//				{
//					if (var3.get(var4) instanceof Entity_IHFrozenMob)
//					{
//						this.setDead();
//						this.frozen = null;
//						return;
//					}
//				}

				if (this.frozen instanceof EntityMob)
				{
					this.frozen.attackTime = 200;
					this.frozen.addPotionEffect(new PotionEffect(15,200));
					((EntityMob)this.frozen).setAttackTarget(this.frozen);
					((EntityMob)this.frozen).setRevengeTarget(this.frozen);
				}

				this.FrozenRest = 200;
			}
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		--this.FrozenRest;

		if (this.frozen == null)
		{
			this.setDead();
		}
		else if (!this.frozen.isBurning() && this.FrozenRest >= 0/* && (this.frozen.hurtTime == 0 || this.FrozenRest >= 190)*/)
		{
			if (this.frozen.getHealth() > 0 && !this.frozen.isDead && !(this.frozen instanceof EntityEnderman))
			{
				this.frozen.setPosition(this.posX, this.frozen.posY, this.posZ);
				this.setPosition(this.frozen.posX, this.frozen.posY, this.frozen.posZ);
				this.rotationYaw = this.frozen.rotationYaw;
				this.frozen.onGround = false;
				if(this.worldObj.isRemote)
					this.frozen.setVelocity(0.0D, this.frozen.motionY, 0.0D);

				if (this.frozen instanceof EntityMob)
				{
					((EntityMob)this.frozen).attackTime = 20;
					((EntityMob)this.frozen).setAttackTarget(this.frozen);
					((EntityMob)this.frozen).setRevengeTarget(this.frozen);
				}

				for (int var1 = 0; var1 < 2; ++var1)
				{
					double var2 = this.rand.nextDouble() * (double)this.width;
					double var4 = this.rand.nextDouble() * Math.PI * 1.0D;
					double var6 = this.posX + var2 * Math.sin(var4);
					double var8 = this.posY + (double)this.height * this.rand.nextDouble();
					double var10 = this.posZ + var2 * Math.cos(var4);
					this.worldObj.spawnParticle("explode", var6, var8, var10, 0.0D, 0.0D, 0.0D);
				}
			}
			else
			{
				this.setDead();
			}
		}
		else
		{
			this.setDead();
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.glass", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
	}

	 /**
	  * Will get destroyed next tick.
	  */
	 public void setDead()
	 {
		 if(!this.worldObj.isRemote && this.frozen instanceof EntityMob)
		 {
			 ((EntityMob)this.frozen).setAttackTarget(this.player);
			 ((EntityMob)this.frozen).setRevengeTarget(this.player);
		 }
		 super.setDead();
	 }

	 /**
	  * Gets how bright this entity is.
	  */
	 public float getBrightness(float var1)
	 {
//		 return this.frozen == null ? 0.0F : (float)this.frozen.getBrightnessForRender(var1);
		 return this.frozen == null ? 0.0F : 1.0f;
	 }

//	 public int getMaxHealth()
//	 {
//		 return 2;
//	 }

	 protected void entityInit() {}

	 /**
	  * (abstract) Protected helper method to read subclass entity data from NBT.
	  */
	 protected void readEntityFromNBT(NBTTagCompound var1) {}

	 /**
	  * (abstract) Protected helper method to write subclass entity data to NBT.
	  */
	 protected void writeEntityToNBT(NBTTagCompound var1) {}
}

package CaveStory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMissile extends EntityFireball implements IProjectile
{
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private boolean inGround = false;
    public EntityLiving shootingEntity;
    private int ticksAlive;
    private int ticksInAir = 0;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    public int ExpPower;
    public boolean isSuper;

	public EntityMissile(World par1World)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityMissile(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
	}

	public EntityMissile(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4, float par5)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;
//		if (par2EntityLiving instanceof EntityPlayer)
//		{
//			this.canBePickedUp = 1;
//		}

		this.posY = par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight() - 0.10000000149011612D;
		double d0 = par3EntityLiving.posX - par2EntityLiving.posX;
		double d1 = par3EntityLiving.boundingBox.minY + (double)(par3EntityLiving.height / 3.0F) - this.posY;
		double d2 = par3EntityLiving.posZ - par2EntityLiving.posZ;
		double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D)
		{
			float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(par2EntityLiving.posX + d4, this.posY, par2EntityLiving.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			float f4 = (float)d3 * 0.2F;
			this.setThrowableHeading(d0, d1 + (double)f4, d2, par4, par5);
		}
	}

	public EntityMissile(World par1World, EntityLiving par2EntityLiving, float par3, int power, boolean isSuper)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;
		this.ExpPower = power;
		this.isSuper = isSuper;
//		if (par2EntityLiving instanceof EntityPlayer)
//		{
//			this.canBePickedUp = 1;
//		}

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
	}


	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
	 */
	 public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= (double)f2;
		par3 /= (double)f2;
		par5 /= (double)f2;
		par1 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
		par3 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
		par5 += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)par8;
		par1 *= (double)par7;
		par3 *= (double)par7;
		par5 *= (double)par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f3) * 180.0D / Math.PI);
//		this.ticksInGround = 0;
	}

	 @SideOnly(Side.CLIENT)

	 /**
	  * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	  * posY, posZ, yaw, pitch
	  */
	 public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	 {
		 this.setPosition(par1, par3, par5);
		 this.setRotation(par7, par8);
	 }

	 @SideOnly(Side.CLIENT)

	 /**
	  * Sets the velocity to the args. Args: x, y, z
	  */
	 public void setVelocity(double par1, double par3, double par5)
	 {
		 this.motionX = par1;
		 this.motionY = par3;
		 this.motionZ = par5;

		 if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		 {
			 float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			 this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			 this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f) * 180.0D / Math.PI);
			 this.prevRotationPitch = this.rotationPitch;
			 this.prevRotationYaw = this.rotationYaw;
			 this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
//			 this.ticksInGround = 0;
		 }
	 }

	 /**
	  * Called to update the entity's position/logic.
	  */
	 public void onUpdate()
	 {
		 if (!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ)))
		 {
			 this.setDead();
		 }
		 else
		 {
			 super.onUpdate();
			 this.setFire(1);

			 if (this.inGround)
			 {
				 int i = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

				 if (i == this.inTile)
				 {
					 ++this.ticksAlive;

					 if (this.ticksAlive == 600)
					 {
						 this.setDead();
					 }

					 return;
				 }

				 this.inGround = false;
				 this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
				 this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
				 this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
				 this.ticksAlive = 0;
				 this.ticksInAir = 0;
			 }
			 else
			 {
				 ++this.ticksInAir;
			 }

			 Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			 Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			 MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
			 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			 if (movingobjectposition != null)
			 {
				 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			 }

			 Entity entity = null;
			 List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			 double d0 = 0.0D;

			 for (int j = 0; j < list.size(); ++j)
			 {
				 Entity entity1 = (Entity)list.get(j);

				 if (entity1.canBeCollidedWith() && (!entity1.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25))
				 {
					 float f = 0.3F;
					 AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
					 MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

					 if (movingobjectposition1 != null)
					 {
						 double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						 if (d1 < d0 || d0 == 0.0D)
						 {
							 entity = entity1;
							 d0 = d1;
						 }
					 }
				 }
			 }

			 if (entity != null)
			 {
				 movingobjectposition = new MovingObjectPosition(entity);
			 }

			 if (movingobjectposition != null)
			 {
				 this.onImpact(movingobjectposition);
			 }

			 this.posX += this.motionX;
			 this.posY += this.motionY;
			 this.posZ += this.motionZ;
			 float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			 this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;

			 for (this.rotationPitch = (float)(Math.atan2((double)f1, this.motionY) * 180.0D / Math.PI) - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			 {
				 ;
			 }

			 while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			 {
				 this.prevRotationPitch += 360.0F;
			 }

			 while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			 {
				 this.prevRotationYaw -= 360.0F;
			 }

			 while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			 {
				 this.prevRotationYaw += 360.0F;
			 }

			 this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			 this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			 float f2 = this.getMotionFactor();

			 if (this.isInWater())
			 {
				 for (int k = 0; k < 4; ++k)
				 {
					 float f3 = 0.25F;
					 this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
				 }

				 f2 = 0.8F;
			 }

			 this.motionX += this.accelerationX;
			 this.motionY += this.accelerationY;
			 this.motionZ += this.accelerationZ;
			 this.motionX *= (double)f2;
			 this.motionY *= (double)f2;
			 this.motionZ *= (double)f2;
			 this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
			 this.setPosition(this.posX, this.posY, this.posZ);
		 }
	 }
	 protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	 {
		 if (!this.worldObj.isRemote)
		 {
			 if (par1MovingObjectPosition.entityHit != null)
			 {
				 par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6);
			 }

			 this.worldObj.newExplosion((Entity)null, this.posX, this.posY, this.posZ, this.ExpPower, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			 this.setDead();
		 }
	 }
	 /**
	  * (abstract) Protected helper method to write subclass entity data to NBT.
	  */
	 public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	 {
		 super.writeEntityToNBT(par1NBTTagCompound);
		 par1NBTTagCompound.setInteger("ExplosionPower", this.ExpPower);
	 }

	 /**
	  * (abstract) Protected helper method to read subclass entity data from NBT.
	  */
	 public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	 {
		 super.readEntityFromNBT(par1NBTTagCompound);

		 if (par1NBTTagCompound.hasKey("ExplosionPower"))
		 {
			 this.ExpPower = par1NBTTagCompound.getInteger("ExplosionPower");
		 }
	 }

	 /**
	  * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	  * prevent them from trampling crops
	  */
	 protected boolean canTriggerWalking()
	 {
		 return false;
	 }

	 @SideOnly(Side.CLIENT)
	 public float getShadowSize()
	 {
		 return 0.0F;
	 }

	 /**
	  * If returns false, the item will not inflict any damage against entities.
	  */
	 public boolean canAttackWithItem()
	 {
		 return false;
	 }
}

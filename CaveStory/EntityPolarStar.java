package CaveStory;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityPolarStar extends EntityThrowable
{
	private int ticksInGround;
	private int inTile;
	private int xTile;
	private int yTile;
	private int zTile;
	private int ticksInAir;
	private int level;
	private int[] dmg = new int[]{2,4,6};
	public EntityPolarStar(World par1World)
	{
		super(par1World);
	}

	public EntityPolarStar(World par1World, EntityLiving par2EntityLiving, int level)
	{
		super(par1World, par2EntityLiving);
		setLevel(level);
	}

	public EntityPolarStar(World par1World, double par2, double par4, double par6, int level)
	{
		super(par1World, par2, par4, par6);
		setLevel(level);
	}
	public void onUpdate()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();

		if (this.throwableShake > 0)
		{
			--this.throwableShake;
		}

		if (this.inGround)
		{
			int i = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

			if (i == this.inTile)
			{
				++this.ticksInGround;

				if (this.ticksInGround == 1200)
				{
					this.setDead();
				}

				return;
			}

			this.inGround = false;
			this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
			this.ticksInGround = 0;
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

		if (!this.worldObj.isRemote)
		{
			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			EntityLivingBase entityliving = this.getThrower();

			for (int j = 0; j < list.size(); ++j)
			{
				Entity entity1 = (Entity)list.get(j);

				if (entity1.canBeCollidedWith() && (entity1 != entityliving || this.ticksInAir >= 5))
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
		}

		if (movingobjectposition != null)
		{
			if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE && this.worldObj.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Block.portal.blockID)
			{
				this.setInPortal();
			}
			else
			{
				this.onImpact(movingobjectposition);
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
		float f2 = 0.99F;
		float f3 = this.getGravityVelocity();

		if (this.isInWater())
		{
			for (int k = 0; k < 4; ++k)
			{
				float f4 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
			}

			f2 = 0.8F;
		}

		this.motionX *= (double)f2;
		this.motionY *= (double)f2;
		this.motionZ *= (double)f2;
//		this.motionY -= (double)f3;
		this.setPosition(this.posX, this.posY, this.posZ);
	}
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	 protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (par1MovingObjectPosition.entityHit != null)
		{
			int b0 = this.dmg[getLevel() - 1];

			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
		}

		for (int i = 0; i < 8; ++i)
		{
			this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

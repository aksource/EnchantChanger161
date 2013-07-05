package Nanashi.AdvancedTools;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Entity_HighSpeedCreeper extends EntityCreeper
{
	public Entity_HighSpeedCreeper(World var1)
	{
		super(var1);
//		this.texture = AdvancedTools.mobTexture + "hscreeper.png";
//		this.moveSpeed = 0.365F;
		this.experienceValue = 15;
	}
    protected void func_110147_ax()
    {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.365D);
    }
	public int getMaxHealth()
	{
		return 30;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();

		if (!this.isPotionActive(Potion.moveSpeed) && this.func_110143_aJ() <= 10)
		{
			this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1));
		}
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.creeper";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.creeperdeath";
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource var1, int var2)
	{
		return var1.damageType == "arrow" ? false : super.attackEntityFrom(var1, var2);
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		if (this.rand.nextFloat() <= 0.25F + 0.1F * (float)var2)
		{
			this.dropItem(AdvancedTools.RedEnhancer.itemID, 1);
		}

		if (this.rand.nextFloat() <= 0.1F + 0.1F * (float)var2)
		{
			this.dropItem(AdvancedTools.BlueEnhancer.itemID, 1);
		}
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	public void onStruckByLightning(EntityLightningBolt var1)
	{
		super.onStruckByLightning(var1);
	}
}

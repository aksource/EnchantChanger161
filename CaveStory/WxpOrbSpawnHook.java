package CaveStory;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class WxpOrbSpawnHook
{

	@ForgeSubscribe
	public void OrbDropEvent(LivingDeathEvent event)
	{
		if(event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer && event.entityLiving instanceof EntityLiving)
		{
			spawnOrb((EntityLiving) event.entityLiving);
		}
	}
	public void spawnOrb(EntityLiving entity)
	{
        int i;
		if (!entity.worldObj.isRemote && !entity.isChild() && entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
        {
			i = entity.experienceValue / 2;

            while (i > 0)
            {
                int j = EntityWeaponOrb.getXPSplit(i);
                i -= j;
                entity.worldObj.spawnEntityInWorld(new EntityWeaponOrb(entity.worldObj, entity.posX, entity.posY, entity.posZ, j));
            }
        }
	}
}
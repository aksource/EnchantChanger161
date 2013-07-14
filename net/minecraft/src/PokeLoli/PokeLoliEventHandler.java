package net.minecraft.src.PokeLoli;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class PokeLoliEventHandler
{
	@ForgeSubscribe
	public void EntityInteractEvent(EntityInteractEvent event)
	{
		if(event.target instanceof EntityLivingBase)
		{
			EntityPlayer player = event.entityPlayer;
			EntityLivingBase living = (EntityLivingBase) event.target;
			if(player.getCurrentEquippedItem().getItem() instanceof ItemMonsterPlacer)
			{
				ItemMonsterPlacer item = (ItemMonsterPlacer) player.getCurrentEquippedItem().getItem();
				item.func_111207_a(player.getCurrentEquippedItem(), player, living);
			}
		}
	}
}
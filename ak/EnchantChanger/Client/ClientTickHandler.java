package ak.EnchantChanger.Client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ak.EnchantChanger.EcItemSephirothSword;
import ak.EnchantChanger.EnchantChanger;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler
{
	public Minecraft mc = Minecraft.getMinecraft();
	@Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.PLAYER)))
        {
            onPlayerTick((EntityPlayer)tickData[0],((EntityPlayer)tickData[0]).worldObj);
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.PLAYER);
        // In my testing only RENDER, CLIENT, & PLAYER did anything on the client side.
        // Read 'cpw.mods.fml.common.TickType.java' for a full list and description of available types
    }

    @Override
    public String getLabel() { return "EC|ClientTickHandler"; }

    public void onPlayerTick(EntityPlayer player, World world)
    {
    	if(player == null) return;
    	ItemStack ep = player.getCurrentEquippedItem();
    	int reach = 10;
    	MovingObjectPosition MOP;
    	if(ep != null && ep.getItem() instanceof EcItemSephirothSword){
    		mc.objectMouseOver = EnchantChanger.getMouseOverCustom(player, world, reach);
    		if(mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityLivingBase){
    			mc.pointedEntityLiving = (EntityLivingBase) mc.objectMouseOver.entityHit;
    		}
    	}
    }

}
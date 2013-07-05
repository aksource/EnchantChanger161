package ak.EnchantChanger.Client;

import java.util.*;

import ak.EnchantChanger.CommonTickHandler;
import ak.EnchantChanger.EcItemMateria;
import ak.EnchantChanger.EnchantChanger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.*;

public class ClientTickHandler implements ITickHandler
{
	public int[] Count= new int[]{0,0,0,0,0,0,0,0,0,0};
	//public Minecraft mc = FMLClientHandler.instance().getClient();
	@Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            onRenderTick();
        }
        else if (type.equals(EnumSet.of(TickType.CLIENT)))
        {

        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
        // In my testing only RENDER, CLIENT, & PLAYER did anything on the client side.
        // Read 'cpw.mods.fml.common.TickType.java' for a full list and description of available types
    }

    @Override
    public String getLabel() { return null; }


    public void onRenderTick()
    {
        //System.out.println("onRenderTick");
        //TODO: Your Code Here
    }

    public void onTickInGUI(GuiScreen guiscreen)
    {
        //System.out.println("onTickInGUI");
        //TODO: Your Code Here
    }

    public void onTickInGame()
    {
    }
}
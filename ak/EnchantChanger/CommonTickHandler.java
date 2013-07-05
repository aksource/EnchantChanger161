package ak.EnchantChanger;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;


public class CommonTickHandler implements ITickHandler
{
    public static int[] LimitBreakCount = new int[]{0,0,0};
    public static int[] LimitBreakCoolDownCount = new int[]{0,0,0};
    public static boolean[] LimitBreakFlag = new boolean[]{false,false,false};
	@Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
    	if (type.equals(EnumSet.of(TickType.WORLD)))
        {
            //this.worldTick((World)var2[0]);
    		//this.playerTick(((EntityPlayer)tickData[0]).worldObj, (EntityPlayerMP)tickData[0]);
        }
        else if (type.equals(EnumSet.of(TickType.PLAYER)))
        {
            this.playerTick(((EntityPlayer)tickData[0]).worldObj, (EntityPlayerMP)tickData[0]);
        }
        else if(type.equals(EnumSet.of(TickType.SERVER)))
        {
        	onTickInGame();
        }
    }


    public EnumSet ticks()
    {
    	return EnumSet.of(TickType.WORLD, TickType.PLAYER, TickType.SERVER);
    }

    public String getLabel()
    {
        return null;
    }
    private void playerTick(World world, EntityPlayer player)
    {
        //put here any code that needs to be done server side, like set WorldGeneration, and let entities spawn on the first server tick.
        //also put all the code in a if(world != null && !world.isRemote) statement, makes it so that you don't get NPEs from that.

    	if(player != null)
    	{
    		this.LimitBreak(player);
    		this.LimitBreakCoolDown();
    	}
    }

    private void onTickInGame()
    {
        //put here any code that needs to be done server side, like set WorldGeneration, and let entities spawn on the first server tick.
        //also put all the code in a if(world != null && !world.isRemote) statement, makes it so that you don't get NPEs from that.

    }
    public void LimitBreak(EntityPlayer player)
    {
    	for(int i=0;i < this.LimitBreakFlag.length;i++)
    	{
	    	if(this.LimitBreakCount[i] <= 0)
	    	{
	    		if(this.LimitBreakFlag[i])
	    		{
	    			player.addChatMessage("LIMIT BREAK FINISH.");
	    			player.clearActivePotions();
	    			for (int k=0;k<33;k++)
	        		{
	    				player.removePotionEffect(k);
	        		}
	    		}
	    		this.LimitBreakFlag[i]=false;
	    		this.LimitBreakCount[i] = 0;
	    	}
	    	else
	    	{
	    		this.LimitBreakCount[i]--;
	    	}
    	}
    }
    public void LimitBreakCoolDown()
    {
    	for(int i=0;i < this.LimitBreakFlag.length;i++)
    	{
    		if(this.LimitBreakCoolDownCount[i]>0)
    		{
    			this.LimitBreakCoolDownCount[i]--;
    		}
    	}
    }
}
package net.minecraft.src;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;

public class mod_ReplaceBlock extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}

	@MLProp(info="TargetBlockIDs")
	public static String targetBlockID = "3, 7, 13";
	@MLProp(info="ReplaceBlockID: Recomended 0-255",min=0,max=4095)
	public static int replaceBlockID= Block.stone.blockID;
	@MLProp(info="targetYposMax",min=1,max=255)
	public static int targetYposMax=20;
	@MLProp(info="targetYposMin",min=1,max=255)
	public static int targetYposMin=1;
	@MLProp(info="chankWidely",min=0,max=16)
	public static int chankWidely= 3;

	private Chunk lastChunk = null;
	private int chunkWide = 16;
	protected ArrayList<Integer> ids = new ArrayList<Integer>();

	public mod_ReplaceBlock()
	{
		try
		{
			String strID[] = targetBlockID.split(",");

			for (int i = 0; i < strID.length; i++)
			{
				ids.add(Integer.parseInt(strID[i].trim()));
			}
		}
		catch (NumberFormatException numberformatexception)
		{
		}
	}

	public boolean isID(int id)
	{
		for(int i = 0; i < ids.size(); i++)
		{
			if(ids.get(i) == id)
			{
				return true;
			}
		}
		return false;
	}

	public void load()
	{
		if(!ids.isEmpty()){
			ModLoader.setInGameHook(this, true, false);
		}
	}

	public boolean onTickInGame(float f, Minecraft minecraft)
	{
		if(minecraft.isSingleplayer())
		{
			int posX = (int)Math.floor(minecraft.thePlayer.posX);
			int posZ = (int)Math.floor(minecraft.thePlayer.posZ);
			int posY = (int)Math.floor(minecraft.thePlayer.posY);
			Chunk chunk = minecraft.theWorld.getChunkFromBlockCoords(posX, posZ);
			if(chunk != lastChunk && targetYposMax+chankWidely >= posY && posY >= targetYposMin-chankWidely)
			{
				lastChunk = chunk;
				int chunkX = chunk.xPosition * chunkWide;
				int chunkZ = chunk.zPosition * chunkWide;
				for(int x = chunkX-chankWidely; x < chunkX + chunkWide+chankWidely; x++){
				for(int z = chunkZ-chankWidely; z < chunkZ + chunkWide+chankWidely; z++)
				{
					for(int y = targetYposMax; y >= targetYposMin; y--)
					{
						if(this.isID(minecraft.theWorld.getBlockId(x, y, z)))
						{
							minecraft.getIntegratedServer().worldServerForDimension(minecraft.thePlayer.dimension).setBlock(x, y, z, replaceBlockID, 0, 3);
						}
					}
				}}
			}
		}
		return true;
	}
}

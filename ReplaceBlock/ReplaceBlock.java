package ReplaceBlock;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="ReplaceBlock", name="ReplaceBlock", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class ReplaceBlock
{
	@Mod.Instance("ReplaceBlock")
	public static ReplaceBlock instance;

	public static int[] targetBlockID;
	public static int replaceBlockID;
	public static int targetYposMax;
	public static int targetYposMin;
	public static int chunkWidely;

	private Chunk lastChunk = null;
	private int chunkWide = 16;
	protected ArrayList<Integer> ids = new ArrayList<Integer>();

	public class LivingUpdateHook
	{
		@ForgeSubscribe
		public void LivingUpdate(LivingUpdateEvent event)
		{
			if(!event.entityLiving.worldObj.isRemote)
			{
				int posX = (int)Math.floor(event.entityLiving.posX);
				int posZ = (int)Math.floor(event.entityLiving.posZ);
				int posY = (int)Math.floor(event.entityLiving.posY);
				Chunk chunk = event.entityLiving.worldObj.getChunkFromBlockCoords(posX, posZ);
				if(chunk != lastChunk && targetYposMax+chunkWidely >= posY && posY >= targetYposMin-chunkWidely)
				{
					lastChunk = chunk;
					int chunkX = chunk.xPosition * chunkWide;
					int chunkZ = chunk.zPosition * chunkWide;
					for(int x = chunkX-chunkWidely; x < chunkX + chunkWide+chunkWidely; x++){
						for(int z = chunkZ-chunkWidely; z < chunkZ + chunkWide+chunkWidely; z++)
						{
							for(int y = targetYposMax; y >= targetYposMin; y--)
							{
								if(this.isID(event.entityLiving.worldObj.getBlockId(x, y, z)))
								{
									//minecraft.getIntegratedServer().worldServerForDimension(minecraft.thePlayer.dimension).setBlock(x, y, z, replaceBlockID, 0, 3);
									event.entityLiving.worldObj.setBlock(x, y, z, replaceBlockID, 0, 3);
								}
							}
						}
					}
				}
			}
		}
		public boolean isID(int id)
		{
			for(int i = 0; i < targetBlockID.length; i++)
			{
				if(targetBlockID[i] == id)
				{
					return true;
				}
			}
			return false;
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		targetBlockID = config.get(Configuration.CATEGORY_GENERAL, "targetBlockID", new int[]{3, 7, 13}).getIntList();
		replaceBlockID = config.get(Configuration.CATEGORY_GENERAL, "replaceBlockID", Block.stone.blockID, "ReplaceBlockID: Recomended 0-255,min=0,max=4095").getInt();
		targetYposMax = config.get(Configuration.CATEGORY_GENERAL, "targetYposMax", 20, "targetYposMax,min=1,max=255").getInt();
		targetYposMax = (targetYposMax < 1)?1:(targetYposMax > 255)?255:targetYposMax;
		targetYposMin = config.get(Configuration.CATEGORY_GENERAL, "targetYposMin", 1, "targetYposMin,min=1,max=255").getInt();
		targetYposMin = (targetYposMin < 1)?1:(targetYposMin > targetYposMax)?targetYposMax:targetYposMin;
		chunkWidely = config.get(Configuration.CATEGORY_GENERAL, "chunkWidely", 3, "chankWidely,min=0,max=16").getInt();
		chunkWidely = (chunkWidely < 0)?0:(chunkWidely > 16)?16:chunkWidely;
		config.save();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new LivingUpdateHook());
	}
}
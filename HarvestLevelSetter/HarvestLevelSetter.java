package HarvestLevelSetter;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="HarvestLevelSetter", name="HarvestLevelSetter", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class HarvestLevelSetter
{
	@Mod.Instance("HarvestLevelSetter")
	public static HarvestLevelSetter instance;

	public static String blockPickaxe;
	public static String blockShovel;
	public static String blockAxe;
	public static String toolPickaxe;
	public static String toolShovel;
	public static String toolAxe;

	private final String[] tools = {"pickaxe","shovel","axe","pickaxe","shovel","axe"};
	private String[] IDLists = new String[6];

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		blockPickaxe = config.get(Configuration.CATEGORY_GENERAL, "blockPickaxe", "", "Harvet Pickaxe Block ID List").getString();
		blockShovel = config.get(Configuration.CATEGORY_GENERAL, "blockShovel", "", "Harvet Shovel Block ID List").getString();
		blockAxe = config.get(Configuration.CATEGORY_GENERAL, "blockAxe", "", "Harvet Axe Block ID List").getString();
		toolPickaxe = config.get(Configuration.CATEGORY_GENERAL, "toolPickaxe", "", "Tool Pickaxe ID List").getString();
		toolShovel = config.get(Configuration.CATEGORY_GENERAL, "toolShovel", "", "Tool Shovel ID List").getString();
		toolAxe = config.get(Configuration.CATEGORY_GENERAL, "toolAxe", "", "Tool Axe ID List").getString();
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		IDLists[0] = blockPickaxe;
		IDLists[1] = blockShovel;
		IDLists[2] = blockAxe;
		IDLists[3] = toolPickaxe;
		IDLists[4] = toolShovel;
		IDLists[5] = toolAxe;

		for(int i = 0; i < 6; i++)
		{
			if(IDLists[i].equals("")) continue;

			String[] ids = IDLists[i].split(",");
			for(int j = 0; j < ids.length; j++)
			{
				ArrayList<Integer> data = setIDs(ids[j]);
				if(data == null) continue;

				int id = data.get(0);
				int lv = data.size()>1 ? data.get(1) : 0;
				int meta = data.size()>2 ? data.get(2) : -1;
				if(meta == -1)
				{
					if(i < 3)
					{
						MinecraftForge.setBlockHarvestLevel(Block.blocksList[id], tools[i], lv);
						FMLLog.fine(String.format("#Block %d %s %d", id, tools[i], lv));
					}else{
						MinecraftForge.setToolClass(Item.itemsList[id], tools[i], lv);
						FMLLog.fine(String.format("#Tools %d %s %d", id, tools[i], lv));
					}
				}else{
					if(i < 3)
					{
						MinecraftForge.setBlockHarvestLevel(Block.blocksList[id], meta, tools[i], lv);
						FMLLog.fine(String.format("#Block %d %s %d %d", id, tools[i], lv, meta));
					}
				}
			}
		}
	}
	public static ArrayList<Integer> setIDs(String listString)
	{
		try
		{
			String[] strID = listString.split(":");
			ArrayList<Integer> IDList = new ArrayList<Integer>(strID.length);
			
			for(int i = 0; i < strID.length; i++)
			{
				IDList.add(Integer.parseInt(strID[i].trim()));
			}
			return IDList;
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
}
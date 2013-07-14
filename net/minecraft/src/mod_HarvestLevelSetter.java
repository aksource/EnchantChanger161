package net.minecraft.src;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
 
public class mod_HarvestLevelSetter extends BaseMod 
{
	public String getVersion() {
		return "1.6.2-1";
	}

	@MLProp(info="Harvet Pickaxe Block ID List")
	public static String blockPickaxe = "";
	@MLProp(info="Harvet Shovel Block ID List")
	public static String blockShovel = "";
	@MLProp(info="Harvet Axe Block ID List")
	public static String blockAxe = "";
	@MLProp(info="Tool Pickaxe ID List")
	public static String toolPickaxe = "";
	@MLProp(info="Tool Shovel ID List")
	public static String toolShovel = "";
	@MLProp(info="Tool Axe ID List")
	public static String toolAxe = "";
	
	private final String[] tools = {"pickaxe","shovel","axe","pickaxe","shovel","axe"};
	private String[] IDLists = new String[6];

	public void load()
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
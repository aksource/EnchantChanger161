package net.minecraft.src;

import net.minecraft.block.Block;

public class mod_OreBlockLight extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}

	@MLProp(info="set Block Light Level = 15")
	public static String LightBlockIDs = "22, 41, 42, 57, 133";
	@MLProp(info="set Block Light Level = 7")
	public static String Light7BlockIDs = "";
	@MLProp(info="set Block Light Level = 0")
	public static String Light0BlockIDs = "";

	public void setLight(String IDs, float Lv)
	{
		try
		{
			for (String S: IDs.split(","))
			{
				int ID = Integer.parseInt(S.trim());
				if(ID > 0 && ID < 4096 && Block.blocksList[ID] != null) Block.blocksList[ID].setLightValue(Lv);
			}
		}
		catch (NumberFormatException numberformatexception)
		{
		}
	}

	public void load()
	{
		setLight(LightBlockIDs, 1.0F);
		setLight(Light7BlockIDs, 0.5F);
		setLight(Light0BlockIDs, 0.0F);
	}

}

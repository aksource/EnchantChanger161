package OreBlockLight;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="OreBlockLight", name="OreBlockLight", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class OreBlockLight
{
	@Mod.Instance("OreBlockLight")
	public static OreBlockLight instance;
	
	public static String LightBlockIDs;
	public static String Light7BlockIDs;
	public static String Light0BlockIDs;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		LightBlockIDs = config.get(Configuration.CATEGORY_GENERAL, "LightBlockIDs", "22, 41, 42, 57, 133", "set Block Light Level = 15").getString();
		Light7BlockIDs = config.get(Configuration.CATEGORY_GENERAL, "Light7BlockIDs", "", "set Block Light Level = 7").getString();
		Light0BlockIDs = config.get(Configuration.CATEGORY_GENERAL, "Light0BlockIDs", "", "set Block Light Level = 0").getString();
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		setLight(LightBlockIDs, 1.0F);
		setLight(Light7BlockIDs, 0.5F);
		setLight(Light0BlockIDs, 0.0F);
	}
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
}
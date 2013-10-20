package LilypadBreed;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="LilypadBreed", name="LilypadBreed", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class LilypadBreed
{
	@Mod.Instance("LilypadBreed")
	public static LilypadBreed instance;

	public static int LilypadRate = 25;
	public static Block waterlily;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		LilypadRate = config.get(Configuration.CATEGORY_GENERAL, "LilypadRate", 25, "LilyPad Spown Rate(0%-100%), min = 0, max = 100").getInt();
		LilypadRate = (LilypadRate <0)?0:(LilypadRate>100)?100:LilypadRate;
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		Block.blocksList[111] = null;
		waterlily = (new BlockLilypadBreed(111)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("waterlily").setTextureName("waterlily");
        Item.itemsList[waterlily.blockID] = new ItemLilypadBread(waterlily.blockID - 256);
	}
}
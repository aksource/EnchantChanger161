package DigBedrock;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="DigBedrock", name="DigBedrock", version="1.6srg-2",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class DigBedrock
{
	@Mod.Instance("DigBedrock")
	public static DigBedrock instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Block.blocksList[7] = null;
		Block BedrockNew = (new BlockBedrock(7)).setHardness(250.0F).setResistance(6000000.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("bedrock");
	}
}
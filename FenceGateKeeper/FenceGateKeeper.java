package FenceGateKeeper;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="FenceGateKeeper", name="FenceGateKeeper", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class FenceGateKeeper
{
	@Mod.Instance("FenceGateKeeper")
	public static FenceGateKeeper instance;
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		Block.blocksList[107] = null;
		Block fenceGate = new kpBlockFenceGate(107).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate");

	}
}
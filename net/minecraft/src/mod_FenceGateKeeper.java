package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.src.FenceGateKeeper.kpBlockFenceGate;

public class mod_FenceGateKeeper extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}

//	@MLProp(info="", min = 1, max = 64)
//	public static int  = ;

	public void load()
	{
		Block.blocksList[107] = null;
		Block fenceGate = new kpBlockFenceGate(107).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate");
	}

}

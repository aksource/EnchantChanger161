package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.src.DigBedrock.BlockBedrock;

public class mod_DigBedrock extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}

	public void load()
	{
		Block.blocksList[7] = null;
		Block BedrockNew = (new BlockBedrock(7)).setHardness(250.0F).setResistance(6000000.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("bedrock");
	}

}

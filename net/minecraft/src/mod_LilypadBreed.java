package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.src.LilypadBreed.BlockLilypadBreed;
import net.minecraft.src.LilypadBreed.ItemLilypadBread;


public class mod_LilypadBreed extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}
	
	@MLProp(info="LilyPad Spown Rate(0%-100%)", min = 0, max = 100)
	public static int LilypadRate = 25;
	public static Block waterlily;
	public void load()
	{
		//描画問題はItemBlockの置き換えで解決．おそらく1.5.2以前も同様にして防げる．
		Block.blocksList[111] = null;
		waterlily = (new BlockLilypadBreed(111)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("waterlily").func_111022_d("waterlily");
        Item.itemsList[waterlily.blockID] = new ItemLilypadBread(waterlily.blockID - 256);
	}

}

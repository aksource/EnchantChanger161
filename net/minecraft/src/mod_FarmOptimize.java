package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.src.FarmOptimize.foBlockCactus;
import net.minecraft.src.FarmOptimize.foBlockCarrot;
import net.minecraft.src.FarmOptimize.foBlockCocoa;
import net.minecraft.src.FarmOptimize.foBlockCrops;
import net.minecraft.src.FarmOptimize.foBlockMushroom;
import net.minecraft.src.FarmOptimize.foBlockNetherStalk;
import net.minecraft.src.FarmOptimize.foBlockPotato;
import net.minecraft.src.FarmOptimize.foBlockReed;
import net.minecraft.src.FarmOptimize.foBlockSapling;
import net.minecraft.src.FarmOptimize.foBlockStem;
import net.minecraft.src.FarmOptimize.foBlockVine;
import net.minecraftforge.common.MinecraftForge;

public class mod_FarmOptimize extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}

	@MLProp(info = "0:noWait  15:default", min = 0, max = 15)
	public static int SugarcaneSpeed = 15;
	@MLProp(info = "", min = 1, max = 250)
	public static int SugarcaneLimit = 3;
	@MLProp(info = "")
	public static boolean SugarcaneGrowWater = false;
	@MLProp(info = "")
	public static boolean SugarcaneUsefulBonemeal = false;
	@MLProp(info = "", min = 1, max = 250)
	public static int SugarcaneUsedBonemealLimit = 3;
	
	@MLProp(info = "0:noWait  15:default", min = 0, max = 15)
	public static int CactusSpeed = 15;
	@MLProp(info = "", min = 1, max = 250)
	public static int CactusLimit = 3;
	@MLProp(info = "")
	public static boolean CactusUsefulBonemeal = false;
	@MLProp(info = "", min = 1, max = 250)
	public static int CactusUsedBonemealLimit = 3;

	@MLProp(info = "0:noWait  1:fast  100:default", min = 0, max = 100)
	public static int growSpeedPunpkin = 100;
	@MLProp(info = "0:noWait  1:fast  100:default", min = 0, max = 100)
	public static int growSpeedWaterMelon = 100;
	@MLProp(info = "0:noWait  1:fast  100:default", min = 0, max = 100)
	public static int growSpeedCrops = 100;
	@MLProp(info = "0:noWait  1:fast  100:default", min = 0, max = 100)
	public static int growSpeedCarrot = 100;
	@MLProp(info = "0:noWait  1:fast  100:default", min = 0, max = 100)
	public static int growSpeedPotato = 100;
	
	@MLProp(info = "0:noWait  7:default", min = 0, max = 7)
	public static int growSpeedSapling = 7;
	@MLProp(info = "0:noWait  25:default", min = 0, max = 25)
	public static int MushroomSpeed = 25;
	@MLProp(info = "area in mushroomLimit  5:default", min = 1, max = 81)
	public static int MushroomLimit = 5;
	@MLProp(info = "mushroom search area  4:default", min = 0, max = 4)
	public static byte MushroomArea = 4;
	@MLProp(info = "0:noWait  10:default", min = 0, max = 10)
	public static int growSpeedNetherWart = 10;
	@MLProp(info = "0:noWait  5:default", min = 0, max = 5)
	public static int growSpeedCocoa = 5;
	@MLProp(info = "0:noWait  4:default  -1:noGrow", min = -1, max = 64)
	public static int growSpeedVine = 4;

	public void load()
	{
		if(SugarcaneSpeed != 15 || SugarcaneLimit != 3 || SugarcaneUsefulBonemeal || SugarcaneGrowWater)
		{
			Block.blocksList[83] = null;
	    	Block reed = (new foBlockReed(83)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("reeds");
			if(SugarcaneUsefulBonemeal) MinecraftForge.EVENT_BUS.register((foBlockReed)reed);
		}
		
		if(CactusSpeed != 15 || CactusLimit != 3 || CactusUsefulBonemeal)
		{
			Block.blocksList[81] = null;
	    	Block cactus = (new foBlockCactus(81)).setHardness(0.4F).setStepSound(Block.soundClothFootstep).setUnlocalizedName("cactus");
			if(CactusUsefulBonemeal) MinecraftForge.EVENT_BUS.register((foBlockCactus)cactus);
		}
		
		if(growSpeedPunpkin != 100)
		{
			Block.blocksList[104] = null;
			Block pumpkinStem = (new foBlockStem(104, Block.pumpkin)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pumpkinStem");
		}
		
		if(growSpeedWaterMelon != 100)
		{
			Block.blocksList[105] = null;
			Block melonStem = (new foBlockStem(105, Block.melon)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pumpkinStem");
		}
		
		if(growSpeedCrops != 100)
		{
			Block.blocksList[59] = null;
			Block crops = (new foBlockCrops(59)).setUnlocalizedName("crops");
		}
		
		if(growSpeedCarrot != 100)
		{
			Block.blocksList[141] = null;
			Block carrot = (new foBlockCarrot(141)).setUnlocalizedName("carrots");
		}
		
		if(growSpeedPotato != 100)
		{
			Block.blocksList[142] = null;
			Block potato = (new foBlockPotato(142)).setUnlocalizedName("potatoes");
		}
		
		if(growSpeedSapling != 7)
		{
			Block.blocksList[6] = null;
			Block sapling = (new foBlockSapling(6)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("sapling");
		}
		
		if(MushroomSpeed != 25 || MushroomLimit != 5 || MushroomArea != 4)
		{
			Block.blocksList[39] = null;
			Block.blocksList[40] = null;
			Block mushroomBrown = (new foBlockMushroom(39, "mushroom_brown")).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setLightValue(0.125F).setUnlocalizedName("mushroom");
			Block mushroomRed = (new foBlockMushroom(40, "mushroom_red")).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("mushroom");
		}
		
		if(growSpeedNetherWart != 10)
		{
			Block.blocksList[115] = null;
			Block netherStalk = (new foBlockNetherStalk(115)).setUnlocalizedName("netherStalk");
		}
		
		if(growSpeedCocoa != 5)
		{
			Block.blocksList[127] = null;
			Block cocoaPlant = (new foBlockCocoa(127)).setHardness(0.2F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("cocoa");
		}
		
		if(growSpeedVine != 4)
		{
			Block.blocksList[106] = null;
			Block vine = (new foBlockVine(106)).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("vine");
		}
		
	}

	public static void addChat(String str)
	{
		ModLoader.getMinecraftInstance().ingameGUI.getChatGUI().printChatMessage(str);

	}
}

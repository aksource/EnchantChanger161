package net.minecraft.src;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.src.SpawnChange.scBlockObsidian;
import net.minecraft.src.SpawnChange.scEntityGhast;
import net.minecraft.src.SpawnChange.scEntityMagmaCube;
import net.minecraft.src.SpawnChange.scEntityPigZombie;
import net.minecraft.src.SpawnChange.scEntitySlime;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;

public class mod_SpawnChange extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-1";
	}

//	@MLProp(info="", min = 1, max = 64)
//	public static int  = ;

	@MLProp(info="ネザーゲートに敵スポーンを許可する。バニラは true")
	public static boolean portalSpawn = false;
	@MLProp(info="ネザーの敵がスポーンする明るさ。バニラは 15", min=0, max=15)
	public static int netherSpawnLightValue = 7;
	@MLProp(info="スライムがスポーンする高さ。バニラは 40", min = 0, max = 255)
	public static double SlimeSpawnHeight = 16;

	public void load()
	{
		if(!portalSpawn)
		{
			Block.blocksList[49] = null;
			Block obsidian = (new scBlockObsidian(49)).setHardness(50.0F).setResistance(2000.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("obsidian");
		}
		
		if(SlimeSpawnHeight != 40)
		{
			ModLoader.removeSpawn(EntitySlime.class, EnumCreatureType.monster);
			ModLoader.registerEntityID(scEntitySlime.class, "Slime", 55);
			ModLoader.addSpawn(scEntitySlime.class, 10, 4, 4,EnumCreatureType.monster);
		}
		
		if(netherSpawnLightValue != 15)
		{
			ModLoader.registerEntityID(scEntityGhast.class, "Ghast", 56);
			ModLoader.registerEntityID(scEntityPigZombie.class, "PigZombie", 57);
			ModLoader.registerEntityID(scEntityMagmaCube.class, "LavaSlime", 62);
			List monsterList = BiomeGenBase.hell.getSpawnableList(EnumCreatureType.monster);
			monsterList.clear();
			monsterList.add(new SpawnListEntry(scEntityGhast.class, 50, 4, 4));
			monsterList.add(new SpawnListEntry(scEntityPigZombie.class, 100, 4, 4));
			monsterList.add(new SpawnListEntry(scEntityMagmaCube.class, 1, 4, 4));
		}
		
	}

}

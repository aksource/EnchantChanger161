package SpawnChange;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid="SpawnChange", name="SpawnChange", version="1.6srg-1",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class SpawnChange
{
	@Mod.Instance("SpawnChange")
	public static SpawnChange instance;
	
	public static boolean portalSpawn;
	public static int netherSpawnLightValue;
	public static int SlimeSpawnHeight;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		portalSpawn = config.get(Configuration.CATEGORY_GENERAL, "portalSpawn", false, "ネザーゲートに敵スポーンを許可する。バニラは true").getBoolean(false);
		netherSpawnLightValue = config.get(Configuration.CATEGORY_GENERAL, "netherSpawnLightValue", 7, "ネザーの敵がスポーンする明るさ。バニラは 15, min=0, max=15").getInt();
		netherSpawnLightValue = (netherSpawnLightValue < 0)?0:(netherSpawnLightValue > 15)?15:netherSpawnLightValue;
		SlimeSpawnHeight = config.get(Configuration.CATEGORY_GENERAL, "SlimeSpawnHeight", 16, "スライムがスポーンする高さ。バニラは 40, min = 0, max = 255").getInt();
		SlimeSpawnHeight = (SlimeSpawnHeight <0)?0:(SlimeSpawnHeight>255)?255:SlimeSpawnHeight;
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		if(!portalSpawn)
		{
			Block.blocksList[49] = null;
			Block obsidian = (new scBlockObsidian(49)).setHardness(50.0F).setResistance(2000.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("obsidian");
		}
		
		if(SlimeSpawnHeight != 40)
		{
			EntityRegistry.removeSpawn(EntitySlime.class, EnumCreatureType.monster);
			EntityRegistry.registerGlobalEntityID(scEntitySlime.class, "Slime", 55);
			EntityRegistry.addSpawn(scEntitySlime.class, 10, 4, 4,EnumCreatureType.monster);
		}
		
		if(netherSpawnLightValue != 15)
		{
			EntityRegistry.registerGlobalEntityID(scEntityGhast.class, "Ghast", 56);
			EntityRegistry.registerGlobalEntityID(scEntityPigZombie.class, "PigZombie", 57);
			EntityRegistry.registerGlobalEntityID(scEntityMagmaCube.class, "LavaSlime", 62);
			List monsterList = BiomeGenBase.hell.getSpawnableList(EnumCreatureType.monster);
			monsterList.clear();
			monsterList.add(new SpawnListEntry(scEntityGhast.class, 50, 4, 4));
			monsterList.add(new SpawnListEntry(scEntityPigZombie.class, 100, 4, 4));
			monsterList.add(new SpawnListEntry(scEntityMagmaCube.class, 1, 4, 4));
		}
	}
}
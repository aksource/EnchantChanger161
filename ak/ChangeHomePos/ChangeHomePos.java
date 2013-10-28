package ak.ChangeHomePos;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.Village;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="ChangeHomePos", name="ChangeHomePos", version="1.0",dependencies="required-after:FML")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class ChangeHomePos
{
	@Mod.Instance("ChangeHomePos")
	public static ChangeHomePos instance;
	public static int posChangeItem;
	public static ChunkPosition pos = new ChunkPosition(0, -1, 0);
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		posChangeItem = config.get(Configuration.CATEGORY_GENERAL, "HomePosChangeItemIDs", Item.arrow.itemID).getInt();
		config.save();
	}
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new interactVillegerEventHook());
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
	
	public class interactVillegerEventHook
	{
		@ForgeSubscribe
		public void interactVilleger(EntityInteractEvent event)
		{
			ItemStack heldItem = event.entityPlayer.getCurrentEquippedItem();
			if(event.target instanceof EntityVillager && heldItem != null && heldItem.itemID == posChangeItem && pos.y != -1)
			{
				Village vil = ObfuscationReflectionHelper.getPrivateValue(EntityVillager.class, (EntityVillager)event.target, 3);
				if(vil == null)
					return;
				((EntityVillager)event.target).setHomeArea(pos.x, pos.y, pos.z, (int)((float)(vil.getVillageRadius() * 0.6F)));
				event.entityPlayer.addChatMessage(String.format("Set Home Pos x: %d y: %d z: %d", pos.x,pos.y,pos.z));
				event.setCanceled(true);
			}
		}
		@ForgeSubscribe
		public void interactBlock(PlayerInteractEvent event)
		{
			if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK 
					&& event.entityPlayer.getCurrentEquippedItem() != null 
					&& event.entityPlayer.getCurrentEquippedItem().itemID == posChangeItem)
			{
				pos = new ChunkPosition(event.x, event.y, event.z);
				event.entityPlayer.addChatMessage(String.format("Regist Home Pos x: %d y: %d z: %d", event.x,event.y,event.z));
			}
		}
	}
}
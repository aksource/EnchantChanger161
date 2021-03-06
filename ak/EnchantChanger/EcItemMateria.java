package ak.EnchantChanger;

import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EcItemMateria extends Item
{
	public static final String[] MateriaMagicNames   = new String[]{"Black","White","Teleport","Floating","Thunder","Despell","Haste","Absorption"};
//	public static final String[] MateriaMagicJPNames = new String[]{"黒"   ,"白"   ,"瞬間移動","浮遊"    ,"雷"     ,"解呪"   ,"加速" ,"吸収"};
	public static int MagicMateriaNum = MateriaMagicNames.length;
	public static int[] magicEnch = new int[]{EnchantChanger.EnchantmentMeteoId, EnchantChanger.EndhantmentHolyId, EnchantChanger.EnchantmentTelepoId, EnchantChanger.EnchantmentFloatId, EnchantChanger.EnchantmentThunderId};
	public static boolean GGEnable = false;
	private boolean LvCap = EnchantChanger.LevelCap;
	private boolean Debug = EnchantChanger.Debug;
	private boolean tera = EnchantChanger.YouAreTera;
	private double BoxSize = 5D;

	public EcItemMateria(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		maxStackSize = 64;
		setMaxDamage(0);
        this.setTextureName(EnchantChanger.EcTextureDomain + "Materia");
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityLiving){
			EntityLiving entityliving = (EntityLiving)entity;
			this.MateriaPotionEffect(itemstack, entityliving, player);
			return false;
		}
		return false;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize > 1){
			return itemstack;
		}
		if(itemstack.getItemDamage() == 0 && itemstack.isItemEnchanted()){
			int EnchantmentKind = EnchantChanger.getMateriaEnchKind(itemstack);
			int Lv = EnchantChanger.getMateriaEnchLv(itemstack);
			if(entityplayer.isSneaking() && Lv > 1){
//				entityplayer.addExperienceLevel(LevelUPEXP(itemstack, false));
				ItemStack expBottle;
				if(Lv > 5)
					expBottle = new ItemStack(EnchantChanger.ItemExExpBottle);
				else
					expBottle = new ItemStack(Item.expBottle);
				if(!world.isRemote)
					entityplayer.dropPlayerItem(expBottle);
				this.addMateriaLv(itemstack, -1);
			}else if ((entityplayer.experienceLevel >= LevelUPEXP(itemstack,true) || entityplayer.capabilities.isCreativeMode) && Lv != 0){
				entityplayer.addExperienceLevel(-LevelUPEXP(itemstack,true));
				this.addMateriaLv(itemstack, 1);
			}
		}else{
			switch(itemstack.getItemDamage()){
			case 1:Meteo(world,entityplayer);break;
			case 2:
				if(entityplayer.isSneaking()){
					GGEnable=!GGEnable;
					entityplayer.addChatMessage("Great Gospel Mode " + GGEnable);
				}else{
					Holy(world,entityplayer);
				}break;
			case 3:Teleport(itemstack,world,entityplayer);break;
			case 5:Thunder(world,entityplayer);break;
			case 6:Despell(entityplayer, entityplayer);break;
			case 7:Haste(entityplayer,entityplayer);break;
			default:;
			}
		}
		return itemstack;
	}
	public void addMateriaLv(ItemStack item, int addLv)
	{
		int EnchantmentKind = EnchantChanger.getMateriaEnchKind(item);
		int Lv = EnchantChanger.getMateriaEnchLv(item);
		NBTTagCompound nbt = item.getTagCompound();
		nbt.removeTag("ench");
		EnchantChanger.addEnchantmentToItem(item, Enchantment.enchantmentsList[EnchantmentKind], Lv + addLv);
	}
	public void MateriaPotionEffect(ItemStack item, EntityLiving entity, EntityPlayer player)
	{
		if(item.getItemDamage() > 0){
			switch(item.getItemDamage()){
			case 6:Despell(player,entity);return;
			case 7:Haste(player,entity);player.addChatMessage("Haste!");return;
			default:return;
			}
		}else{
			int EnchantmentKind = EnchantChanger.getMateriaEnchKind(item);
			int Lv = EnchantChanger.getMateriaEnchLv(item);
			if(EnchantmentKind != 256){
				int potionNum;
				String Message;
				String EntityName = entity.getEntityData().getName();
				switch (EnchantmentKind){
				case 1:potionNum = 12;Message = "fire resistance";break;
				case 5:potionNum = 13;Message = "more Oxygen";break;
				case 2:potionNum = 8 ;Message = "high jump";break;
				case 0:potionNum = 11;Message = "defence power";break;
				default:return;
				}
				if(player.experienceLevel > LevelUPEXP(item, false) || player.capabilities.isCreativeMode){
					entity.addPotionEffect(new PotionEffect(potionNum,20*60*EnchantChanger.MateriaPotionMinutes,Lv));
					player.addExperienceLevel(-LevelUPEXP(item, false));
					player.addChatMessage(EntityName + " gets "+ Message);
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-6);
					return;
				}
			}
		}
	}
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		if(par1ItemStack.getItemDamage() ==0){
			return par1ItemStack.isItemEnchanted() ? "ItemMateria":"ItemMateria.Base";
		}else{
			int var3 = par1ItemStack.getItemDamage() - 1;
			return "ItemMateria."+MateriaMagicNames[var3];
		}
	}
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List itemList)
	{
		itemList.add(new ItemStack(this, 1, 0));
		for(int i = 0; i < Enchantment.enchantmentsList.length;i++){
			if(Enchantment.enchantmentsList[i] != null && !this.isMagicEnch(i)){
				ItemStack stack1 = new ItemStack(this, 1, 0);
				stack1.addEnchantment(Enchantment.enchantmentsList[i], 1);
				itemList.add(stack1);
				ItemStack stack2 = new ItemStack(this, 1, 0);
				stack2.addEnchantment(Enchantment.enchantmentsList[i], 10);
				itemList.add(stack2);
				if(EnchantChanger.Debug){
					ItemStack stack3 = new ItemStack(this, 1, 0);
					stack3.addEnchantment(Enchantment.enchantmentsList[i], 127);
					itemList.add(stack3);
				}
			}
		}
		for(int i = 0;i < MagicMateriaNum; i++){
			ItemStack magic = new ItemStack(this, 1,1 + i);
			if(i < this.magicEnch.length)
				magic.addEnchantment(Enchantment.enchantmentsList[this.magicEnch[i]], 1);
			itemList.add(magic);
		}
	}
	public boolean isMagicEnch(int enchID)
	{
		for(int i=0;i < this.magicEnch.length;i++)
			if(enchID == this.magicEnch[i])
				return true;
		return false;
	}
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
	{
		return par1ItemStack.getItemDamage() > 0;
	}
	@Override
	public EnumRarity getRarity(ItemStack item)
	{
		if(item.getItemDamage() > 0)
			return EnumRarity.rare;
		else{
			if(EnchantChanger.getMateriaEnchKind(item) == 256 || EnchantChanger.getMateriaEnchLv(item) < 6)
				return EnumRarity.common;
			else if(EnchantChanger.getMateriaEnchLv(item) < 11)
				return EnumRarity.uncommon;
			else
				return EnumRarity.rare;
		}
	}
	public int LevelUPEXP(ItemStack item, boolean next)
	{
		int EnchantmentKind = EnchantChanger.getMateriaEnchKind(item);
		int Lv = EnchantChanger.getMateriaEnchLv(item);
		int nextLv = next ?1:0;
		if(EnchantmentKind == 256)
			return 0;
		if(Lv < 5 || EnchantChanger.Difficulty == 0){
			return Enchantment.enchantmentsList[EnchantmentKind].getMinEnchantability(Lv + nextLv);
		}else{
			return Enchantment.enchantmentsList[EnchantmentKind].getMaxEnchantability(Lv + nextLv);
		}
	}
	public WeightedRandomChestContent addMateriaInChest(int kind, int par2, int par3, int par4)
	{
		ItemStack var6 = new ItemStack(this, 1,kind + 1);
		if(kind < 5)
			var6.addEnchantment(Enchantment.enchantmentsList[EnchantChanger.EnchantmentMeteoId + kind], 1);
		return new WeightedRandomChestContent(var6, par2, par3, par4);
	}
	public static void Teleport(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(!canMagic(entityplayer) || world.isRemote){return;}
		if(entityplayer.isSneaking()){
			ChunkCoordinates Spawn;		
			int dimID = world.provider.dimensionId;
			if(entityplayer.getBedLocation(dimID) != null){
				Spawn = entityplayer.getBedLocation(dimID);
			}else{
				Spawn = world.getSpawnPoint();
			}
			teleportToChunkCoord(world, entityplayer, Spawn, entityplayer.isSneaking(), true, dimID);
		}else{
			if(setTeleportPoint(world,entityplayer) != null){
				int chunkX = MathHelper.floor_double(setTeleportPoint(world,entityplayer).xCoord);
				int chunkY = MathHelper.floor_double(setTeleportPoint(world,entityplayer).yCoord);
				int chunkZ = MathHelper.floor_double(setTeleportPoint(world,entityplayer).zCoord);
				ChunkCoordinates chunk = new ChunkCoordinates(chunkX, chunkY, chunkZ);
				teleportToChunkCoord(world, entityplayer, chunk, entityplayer.isSneaking(), false, world.provider.dimensionId);
			}
		}
	}
	private static void teleportToChunkCoord(World world, EntityPlayer entityplayer, ChunkCoordinates chunk, boolean isSneaking, boolean telepoDim, int dimID)
	{
		entityplayer.setPositionAndUpdate(chunk.posX, chunk.posY, chunk.posZ);
		entityplayer.fallDistance = 0.0F;
		if(telepoDim)
			travelDimension(entityplayer, dimID);
		decreasePlayerFood(entityplayer, isSneaking?20:2);
		for (int var2 = 0; var2 < 32; ++var2){
			world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + world.rand.nextDouble() * 2.0D, entityplayer.posZ, world.rand.nextGaussian(), 0.0D, world.rand.nextGaussian());
		}
	}
	private static void travelDimension(EntityPlayer player, int nowDim)
	{
		if(nowDim == 0)return;
		else if(player instanceof EntityPlayerMP){
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			transferPlayerToDimension(playerMP.mcServer.getConfigurationManager(), playerMP, 0, new MateriaTeleporter(playerMP.mcServer.worldServerForDimension(0)));
		}
	}
	public static void transferPlayerToDimension(ServerConfigurationManager serverConf, EntityPlayerMP par1EntityPlayerMP, int par2, Teleporter teleporter)
	{
		int j = par1EntityPlayerMP.dimension;
		WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(par1EntityPlayerMP.dimension);
		par1EntityPlayerMP.dimension = par2;
		WorldServer worldserver1 = MinecraftServer.getServer().worldServerForDimension(par1EntityPlayerMP.dimension);
		par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte)par1EntityPlayerMP.worldObj.difficultySetting, worldserver1.getWorldInfo().getTerrainType(), worldserver1.getHeight(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
		worldserver.removePlayerEntityDangerously(par1EntityPlayerMP);
		par1EntityPlayerMP.isDead = false;
		serverConf.transferEntityToWorld(par1EntityPlayerMP, par2, worldserver, worldserver1, teleporter);
		serverConf.func_72375_a(par1EntityPlayerMP, worldserver);
		par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
		par1EntityPlayerMP.theItemInWorldManager.setWorld(worldserver1);
		serverConf.updateTimeAndWeatherForPlayer(par1EntityPlayerMP, worldserver1);
		serverConf.syncPlayerInventory(par1EntityPlayerMP);
		Iterator iterator = par1EntityPlayerMP.getActivePotionEffects().iterator();

		while (iterator.hasNext())
		{
			PotionEffect potioneffect = (PotionEffect)iterator.next();
			par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(par1EntityPlayerMP.entityId, potioneffect));
		}

		GameRegistry.onPlayerChangedDimension(par1EntityPlayerMP);
	}
	public static Vec3 setTeleportPoint(World world, EntityPlayer entityplayer)
	{
		float var1=1F;
		double distLimit = 150.0D;
		double viewX = entityplayer.getLookVec().xCoord;
		double viewY = entityplayer.getLookVec().yCoord;
		double viewZ = entityplayer.getLookVec().zCoord;
//		double PlayerposX = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)var1;
//		double PlayerposY = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)var1 + 1.62D - (double)entityplayer.yOffset;
//		double PlayerposZ = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)var1;
		Vec3 PlayerPosition = world.getWorldVec3Pool().getVecFromPool(entityplayer.posX, entityplayer.posY + 1.62D - (double)entityplayer.yOffset, entityplayer.posZ);
		Vec3 PlayerLookVec = PlayerPosition.addVector(viewX * distLimit, viewY * distLimit, viewZ * distLimit);
		MovingObjectPosition MOP = world.clip(PlayerPosition, PlayerLookVec, true);
		if(MOP != null && MOP.typeOfHit == EnumMovingObjectType.TILE){
			int BlockposX = MOP.blockX;
			int BlockposY = MOP.blockY;
			int BlockposZ = MOP.blockZ;
			int Blockside = MOP.sideHit;
			switch(Blockside){
			case 0:BlockposY -=2;break;
			case 1:BlockposY++;break;
			case 2:BlockposZ--;break;
			case 3:BlockposZ++;break;
			case 4:BlockposX--;break;
			case 5:BlockposX++;break;
			}
			return world.getWorldVec3Pool().getVecFromPool(BlockposX, BlockposY, BlockposZ);
		}else{
			return null;
		}
	}
	public static void Holy(World world, EntityPlayer entityplayer)
	{
		if(!canMagic(entityplayer)){return;}
		decreasePlayerFood(entityplayer, 6);
		List EntityList = world.getEntitiesWithinAABB(EntityLivingBase.class, entityplayer.boundingBox.expand(5D, 5D, 5D));
		for (int i=0; i < EntityList.size();i++){
			Entity entity=(Entity) EntityList.get(i);
			if(((EntityLivingBase)entity).isEntityUndead()){
				int var1 = MathHelper.floor_float(((EntityLivingBase) entity).getMaxHealth()/2);
				entity.attackEntityFrom(DamageSource.magic, var1);
			}
		}
	}
	public static void Meteo(World world, EntityPlayer entityplayer)
	{
		if(!canMagic(entityplayer)){return;}
		decreasePlayerFood(entityplayer, 6);
		Vec3 EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null && !world.isRemote)
			world.spawnEntityInWorld( new EcEntityMeteo(world, EndPoint.xCoord,(double)200,EndPoint.zCoord,0.0D,-1D,0D,0.0F,0.0F));
	}
	public static void Thunder(World world, EntityPlayer entityplayer)
	{
		if(!canMagic(entityplayer)){return;}
		decreasePlayerFood(entityplayer, 6);
		Vec3 EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null)
			world.spawnEntityInWorld( new EntityLightningBolt(world, EndPoint.xCoord, EndPoint.yCoord, EndPoint.zCoord));
	}
	public void Despell(EntityPlayer player,Entity entity)
	{
		if(entity instanceof EntityLiving){
			((EntityLiving) entity).clearActivePotions();
			for (int i=0;i<33;i++){
				((EntityLiving) entity).removePotionEffect(i);
			}
			decreasePlayerFood(player, 2);
		}
	}
	public void Haste(EntityPlayer player,EntityLivingBase entityliving)
	{
		entityliving.addPotionEffect(new PotionEffect(1,20*60*5,1));
		decreasePlayerFood(player, 2);
	}
	private static void decreasePlayerFood(EntityPlayer player, int dec)
	{
		if(!player.capabilities.isCreativeMode){
			player.getFoodStats().addStats(-dec, 1.0F);
		}
	}
	private static boolean canMagic(EntityPlayer player)
	{
		return player.getFoodStats().getFoodLevel() > 0 || EnchantChanger.YouAreTera || player.capabilities.isCreativeMode;
	}
}

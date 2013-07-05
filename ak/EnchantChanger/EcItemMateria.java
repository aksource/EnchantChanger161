package ak.EnchantChanger;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EcItemMateria extends Item implements IItemRenderer
{
	public static final String[] MateriaMagicNames = new String[]{"Black","White","Teleport","Floating","Thunder","Despell","Haste","Absorption"};
	public static final String[] MateriaMagicJPNames = new String[]{"黒","白","瞬間移動","浮遊","雷","解呪","加速","吸収"};
	public static int MagicMateriaNum = MateriaMagicNames.length;
	public static int[] magicEnch = new int[]{EnchantChanger.EnchantmentMeteoId, EnchantChanger.EndhantmentHolyId, EnchantChanger.EnchantmentTelepoId, EnchantChanger.EnchantmentFloatId, EnchantChanger.EnchantmentThunderId};
	public static boolean GGEnable = false;
	private boolean LvCap = EnchantChanger.LevelCap;
	private boolean Debug = EnchantChanger.Debug;
	private boolean tera = EnchantChanger.YouAreTera;
	protected static Random rand;
	private double BoxSize = 5D;
	private static double vert[];
	private static int  face[];
	private ResourceLocation materia0 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia0.png");
	private ResourceLocation materia1 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia1.png");
	private ResourceLocation materia3 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia3.png");
	private ResourceLocation materia4 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia4.png");
	private ResourceLocation materia5 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia5.png");
	private ResourceLocation materia6 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia6.png");
	private ResourceLocation materia7 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia7.png");
	private ResourceLocation materia8 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia8.png");
	private ResourceLocation materia9 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia9.png");
	private ResourceLocation materia10 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia10.png");
	private ResourceLocation materia11 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia11.png");
	private ResourceLocation materia12 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia12.png");
	private ResourceLocation materia13 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia13.png");
	private ResourceLocation materia14 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia14.png");
	private ResourceLocation materia15 = new ResourceLocation(EnchantChanger.EcAssetsDomain, "textures/gui/materia15.png");

	public EcItemMateria(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		maxStackSize = 64;
		setMaxDamage(0);
		this.rand = new Random();
        this.func_111206_d(EnchantChanger.EcTextureDomain + "Materia");
	}
	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving)entity;
			this.MateriaPotionEffect(itemstack, entityliving, player);
			return false;
		}
		return false;
	}
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize > 1)
		{
			return itemstack;
		}
		if(itemstack.getItemDamage() == 0 && itemstack.isItemEnchanted())
		{
			int EnchantmentKind = EnchantChanger.getMateriaEnchKind(itemstack);
			int Lv = EnchantChanger.getMateriaEnchLv(itemstack);
			if(entityplayer.isSneaking() && Lv > 1)
			{
				entityplayer.addExperienceLevel(LevelUPEXP(itemstack, false));
				this.addMateriaLv(itemstack, -1);
			}
			else if ((entityplayer.experienceLevel >= LevelUPEXP(itemstack,true) || entityplayer.capabilities.isCreativeMode) && Lv != 0)
			{
				entityplayer.addExperienceLevel(-LevelUPEXP(itemstack,true));
				this.addMateriaLv(itemstack, 1);
			}
		}
		else
		{
			switch(itemstack.getItemDamage())
			{
			case 1:Meteo(world,entityplayer);break;
			case 2:
				if(entityplayer.isSneaking())
				{
					GGEnable=!GGEnable;
					entityplayer.addChatMessage("Great Gospel Mode " + GGEnable);
				}
				else
				{
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
		if(item.getItemDamage() > 0)
		{
			switch(item.getItemDamage())
			{
			case 6:Despell(player,entity);return;
			case 7:Haste(player,entity);player.addChatMessage("Haste!");return;
			default:return;
			}
		}
		else
		{
			int EnchantmentKind = EnchantChanger.getMateriaEnchKind(item);
			int Lv = EnchantChanger.getMateriaEnchLv(item);
			if(EnchantmentKind != 256)
			{
				int potionNum;
				String Message;
				String EntityName = entity.getEntityData().getName();
				switch (EnchantmentKind)
				{
				case 1:potionNum = 12;Message="fire resistance";break;
				case 5:potionNum = 13;Message="more Oxygen";break;
				case 2:potionNum = 8;Message="high jump";break;
				case 0:potionNum = 11;Message="defence power";break;
				default:return;
				}
				if(player.experienceLevel > LevelUPEXP(item, false) || player.capabilities.isCreativeMode)
				{
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
		if(par1ItemStack.getItemDamage() ==0)
		{
			return par1ItemStack.isItemEnchanted() ? "ItemMateria":"ItemMateria.Base";
		}
		else
		{
			int var3 = par1ItemStack.getItemDamage() - 1;
			return "ItemMateria."+MateriaMagicNames[var3];
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List itemList)
	{
		itemList.add(new ItemStack(this, 1, 0));
		for(int i = 0; i < Enchantment.enchantmentsList.length;i++)
		{
			if(Enchantment.enchantmentsList[i] != null && !this.isMagicEnch(i))
			{
				ItemStack stack1 = new ItemStack(this, 1, 0);
				stack1.addEnchantment(Enchantment.enchantmentsList[i], 1);
				itemList.add(stack1);
				ItemStack stack2 = new ItemStack(this, 1, 0);
				stack2.addEnchantment(Enchantment.enchantmentsList[i], 10);
				itemList.add(stack2);
				if(EnchantChanger.Debug)
				{
					ItemStack stack3 = new ItemStack(this, 1, 0);
					stack3.addEnchantment(Enchantment.enchantmentsList[i], 127);
					itemList.add(stack3);
				}
			}
		}
		for(int i=0;i < MagicMateriaNum; i++)
		{
			ItemStack magic = new ItemStack(this, 1,1 + i);
			if(i<this.magicEnch.length)
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
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
	{
		return par1ItemStack.getItemDamage() > 0;
	}
	public EnumRarity getRarity(ItemStack item)
	{
		if(item.getItemDamage() > 0)
			return EnumRarity.rare;
		else
		{
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
		if(Lv < 5 || EnchantChanger.Difficulty == 0)
		{
			return Enchantment.enchantmentsList[EnchantmentKind].getMinEnchantability(Lv + nextLv);
		}
		else
		{
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
		ChunkCoordinates Spawn;
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(entityplayer.getBedLocation() !=null)
		{
			Spawn = entityplayer.getBedLocation();
		}
		else
		{
			Spawn = world.getSpawnPoint();
		}
		double SpawnX = Spawn.posX;
		double SpawnY = Spawn.posY;
		double SpawnZ = Spawn.posZ;
		if(entityplayer.isSneaking() && entityplayer.dimension == 0)
		{
			entityplayer.setPositionAndUpdate(SpawnX, SpawnY, SpawnZ);
			if(!entityplayer.capabilities.isCreativeMode)
			{
				entityplayer.getFoodStats().addStats(-20, 1.0F);
				entityplayer.fallDistance = 0.0F;
			}
			for (int var2 = 0; var2 < 32; ++var2)
			{
				world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + EcItemMateria.rand.nextDouble() * 2.0D, entityplayer.posZ, EcItemMateria.rand.nextGaussian(), 0.0D, EcItemMateria.rand.nextGaussian());
			}
		}
		else
		{
			if(setTeleportPoint(world,entityplayer) !=null)
			{
				entityplayer.setPositionAndUpdate(setTeleportPoint(world,entityplayer).xCoord, setTeleportPoint(world,entityplayer).yCoord, setTeleportPoint(world,entityplayer).zCoord);
				if(!entityplayer.capabilities.isCreativeMode)
				{
					entityplayer.getFoodStats().addStats(-2, 1.0F);
					entityplayer.fallDistance = 0.0F;
				}
				for (int var2 = 0; var2 < 32; ++var2)
				{
					world.spawnParticle("portal", entityplayer.posX, entityplayer.posY + EcItemMateria.rand.nextDouble() * 2.0D, entityplayer.posZ, EcItemMateria.rand.nextGaussian(), 0.0D, EcItemMateria.rand.nextGaussian());
				}
			}
		}
	}
	public static Vec3 setTeleportPoint(World world, EntityPlayer entityplayer)
	{
		float var1=1F;
		double Dislimit = 150.0D;
		double viewX = entityplayer.getLookVec().xCoord;
		double viewY = entityplayer.getLookVec().yCoord;
		double viewZ = entityplayer.getLookVec().zCoord;
		double PlayerposX = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)var1;
		double PlayerposY = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)var1 + 1.62D - (double)entityplayer.yOffset;
		double PlayerposZ = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)var1;
		Vec3 PlayerPosition = Vec3.createVectorHelper(PlayerposX, PlayerposY, PlayerposZ);
		Vec3 PlayerLookVec = PlayerPosition.addVector(viewX*Dislimit, viewY*Dislimit, viewZ*Dislimit);
		MovingObjectPosition MOP = world.rayTraceBlocks_do(PlayerPosition, PlayerLookVec, true);
		if(MOP !=null)
		{
			if (MOP.typeOfHit == EnumMovingObjectType.TILE)
			{
				int BlockposX = MOP.blockX;
				int BlockposY = MOP.blockY;
				int BlockposZ = MOP.blockZ;
				int Blockside = MOP.sideHit;
				switch(Blockside)
				{
				case 0:BlockposY -=2;break;
				case 1:BlockposY++;break;
				case 2:BlockposZ--;break;
				case 3:BlockposZ++;break;
				case 4:BlockposX--;break;
				case 5:BlockposX++;break;
				}
				Vec3 TelepoVec = Vec3.createVectorHelper(BlockposX, BlockposY, BlockposZ);
				return TelepoVec;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	public static void Holy(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
		{
			entityplayer.getFoodStats().addStats(-6, 1.0F);
		}
		List EntityList = world.getEntitiesWithinAABB(EntityLivingBase.class, entityplayer.boundingBox.expand(5D, 5D, 5D));
		for (int i=0; i < EntityList.size();i++)
		{
			Entity entity=(Entity) EntityList.get(i);
			if(((EntityLivingBase)entity).isEntityUndead())
			{
				int var1 = MathHelper.floor_float(((EntityLivingBase) entity).func_110138_aP()/2);
//				int var1 = 10;
				entity.attackEntityFrom(DamageSource.magic, var1);
			}
		}
	}
	public static void Meteo(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
		{
			entityplayer.getFoodStats().addStats(-6, 1.0F);
		}
		Vec3 EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null && !world.isRemote)
			world.spawnEntityInWorld( new EcEntityMeteo(world, EndPoint.xCoord,(double)200,EndPoint.zCoord,0.0D,-1D,0D,0.0F,0.0F));
	}
	public static void Thunder(World world, EntityPlayer entityplayer)
	{
		if(entityplayer.getFoodStats().getFoodLevel() < 0 && !EnchantChanger.YouAreTera && !entityplayer.capabilities.isCreativeMode)
		{
			return;
		}
		if(!entityplayer.capabilities.isCreativeMode)
		{
			entityplayer.getFoodStats().addStats(-6, 1.0F);
		}
		Vec3 EndPoint = setTeleportPoint(world,entityplayer);
		if(EndPoint != null)
			world.spawnEntityInWorld( new EntityLightningBolt(world, EndPoint.xCoord, EndPoint.yCoord, EndPoint.zCoord));
	}
	public void Despell(EntityPlayer player,Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			((EntityLiving) entity).clearActivePotions();
			for (int i=0;i<33;i++)
			{
				((EntityLiving) entity).removePotionEffect(i);
			}
			if(!player.capabilities.isCreativeMode)
			{
				player.getFoodStats().addStats(-2, 1.0F);
			}
		}
	}
	public void Haste(EntityPlayer player,EntityLivingBase entityliving)
	{
		entityliving.addPotionEffect(new PotionEffect(1,20*60*5,1));
		if(!player.capabilities.isCreativeMode)
		{
			player.getFoodStats().addStats(-2, 1.0F);
		}
	}
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY;
	}
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		float f0 = 0.2F;
		float f1 = 16F;
		if(type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			this.renderMateria(item, 0.2f,0,0,f0, type);
		else if(type == ItemRenderType.INVENTORY)
			this.renderMateria(item, 0f, 0f, 0f, f1, type);
		else if(type == ItemRenderType.ENTITY)
			this.renderMateria(item, 0,0,0,f0, type);
	}
	public void renderMateria(ItemStack item, float x, float y, float z, float size, ItemRenderType type)
	{
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glPushMatrix();
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(this.getTextuerfromEnch(item)));
		mc.func_110434_K().func_110577_a(this.getTextuerfromEnch(item));
		GL11.glTranslatef(x, y, z);
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			GL11.glTranslatef(0.3f, 0.2f, 0);
//		GL11.glEnable(GL_BLEND);
//		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(10);

		GL11.glScalef(size, size, size);
		tessellator.startDrawing(4);
		for (int v : face) {
			double d = Math.atan2(vert[v*3],vert[v*3+2])/Math.PI;
			if(d>0)
			{
				tessellator.addVertexWithUV(
						0 + (vert[v*3] + 1)/2,
						0 + (vert[v*3+1]+1)/2,
						0 + (vert[v*3+2]+1)/2,
						d/16D,
						(vert[v*3 +1]+1)/32D)	;
			}
			else
			{
				tessellator.addVertexWithUV(
						0 + (vert[v*3] + 1)/2,
						0 + (vert[v*3+1]+1)/2,
						0 + (vert[v*3+2]+1)/2,
						-d/16D,
						(vert[v*3 +1]+1)/32D)	;
			}

		}
		tessellator.draw();
		GL11.glTranslatef(-x, -y, -z);
//		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}
	public ResourceLocation getTextuerfromEnch(ItemStack item)
	{
		if(item.getItemDamage() > 0)
		{
			int num;
			switch(item.getItemDamage() - 1)
			{
			case 0 :return materia0;
			case 1 :return materia15;
			case 2 :return materia5;
			case 3 :return materia9;
			case 4 :return materia11;
			case 5 :return materia7;
			case 6 :return materia6;
			case 7 :return materia10;
			default :return materia0;
			}
		}
		else if(!item.isItemEnchanted())
			return materia0;
		else
		{
			int num;
			switch(EnchantChanger.getMateriaEnchKind(item))
			{
			case 0:return materia8;
			case 1:return materia8;
			case 2:return materia8;
			case 3:return materia8;
			case 4:return materia8;
			case 5:return materia4;
			case 6:return materia4;
			case 7:return materia8;
			case 16:return materia13;
			case 17:return materia13;
			case 18:return materia13;
			case 19:return materia14;
			case 20:return materia1;
			case 21:return materia9;
			case 32:return materia5;
			case 33:return materia7;
			case 34:return materia3;
			case 35:return materia9;
			case 48:return materia13;
			case 49:return materia14;
			case 50:return materia1;
			case 51:return materia12;
			default :return materia10;
			}
		}
	}
	static
	{
		vert = new double[]{
				0.000000,1.000000,0.000000,
				0.000000,-1.000000,0.000000,
				0.873422,0.486965,0.000000,
				-0.873422,-0.486965,0.000000,
				0.269902,0.486965,-0.830673,
				-0.269902,-0.486965,0.830673,
				-0.706613,0.486965,-0.513384,
				0.706613,-0.486965,0.513384,
				-0.706613,0.486965,0.513385,
				0.706613,-0.486965,-0.513385,
				0.269902,0.486965,0.830673,
				-0.269902,-0.486965,-0.830673,
				0.506476,0.862254,0.000000,
				0.666149,0.567453,-0.483986,
				0.156510,0.862254,-0.481687,
				-0.254446,0.567453,-0.783106,
				-0.409748,0.862254,-0.297699,
				-0.823406,0.567453,0.000000,
				-0.409748,0.862254,0.297699,
				-0.254446,0.567453,0.783106,
				0.156510,0.862254,0.481687,
				0.666149,0.567453,0.483986,
				-0.666149,-0.567453,0.483986,
				-0.506476,-0.862254,0.000000,
				-0.156510,-0.862254,0.481687,
				0.254446,-0.567453,0.783106,
				0.409748,-0.862254,0.297699,
				0.823406,-0.567453,-0.000000,
				0.409748,-0.862254,-0.297699,
				0.254446,-0.567453,-0.783106,
				-0.156510,-0.862254,-0.481687,
				-0.666149,-0.567453,-0.483986,
				0.951056,0.000000,-0.309017,
				0.587785,0.000000,-0.809017,
				-0.000000,0.000000,-1.000000,
				-0.587785,0.000000,-0.809017,
				-0.951056,0.000000,-0.309017,
				-0.951056,0.000000,0.309017,
				-0.587785,0.000000,0.809017,
				0.000000,0.000000,1.000000,
				0.587785,0.000000,0.809017,
				0.951056,0.000000,0.309017,
				0.220950,0.699114,-0.680015,
				0.430402,0.747999,-0.505224,
				0.485560,0.546961,-0.681957,
				0.262436,0.964949,0.000000,
				0.347236,0.903206,-0.252282,
				0.081097,0.964949,-0.249592,
				0.798626,0.546961,-0.251059,
				0.613498,0.747999,-0.253213,
				0.715010,0.699114,0.000000,
				-0.578456,0.699114,-0.420272,
				-0.347495,0.747999,-0.565459,
				-0.498533,0.546961,-0.672532,
				-0.132633,0.903206,-0.408201,
				-0.212316,0.964949,-0.154256,
				0.008017,0.546961,-0.837120,
				-0.051239,0.747999,-0.661719,
				-0.578456,0.699114,0.420273,
				-0.645166,0.747999,0.155751,
				-0.793671,0.546961,0.266309,
				-0.429208,0.903206,0.000000,
				-0.212316,0.964949,0.154256,
				-0.793671,0.546961,-0.266309,
				-0.645166,0.747999,-0.155751,
				0.220950,0.699114,0.680015,
				-0.051239,0.747999,0.661719,
				0.008017,0.546961,0.837120,
				-0.132632,0.903206,0.408201,
				0.081097,0.964949,0.249592,
				-0.498533,0.546961,0.672532,
				-0.347495,0.747999,0.565459,
				0.613498,0.747999,0.253213,
				0.798626,0.546961,0.251059,
				0.347237,0.903206,0.252282,
				0.485561,0.546961,0.681957,
				0.430402,0.747999,0.505224,
				-0.081097,-0.964949,0.249592,
				-0.347236,-0.903206,0.252282,
				-0.262436,-0.964949,0.000000,
				-0.485560,-0.546961,0.681957,
				-0.430402,-0.747999,0.505224,
				-0.220950,-0.699114,0.680015,
				-0.715010,-0.699114,0.000000,
				-0.613498,-0.747999,0.253213,
				-0.798626,-0.546961,0.251059,
				0.212316,-0.964949,0.154256,
				0.132633,-0.903206,0.408201,
				0.498533,-0.546961,0.672532,
				0.347495,-0.747999,0.565459,
				0.578456,-0.699114,0.420272,
				0.051239,-0.747999,0.661719,
				-0.008017,-0.546961,0.837120,
				0.212316,-0.964949,-0.154256,
				0.429208,-0.903206,-0.000000,
				0.793671,-0.546961,-0.266309,
				0.645166,-0.747999,-0.155751,
				0.578456,-0.699114,-0.420273,
				0.645166,-0.747999,0.155751,
				0.793671,-0.546961,0.266309,
				-0.081097,-0.964949,-0.249592,
				0.132632,-0.903206,-0.408201,
				-0.008017,-0.546961,-0.837120,
				0.051239,-0.747999,-0.661719,
				-0.220950,-0.699114,-0.680015,
				0.347495,-0.747999,-0.565459,
				0.498533,-0.546961,-0.672532,
				-0.347237,-0.903206,-0.252282,
				-0.798626,-0.546961,-0.251059,
				-0.613498,-0.747999,-0.253213,
				-0.430402,-0.747999,-0.505224,
				-0.485561,-0.546961,-0.681957,
				0.676468,-0.254494,-0.691103,
				0.809017,0.000000,-0.587785,
				0.866318,-0.254494,-0.429797,
				0.664005,0.300487,-0.684693,
				0.448238,0.254494,-0.856922,
				0.953495,0.254494,-0.161496,
				0.856371,0.300487,-0.419925,
				-0.448238,-0.254494,-0.856922,
				-0.309017,0.000000,-0.951056,
				-0.141054,-0.254494,-0.956732,
				-0.445993,0.300487,-0.843088,
				-0.676468,0.254494,-0.691103,
				0.141054,0.254494,-0.956732,
				-0.134739,0.300487,-0.944221,
				-0.953495,-0.254494,0.161496,
				-1.000000,0.000000,0.000000,
				-0.953495,-0.254494,-0.161496,
				-0.939644,0.300487,0.163636,
				-0.866318,0.254494,0.429797,
				-0.866318,0.254494,-0.429797,
				-0.939644,0.300487,-0.163636,
				-0.141054,-0.254494,0.956732,
				-0.309017,0.000000,0.951057,
				-0.448238,-0.254494,0.856922,
				-0.134739,0.300487,0.944221,
				0.141054,0.254494,0.956732,
				-0.676468,0.254494,0.691103,
				-0.445993,0.300487,0.843088,
				0.866318,-0.254494,0.429797,
				0.809017,0.000000,0.587785,
				0.676468,-0.254494,0.691103,
				0.856371,0.300487,0.419925,
				0.953495,0.254494,0.161496,
				0.448238,0.254494,0.856922,
				0.664005,0.300487,0.684693,
				-0.664005,-0.300487,0.684693,
				-0.809017,0.000000,0.587785,
				-0.856371,-0.300487,0.419925,
				0.445993,-0.300487,0.843088,
				0.309017,0.000000,0.951056,
				0.134739,-0.300487,0.944221,
				0.939644,-0.300487,-0.163636,
				1.000000,0.000000,-0.000000,
				0.939644,-0.300487,0.163636,
				0.134739,-0.300487,-0.944221,
				0.309017,0.000000,-0.951057,
				0.445993,-0.300487,-0.843088,
				-0.856371,-0.300487,-0.419925,
				-0.809017,0.000000,-0.587785,
				-0.664005,-0.300487,-0.684693
		};
		face = new int[]{
				13,44,43,
				4,42,44,
				14,43,42,
				42,43,44,
				14,47,46,
				0,45,47,
				12,46,45,
				45,46,47,
				12,50,49,
				2,48,50,
				13,49,48,
				48,49,50,
				14,46,43,
				12,49,46,
				13,43,49,
				49,43,46,
				15,53,52,
				6,51,53,
				16,52,51,
				51,52,53,
				16,55,54,
				0,47,55,
				14,54,47,
				47,54,55,
				14,42,57,
				4,56,42,
				15,57,56,
				56,57,42,
				16,54,52,
				14,57,54,
				15,52,57,
				57,52,54,
				17,60,59,
				8,58,60,
				18,59,58,
				58,59,60,
				18,62,61,
				0,55,62,
				16,61,55,
				55,61,62,
				16,51,64,
				6,63,51,
				17,64,63,
				63,64,51,
				18,61,59,
				16,64,61,
				17,59,64,
				64,59,61,
				19,67,66,
				10,65,67,
				20,66,65,
				65,66,67,
				20,69,68,
				0,62,69,
				18,68,62,
				62,68,69,
				18,58,71,
				8,70,58,
				19,71,70,
				70,71,58,
				20,68,66,
				18,71,68,
				19,66,71,
				71,66,68,
				21,73,72,
				2,50,73,
				12,72,50,
				50,72,73,
				12,45,74,
				0,69,45,
				20,74,69,
				69,74,45,
				20,65,76,
				10,75,65,
				21,76,75,
				75,76,65,
				12,74,72,
				20,76,74,
				21,72,76,
				76,72,74,
				23,79,78,
				1,77,79,
				24,78,77,
				77,78,79,
				24,82,81,
				5,80,82,
				22,81,80,
				80,81,82,
				22,85,84,
				3,83,85,
				23,84,83,
				83,84,85,
				24,81,78,
				22,84,81,
				23,78,84,
				84,78,81,
				24,77,87,
				1,86,77,
				26,87,86,
				86,87,77,
				26,90,89,
				7,88,90,
				25,89,88,
				88,89,90,
				25,92,91,
				5,82,92,
				24,91,82,
				82,91,92,
				26,89,87,
				25,91,89,
				24,87,91,
				91,87,89,
				26,86,94,
				1,93,86,
				28,94,93,
				93,94,86,
				28,97,96,
				9,95,97,
				27,96,95,
				95,96,97,
				27,99,98,
				7,90,99,
				26,98,90,
				90,98,99,
				28,96,94,
				27,98,96,
				26,94,98,
				98,94,96,
				28,93,101,
				1,100,93,
				30,101,100,
				100,101,93,
				30,104,103,
				11,102,104,
				29,103,102,
				102,103,104,
				29,106,105,
				9,97,106,
				28,105,97,
				97,105,106,
				30,103,101,
				29,105,103,
				28,101,105,
				105,101,103,
				30,100,107,
				1,79,100,
				23,107,79,
				79,107,100,
				23,83,109,
				3,108,83,
				31,109,108,
				108,109,83,
				31,111,110,
				11,104,111,
				30,110,104,
				104,110,111,
				23,109,107,
				31,110,109,
				30,107,110,
				110,107,109,
				32,114,113,
				9,112,114,
				33,113,112,
				112,113,114,
				33,116,115,
				4,44,116,
				13,115,44,
				44,115,116,
				13,48,118,
				2,117,48,
				32,118,117,
				117,118,48,
				33,115,113,
				13,118,115,
				32,113,118,
				118,113,115,
				34,121,120,
				11,119,121,
				35,120,119,
				119,120,121,
				35,123,122,
				6,53,123,
				15,122,53,
				53,122,123,
				15,56,125,
				4,124,56,
				34,125,124,
				124,125,56,
				35,122,120,
				15,125,122,
				34,120,125,
				125,120,122,
				36,128,127,
				3,126,128,
				37,127,126,
				126,127,128,
				37,130,129,
				8,60,130,
				17,129,60,
				60,129,130,
				17,63,132,
				6,131,63,
				36,132,131,
				131,132,63,
				37,129,127,
				17,132,129,
				36,127,132,
				132,127,129,
				38,135,134,
				5,133,135,
				39,134,133,
				133,134,135,
				39,137,136,
				10,67,137,
				19,136,67,
				67,136,137,
				19,70,139,
				8,138,70,
				38,139,138,
				138,139,70,
				39,136,134,
				19,139,136,
				38,134,139,
				139,134,136,
				40,142,141,
				7,140,142,
				41,141,140,
				140,141,142,
				41,144,143,
				2,73,144,
				21,143,73,
				73,143,144,
				21,75,146,
				10,145,75,
				40,146,145,
				145,146,75,
				41,143,141,
				21,146,143,
				40,141,146,
				146,141,143,
				22,80,147,
				5,135,80,
				38,147,135,
				135,147,80,
				38,138,148,
				8,130,138,
				37,148,130,
				130,148,138,
				37,126,149,
				3,85,126,
				22,149,85,
				85,149,126,
				38,148,147,
				37,149,148,
				22,147,149,
				149,147,148,
				25,88,150,
				7,142,88,
				40,150,142,
				142,150,88,
				40,145,151,
				10,137,145,
				39,151,137,
				137,151,145,
				39,133,152,
				5,92,133,
				25,152,92,
				92,152,133,
				40,151,150,
				39,152,151,
				25,150,152,
				152,150,151,
				27,95,153,
				9,114,95,
				32,153,114,
				114,153,95,
				32,117,154,
				2,144,117,
				41,154,144,
				144,154,117,
				41,140,155,
				7,99,140,
				27,155,99,
				99,155,140,
				32,154,153,
				41,155,154,
				27,153,155,
				155,153,154,
				29,102,156,
				11,121,102,
				34,156,121,
				121,156,102,
				34,124,157,
				4,116,124,
				33,157,116,
				116,157,124,
				33,112,158,
				9,106,112,
				29,158,106,
				106,158,112,
				34,157,156,
				33,158,157,
				29,156,158,
				158,156,157,
				31,108,159,
				3,128,108,
				36,159,128,
				128,159,108,
				36,131,160,
				6,123,131,
				35,160,123,
				123,160,131,
				35,119,161,
				11,111,119,
				31,161,111,
				111,161,119,
				36,160,159,
				35,161,160,
				31,159,161,
				161,159,160
		};
	}
}

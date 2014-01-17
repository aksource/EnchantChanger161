package Nanashi.AdvancedTools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUGTool extends ItemTool
{
	public String BaseName;
	public boolean canDestroyABlock = false;
	protected int cDestroyRange;
	private int[] rangeArray = new int[]{2,4,7,9,9};
	private int saftyCount;
	private int breakcount;
	public int side;

	protected ItemUGTool(int var1, int var2, EnumToolMaterial var3, Block[] var4)
	{
		super(var1, var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SaftyCounter;
		this.saftyCount = AdvancedTools.UGTools_SaftyCounter;
	}

	protected ItemUGTool(int var1, int var2, EnumToolMaterial var3, Block[] var4, float var5)
	{
		super(var1, var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SaftyCounter;
		this.saftyCount = AdvancedTools.UGTools_SaftyCounter;
		this.setMaxDamage((int)(var5 * (float)this.getMaxDamage()));
	}

	public boolean doChainDestraction(Block var1)
	{
		return false;
	}
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) 
	{
		getRange(par1ItemStack);
	}
	@Override
	public Item setUnlocalizedName(String var1)
	{
		super.setUnlocalizedName(var1);

		if (this.BaseName == null){
			this.BaseName = var1;
		}

		return this;
	}
	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, int blockid, int x, int y, int z, EntityLivingBase breaker)
	{
		if (!world.isRemote){
			item.damageItem(1, breaker);
			this.breakcount = 0;
			int range = getRange(item);
			if (this.canHarvestBlock(Block.blocksList[blockid]) && range != 0 && breaker instanceof EntityPlayer){
				this.destroyAroundBlock(item, world, blockid, x, y, z, (EntityPlayer)breaker, range);
				item.damageItem(this.breakcount, breaker);
				return true;
			}else{
				return true;
			}
		}
		else return false;
	}
	private boolean destroyAroundBlock(ItemStack var1, World world, int blockid, int x, int y, int z, EntityPlayer var6, int range)
	{
		this.searchAndDestroyBlock(world,x, y, z, side, blockid, var1, var6, range);
		return true;
	}
	protected void searchAndDestroyBlock(World world, int x, int y, int z, int side, int blockid, ItemStack var6, EntityPlayer var7, int range)
	{
		ArrayList var8 = new ArrayList();
		var8.add(new ChunkPosition(x, y, z));
		int minX;
		int minY;
		int minZ;
		int maxX;
		int maxY;
		int maxZ;

		if (!this.doChainDestraction(Block.blocksList[blockid])){
			minX = x - range;
			minY = y - range;
			minZ = z - range;
			maxX = x + range;
			maxY = y + range;
			maxZ = z + range;

			if (side == 0 || side == 1){
				minY = y;
				maxY = y;
			}

			if (side == 2 || side == 3){
				minZ = z;
				maxZ = z;
				minY += range - 1;
				maxY += range - 1;
			}

			if (side == 4 || side == 5){
				minX = x;
				maxX = x;
				minY += range - 1;
				maxY += range - 1;
			}
		}else{
			minX = x - this.cDestroyRange;
			minY = y - this.cDestroyRange;
			minZ = z - this.cDestroyRange;
			maxX = x + this.cDestroyRange;
			maxY = y + this.cDestroyRange;
			maxZ = z + this.cDestroyRange;
		}

		ChunkPosition minChunkPos = new ChunkPosition(minX, minY, minZ);
		ChunkPosition maxChunkPos = new ChunkPosition(maxX, maxY, maxZ);

		for (int var17 = 0; var17 < this.saftyCount; ++var17){
			ArrayList var18 = new ArrayList();

			for (int var19 = 0; var19 < var8.size(); ++var19){
				var18.addAll(this.searchAroundBlock(world,(ChunkPosition)var8.get(var19), minChunkPos, maxChunkPos, blockid, var6, var7));
			}

			if (var18.isEmpty()){
				break;
			}

			var8.clear();
			var8.addAll(var18);
		}
	}

	protected ArrayList searchAroundBlock(World world,ChunkPosition var1, ChunkPosition minChunkPos, ChunkPosition maxChunkPos, int var4, ItemStack var5, EntityPlayer var6)
	{
		ArrayList var7 = new ArrayList();
		ChunkPosition[] var8 = new ChunkPosition[6];

		if (var1.y > minChunkPos.y){
			var8[0] = new ChunkPosition(var1.x, var1.y - 1, var1.z);
		}

		if (var1.y < maxChunkPos.y){
			var8[1] = new ChunkPosition(var1.x, var1.y + 1, var1.z);
		}

		if (var1.z > minChunkPos.z){
			var8[2] = new ChunkPosition(var1.x, var1.y, var1.z - 1);
		}

		if (var1.z < maxChunkPos.z){
			var8[3] = new ChunkPosition(var1.x, var1.y, var1.z + 1);
		}

		if (var1.x > minChunkPos.x){
			var8[4] = new ChunkPosition(var1.x - 1, var1.y, var1.z);
		}

		if (var1.x < maxChunkPos.x){
			var8[5] = new ChunkPosition(var1.x + 1, var1.y, var1.z);
		}

		for (int var9 = 0; var9 < 6; ++var9){
			if (var8[var9] != null && this.destroyBlock(world,var8[var9], var4, var5, var6)){
				var7.add(var8[var9]);
			}
		}

		return var7;
	}

	private boolean destroyChainedBlock()
	{
		return true;
	}

	protected boolean destroyBlock(World world, ChunkPosition var1, int blockid, ItemStack var3, EntityPlayer var4)
	{
		int var5 = world.getBlockId(var1.x, var1.y, var1.z);

		if (var5 == 0){
			return false;
		}else{
			if (blockid == -1){
				if (!this.canHarvestBlock(Block.blocksList[var5])){
					return false;
				}
			}else if (Block.blocksList[blockid] != Block.oreRedstone && Block.blocksList[blockid] != Block.oreRedstoneGlowing){
				if (Block.blocksList[blockid] != Block.dirt && Block.blocksList[blockid] != Block.grass){
					if (var5 != blockid){
						return false;
					}
				}else if (Block.blocksList[var5] != Block.dirt && Block.blocksList[var5] != Block.grass){
					return false;
				}
			}else if (Block.blocksList[var5] != Block.oreRedstone && Block.blocksList[var5] != Block.oreRedstoneGlowing){
				return false;
			}

			return this.checkandDestroy(world,var1, var5, var3, var4);
		}
	}

	private boolean checkandDestroy(World world,ChunkPosition var1, int var2, ItemStack var3, EntityPlayer var4)
	{
		int var5 = world.getBlockMetadata(var1.x, var1.y, var1.z);
		boolean var6 = world.setBlock(var1.x, var1.y, var1.z, 0);

		if (var6){
			Block.blocksList[var2].onBlockDestroyedByPlayer(world, var1.x, var1.y, var1.z, var5);
			if(AdvancedTools.dropGather){
				Block.blocksList[var2].harvestBlock(world, var4, MathHelper.ceiling_double_int( var4.posX), MathHelper.ceiling_double_int( var4.posY), MathHelper.ceiling_double_int( var4.posZ), var5);
			}else{
				Block.blocksList[var2].harvestBlock(world, var4, var1.x, var1.y, var1.z, var5);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var3) <= 0){
				this.breakcount++;
			}

			return true;
		}else{
			return false;
		}
	}
	@Override
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		if (!var2.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) var3;
			int range = this.setRange(var1, var3);
			if (range == 0){
				player.addChatMessage(this.BaseName + " will harvest only one.");
			}else{
				player.addChatMessage(this.BaseName + "\'s range was changed to " + (range * 2 + 1) + "x" + (range * 2 + 1));
			}
		}
		return var1;
	}

	private int setRange(ItemStack var1, EntityPlayer var3)
	{
		int range = getRange(var1);
		int toolMaterialOrd = this.toolMaterial.ordinal();
		if(!var3.isSneaking()){
			++range;
			if(range > this.rangeArray[toolMaterialOrd]){
				range = 0;
			}
		}else{
			--range;
			if(range < 0){
				range = this.rangeArray[toolMaterialOrd];
			}
		}
		var1.getTagCompound().setInteger("range", range);
		return range;
	}
	private int getRange(ItemStack item)
	{
		int range;
		if(item.hasTagCompound() && item.getTagCompound().hasKey("range")){
			range = item.getTagCompound().getInteger("range");
		}else{
			range = AdvancedTools.UGTools_DestroyRangeLV;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("range", range);
			item.setTagCompound(nbt);
		}
		return range;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List par3List, boolean par4)
	{
		super.addInformation(item, player, par3List, par4);
		int range = getRange(item);
		if (range == 0){
			par3List.add("Range: Only one");
		}else{
			par3List.add("Range: " + (range * 2 + 1) + "x" + (range * 2 + 1));
		}
	}
	public boolean checkArrays(Block block, int[] blocklist)
	{
		int blockID = block.blockID;
		for(int i = 0;i<blocklist.length;i++){
			if(blocklist[i] == blockID){
				return true;
			}
		}
		return false;
	}
}

package CaveStory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLevelUpWeapons extends Item implements IItemRenderer
{
	public int Level;
	private int prevLevel;
	public int points;
	private int prevPoints;
	private boolean upDate = true;
	private Minecraft mc;
	public ItemLevelUpWeapons(int id)
	{
		super(id);
	}
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par3Entity instanceof EntityPlayer && par5)
		{
			if(this.upDate)
			{
				this.readFromNBT(par1ItemStack.getTagCompound());
				this.upDate = false;
			}
			else
			{
				if(this.Level < 2 && this.points >= (this.Level + 1) * 10)
				{
					addLevel(1);
					addWeaponPoints(-(this.Level + 1) * 10);
					if(this.Level > 2)
					{
						setLevel(3);
						if(this.points > (this.Level + 1) * 10);
					}
				}
				else
				{
					this.setWeaponPoints(this.points > (this.Level + 1) * 10 ? (this.Level + 1) * 10:this.getWeaponPoints());
				}
				if(this.points != this.prevPoints || this.Level != this.prevLevel)
				{
					this.writeToNBT(par1ItemStack.getTagCompound());
					this.upDate = true;
				}
			}
		}
	}
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}
	public void readFromNBT(NBTTagCompound var1) {
		this.Level = var1.getInteger("CSItemLevel");
		this.points = var1.getInteger("CSItemPoints");
		this.prevPoints = this.points;
		this.prevLevel = this.Level;
	}
	public void writeToNBT(NBTTagCompound var1) {
		var1.setInteger("CSItemLevel", Level);
		var1.setInteger("CSItemPoints", points);
	}
	public void addWeaponPoints(int par1)
	{
		this.points += par1;
	}
	public void setWeaponPoints(int par1)
	{
		this.points = par1;
	}
	public int getWeaponPoints()
	{
		return this.points;
	}
	public void addLevel(int par1)
	{
		this.Level += par1;
	}
	public void setLevel(int par1)
	{
		this.Level = par1;
	}
	public int getLevel()
	{
		return this.Level;
	}
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED;
	}
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		renderWeapon((EntityLiving) data[1], item);
	}
	@SideOnly(Side.CLIENT)
	public void renderWeapon(EntityLiving entity, ItemStack stack)
	{
		mc = Minecraft.getMinecraft();
		Icon icon = entity.getItemIcon(stack, 0);
		if (icon == null)
		{
//			GL11.glPopMatrix();
			return;
		}
        TextureManager texturemanager = mc.func_110434_K();
        texturemanager.func_110577_a(texturemanager.func_130087_a(stack.getItemSpriteNumber()));
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		Tessellator tessellator = Tessellator.instance;
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		float f4 = 0.0F;
		float f5 = 0.3F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-f4, -f5, 0.0F);
		float f6 = 1.5F;
		GL11.glScalef(f6, f6, f6);
//		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		RenderManager.instance.itemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getOriginX(), icon.getOriginY(), 0.0625F);

		if (stack != null && stack.hasEffect()/* && par3 == 0*/)
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			texturemanager.func_110577_a(new ResourceLocation("%blur%/misc/glint.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			float f7 = 0.76F;
			GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float f8 = 0.125F;
			GL11.glScalef(f8, f8, f8);
			float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
			GL11.glTranslatef(f9, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			RenderManager.instance.itemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(f8, f8, f8);
			f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
			GL11.glTranslatef(-f9, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			RenderManager.instance.itemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
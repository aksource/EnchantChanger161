package ak.PrepaidEnchantment.Client;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import ak.PrepaidEnchantment.ContainerPEnchantment;
import ak.PrepaidEnchantment.PrepaidEnchantment;
import ak.PrepaidEnchantment.TileEntityPEnchantmentTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPEnchantment extends GuiContainer
{
	/** The book model used on the GUI. */
	private static ModelBook bookModel = new ModelBook();
	private Random rand = new Random();
	private ResourceLocation tex = new ResourceLocation(PrepaidEnchantment.TextureDomain + PrepaidEnchantment.GuiPEnchTex);

	/** ContainerEnchantment object associated with this gui */
	private ContainerPEnchantment containerEnchantment;
	public int field_74214_o;
	public float field_74213_p;
	public float field_74212_q;
	public float field_74211_r;
	public float field_74210_s;
	public float field_74209_t;
	public float field_74208_u;
	ItemStack theItemStack;
	private String field_94079_C;
	private TileEntityPEnchantmentTable tileEntity;
	private GuiButtonPEnch5 next5ButtonIndex;
	private GuiButtonPEnch5 previous5ButtonIndex;
	private GuiButtonPEnch10 next10ButtonIndex;
	private GuiButtonPEnch10 previous10ButtonIndex;
	private int CurrentMaxLevel = 10;

	public GuiPEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, String par6Str, TileEntityPEnchantmentTable te)
	{
		super(new ContainerPEnchantment(par1InventoryPlayer, par2World, par3, par4, par5, te));
		this.containerEnchantment = (ContainerPEnchantment)this.inventorySlots;
		this.field_94079_C = par6Str;
		this.tileEntity = te;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(this.field_94079_C == null ? StatCollector.translateToLocal("container.prepaidenchantmenttable") : this.field_94079_C, 12, 5, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 134, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocalFormatted("container.prepaidenchantmenttable.cost", new Object[] {Integer.valueOf(this.tileEntity.getPoint())}), 22, this.ySize + 35, 8453920);
	}
    public void initGui()
    {
        super.initGui();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.buttonList.add(this.next5ButtonIndex = new GuiButtonPEnch5(1, i + 3 + 16 + 12, j + 64, true));
        this.buttonList.add(this.previous5ButtonIndex = new GuiButtonPEnch5(2, i + 3 + 16, j + 64, false));
        this.buttonList.add(this.next10ButtonIndex = new GuiButtonPEnch10(3, i + 3 + 16 + 12 + 12, j + 64, true));
        this.buttonList.add(this.previous10ButtonIndex = new GuiButtonPEnch10(4, i + 3, j + 64, false));
        this.next5ButtonIndex.enabled = false;
        this.previous5ButtonIndex.enabled = false;
        this.next10ButtonIndex.enabled = false;
        this.previous10ButtonIndex.enabled = false;
    }
	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		super.updateScreen();
		this.func_74205_h();
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		int l = (this.width - this.xSize) / 2;
		int i1 = (this.height - this.ySize) / 2;

		for (int j1 = 0; j1 < 3; ++j1)
		{
			int k1 = par1 - (l + 60);
			int l1 = par2 - (i1 + 14 + 19 * j1);

			if (k1 >= 0 && l1 >= 0 && k1 < 108 && l1 < 19 && this.containerEnchantment.enchantItem(this.mc.thePlayer, j1))
			{
				this.mc.playerController.sendEnchantPacket(this.containerEnchantment.windowId, j1);
			}
		}
	}
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		boolean flag = false;

		if (par1GuiButton == this.next5ButtonIndex && this.next5ButtonIndex.enabled)
		{
			this.CurrentMaxLevel +=5;
			flag = true;
			this.next5ButtonIndex.enabled = this.CurrentMaxLevel <= this.tileEntity.getPoint();
		}
		else if (par1GuiButton == this.previous5ButtonIndex && this.previous5ButtonIndex.enabled)
		{
			this.CurrentMaxLevel -=5;
			flag = true;
			this.previous5ButtonIndex.enabled = this.CurrentMaxLevel >= 15;
		}
		else if (par1GuiButton == this.next10ButtonIndex && this.next5ButtonIndex.enabled)
		{
			this.CurrentMaxLevel +=10;
			flag = true;
			this.next5ButtonIndex.enabled = this.CurrentMaxLevel <= this.tileEntity.getPoint();
		}
		else if (par1GuiButton == this.previous10ButtonIndex && this.previous10ButtonIndex.enabled)
		{
			this.CurrentMaxLevel -=5;
			flag = true;
			this.previous10ButtonIndex.enabled = this.CurrentMaxLevel >= 20;
		}
		if (flag)
		{
			containerEnchantment.onButtonPushed(par1GuiButton.id);
		}
	}
	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		this.mc.renderEngine.bindTexture(PrepaidEnchantment.GuiPEnchTex);
		Minecraft MC = Minecraft.getMinecraft();
		TextureManager texturemanager = MC.func_110434_K();
		texturemanager.func_110581_b(tex);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glViewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
		GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
		GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
		float f1 = 1.0F;
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslatef(0.0F, 3.3F, -16.0F);
		GL11.glScalef(f1, f1, f1);
		float f2 = 5.0F;
		GL11.glScalef(f2, f2, f2);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//		this.mc.renderEngine.bindTexture("/item/book.png");
//		GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
//		float f3 = this.field_74208_u + (this.field_74209_t - this.field_74208_u) * par1;
//		GL11.glTranslatef((1.0F - f3) * 0.2F, (1.0F - f3) * 0.1F, (1.0F - f3) * 0.25F);
//		GL11.glRotatef(-(1.0F - f3) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
//		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
//		float f4 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.25F;
//		float f5 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.75F;
//		f4 = (f4 - (float)MathHelper.truncateDoubleToInt((double)f4)) * 1.6F - 0.3F;
//		f5 = (f5 - (float)MathHelper.truncateDoubleToInt((double)f5)) * 1.6F - 0.3F;
//
//		if (f4 < 0.0F)
//		{
//			f4 = 0.0F;
//		}
//
//		if (f5 < 0.0F)
//		{
//			f5 = 0.0F;
//		}
//
//		if (f4 > 1.0F)
//		{
//			f4 = 1.0F;
//		}
//
//		if (f5 > 1.0F)
//		{
//			f5 = 1.0F;
//		}
//
//		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//		bookModel.render((Entity)null, 0.0F, f4, f5, f3, 0.0F, 0.0625F);
//		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//		RenderHelper.disableStandardItemLighting();
//		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
//		GL11.glPopMatrix();
//		GL11.glMatrixMode(GL11.GL_MODELVIEW);
//		GL11.glPopMatrix();
//		RenderHelper.disableStandardItemLighting();
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		this.mc.renderEngine.bindTexture(PrepaidEnchantment.GuiPEnchTex);
		EnchantmentNameParts.instance.setRandSeed(this.containerEnchantment.nameSeed);

		for (int i1 = 0; i1 < 3; ++i1)
		{
			String s = EnchantmentNameParts.instance.generateRandomEnchantName();
			this.zLevel = 0.0F;
//			this.mc.renderEngine.bindTexture(PrepaidEnchantment.GuiPEnchTex);
			texturemanager.func_110581_b(new ResourceLocation(PrepaidEnchantment.GuiPEnchTex));
			int j1 = this.containerEnchantment.enchantLevels[i1];
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (j1 == 0)
			{
				this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 185, 108, 19);
			}
			else
			{
				String s1 = "" + j1;
				FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
				int k1 = 6839882;

				if (this.mc.thePlayer.experienceLevel < j1 && !this.mc.thePlayer.capabilities.isCreativeMode)
				{
					this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 185, 108, 19);
					fontrenderer.drawSplitString(s, k + 62, l + 16 + 19 * i1, 104, (k1 & 16711422) >> 1);
					fontrenderer = this.mc.fontRenderer;
					k1 = 4226832;
					fontrenderer.drawStringWithShadow(s1, k + 62 + 104 - fontrenderer.getStringWidth(s1), l + 16 + 19 * i1 + 7, k1);
				}
				else
				{
					int l1 = par2 - (k + 60);
					int i2 = par3 - (l + 14 + 19 * i1);

					if (l1 >= 0 && i2 >= 0 && l1 < 108 && i2 < 19)
					{
						this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 204, 108, 19);
						k1 = 16777088;
					}
					else
					{
						this.drawTexturedModalRect(k + 60, l + 14 + 19 * i1, 0, 166, 108, 19);
					}

					fontrenderer.drawSplitString(s, k + 62, l + 16 + 19 * i1, 104, k1);
					fontrenderer = this.mc.fontRenderer;
					k1 = 8453920;
					fontrenderer.drawStringWithShadow(s1, k + 62 + 104 - fontrenderer.getStringWidth(s1), l + 16 + 19 * i1 + 7, k1);
				}
			}
		}
	}

	public void func_74205_h()
	{
		ItemStack itemstack = this.inventorySlots.getSlot(0).getStack();

		if (!ItemStack.areItemStacksEqual(itemstack, this.theItemStack))
		{
			this.theItemStack = itemstack;

			do
			{
				this.field_74211_r += (float)(this.rand.nextInt(4) - this.rand.nextInt(4));
			}
			while (this.field_74213_p <= this.field_74211_r + 1.0F && this.field_74213_p >= this.field_74211_r - 1.0F);
		}

		++this.field_74214_o;
		this.field_74212_q = this.field_74213_p;
		this.field_74208_u = this.field_74209_t;
		boolean flag = false;

		for (int i = 0; i < 3; ++i)
		{
			if (this.containerEnchantment.enchantLevels[i] != 0)
			{
				flag = true;
			}
		}

		if (flag)
		{
			this.field_74209_t += 0.2F;
		}
		else
		{
			this.field_74209_t -= 0.2F;
		}

		if (this.field_74209_t < 0.0F)
		{
			this.field_74209_t = 0.0F;
		}

		if (this.field_74209_t > 1.0F)
		{
			this.field_74209_t = 1.0F;
		}

		float f = (this.field_74211_r - this.field_74213_p) * 0.4F;
		float f1 = 0.2F;

		if (f < -f1)
		{
			f = -f1;
		}

		if (f > f1)
		{
			f = f1;
		}

		this.field_74210_s += (f - this.field_74210_s) * 0.9F;
		this.field_74213_p += this.field_74210_s;
	}
}
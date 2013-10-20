package ak.PrepaidEnchantment.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ak.PrepaidEnchantment.PrepaidEnchantment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonPEnch10 extends GuiButton
{
	/**
	 * If true, then next page button will face to right, if false then next page button will face to left.
	 */
	private final boolean mirrored;

	public GuiButtonPEnch10(int par1, int par2, int par3, boolean par4)
	{
		super(par1, par2, par3, 12, 19, "");
		this.mirrored = par4;
	}

	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if (this.drawButton)
		{
//			par1Minecraft.renderEngine.bindTexture(PrepaidEnchantment.GuiPEnchTex);
			Minecraft MC = Minecraft.getMinecraft();
			TextureManager texturemanager = MC.getTextureManager();
			texturemanager.getTexture(new ResourceLocation(PrepaidEnchantment.GuiPEnchTex));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean flag = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int k = 19*2;
			int l = 176;

			if (!this.enabled)
			{
				l += this.width * 2;
			}
			else if (flag)
			{
				l += this.width;
			}

			if (!this.mirrored)
			{
				k += this.height;
			}

			this.drawTexturedModalRect(this.xPosition, this.yPosition, l, k, this.width, this.height);
		}
	}
}

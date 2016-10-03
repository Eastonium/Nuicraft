package eastonium.nuicraft.machine.purifier;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import eastonium.nuicraft.Bionicle;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPurifierInventory extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation(Bionicle.MODID + ":textures/gui/purifier.png");
	private TileInventoryPurifier tileEntity;
	
	private static final int TANK1_XPOS = 6;
	private static final int TANK1_YPOS = 7;
	private static final int TANK1_XLEN = 65;
	private static final int TANK1_YLEN = 64;
	private static final int TANK1_OVERLAY_XPOS = 176;
	private static final int TANK1_OVERLAY_YPOS = 0;

	private static final int TANK2_XPOS = 133;
	private static final int TANK2_YPOS = 7;
	private static final int TANK2_XLEN = 18 ;
	private static final int TANK2_YLEN = 64;
	private static final int TANK2_OVERLAY_XPOS = 176;
	private static final int TANK2_OVERLAY_YPOS = 64;
	
	public GuiPurifierInventory(InventoryPlayer player, TileInventoryPurifier tileEntity){
		super(new ContainerPurifier(player, tileEntity));
		this.tileEntity = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		String name = this.tileEntity.getName();
		this.fontRendererObj.drawString(name, this.xSize - this.fontRendererObj.getStringWidth(name) - 44, 12, 4346200);

		List<String> hoveringText = new ArrayList<String>();

		if (isInRect(guiLeft + 7, guiTop + 7, 63, 64, mouseX, mouseY)){
			hoveringText.add("Raw Liquid Protodermis:");
			hoveringText.add(tileEntity.rawTank.getFluidAmount() + "mb");
		}
		
		if (isInRect(guiLeft + 134, guiTop + 7, 16, 64, mouseX, mouseY)){
			hoveringText.add("Pure Liquid Protodermis:");
			hoveringText.add(tileEntity.pureTank.getFluidAmount() + "mb");
		}
		
		if (!hoveringText.isEmpty()){
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		this.mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		double fractionLeft;				
		if (!this.tileEntity.isTankEmpty(tileEntity.rawTank)){
			fractionLeft = this.tileEntity.fractionOfTankRemaining(tileEntity.rawTank);
			int yOffset = (int)((1.0 - fractionLeft) * TANK1_YLEN);
			this.drawTexturedModalRect(k + TANK1_XPOS, l + TANK1_YPOS + yOffset, TANK1_OVERLAY_XPOS, TANK1_OVERLAY_YPOS + yOffset, TANK1_XLEN, TANK1_YLEN - yOffset);
		}
		if (!this.tileEntity.isTankEmpty(tileEntity.pureTank)){
			fractionLeft = this.tileEntity.fractionOfTankRemaining(tileEntity.pureTank);
			int yOffset = (int)((1.0 - fractionLeft) * TANK2_YLEN);
			this.drawTexturedModalRect(k + TANK2_XPOS, l + TANK2_YPOS + yOffset, TANK2_OVERLAY_XPOS, TANK2_OVERLAY_YPOS + yOffset, TANK2_XLEN, TANK2_YLEN - yOffset);
		}
	}

	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY){
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
}

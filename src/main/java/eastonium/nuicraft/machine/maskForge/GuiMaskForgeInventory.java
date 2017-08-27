package eastonium.nuicraft.machine.maskForge;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMaskForgeInventory extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation(NuiCraft.MODID, "textures/gui/mask_forge_gui.png");
	private TileInventoryMaskForge tileEntity;

	public GuiMaskForgeInventory(InventoryPlayer player, TileInventoryMaskForge tileEntity){
		super(new ContainerMaskForge(player, tileEntity));
		this.tileEntity = tileEntity;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		//String name = this.tileEntity.hasCustomName() ? this.tileEntity.getName() : StatCollector.translateToLocal(this.tileEntity.getName());
		String name = tileEntity.getName();
		fontRenderer.drawString(name, xSize - fontRenderer.getStringWidth(name) - 10, 7, 4346200);

		List<String> hoveringText = new ArrayList<String>();

		if (isInRect(guiLeft + 7, guiTop + 7, 16, 64, mouseX, mouseY)){
			hoveringText.add("Lava:");
			hoveringText.add(tileEntity.tank.getFluidAmount() + "mb");
		}
		
		if (!hoveringText.isEmpty()){
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
		}
		
		//String lavaTip = String.valueOf(this.tileEntity.tank.getFluidAmount());
		//this.fontRendererObj.drawString(lavaTip, 28, 7, 4346200);//TODO make hover tooltip
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		double fractionLeft = tileEntity.fractionLeftOfCompletion();
		drawTexturedModalRect(k + 105, l + 31, 176, 0, (int)(fractionLeft * 24), 16);

		if (!tileEntity.isTankEmpty()){
			fractionLeft = tileEntity.fractionOfFuelRemaining();
			int yOffset = (int)((1.0 - fractionLeft) * 64);
			drawTexturedModalRect(k + 6, l + 7 + yOffset, 176, 17 + yOffset, 18, 64 - yOffset);
		}

	}

	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY){
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
}

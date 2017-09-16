package eastonium.nuicraft.machine.reservior;

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
public class GuiReserviorInventory extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation(NuiCraft.MODID, "textures/gui/reservior_gui.png");
	private TileInventoryReservior tileEntity;

	public GuiReserviorInventory(InventoryPlayer player, TileInventoryReservior tileEntity){
		super(new ContainerReservior(player, tileEntity));
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
//		String name = tileEntity.getName();
//		fontRenderer.drawString(name, xSize - fontRenderer.getStringWidth(name) - 10, 7, 4346200);

		List<String> hoveringText = new ArrayList<String>();
		if (isInRect(guiLeft + 72, guiTop + 8, 32, 64, mouseX, mouseY) && !tileEntity.isTankEmpty()){
			hoveringText.add(tileEntity.tank.getFluid().getLocalizedName() + ":");
			hoveringText.add(tileEntity.tank.getFluidAmount() + "mb");
		}
		if (isInRect(guiLeft + 51, guiTop + 35, 14, 8, mouseX, mouseY)){
			hoveringText.add("Empty");
		}
		if (isInRect(guiLeft + 111, guiTop + 35, 14, 8, mouseX, mouseY)){
			hoveringText.add("Fill");
		}
		
		if (!hoveringText.isEmpty()){
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		if (!tileEntity.isTankEmpty()){
			double fraction = tileEntity.fractionOfFluid();
			int yOffset = (int)((1.0 - fraction) * 64);
			drawTexturedModalRect(k + 6, l + 7 + yOffset, 176, 17 + yOffset, 18, 64 - yOffset);
		}

	}

	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY){
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
}

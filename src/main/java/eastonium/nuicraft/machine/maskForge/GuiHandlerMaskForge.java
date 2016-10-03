package eastonium.nuicraft.machine.maskForge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerMaskForge implements IGuiHandler {
	private static final int MASK_FORGE_GUI_ID = 0;
	public static int getGuiID() {return MASK_FORGE_GUI_ID;}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileInventoryMaskForge) {
			TileInventoryMaskForge tileInventoryMaskForge = (TileInventoryMaskForge) tileEntity;
			return new ContainerMaskForge(player.inventory, tileInventoryMaskForge);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileInventoryMaskForge) {
			TileInventoryMaskForge tileInventoryMaskForge = (TileInventoryMaskForge) tileEntity;
			return new GuiMaskForgeInventory(player.inventory, tileInventoryMaskForge);
		}
		return null;
	}

}
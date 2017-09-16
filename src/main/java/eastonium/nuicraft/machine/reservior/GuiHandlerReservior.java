package eastonium.nuicraft.machine.reservior;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerReservior implements IGuiHandler {
	private static final int GUI_ID = 1;
	public static int getGuiID() {return GUI_ID;}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileInventoryReservior) {
			TileInventoryReservior tileInventoryMaskForge = (TileInventoryReservior) tileEntity;
			return new ContainerReservior(player.inventory, tileInventoryMaskForge);
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
		if (tileEntity instanceof TileInventoryReservior) {
			TileInventoryReservior tileInventoryMaskForge = (TileInventoryReservior) tileEntity;
			return new GuiReserviorInventory(player.inventory, tileInventoryMaskForge);
		}
		return null;
	}

}
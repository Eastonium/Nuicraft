package eastonium.nuicraft.machine.purifier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerPurifier implements IGuiHandler {
	private static final int PURIFIER_GUI_ID = 1;
	public static int getGuiID() {return PURIFIER_GUI_ID;}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileInventoryPurifier) {
			TileInventoryPurifier tileInventoryPurifier = (TileInventoryPurifier)tileEntity;
			return new ContainerPurifier(player.inventory, tileInventoryPurifier);
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
		if (tileEntity instanceof TileInventoryPurifier) {
			TileInventoryPurifier tileInventoryPurifier = (TileInventoryPurifier) tileEntity;
			return new GuiPurifierInventory(player.inventory, tileInventoryPurifier);
		}
		return null;
	}

}
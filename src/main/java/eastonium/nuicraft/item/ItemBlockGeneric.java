package eastonium.nuicraft.item;

import java.util.logging.Level;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockGeneric extends ItemBlock {
	public ItemBlockGeneric(Block block) {
		super(block);
		this.setRegistryName(block.getRegistryName());
	}
}

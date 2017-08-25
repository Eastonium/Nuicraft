package eastonium.nuicraft.blocks;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockMetal extends Block {

	public BlockMetal(String name, Material materialIn) {
		super(materialIn);
		setSoundType(SoundType.METAL);
		setCreativeTab(NuiCraft.bio_block_tab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
	}
}

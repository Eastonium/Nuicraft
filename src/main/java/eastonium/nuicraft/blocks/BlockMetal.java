package eastonium.nuicraft.blocks;

import eastonium.nuicraft.Bionicle;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockMetal extends Block {

	public BlockMetal(Material materialIn) {
		super(materialIn);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(Bionicle.bioBlockTab);
	}
	
	public Block setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
}

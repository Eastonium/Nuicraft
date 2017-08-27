package eastonium.nuicraft.fluid;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockNuiCraftFluid extends BlockFluidClassic {
	public BlockNuiCraftFluid(Fluid fluid, Material material) {
		super(fluid, material);
		this.setCreativeTab(NuiCraft.nuicraftTab);
		this.setUnlocalizedName(NuiCraft.MODID + ".fluid_" + fluid.getName());
		this.setRegistryName(NuiCraft.MODID, "fluid_" + fluid.getName());
	}
}

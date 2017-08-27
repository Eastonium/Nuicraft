package eastonium.nuicraft.fluid;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidGeneric extends Fluid {
	public FluidGeneric(String fluidName, ResourceLocation still, ResourceLocation flowing) {
		super(fluidName, still, flowing);
		this.setUnlocalizedName(NuiCraft.MODID + "." + fluidName);
	}
}

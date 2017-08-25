package eastonium.nuicraft.fluid;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.CommonProxyBionicle;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BionicleFluidUtil {	

	public static final int BUCKET_VOLUME = 1000;
	public static final Set<Fluid> fluids = new HashSet<>();
	public static final Set<IFluidBlock> modFluidBlocks = new HashSet<>();

	/*public static void registerFluidContainers() {
		registerTank(FluidRegistry.WATER);
		registerTank(FluidRegistry.LAVA);

		for (Fluid fluid : fluids) {
			registerBucket(fluid);
			registerTank(fluid);
		}
	}*/

	/**
	 * Create a {@link Fluid} and its {@link IFluidBlock}, or use the existing ones if a fluid has already been registered with the same name.
	 *
	 * @param name         The name of the fluid
	 * @param textureName  The base name of the fluid's texture
	 * @param hasFlowIcon  Does the fluid have a flow icon?
	 * @param fluidPropertyApplier A function that sets the properties of the {@link Fluid}
	 * @param blockFactory A function that creates the {@link IFluidBlock}
	 * @return The fluid and block
	 */
	public static <T extends Block & IFluidBlock> Fluid createFluid(String name, String textureName, boolean hasFlowIcon, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory) {
		ResourceLocation still = new ResourceLocation(textureName + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(textureName + "_flow") : still;

		Fluid fluid = new Fluid(name, still, flowing);
		boolean useOwnFluid = FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);

		if(useOwnFluid){
			fluidPropertyApplier.accept(fluid);
			registerFluidBlock(blockFactory.apply(fluid));
		}else{
			fluid = FluidRegistry.getFluid(name);
		}
		fluids.add(fluid);
		return fluid;
	}

	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
		String fluidName = "fluid." + block.getFluid().getName();
		block.setRegistryName(fluidName);
		block.setUnlocalizedName(NuiCraft.MODID + "." + fluidName);
//		CommonProxyBionicle.registerBlock(block); TODO 
//		GameRegistry.register(block);
//		CommonProxyBionicle.registerItem(new ItemBlock(block).setRegistryName(block.getRegistryName()));
//		GameRegistry.register(new ItemBlock(block), block.getRegistryName());
		modFluidBlocks.add(block);
		return block;
	}

	/*private static void registerBucket(Fluid fluid) {
		ItemStack filledBucket = ModItems.bucket.registerBucketForFluid(fluid);

		if (!FluidContainerRegistry.registerFluidContainer(fluid, filledBucket, FluidContainerRegistry.EMPTY_BUCKET)) {
			Logger.error("Unable to register bucket of %s as fluid container", fluid.getName());
		}
	}

	private static void registerTank(Fluid fluid) {
		FluidStack fluidStack = new FluidStack(fluid, 10 * BUCKET_VOLUME);
		((ItemFluidTank) Item.getItemFromBlock(ModBlocks.fluidTank)).addFluid(fluidStack);
	}*/
}

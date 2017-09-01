package eastonium.nuicraft;

import eastonium.nuicraft.fluid.FluidGeneric;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NuiCraftFluids {
	public static final Fluid protodermis = createFluid("protodermis");
	public static final Fluid protodermis_pure = createFluid("protodermis_pure");
	public static final Fluid protodermis_molten = createFluid("protodermis_molten")
			.setLuminosity(15).setDensity(3500).setViscosity(4000).setTemperature(2800);
	public static final Fluid protodermis_pure_molten = createFluid("protodermis_pure_molten")
			.setLuminosity(15).setDensity(3500).setViscosity(4000).setTemperature(2800);
	
	private static Fluid createFluid(String fluidName) {
		ResourceLocation still = new ResourceLocation(NuiCraft.MODID, "blocks/" + fluidName + "_still");
		ResourceLocation flowing = new ResourceLocation(NuiCraft.MODID, "blocks/" + fluidName + "_flow");
		Fluid fluid = new FluidGeneric(fluidName, still, flowing);
		FluidRegistry.registerFluid(fluid);
		return fluid;
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		setTextureLocation(protodermis);
		setTextureLocation(protodermis_pure);
		setTextureLocation(protodermis_molten);
		setTextureLocation(protodermis_pure_molten);
	}
	
	private static void setTextureLocation(Fluid fluid) {
		Item item = Item.getItemFromBlock(fluid.getBlock());
		ModelBakery.registerItemVariants(item);
		ModelResourceLocation location = new ModelResourceLocation(NuiCraft.MODID + ":fluid", fluid.getName());
		ModelLoader.setCustomMeshDefinition(item, stack -> location);
		ModelLoader.setCustomStateMapper(fluid.getBlock(), new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return location;
			}
		});
	}
	
	
	
//	public static <T extends Block & IFluidBlock> Fluid createFluid(String name, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory) {
//		ResourceLocation still = new ResourceLocation(NuiCraft.MODID, name + "_still");
//		ResourceLocation flowing = new ResourceLocation(NuiCraft.MODID, name + "_flow");
//
//		Fluid fluid = new Fluid(name, still, flowing);
//		boolean useOwnFluid = FluidRegistry.registerFluid(fluid);
//		FluidRegistry.addBucketForFluid(fluid);
//
//		if(useOwnFluid){
//			fluidPropertyApplier.accept(fluid);
//			registerFluidBlock(blockFactory.apply(fluid));
//		}else{
//			fluid = FluidRegistry.getFluid(name);
//		}
//		fluids.add(fluid);
//		return fluid;
//	}
//
//	private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
//		String fluidName = "fluid." + block.getFluid().getName();
//		block.setRegistryName(fluidName);
//		block.setUnlocalizedName(NuiCraft.MODID + "." + fluidName);
////		CommonProxyBionicle.registerBlock(block); TODO 
////		GameRegistry.register(block);
////		CommonProxyBionicle.registerItem(new ItemBlock(block).setRegistryName(block.getRegistryName()));
////		GameRegistry.register(new ItemBlock(block), block.getRegistryName());
//		modFluidBlocks.add(block);
//		return block;
//	}
}

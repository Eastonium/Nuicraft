package eastonium.nuicraft;

import eastonium.nuicraft.block.BlockBamboo;
import eastonium.nuicraft.block.BlockKoro.EnumKoroBlock;
import eastonium.nuicraft.block.BlockProtodermisDeposit;
import eastonium.nuicraft.item.ItemGenericMeta;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ObjectHolder(NuiCraft.MODID)
public class NuiCraftBlocks {		
	public static final Block fluid_protodermis = null;
	public static final Block fluid_protodermis_pure = null;
	public static final Block fluid_protodermis_molten = null;
	public static final Block fluid_protodermis_pure_molten = null;
	
	public static final Block mask_forge = null;
	public static final Block purifier = null;
	
	public static final Block koro_block = null;
	
	public static final Block nuva_cube = null;
	//public static final Block crystalized_protodermis = null;
	public static final Block matanui_stone = null;
	public static final Block makuta_stone = null;
	public static final Block lightstone = null;
	
	public static final Block lightstone_ore = null;
	public static final Block heatstone_ore = null;
	public static final Block protodermis_ore = null;
	
	public static final Block bamboo = null;
	
	public static final Block block_protodermis = null;
	public static final Block block_protosteel = null;
	
	@SideOnly(Side.CLIENT)
    public static void initModels() {
		setTextureLocation(mask_forge);		
		//ModelLoader.setCustomModelResourceLocation(Item.REGISTRY.getObject(purifier.getRegistryName()), 0, new ModelResourceLocation(purifier.getRegistryName(), "normal"));

		setTextureLocation(nuva_cube);
		//setTextureLocation(crystalized_protodermis);
		setTextureLocation(matanui_stone);
		setTextureLocation(makuta_stone);
		ModelLoader.setCustomModelResourceLocation(Item.REGISTRY.getObject(lightstone.getRegistryName()), 0, new ModelResourceLocation(lightstone.getRegistryName(), "inventory"));
		setTextureLocation(lightstone_ore);
		setTextureLocation(heatstone_ore);
		
		ModelLoader.setCustomStateMapper(protodermis_ore, (new StateMap.Builder()).ignore(new IProperty[] {BlockProtodermisDeposit.DROPS}).build());
		setTextureLocation(protodermis_ore);
		
		ModelLoader.setCustomStateMapper(bamboo, (new StateMap.Builder()).ignore(new IProperty[] {BlockBamboo.AGE}).build());
		setTextureLocation(bamboo);
		
		setTextureLocation(block_protodermis);
		setTextureLocation(block_protosteel);
    }

	private static void setTextureLocation(Block block){
		Item item = Item.REGISTRY.getObject(block.getRegistryName());
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
package eastonium.nuicraft.item;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftBlocks;
import eastonium.nuicraft.block.BlockKoro.EnumKoroBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBlockKoro extends ItemBlock {
	public ItemBlockKoro(){
		super(NuiCraftBlocks.koro_block);
		setHasSubtypes(true);
		setRegistryName(NuiCraftBlocks.koro_block.getRegistryName());
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack){
    	return "tile." + NuiCraft.MODID + ".koro_block_" + EnumKoroBlock.byMetadata(itemstack.getItemDamage()).getName();
	}

	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}

	public static void setTextureLocations() {
		for(EnumKoroBlock enumBlock : EnumKoroBlock.values()){
			ModelLoader.setCustomModelResourceLocation(
					Item.REGISTRY.getObject(NuiCraftBlocks.koro_block.getRegistryName()),
					enumBlock.getMetadata(),
					new ModelResourceLocation(NuiCraftBlocks.koro_block.getRegistryName().toString(),
							"meta=" + enumBlock.getMetadata()));
		}
	}
}

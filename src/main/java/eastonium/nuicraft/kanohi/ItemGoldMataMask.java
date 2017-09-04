package eastonium.nuicraft.kanohi;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.NuiCraftItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class ItemGoldMataMask extends ItemMaskMeta {	
	protected static final String[] mataNames = new String[]{"kakama", "pakari", "kaukau", "miru", "hau", "akaku"};

	public ItemGoldMataMask(){
		super("mask_mata_gold", 6, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer){
		return NuiCraft.MODID + ":textures/models/masks/gold/mata_" + mataNames[stack.getMetadata()] + "_gold.png";
	}

	public static void setTextureLocations() {
		for(int i = 0; i < mataNames.length; i++){
			ModelLoader.setCustomModelResourceLocation(
				NuiCraftItems.mask_mata_gold,
				i,
				new ModelResourceLocation(NuiCraft.MODID + ":mask_gold", "name=mata_" + mataNames[i]));
		}		
	}
}

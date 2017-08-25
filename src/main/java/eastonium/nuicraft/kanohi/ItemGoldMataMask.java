package eastonium.nuicraft.kanohi;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemGoldMataMask extends ItemMaskMeta {
	
	public static final String[] mataNames = new String[]{"kakama", "pakari", "kaukau", "miru", "hau", "akaku"};

	public ItemGoldMataMask(){
		super("mask_mata_gold", 6, true);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer){
		return NuiCraft.MODID + ":textures/models/masks/mask_mata_" + mataNames[stack.getMetadata()] + "_1.png";
	}
}

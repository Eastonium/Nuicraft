package eastonium.nuicraft.kanohi;

import eastonium.nuicraft.Bionicle;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemGoldMataMask extends ItemMaskMeta{
	
	public static final String[] mataNames = new String[]{"Kakama", "Pakari", "Kaukau", "Miru", "Hau", "Akaku"};

	public ItemGoldMataMask(){
		super(true, 6);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer){
		return Bionicle.MODID + ":textures/models/masks/Mata" + mataNames[stack.getMetadata()] + "_1.png";
	}
}

package eastonium.nuicraft.kanohi;

import java.util.List;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMaskMeta extends ItemMask {
	public int numberOfSubitems;
	
	public ItemMaskMeta(String name, int numberOfSubitems, boolean isShiny){
		super(name, isShiny);
		this.numberOfSubitems = numberOfSubitems;
		hasSubtypes = true;
	}
	
	public ItemMaskMeta(String name, int numberOfSubitems) {
		this(name, numberOfSubitems, false);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer){
		return NuiCraft.MODID + ":textures/models/masks/" + getRegistryName().getResourcePath() + "_" + stack.getItemDamage() + ".png";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
		if (tab != getCreativeTab()) return;

		for(int i = 0; i < numberOfSubitems; i++){
			items.add(new ItemStack(this, 1, i));
		}		
	}
}

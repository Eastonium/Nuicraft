package eastonium.nuicraft.kanohi;

import java.util.List;

import eastonium.nuicraft.Bionicle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMaskMeta extends ItemMask {
	public int numberOfSubitems;
	
	public ItemMaskMeta(boolean isShiny, int numberOfSubitems){
		super(isShiny);
		this.numberOfSubitems = numberOfSubitems;
		this.hasSubtypes = true;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer){
		return Bionicle.MODID + ":textures/models/masks/" + this.getUnlocalizedName().substring(9) + "_" + stack.getItemDamage() + ".png";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List){
		for(int i = 0; i < this.numberOfSubitems; i++){
			par3List.add(new ItemStack(par1, 1, i));
		}		
	}
}

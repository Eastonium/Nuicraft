package eastonium.nuicraft.items;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNormal extends Item
{
	public boolean effect = false;
		
	public ItemNormal(String name, boolean effect){
		setCreativeTab(NuiCraft.bio_material_tab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
		this.effect = effect;
	}
	
	public ItemNormal(String name) {
		this(name, false);
	}
	
//	public Item setName(String name){
//        super.setUnlocalizedName(name);
//        this.setRegistryName(NuiCraft.MODID, name);
//        return this;
//    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack){
		return effect;
	}
}
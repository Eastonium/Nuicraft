package eastonium.nuicraft.item;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGeneric extends Item
{
	private boolean effect = false;
		
	public ItemGeneric(String name, boolean effect){
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
		this.effect = effect;
	}
	
	public ItemGeneric(String name) {
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
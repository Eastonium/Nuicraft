package eastonium.nuicraft.items;

import eastonium.nuicraft.Bionicle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNormal extends Item
{
	public boolean effect = false;
		
	public ItemNormal(boolean effect){
		this.setCreativeTab(Bionicle.bioMaterialTab);
		this.effect = effect;
	}
	
	public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack){
		return effect;
	}
}
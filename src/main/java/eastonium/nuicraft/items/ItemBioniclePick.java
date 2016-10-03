package eastonium.nuicraft.items;

import eastonium.nuicraft.Bionicle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class ItemBioniclePick extends ItemPickaxe {
	public ItemBioniclePick(ToolMaterial par1){
		super(par1);
		this.setCreativeTab(Bionicle.bioToolTab);
	}
	
	public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
}

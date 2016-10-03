package eastonium.nuicraft.items;

import eastonium.nuicraft.Bionicle;
import net.minecraft.item.Item;

public class ItemSluice extends Item {	
	
	public ItemSluice(){
		this.maxStackSize = 1;
		this.setCreativeTab(Bionicle.bioToolTab);
	}
	
	public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
}


package eastonium.nuicraft.items;

import eastonium.nuicraft.Bionicle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class ItemBionicleAxe extends ItemAxe {
	public ItemBionicleAxe(ToolMaterial material, float damage, float speed){
		super(material, damage, speed);
		this.setCreativeTab(Bionicle.bioToolTab);
	}
	
	public Item setName(String name){
        super.setUnlocalizedName(name);
        this.setRegistryName(Bionicle.MODID, name);
        return this;
    }
}

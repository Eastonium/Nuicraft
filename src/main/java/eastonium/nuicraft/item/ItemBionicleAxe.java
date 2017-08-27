package eastonium.nuicraft.item;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class ItemBionicleAxe extends ItemAxe {
	public ItemBionicleAxe(String name, ToolMaterial material, float damage, float speed){
		super(material, damage, speed);
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
	}
}

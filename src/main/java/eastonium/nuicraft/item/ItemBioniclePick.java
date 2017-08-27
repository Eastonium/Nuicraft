package eastonium.nuicraft.item;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class ItemBioniclePick extends ItemPickaxe {
	public ItemBioniclePick(String name, ToolMaterial par1){
		super(par1);
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
	}
}

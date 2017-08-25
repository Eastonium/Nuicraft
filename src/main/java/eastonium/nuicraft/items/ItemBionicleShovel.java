package eastonium.nuicraft.items;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class ItemBionicleShovel extends ItemSpade{
	public ItemBionicleShovel(String name, ToolMaterial par1){
		super(par1);
		setCreativeTab(NuiCraft.bio_tool_tab);
		setUnlocalizedName(NuiCraft.MODID + "." + name);
		setRegistryName(name);
	}
}

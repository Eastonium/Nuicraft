package eastonium.nuicraft.items;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.item.Item;

public class ItemSluice extends Item {	
	
	public ItemSluice(){
		maxStackSize = 1;
		setCreativeTab(NuiCraft.bio_tool_tab);
		setUnlocalizedName(NuiCraft.MODID + ".sluice");
		setRegistryName("sluice");
	}
}


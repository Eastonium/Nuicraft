package eastonium.nuicraft.item;

import eastonium.nuicraft.NuiCraft;
import net.minecraft.item.Item;

public class ItemSluice extends Item {	
	
	public ItemSluice(){
		maxStackSize = 1;
		setCreativeTab(NuiCraft.nuicraftTab);
		setUnlocalizedName(NuiCraft.MODID + ".sluice");
		setRegistryName("sluice");
	}
}


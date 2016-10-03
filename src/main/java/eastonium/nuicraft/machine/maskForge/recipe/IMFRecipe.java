package eastonium.nuicraft.machine.maskForge.recipe;

import net.minecraft.item.ItemStack;

public interface IMFRecipe {
	
	boolean matches(ItemStack[] inputStacks);
	
	ItemStack getOutput();
	
	ItemStack[] getRemainingItems();
}

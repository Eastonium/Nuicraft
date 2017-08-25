package eastonium.nuicraft.machine.maskForge.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IMFRecipe {
	
	boolean matches(NonNullList<ItemStack> inputStacks);
	
	ItemStack getOutput();
	
	NonNullList<ItemStack> getRemainingItems();
}

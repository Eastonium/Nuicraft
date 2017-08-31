package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.util.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public interface IMFRecipe {
	
	boolean matches(NonNullList<ItemStack> inputStacks);
	
	ItemStack getOutput();
	
	NonNullList<ItemStack> getRemainingItems();
	
	default NonNullList<CountedIngredient> getIngredients() {
        return NonNullList.<CountedIngredient>create();
    }
}

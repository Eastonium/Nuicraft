package eastonium.nuicraft.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CountedIngredient {
	
	public int count = 0;
	public Ingredient ing = Ingredient.EMPTY;
	
	public <I extends Ingredient> CountedIngredient(I ing, int count) {
		this.ing = ing;
		this.count = count;
	}
	public CountedIngredient(Item item, int count) {
		this.ing = Ingredient.fromItem(item);
		this.count = count;
	}
	public CountedIngredient(Block block, int count) {
		this(Item.getItemFromBlock(block), count);
	}
	public CountedIngredient(ItemStack stack) {
		this.ing = Ingredient.fromStacks(stack);
		this.count = stack.getCount();
	}
	
	public boolean apply(ItemStack stack) {
		if (ing.apply(stack)) {
			int commonSize = Math.min(stack.getCount(), count);
			stack.shrink(commonSize);
			count -= commonSize;
			return true;
		} else {
			return false;
		}
	}
	
	public CountedIngredient copy() {
		return new CountedIngredient(ing, count);
	}
}

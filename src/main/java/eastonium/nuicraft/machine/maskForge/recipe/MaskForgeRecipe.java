package eastonium.nuicraft.machine.maskForge.recipe;

import java.util.Iterator;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Level;

import eastonium.nuicraft.NuiCraft;
import eastonium.nuicraft.util.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MaskForgeRecipe implements IMFRecipe {

	private final int duration;
	@Nonnull
	protected ItemStack output = ItemStack.EMPTY;
	protected NonNullList<CountedIngredient> reqInputs = NonNullList.create();
	private NonNullList<ItemStack> returnStacks = NonNullList.create();


	public MaskForgeRecipe(int duration, ItemStack output, CountedIngredient... inputs) {
		this.duration = duration;
		this.output = output;
		for (CountedIngredient input : inputs) this.reqInputs.add(input);
	}
	
	@Override
    public boolean matches(NonNullList<ItemStack> inputStacks) {
		//NuiCraft.logger.log(Level.INFO, "checking for match");
        NonNullList<CountedIngredient> required = requiredIngCopy();
        returnStacks.clear();
        returnStacks.addAll(inputStacks);
        for (ItemStack stack : returnStacks) {
            if (stack.isEmpty()) continue;
            
            boolean inRecipe = false;
            Iterator<CountedIngredient> req = required.iterator();
            while (req.hasNext()) {
                if (req.next().apply(stack)) inRecipe = true;
                if (stack.isEmpty()) break;
            }
            if (!inRecipe) return false;
        }
        Iterator<CountedIngredient> req = required.iterator();
        while (req.hasNext()) {
        	if (req.next().count > 0) return false;
        }
        return true;
    }
	
	private NonNullList<CountedIngredient> requiredIngCopy() {
		NonNullList<CountedIngredient> copy = NonNullList.create();
		Iterator<CountedIngredient> ings = reqInputs.iterator();
		while (ings.hasNext()) {
			copy.add(ings.next().copy());
		}
		return copy;
	}

	@Override
	public ItemStack getOutput() {
		return this.output.copy();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems() {
		for(int i = 0; i < returnStacks.size(); i++){
			if (returnStacks.get(i).isEmpty()) returnStacks.remove(i);
		}
		return returnStacks;
	}
	
	@Override
	public NonNullList<CountedIngredient> getIngredients() { 
		return reqInputs;
	}
	
	@Override
	public int getRecipeDuration() {
		return duration;
	}
}
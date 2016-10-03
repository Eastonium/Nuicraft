package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.Bionicle;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class MaskMFRecipe implements IMFRecipe {

	private final ItemStack recipeOutput;
	private final int[] requiredKanokaTypes;
	private ItemStack[] returnStacks = null;

	public MaskMFRecipe(ItemStack output, int[] kanokaTypes){
		this.recipeOutput = output;
		this.requiredKanokaTypes = kanokaTypes;
	}

	@Override
	public boolean matches(ItemStack[] inputStacks){
		int[] tempKanokaTypes = new int[6];
		int i = 0;
		for(ItemStack stack : inputStacks){
			if(stack != null){
				if(stack.getItem() == Bionicle.kanokaDisc){
					tempKanokaTypes[i] = (int)ItemKanokaDisc.getKanokaNumber(stack)[1];
					i++;
				}else return false;
			}
		}
		int[] inputKanokaTypes = new int[i];
		for(int j = 0; j < i; j++){
			inputKanokaTypes[j] = tempKanokaTypes[j];
		}
		Arrays.sort(inputKanokaTypes);
		
		returnStacks = inputStacks;

		return Arrays.equals(requiredKanokaTypes, inputKanokaTypes);
	}

	@Override
	public ItemStack getOutput() {
		if (this.recipeOutput.getItem() instanceof ItemColoredMask){
			ItemColoredMask itemMask = (ItemColoredMask)this.recipeOutput.getItem();
			ItemStack output = this.recipeOutput.copy();
			itemMask.setColor(output, itemMask.DEFAULT_COLOR);
			return output;
		}
		return this.recipeOutput.copy();
	}

	@Override
	public ItemStack[] getRemainingItems() {
		for(int i = 0; i < returnStacks.length; i++){
			if(returnStacks[i] != null){
				returnStacks[i].stackSize--;
				if(returnStacks[i].stackSize <= 0){
					returnStacks[i] = null;
				}
			}
		}
		return returnStacks;
	}
}

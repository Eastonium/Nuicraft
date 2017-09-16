package eastonium.nuicraft.machine.maskForge.recipe;

import eastonium.nuicraft.NuiCraftItems;
import eastonium.nuicraft.kanohi.ItemColoredMask;
import eastonium.nuicraft.kanoka.ItemKanokaDisc;
import eastonium.nuicraft.machine.maskForge.TileInventoryMaskForge;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import scala.actors.threadpool.Arrays;

public class MaskRecipe implements IMFRecipe {

	private final ItemStack recipeOutput;
	private final int[] requiredKanokaTypes;
	private NonNullList<ItemStack> returnStacks = NonNullList.<ItemStack>withSize(TileInventoryMaskForge.INPUT_SLOTS_COUNT, ItemStack.EMPTY);;

	public MaskRecipe(ItemStack recipeOutput, int... requiredKanokaTypes){
		this.recipeOutput = recipeOutput;
		this.requiredKanokaTypes = requiredKanokaTypes;
	}

	@Override
	public boolean matches(NonNullList<ItemStack> inputStacks){
		int[] tempKanokaTypes = new int[TileInventoryMaskForge.INPUT_SLOTS_COUNT];
		int i = 0;
		for(ItemStack stack : inputStacks){
			if(!stack.isEmpty()){
				if(stack.getItem() == NuiCraftItems.kanoka_disc){
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
			ItemColoredMask itemMask = (ItemColoredMask)recipeOutput.getItem();
			ItemStack output = recipeOutput.copy();
			itemMask.setColor(output, itemMask.DEFAULT_COLOR);
			return output;
		}
		return recipeOutput.copy();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems() {
		for(int i = 0; i < returnStacks.size(); i++){
			if(!returnStacks.get(i).isEmpty()){
				returnStacks.get(i).shrink(1);;
				if(returnStacks.get(i).isEmpty()) returnStacks.remove(i);
			}
		}
		return returnStacks;
	}
	
	@Override
	public int getRecipeDuration() {
		return 1000;
	}
}
